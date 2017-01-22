package com.example.eric_lai.androidhelloworld.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.eric_lai.androidhelloworld.R;

import java.io.InputStream;
import java.net.MalformedURLException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sourceIv = (ImageView) findViewById(R.id.sourceIv);
        targetIv = (ImageView) findViewById(R.id.targetIv);
        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("image");
        Log.v(TAG, "imagePath: " + imagePath);

        final String targetPath = "https://github.com/LAIHAOTAO/laihaotao.github" +
                ".io/blob/master/images/merge.JPG?raw=true";

//        setPic(sourceIv, null, imagePath, FILE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(targetPath);
                    InputStream stream = url.openConnection().getInputStream();
                    setPic(targetIv, stream, null, STREAM);
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    private void setPic(ImageView mImageView, InputStream stream,
                        String mCurrentPhotoPath, int format) {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        if (format == FILE) {
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        } else {
            BitmapFactory.decodeStream(stream, null, bmOptions);
        }
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap;
        if (format == FILE) {
            bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        } else {
            bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
        }
        mImageView.setImageBitmap(bitmap);
    }

}
