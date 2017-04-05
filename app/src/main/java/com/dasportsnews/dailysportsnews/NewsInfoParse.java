package com.dasportsnews.dailysportsnews;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class NewsInfoParse {
    public static ArrayList<NewsInfo> getCards(URL url) throws IOException {
        Gson gson = new Gson();
        String jsonString = readFromUrl(url);
        NewsInfo[] newsInfos = gson.fromJson(jsonString, NewsInfo[].class);
        ArrayList<NewsInfo> al = new ArrayList<>();
        Collections.addAll(al, newsInfos);
        return al;
    }

    private static String readFromUrl(URL url) throws IOException {
        System.setProperty("http.agent", "Chrome");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));
        String jsonString = in.readLine();
        in.close();
        return jsonString;
    }
}