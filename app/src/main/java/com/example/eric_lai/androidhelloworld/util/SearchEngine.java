package com.example.eric_lai.androidhelloworld.util;

import com.alibaba.fastjson.JSON;

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
        if (gender.equals("Female")) {
            result.append("women");
            suffix = "dress";
        } else {
            result.append("men");
        }

        result.append(" in ").append(color).append(" ").append(style);

        return result.toString();
    }

    public static List<String> bingImageSearch(String keyWords, int count) {
        List<String> queryResultList = new ArrayList<>();
        final String headerNm = "Ocp-Apim-Subscription-Key";
        final String headerValue = "0dea6c8ac73f4835b1c8b33d8260a622";
        String url = "https://api.cognitive.microsoft.com/bing/v5.0/images/search" + "?" +
                "q=" + keyWords + "&" + "count=" + count;
        OkHttpHelper.Header header = new OkHttpHelper.Header(headerNm, headerValue);
        OkHttpHelper helper = new OkHttpHelper(header, url);
        String jsonResult = helper.start();
        System.out.println(jsonResult);
        Map map = JSON.parseObject(jsonResult, Map.class);
        extractURL(map, queryResultList);
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
