package com.example.eric_lai.androidhelloworld.activity.util;

import com.alibaba.fastjson.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchEngine {

    // test
    public static void main(String[] args) {

        System.out.println(keywordGen("Female", "formal", "blue"));
        System.out.println(bingImageSearch(
                keywordGen("Female", "formal", "blue"),
                2));
    }

    public static String keywordGen(String gender, String style, String color) {
        StringBuilder result = new StringBuilder();
        String suffix = "";
        if (gender == "Female") {
            result.append("women");
            suffix = "dress";
        } else {
            result.append("men");
        }

        result.append(" in " + color + " " + style);

        return result.toString();
    }

    public static List<String> bingImageSearch(String keyWords, int count) {
        List<String> queryResultList = new ArrayList<>();

//        HttpClient httpclient = HttpClients.createDefault();
        OkHttpClient httpclient = new OkHttpClient();
        try {
//            URIBuilder builder = new URIBuilder("https://api.cognitive.microsoft
// .com/bing/v5.0/images/search");
            String url = "https://api.cognitive.microsoft.com/bing/v5.0/images/search" + "?" +
                    "q=" + keyWords + "&" + "count=" + count;
//            builder.setParameter("q", keyWords);
//            builder.setParameter("count", "" + count);
//            URI uri = builder.build();
//            HttpGet request = new HttpGet(uri);
            String headString = "Ocp-Apim-Subscription-Key";
            String headValue = "0dea6c8ac73f4835b1c8b33d8260a622";
            Request request = new Request.Builder().header(headString, headValue).url(url).build();

            Response response = null;
            try {
                response = httpclient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ResponseBody entity = response.body();
            int i = 0;
//            if (entity != null) {
//                Map<String, Object> queryResultStructure =
//                        JSON.parseObject(EntityUtils.toString(entity), Map.class);
//                extractURL(queryResultStructure, queryResultList);
//            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return queryResultList;
        }

    private static void extractURL(Map<String, Object> queryResultStructure, List<String>
            queryResultList) {
        List resultList = (List) queryResultStructure.get("value");
        if (resultList != null && resultList.size() != 0) {
            for (Object object : resultList) {
                Map map = (Map) object;
                queryResultList.add((String) map.get("contentUrl"));
            }
        }
    }
}
