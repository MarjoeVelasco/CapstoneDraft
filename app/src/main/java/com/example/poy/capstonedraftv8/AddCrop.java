package com.example.poy.capstonedraftv8;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddCrop extends AppCompatActivity {

    myDbAdapter helper;

    Button add_crop,view_crop;
    RadioGroup crop;
    Spinner variety;
    EditText crop_name;
    RadioButton onion,rice;
    String crop_type="rice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);

        helper = new myDbAdapter(this);

        crop_name = (EditText)findViewById(R.id.crop_name);

        add_crop = (Button)findViewById(R.id.add_crop);
        view_crop = (Button)findViewById(R.id.button3);
        crop = (RadioGroup)findViewById(R.id.crop);
        variety = (Spinner)findViewById(R.id.variety);
        onion = (RadioButton)findViewById(R.id.onion);
        rice = (RadioButton)findViewById(R.id.rice);


        final List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Rc14");
        spinnerArray.add("Rc68");
        spinnerArray.add("Rc222 (Triple 2)");
        spinnerArray.add("Rc18 (Ala)");
        spinnerArray.add("Rc194 (Submarino)");
        spinnerArray.add("Rc68");
        spinnerArray.add("Rc238");
        spinnerArray.add("Rc216");
        spinnerArray.add("Rc160");
        spinnerArray.add("Rc300");


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        variety.setAdapter(adapter);

        crop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rice:
                        crop_type="rice";
                        spinnerArray.clear();
                        spinnerArray.add("Rc9");
                        spinnerArray.add("Rc14");
                        spinnerArray.add("Rc18 (Ala)");
                        spinnerArray.add("Rc68");
                        spinnerArray.add("Rc160");
                        spinnerArray.add("Rc194 (Submarino)");
                        spinnerArray.add("Rc216");
                        spinnerArray.add("Rc222 (Triple 2)");
                        spinnerArray.add("Rc238");
                        spinnerArray.add("Rc300");

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        variety.setAdapter(adapter);

                        break;

                    case R.id.onion:
                        crop_type="onion";
                        spinnerArray.clear();
                        spinnerArray.add("BGS 95 (F1 Hybrid)");
                        spinnerArray.add("Cal 120");
                        spinnerArray.add("Cal 202");
                        spinnerArray.add("Capri");
                        spinnerArray.add("CX-12");
                        spinnerArray.add("Grannex 429");
                        spinnerArray.add("Hybrid Red Orient");
                        spinnerArray.add("Liberty");
                        spinnerArray.add("Red Creole");
                        spinnerArray.add("Red Pinoy");
                        spinnerArray.add("Rio Bravo");
                        spinnerArray.add("Rio Hondo");
                        spinnerArray.add("Rio Raji Red");
                        spinnerArray.add("Rio Tinto");
                        spinnerArray.add("Super Pinoy");
                        spinnerArray.add("SuperX");
                        spinnerArray.add("Texas Grano");
                        spinnerArray.add("Yellow Grannex");



                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        variety.setAdapter(adapter);

                        break;




                }
            }

        });


        add_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crop_name_a = crop_name.getText().toString();
                String crop_variety = variety.getSelectedItem().toString();

                Cursor dbres = helper.getCropId(crop_name_a);
                if(dbres.getCount() >=1)
                {

                    Snackbar.make(findViewById(android.R.id.content),"Crop name already exists",Snackbar.LENGTH_SHORT).show();

                }
                else
                {
                    try
                    {
                        long result = helper.insertCrop(crop_name_a,crop_type,crop_variety);

                        if(result != -1)
                        {
                            Snackbar.make(findViewById(android.R.id.content),"Crop Added",Snackbar.LENGTH_SHORT).show();
                            crop_name.setText(null);
                            crop.check(R.id.rice);
                        }

                        else
                            Snackbar.make(findViewById(android.R.id.content),"Failed",Snackbar.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    {
                        Message.message(getApplicationContext(),e.getMessage());
                    }
                }




            }
        });




    }

    public void viewdata(View view)
    {
        String data = helper.getDataCrop();
        Message.message(this,data);
    }

    @Override
    public void onBackPressed() {
        Intent intent3 = new Intent(getApplicationContext(), Crop_menu.class);
        startActivity(intent3);
        finish();

    }
}
