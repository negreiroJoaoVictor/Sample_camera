package com.devnegreiro.sample_camera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.GnssAntennaInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    static final int REQUEST_IMAGE_CAPTURE = 1; // Unique identifier by the code to take a photo
    String mCurrentPhotoPath; //store the "path" of the file


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_TakePicture = findViewById(R.id.button_takePicture);


        btn_TakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(); //call a intent method
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //run when the user returns from the camera
        super.onActivityResult(requestCode, resultCode, data);

        ImageView imageView = (ImageView) findViewById(R.id.imageView_photo);

        if(requestCode == REQUEST_IMAGE_CAPTURE  && resultCode == RESULT_OK) { // check if that result is sucessful

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap); //show the bitmap image on-screen

        }

    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // create a new intent which will open a camera
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) { // check if the intent will work



            File photoFile = null; //Create the File where the photo should go
            try {
                photoFile = createImageFile();

            } catch (IOException e) {
                e.printStackTrace(); //Error occurred while creating the File
            }

            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        "com.devnegreiro.android.fileprovider",
                        photoFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE); //result is the photo which is taken
            }

        }

    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); ///create a string and initialises it with a timestamp
        String imageFileName = "JPEG_" + timeStamp + "_"; //Concatenate the strings
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir); //prefix,suffix,directory

       //Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath(); //this stores the filepath  in the variable
        return image;



    }

}