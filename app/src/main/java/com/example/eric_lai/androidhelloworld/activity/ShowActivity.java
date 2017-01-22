package com.example.eric_lai.androidhelloworld.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.eric_lai.androidhelloworld.R;
import com.example.eric_lai.androidhelloworld.util.ClothesMatch;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by grey on 17/1/21.
 */

public class ShowActivity extends AppCompatActivity {

    private static final String TAG = "ShowActivity";

    private static final int FILE = 1;
    private static final int STREAM = 2;

    private ImageView sourceIv;
    private ImageView targetIv;
    private ProgressBar progressBar;
    private Button againBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sourceIv = (ImageView) findViewById(R.id.sourceIv);
        targetIv = (ImageView) findViewById(R.id.targetIv);
        againBtn = (Button) findViewById(R.id.again);
        Intent intent = getIntent();
        final String imagePath = intent.getStringExtra("image");
        Log.v(TAG, "imagePath: " + imagePath);
        final String gender = intent.getStringExtra("gender");
        final String[] targetPath = new String[1];
        final Thread getRemotePath = new Thread(new Runnable() {
            @Override
            public void run() {
                targetPath[0] = ClothesMatch.getMatchedURL(gender, imagePath);
                Log.v(TAG, "targetPath: " + targetPath[0]);
            }
        });
        getRemotePath.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getRemotePath.join();
                } catch (InterruptedException e) {
                    Log.e(TAG, "thread error !!!");
                    e.printStackTrace();
                }
                final Bitmap bmp1 = BitmapFactory.decodeFile(imagePath);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sourceIv.setImageBitmap(bmp1);
                    }
                });
                try {
                    URL url = new URL(targetPath[0]);
                    final InputStream stream = url.openConnection().getInputStream();
                    final Bitmap bmp = BitmapFactory.decodeStream(stream);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            targetIv.setImageBitmap(bmp);
                            progressBar.setVisibility(View.INVISIBLE);
                            againBtn.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void again(View view) {
        Intent intent = new Intent(ShowActivity.this, MainActivity.class);
        intent.setClass(ShowActivity.this, MainActivity.class);
        finish();
        ShowActivity.this.startActivity(intent);
    }

}
