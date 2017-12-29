package com.example.root.sdsu_gmap;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import ge.sanapps.jsonparser.JSONParser;

/**
 * Created by root on 11/17/17.
 */

public class NetworkCommunicator extends AsyncTask<Void, Void, HashMap<String, Object>> {

    private String url = "";
    private String urlParameters = "";

    NetworkCommunicator(String url, ArrayList<String> parameters) {
        this.url = url;

        urlParameters = TextUtils.join("&", parameters);
    }

    @Override
    protected HashMap<String, Object> doInBackground(Void... params) {

        try {

            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            URL LoginURL = new URL(url);

            //TODO: Convert this to https (HttpsURLConnection)
            HttpURLConnection conn = (HttpURLConnection) LoginURL.openConnection();

            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postData.length));

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);


            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder content = new StringBuilder();

            String line;
            while((line = in.readLine()) != null)
            {
                content.append(line);
            }

            return JSONParser.Parse(content.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(final HashMap<String, Object>  success) {
        super.onPostExecute(success);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}