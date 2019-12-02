package com.foxhis.sendip.sendip.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetRemoteIp {

    public static String getV4Ip() {
        String ip = "";
        StringBuilder inputLine = new StringBuilder();
        String read = "";
        BufferedReader in = null;
        for (AccessWebsite website:AccessWebsite.values()) {
            try {
                URL url = new URL(website.getName());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                while ((read = in.readLine()) != null) {
                    inputLine.append(read + "\r\n");
                }
                break;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
       Pattern p = Pattern.compile("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            ip = m.group();
        }
        return ip;
    }

}
