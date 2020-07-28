package com.devnegreiro.sample_camera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    static final int REQUEST_IMAGE_CAPTURE = 1; // Unique identifier by the code to take a photo


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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //run when the user returns from the camera
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
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE); //result is the photo which is taken

        }

    }

}