package com.example.finalprojectsunday;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {

    Button button;
    ImageView tvPlaceholder;
    ImageView imgSampleOne;
    ImageView imgSampleTwo;
    ImageView imgSampleThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvPlaceholder=findViewById(R.id.imageView);
        imgSampleOne=(ImageView) findViewById(R.id.imgSampleOne);
        imgSampleTwo=(ImageView) findViewById(R.id.imgSampleTwo);
        imgSampleThree=(ImageView) findViewById(R.id.imgSampleThree);

        Button button = (Button) findViewById(R.id.captureImageFab);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity2.this, ModelActivity.class);
               startActivity(myIntent);
                // Do something in response to button click
            }
        });

        imgSampleOne.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            public void onClick(View v) {


                imgSampleOne.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) imgSampleOne.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                tvPlaceholder.setImageBitmap(bitmap);
            }
        });

        imgSampleTwo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            public void onClick(View v) {

                imgSampleTwo.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) imgSampleTwo.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                tvPlaceholder.setImageBitmap(bitmap);
            }
        });
//
        imgSampleThree.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            public void onClick(View v) {


                imgSampleThree.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) imgSampleThree.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                tvPlaceholder.setImageBitmap(bitmap);
            }
        });







    }
}