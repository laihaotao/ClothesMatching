package com.example.eric_lai.androidhelloworld.activity.activity;

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

/**
 * Created by grey on 17/1/21.
 */

public class ShowActivity extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        imageView = (ImageView) findViewById(R.id.imageView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Intent intent=getIntent();
        String imagePath = intent.getStringExtra("image");
        Log.v("wait activity", "imagePath: " + imagePath);
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bm);
        progressBar.setVisibility(View.INVISIBLE);
    }


}
