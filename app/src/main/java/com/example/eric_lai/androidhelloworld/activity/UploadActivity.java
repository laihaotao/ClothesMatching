package com.example.eric_lai.androidhelloworld.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.example.eric_lai.androidhelloworld.R;

public class UploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
    }


    public void cancer(View view) {
    Intent intent = new Intent(UploadActivity.this, MainActivity.class);
        intent.setClass(UploadActivity.this, MainActivity.class);
    UploadActivity.this.startActivity(intent);

}


    public void confirm(View view) {
//        Intent intent = new Intent(UploadActivity.this, UploadActivity.class);
//        intent.setClass(UploadActivity.this,UploadActivity.class);
//        UploadActivity.this.startActivity(intent);
    }


}

