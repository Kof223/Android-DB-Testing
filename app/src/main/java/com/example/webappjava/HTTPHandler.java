package com.example.webappjava;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HTTPHandler {
    /**
     * Created by Ravi Tamada on 01/09/16.
     * www.androidhive.info
     */
    private static final String TAG = HTTPHandler.class.getSimpleName();

    public HTTPHandler() {
    }

    public String makeServiceCall(String reqUrl, String method, String param) {
        String response = null;
        try {
            switch (method) {
                case "GET": {
                    URL url;
                    if (param.isEmpty()) {
                        url = new URL(reqUrl);
                    } else {
                        url = new URL(reqUrl + "/" + param);
                    }
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    InputStream in;
                    conn.setRequestMethod("GET");
                    // read the response
                    in = new BufferedInputStream(conn.getInputStream());
                    response = convertStreamToString(in);
                    break;
                }
                case "POST": {
                    URL url = new URL(reqUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    InputStream in;
                    conn.setRequestMethod("POST");
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(param);

                    os.flush();
                    os.close();

                    in = new BufferedInputStream(conn.getInputStream());
                    response = convertStreamToString(in);
                    //response = String.valueOf(conn.getResponseCode());
                    break;
                }
                case "DELETE": {
                    URL url = new URL(reqUrl + "/" + param);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    InputStream in;
                    conn.setRequestMethod("DELETE");
                    in = new BufferedInputStream(conn.getInputStream());
                    response = convertStreamToString(in);
                    //response = String.valueOf(conn.getResponseCode());
                    break;
                }
                default:
                    response = "Error! Not a valid HTTP Method";
                    break;
            }

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
