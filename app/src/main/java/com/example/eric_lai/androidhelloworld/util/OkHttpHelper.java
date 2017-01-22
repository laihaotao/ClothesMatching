package com.example.eric_lai.androidhelloworld.util;

import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by ERIC_LAI on 2017-01-21.
 */
public class OkHttpHelper {

    private static final String TAG = "OkHttpHelper";
    private Header header;
    private String url;

    public static class Header {
        String name;
        String value;

        public Header(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    public OkHttpHelper(Header header, String url) {
        this.header = header;
        this.url = url;
    }

    public String start() {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        if (this.header != null) {
            builder.header(this.header.name, this.header.value);
        }
        builder.url(this.url);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                Log.e(TAG, "response error !!!!!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
