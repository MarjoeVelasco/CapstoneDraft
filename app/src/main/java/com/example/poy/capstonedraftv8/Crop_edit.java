package com.example.poy.capstonedraftv8;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Crop_edit extends AppCompatActivity {

    myDbAdapter helper;
    Button update;
    RadioGroup crop;
    Spinner variety;
    EditText crop_name;
    RadioButton onion,rice;

    String crop_type="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_edit);

        helper = new myDbAdapter(this);

        update = (Button)findViewById(R.id.update);
        crop = (RadioGroup)findViewById(R.id.crop);

        variety = (Spinner)findViewById(R.id.variety);

        onion = (RadioButton)findViewById(R.id.onion);
        rice = (RadioButton)findViewById(R.id.rice);

        crop_name = (EditText)findViewById(R.id.crop_name);

        Intent intent = getIntent();

        final String crop_id= (String) intent.getSerializableExtra("crop_id");

        final List<String> spinnerArray =  new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        onion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });

        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        String crop_name2="";
        String crop_type2="";
        String variety2="";
        Cursor dbres = helper.getCropData(crop_id);
        while (dbres.moveToNext()) {

            crop_name2 = String.format(dbres.getString(1));
            crop_type2 = String.format(dbres.getString(2));
            variety2 = String.format(dbres.getString(3));


        }

        crop_name.setText(crop_name2);

        switch(crop_type2){

            case "rice":
                crop_type="rice";
                spinnerArray.clear();
                spinnerArray.add(variety2);
                rice.setChecked(true);

                String[] crop_rice = new String[10];
                crop_rice[0]="Rc9";
                crop_rice[1]="Rc14";
                crop_rice[2]="Rc18 (Ala)";
                crop_rice[3]="Rc68";
                crop_rice[4]="Rc160";
                crop_rice[5]="Rc194 (Submarino)";
                crop_rice[6]="Rc216";
                crop_rice[7]="Rc222 (Triple 2)";
                crop_rice[8]="Rc238";
                crop_rice[9]="Rc300";


                for(int i=0;i<crop_rice.length;i++)
                {
                    if(!variety2.equalsIgnoreCase(crop_rice[i]))
                    {
                        spinnerArray.add(crop_rice[i]);
                    }
                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                variety.setAdapter(adapter);

                break;

            case "onion":
                crop_type="onion";
                spinnerArray.clear();
                spinnerArray.add(variety2);
                onion.setChecked(true);

                String[] crop_onion = new String[18];
                crop_onion[0]="BGS 95 (F1 Hybrid)";
                crop_onion[1]="Cal 120";
                crop_onion[2]="Cal 202";
                crop_onion[3]="Capri";
                crop_onion[4]="CX-12";
                crop_onion[5]="Grannex 429";
                crop_onion[6]="Hybrid Red Orient";
                crop_onion[7]="Liberty";
                crop_onion[8]="Red Creole";
                crop_onion[9]="Red Pinoy";
                crop_onion[10]="Rio Bravo";
                crop_onion[11]="Rio Hondo";
                crop_onion[12]="Rio Raji Red";
                crop_onion[13]="Rio Tinto";
                crop_onion[14]="Super Pinoy";
                crop_onion[15]="SuperX";
                crop_onion[16]="Texas Grano";
                crop_onion[17]="Yellow Grannex";

                for(int i=0;i<crop_onion.length;i++)
                {
                    if(!variety2.equalsIgnoreCase(crop_onion[i]))
                    {
                        spinnerArray.add(crop_onion[i]);
                    }
                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                variety.setAdapter(adapter);

                break;


        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crop_name_a = crop_name.getText().toString();
                String crop_variety = variety.getSelectedItem().toString();

                try
                {
                    long result = helper.updateCrop(crop_id,crop_name_a,crop_type,crop_variety);

                    if(result != -1)
                    {
                        Intent intent = new Intent(getApplicationContext(),Crop_pane.class);
                        intent.putExtra("crop_type",crop_type);
                        intent.putExtra("status","success");
                        startActivity(intent);

                    }

                    else
                        Snackbar.make(findViewById(android.R.id.content),"Updating Failed",Snackbar.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Message.message(getApplicationContext(),e.getMessage());
                }
            }
        });





    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),Crop_pane.class);
        intent.putExtra("crop_type",crop_type);
        intent.putExtra("status","failed");
        startActivity(intent);
        finish();
    }
}
