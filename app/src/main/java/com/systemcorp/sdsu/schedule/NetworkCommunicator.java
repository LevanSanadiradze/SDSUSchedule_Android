package com.systemcorp.sdsu.schedule;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ge.sanapps.jsonparser.JSONParser;

/**
 * Created by root on 11/17/17.
 */

public class NetworkCommunicator extends AsyncTask<Void, Void, Pair<Object, CookieManager>> {

    private String url = "";
    private String urlParameters = "";
    private String Cookies = "";

    public NetworkCommunicator(String url, ArrayList<String> parameters, String Cookies) {
        this.url = url;
        this.Cookies = Cookies;

        if (!parameters.isEmpty())
            urlParameters = TextUtils.join("&", parameters);
    }

    @Override
    protected Pair<Object, CookieManager> doInBackground(Void... params) {

        try {

            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            URL LoginURL = new URL(url);

            //TODO: Convert this to https (HttpsURLConnection)
            HttpURLConnection conn = (HttpURLConnection) LoginURL.openConnection();

            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(Constants.CONNECTION_TIMEOUT_MS);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
            conn.setRequestProperty("Cookie", Cookies);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder content = new StringBuilder();

            String line;
            while ((line = in.readLine()) != null) {
                content.append(line);
            }

            CookieManager cookieManager = new CookieManager();

            Map<String, List<String>> headerFields = conn.getHeaderFields();
            List<String> cookiesHeader = headerFields.get("Set-Cookie");
            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }

            return new Pair<>((Object) JSONParser.Parse(content.toString()), cookieManager);

        } catch (SocketTimeoutException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(final Pair<Object, CookieManager> success) {
        super.onPostExecute(success);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}