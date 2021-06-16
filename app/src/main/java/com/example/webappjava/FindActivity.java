package com.example.webappjava;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class FindActivity extends AppCompatActivity {

    private static final String url = "http://192.168.0.21/index.php/data";
    private TextView header, info;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        // Get the Intent that started this activity and extract the string
        String name = getIntent().getStringExtra("name");

        HTTPHandler sh = new HTTPHandler();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // String passed from the url
                String response = sh.makeServiceCall(url, "GET", name);

                Log.e(TAG, "Response from url: " + response);

//                if (jsonStr != null) {
//                    try {
//                        JSONObject jsonObj = new JSONObject(jsonStr);
//
//                        String name = jsonObj.getString("name");
//                        String age = jsonObj.getString("age");
//
//                        result = "Name: " + name + "\nAge: " + age;
//
//                    } catch (final JSONException e) {
//                        Log.e(TAG, "Json parsing error: " + e.getMessage());
//
//                    }
//                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        header = (TextView) findViewById(R.id.header);
                        info = (TextView) findViewById(R.id.info);
                        WebView webView = (WebView) findViewById(R.id.webView);

                        if (response == null) {
                            webView.setVisibility(View.GONE);
                            header.setText("Person Not Found");
                            info.setText("");
                        } else {
                            webView.loadDataWithBaseURL(url, response, "text/html", "base64", null);
                            webView.setVisibility(View.VISIBLE);
                            //info.setText(result);
                        }
                    }
                });
            }
        });

    }
}