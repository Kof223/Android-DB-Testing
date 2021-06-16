package com.example.webappjava;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class ViewAllActivity extends AppCompatActivity {

    private static final String url = "http://192.168.0.21/index.php/data";
    ArrayList<HashMap<String, String>> contactList;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        contactList = new ArrayList<>();
        HTTPHandler sh = new HTTPHandler();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // String passed from the url
                String response = sh.makeServiceCall(url, "GET", "");

                Log.e(TAG, "Response from url: " + response);

//                if (jsonStr != null) {
//                    try {
////                        JSONObject jsonObj = new JSONObject(jsonStr);
////
////                        // Getting JSON Array node
//                        JSONArray contacts = new JSONArray(response);//jsonObj.getJSONArray("data");
//
//                        // looping through All Contacts
//                        for (int i = 0; i < contacts.length(); i++) {
//                            JSONObject c = contacts.getJSONObject(i);
//
//                            String name = c.getString("name");
//                            String age = c.getString("age");
//
//                            // tmp hash map for single contact
//                            HashMap<String, String> contact = new HashMap<>();
//
//                            // adding each child node to HashMap key => value
//                            contact.put("name", name);
//                            contact.put("age", age);
//
//                            // adding contact to contact list
//                            contactList.add(contact);
//                        }
//                    } catch (final JSONException e) {
//                        Log.e(TAG, "Json parsing error: " + e.getMessage());
//
//                    }
//                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        WebView webView = (WebView) findViewById(R.id.webView);
                        webView.loadDataWithBaseURL(url, response, "text/html", "base64", null);

//                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//                        CustomAdapter adapter = new CustomAdapter(contactList);
//                        recyclerView.setHasFixedSize(true);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(ViewAllActivity.this));
//                        recyclerView.setAdapter(adapter);

                    }
                });
            }
        });

    }
}