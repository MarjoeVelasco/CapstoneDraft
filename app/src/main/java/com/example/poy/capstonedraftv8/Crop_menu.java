package com.example.poy.capstonedraftv8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Crop_menu extends AppCompatActivity {

    Button add_crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_menu);

        add_crop = (Button)findViewById(R.id.add_crop);



        add_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Crop_menu.this,AddCrop.class);
                startActivity(intent);
                finish();

            }
        });


    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Crop_menu.this,Menu.class);
        startActivity(intent);
        finish();
    }


}
