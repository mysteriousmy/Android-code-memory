package com.zte.reader.net;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2018/3/29.
 */

public class ConnectionToNet {

    private HttpURLConnection connection;

    public static boolean login(String phone, String password) throws MalformedURLException, IOException {
        URL url = new URL("http://10.0.2.2:8080/tester/login?phone=" + phone + "&password=" + password);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        connection.connect();



        InputStream input = connection.getInputStream();

        InputStreamReader isr = new InputStreamReader(input);

        BufferedReader reader = new BufferedReader(isr);

        String back = reader.readLine();

        Log.d("debug","==========back = "+back);

        input.close();

        connection.disconnect();

        return false;
    }

}
