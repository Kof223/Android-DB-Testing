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

public class DeleteActivity extends AppCompatActivity {

    private static final String url = "http://192.168.0.21/index.php/data";
    private TextView info;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        // Get the Intent that started this activity and extract the string
        String name = getIntent().getStringExtra("name");

        HTTPHandler sh = new HTTPHandler();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // String passed from the url
                String response = sh.makeServiceCall(url, "DELETE", name);

                Log.e(TAG, "Response from url: " + response);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        webView = (WebView) findViewById(R.id.webView);

                        if (response == null) {
                            info = (TextView) findViewById(R.id.response);
                            info.setText("Did Not Find Person");
                            webView.setVisibility(View.GONE);
                        } else {
                            webView.loadDataWithBaseURL(url, response, "text/html", "base64", null);
                            webView.setVisibility(View.VISIBLE);
                            //                            String postData = "DELETE";
//                            webView.postUrl(url+"/"+name, postData.getBytes());
//                            info.setText("Delete Successful");
                        }
                    }
                });
            }
        });
    }
}