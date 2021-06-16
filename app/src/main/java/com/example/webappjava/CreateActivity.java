package com.example.webappjava;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class CreateActivity extends AppCompatActivity {

    private static final String url = "http://192.168.0.21/index.php/data";
    private TextView nameInput, ageInput;
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setVisibility(View.GONE);
        nameInput = (TextView) findViewById(R.id.nameInput);
        ageInput = (TextView) findViewById(R.id.ageInput);

        final Button send = findViewById(R.id.createSendButton);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameInput.getText().toString().trim();
                String age = ageInput.getText().toString().trim();

                Snackbar msg = Snackbar.make(findViewById(R.id.createSendButton), "",
                        Snackbar.LENGTH_SHORT);
                View view = msg.getView();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                params.gravity = Gravity.TOP;
                view.setLayoutParams(params);

                if (name.equals("") || age.equals("")) {
                    // Notify to have both inputted
                    msg.setText("Please input both a name and age");
                    msg.show();
                } else {
                    HTTPHandler sh = new HTTPHandler();
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Handler handler = new Handler(Looper.getMainLooper());

                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject();
                                json.put("name", name);
                                json.put("age", age);
                                // String passed from the url
                                response = sh.makeServiceCall(url, "POST", json.toString());

                                Log.e(TAG, "Response from url: " + response);

                            } catch (final JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //UI Thread work here
                                    if (response == null) {
                                        // Pop-up for okay
                                        msg.setText("Failed to Create");
                                        msg.show();
                                    } else {
                                        // Pop-up for fail
//                                        msg.setText("Created Successfully");
//                                        msg.show();
                                        send.setVisibility(View.GONE);
                                        webView.loadDataWithBaseURL(url, response, "text/html", "base64", null);
                                        webView.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}