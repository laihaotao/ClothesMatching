package com.example.eric_lai.androidhelloworld.util;

import android.graphics.Color;

import java.util.List;

/**
 * Created by m.ding on 2017-01-21.
 */
public class ClothesMatch {

    public static String getMatchedURL(String gender, String filename) {
        String[] tags = SourcePrediction.getStyleAndColor(filename);
        // tag[0]---style, tag[1]---color
        int color = Color.parseColor(tags[1]);
        String matchedColor = matchColor(color);
        String keyword = SearchEngine.keywordGen(gender, tags[0], matchedColor);
        List<String> result = SearchEngine.bingImageSearch(keyword, 3);
        return result.get(2);
    }

    private static String matchColor(int color) {
        String matchingColorStr;
        switch (color) {
            case Color.BLUE:
                matchingColorStr = "ryan";
                break;
            case Color.CYAN:
                matchingColorStr = "blue";
                break;
            case Color.DKGRAY:
                matchingColorStr = "black";
                break;
            case Color.LTGRAY:
                matchingColorStr = "black";
                break;
            case Color.GRAY:
                matchingColorStr = "black";
                break;
            case Color.RED:
                matchingColorStr = "magenta";
                break;
            case Color.TRANSPARENT:
                matchingColorStr = "gray";
                break;
            case Color.WHITE:
                matchingColorStr = "black";
                break;
            default:
                matchingColorStr = "white";
        }
        return matchingColorStr;
    }


}
