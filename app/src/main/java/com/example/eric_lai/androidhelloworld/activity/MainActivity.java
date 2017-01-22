package com.example.eric_lai.androidhelloworld.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import com.example.eric_lai.androidhelloworld.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.eric_lai.androidhelloworld.R.id.spinner;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_UPLOAD = 2;
    private String mCurrentPhotoPath;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent(MainActivity.this, ShowActivity.class);
        String imagePath = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                intent.putExtra("image", mCurrentPhotoPath);
                intent.putExtra("previous", this.getResources().getInteger(R.integer.TAKE_PHOTO));
                String spinnerValue = (String) spinner.getSelectedItem();
                intent.putExtra("gender", spinnerValue);
            } else if (requestCode == REQUEST_UPLOAD) {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    imagePath = c.getString(columnIndex);
                    String spinnerValue = (String) spinner.getSelectedItem();
                    intent.putExtra("image", imagePath);
                    intent.putExtra("previous", this.getResources().getInteger(R.integer.UPLOAD_PHOTO));
                    intent.putExtra("gender", spinnerValue);
                    c.close();
                }
            }
            intent.setClass(MainActivity.this, ShowActivity.class);
            finish();
            MainActivity.this.startActivity(intent);
        }

    }

    public void uploadPhoto(View view) {
        String spinnerValue = (String) spinner.getSelectedItem();
        if (spinnerValue.equals("Gender")) {
            new AlertDialog.Builder(this).setTitle("Message").setMessage("Please choose gender !")
                    .setPositiveButton("Comfirm", null).show();

        } else {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_UPLOAD);
        }
    }

    public void takePhoto(View view) {
        String spinnerValue = (String) spinner.getSelectedItem();
        if (spinnerValue.equals("Gender")) {
            new AlertDialog.Builder(this).setTitle("Message").setMessage("Please choose gender !")
                    .setPositiveButton("Comfirm", null).show();

        } else {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
