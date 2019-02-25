package com.example.poy.capstonedraftv8;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;


public class Event_pane2 extends AppCompatActivity {

    myDbAdapter helper;
    TextView pane,pane2,r1,time,desc;
    TextView btn,task_title;
    Button save,del;
    ImageButton landPrep,crop,care,pest,harvest,others;
    Spinner desc_choice,crop_name;
    RadioGroup crop_type;
    RadioButton onion,rice;

    String id="";
    String color="";
    String event_name="";
    String event_date="";
    String event_time="";

    String crop_variety_checker="";

    String event_date_start="";
    String event_date_end="";

    int checker=1;
    int icon=1;

    int flag;

    public static String title;
    public static String start_date;
    public static String end_date;
    public static String start_time;

    public static int mPickedColor ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_event_pane2);

        helper = new myDbAdapter(this);
        save=(Button)findViewById(R.id.save2);
        pane = (TextView) findViewById(R.id.textView);
        pane2 = (TextView) findViewById(R.id.textView7);
        time = (TextView) findViewById(R.id.time);
        r1 = (TextView) findViewById(R.id.r1);
        desc = (TextView) findViewById(R.id.desc2);

        task_title = (TextView)findViewById(R.id.task_title);

        landPrep = (ImageButton) findViewById(R.id.landPrep);
        crop = (ImageButton) findViewById(R.id.crop);
        care = (ImageButton) findViewById(R.id.care);
        pest = (ImageButton) findViewById(R.id.pest);
        harvest = (ImageButton) findViewById(R.id.harvest);
        others = (ImageButton) findViewById(R.id.others);

        desc_choice = (Spinner)findViewById(R.id.desc_choice);
        crop_name = (Spinner)findViewById(R.id.crop_name);

        crop_type = (RadioGroup)findViewById(R.id.crop_type);

        onion = (RadioButton)findViewById(R.id.onion);
        rice = (RadioButton)findViewById(R.id.rice);

        Intent intent = getIntent();
//Get the USERNAME passed from IntentExampleActivity
        String event_id = (String) intent.getSerializableExtra("event_id");
        String date_id = (String)intent.getSerializableExtra("date_id");


        final List<String> spinnerArrayCrop =  new ArrayList<String>();

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArrayCrop);


        Cursor dbres = helper.getEventInfo(date_id);

        pane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(),"Date Picker");
            }
        });

        pane2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragmentEnd();
                newFragment.show(getFragmentManager(),"Date Picker");
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get a GridView object from ColorPicker class
                GridView gv = (GridView) ColorPicker.getColorPicker(Event_pane2.this);



                // Initialize a new AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(Event_pane2.this,R.style.MyAlertDialogTheme);

                // Set the alert dialog content to GridView (color picker)
                builder.setTitle(Html.fromHtml("<font color='#000000'>Choose the color</font>"));
                builder.setView(gv);

                // Initialize a new AlertDialog object
                final AlertDialog dialog = builder.create();

                // Show the color picker window
                dialog.show();

                // Set the color picker dialog size


                // Set an item click listener for GridView widget
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Get the pickedColor from AdapterView
                        mPickedColor = (int) parent.getItemAtPosition(position);

                        // Set the layout background color as picked color
                        r1.setBackgroundColor(mPickedColor);

                        // close the color picker
                        dialog.dismiss();
                    }
                });
            }
        });

        final List<String> spinnerArray =  new ArrayList<String>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        desc_choice.setAdapter(adapter);

        desc.setVisibility(View.GONE);
        desc_choice.setVisibility(View.VISIBLE);

        landPrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon=1;

                task_title.setText("Land Preparation");
                spinnerArray.clear();
                spinnerArray.add("Plowing");
                spinnerArray.add("Harrowing");
                spinnerArray.add("Levelling");

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                desc_choice.setAdapter(adapter);

                desc.setVisibility(View.GONE);
                desc_choice.setVisibility(View.VISIBLE);

                checker=1;

            }
        });

        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon=2;
                task_title.setText("Crop Operation");
                spinnerArray.clear();
                spinnerArray.add("Transplanting");
                spinnerArray.add("Replanting");
                spinnerArray.add("Pulling of Seedlings");

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                desc_choice.setAdapter(adapter);


                desc.setVisibility(View.GONE);
                desc_choice.setVisibility(View.VISIBLE);

                checker=1;

            }
        });

        care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon=3;
                task_title.setText("Water and Maintenance");
                spinnerArray.clear();
                spinnerArray.add("Irrigation");
                spinnerArray.add("Water Pump");
                spinnerArray.add("Weeding");
                spinnerArray.add("Equipment Repair");


                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                desc_choice.setAdapter(adapter);

                desc.setVisibility(View.GONE);
                desc_choice.setVisibility(View.VISIBLE);

                checker=1;

            }
        });

        pest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon=4;
                task_title.setText("Pest and Disease Control");
                spinnerArray.clear();
                spinnerArray.add("Molluscicide");
                spinnerArray.add("Pesticide");
                spinnerArray.add("Herbicide");
                spinnerArray.add("Fungicide");


                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                desc_choice.setAdapter(adapter);

                desc.setVisibility(View.GONE);
                desc_choice.setVisibility(View.VISIBLE);

                checker=1;

            }
        });

        harvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon=5;
                task_title.setText("Harvest Management");
                spinnerArray.clear();
                spinnerArray.add("Reaping");
                spinnerArray.add("Threshing");
                spinnerArray.add("Transportation");
                spinnerArray.add("Drying");
                spinnerArray.add("Harvesting");


                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                desc_choice.setAdapter(adapter);

                desc.setVisibility(View.GONE);
                desc_choice.setVisibility(View.VISIBLE);

                checker=1;

            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon=6;
                task_title.setText("Others");


                desc.setVisibility(View.VISIBLE);
                desc_choice.setVisibility(View.GONE);

                checker=0;

            }
        });


        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerArrayCrop.clear();
                String crop_type_choice="rice";
                Cursor dbres15 = helper.getAllDataCropSpes(crop_type_choice);
                while (dbres15.moveToNext()) {

                    String crop = String.format(dbres15.getString(1));
                    String variety2 = String.format(dbres15.getString(3));


                    spinnerArrayCrop.add(crop + " (" + variety2 + ")");


                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    crop_name.setAdapter(adapter2);


                }
            }
        });

        onion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerArrayCrop.clear();
                String crop_type_choice2="onion";
                Cursor dbres16 = helper.getAllDataCropSpes(crop_type_choice2);
                while (dbres16.moveToNext()) {

                    String crop = String.format(dbres16.getString(1));
                    String variety2 = String.format(dbres16.getString(3));


                    spinnerArrayCrop.add(crop + " (" + variety2 + ")");


                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    crop_name.setAdapter(adapter2);


                }

            }
        });







        if(dbres.getCount() == 1)
        {
            flag=1;

            int icon_choice=0;
            String crop_type="";
            String crop_name2="";
            String variety="";
            String crop_id="";
            while (dbres.moveToNext()){

                id= String.format(dbres.getString(6));
                color = String.format(dbres.getString(2));
                event_name = String.format(dbres.getString(3));
                event_date = String.format(dbres.getString(4));
                event_time = String.format(dbres.getString(5));
                icon_choice=Integer.parseInt(String.format(dbres.getString(7)));
                crop_id=String.format(dbres.getString(8));

                Cursor dbres8 = helper.getCropData(crop_id);
                while (dbres8.moveToNext()) {

                    crop_type=String.format(dbres8.getString(2));
                    crop_name2=String.format(dbres8.getString(1));
                    variety=String.format(dbres8.getString(3));

                }

                crop_variety_checker=crop_name2 + " (" + variety + ")";
                spinnerArrayCrop.add(crop_name2 + " (" + variety + ")");


            }




            Message.message(getApplicationContext(),crop_type);

            if(crop_type.equalsIgnoreCase("rice"))
            {
                rice.setChecked(true);
                onion.setChecked(false);
                String crop_type_choice="rice";
                Cursor dbres12 = helper.getAllDataCropSpes(crop_type_choice);
                while (dbres12.moveToNext()) {

                    String crop = String.format(dbres12.getString(1));
                    String variety2 = String.format(dbres12.getString(3));

                    if(!crop_variety_checker.equals(crop + " (" + variety2 + ")")) {
                        spinnerArrayCrop.add(crop + " (" + variety2 + ")");
                    }

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    crop_name.setAdapter(adapter2);


                }
            }
            else if(crop_type.equalsIgnoreCase("onion"))
            {
                rice.setChecked(false);
                onion.setChecked(true);

                String crop_type_choice="onion";

                Cursor dbres17 = helper.getAllDataCropSpes(crop_type_choice);
                while (dbres17.moveToNext()) {

                    String crop = String.format(dbres17.getString(1));
                    String variety2 = String.format(dbres17.getString(3));

                    if(!crop_variety_checker.equals(crop + " (" + variety2 + ")")) {
                        spinnerArrayCrop.add(crop + " (" + variety2 + ")");
                    }

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    crop_name.setAdapter(adapter2);


                }


            }
            pane.setText(event_date);
            r1.setBackgroundColor(Integer.parseInt(color));
            mPickedColor=Integer.parseInt(color);
            pane2.setText(event_date);
            time.setText(event_time);
            desc.setText(event_name);

            Message.message(getApplicationContext(),icon_choice+"");

            spinnerArray.add(event_name);
            switch (icon_choice)
            {
                case 1:
                    icon=1;
                    checker=1;
                    task_title.setText("Land Preparation");
                    String[] event1 = new String[3];
                    event1[0]="Plowing";
                    event1[1]="Harrowing";
                    event1[2]="Levelling";
                    for(int i=0;i<event1.length;i++)
                    {
                        if(!event_name.equalsIgnoreCase(event1[i]))
                        {
                            spinnerArray.add(event1[i]);
                        }
                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);
                    break;

                case 2:
                    icon=2;
                    checker=1;
                    task_title.setText("Crop Operation");
                    String[] event2 = new String[3];
                    event2[0]="Transplanting";
                    event2[1]="Replanting";
                    event2[2]="Pulling of Seedlings";
                    for(int i=0;i<event2.length;i++)
                    {
                        if(!event_name.equalsIgnoreCase(event2[i]))
                        {
                            spinnerArray.add(event2[i]);
                        }
                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);
                    break;

                case 3:
                    icon=3;
                    checker=1;
                    task_title.setText("Water and Maintenance");
                    String[] event3 = new String[4];
                    event3[0]="Irrigation";
                    event3[1]="Water";
                    event3[2]="Weeding";
                    event3[3]="Equipment Repair";
                    for(int i=0;i<event3.length;i++)
                    {
                        if(!event_name.equalsIgnoreCase(event3[i]))
                        {
                            spinnerArray.add(event3[i]);
                        }
                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);
                    break;

                case 4:
                    icon=4;
                    checker=1;
                    task_title.setText("Pest and Disease Control");
                    String[] event4 = new String[4];
                    event4[0]="Molluscicide";
                    event4[1]="Pesticide";
                    event4[2]="Herbicide";
                    event4[3]="Fungicide";
                    for(int i=0;i< event4.length;i++)
                    {
                        String check = event4[i];
                        if(!check.equals(event_name))
                        {
                            spinnerArray.add(event4[i]);
                        }

                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);
                    break;

                case 5:
                    icon=5;
                    checker=1;
                    task_title.setText("Harvest Management");

                    String[] event5 = new String[5];
                    event5[0] = "Reaping";
                    event5[1] = "Threshing";
                    event5[2] = "Transportation";
                    event5[3] = "Drying";
                    event5[4] = "Harvesting";
                    for (int i = 0; i <event5.length; i++) {

                        String check = event5[i];
                        if (!check.equals(event_name)) {
                            spinnerArray.add(event5[i]);
                        }
                    }

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);
                    break;

                case 6:
                    icon=6;
                    task_title.setText("Others");
                    desc.setVisibility(View.VISIBLE);
                    desc_choice.setVisibility(View.GONE);
                    checker=0;
                    break;
            }








        }
        else
        {
            flag=0;

            Cursor dbres2 = helper.getStartDate(date_id);

            while (dbres2.moveToNext()) {

                id= String.format(dbres2.getString(6));
                color = String.format(dbres2.getString(2));
                event_name = String.format(dbres2.getString(3));
                event_date_start = String.format(dbres2.getString(4));
                event_time = String.format(dbres2.getString(5));


            }

            Cursor dbres3 = helper.getEndDate(date_id);

            while (dbres3.moveToNext()) {

                event_date_end = String.format(dbres3.getString(4));


            }

            pane.setText(event_date_start);
            r1.setBackgroundColor(Integer.parseInt(color));
            mPickedColor=Integer.parseInt(color);
            pane2.setText(event_date_end);
            time.setText(event_time);
            desc.setText(event_name);




        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start_date = pane.getText().toString();
                end_date = pane2.getText().toString();
                start_time = time.getText().toString();

                try {


                    if (checker == 0) {
                        title = desc.getText().toString();
                    } else {
                        title = desc_choice.getSelectedItem().toString();
                    }

                    if (start_date.equals(end_date)) {

                        String f_timestamp = start_date + " " + start_time;


                        long millisSinceEpoch = epochConverter(f_timestamp);

                        String crop_name1 = crop_name.getSelectedItem().toString();

                        int crop_id = getCropId(crop_name1);

                        int a = helper.updateEvent(millisSinceEpoch, mPickedColor, title, start_date, start_time, id, icon, crop_id);
                        if (a <= 0) {
                            Message.message(getApplicationContext(), "Unsuccessful");

                        } else {
                            Message.message(getApplicationContext(), "Event Updated");

                        }
                        //Message.message(getApplicationContext(),f_timestamp+" "+millisSinceEpoch);


                    } else {
                        Cursor dbres4 = helper.getDateIdMax();
                        String d_id = "";


                        try {
                            while (dbres4.moveToNext()) {

                                d_id = String.format(dbres4.getString(0));
                                if (d_id.equals("0")) {
                                    d_id = "0";
                                }

                            }

                            int dd_id = Integer.parseInt(d_id);
                            dd_id = dd_id + 1;

                            String[] a = start_date.split("-");
                            String[] b = end_date.split("-");

                            int c = Integer.parseInt(a[2]);
                            int d = Integer.parseInt(b[2]);

                            int e = Integer.parseInt(a[1]);
                            String ee;
                            if (e <= 9) {
                                ee = "0" + e;
                            } else {
                                ee = String.valueOf(e);
                            }

                            if (d > c) {


                                String aa;
                                for (int i = c; i <= d; i++) {
                                    if (i <= 9) {
                                        aa = a[0] + "-" + ee + "-" + "0" + i;
                                    } else {
                                        aa = a[0] + "-" + ee + "-" + i;
                                    }
                                    String bb = start_time;


                                    String f_timestamp = aa + " " + bb;

                                    long millisSinceEpoch = epochConverter(f_timestamp);
                                    //ccc=ccc+f_timestamp+" "+millisSinceEpoch+"\n";
                                    helper.insertData(millisSinceEpoch, mPickedColor, title, aa, bb, dd_id, 1, 1);
                                }
                                //Message.message(getApplicationContext(),ccc);

                            } else {

                                String aa;
                                int s = d;
                                int l = c;
                                for (int i = d; i <= c; i++) {
                                    if (i <= 9) {
                                        aa = a[0] + "-" + ee + "-" + "0" + i;
                                    } else {
                                        aa = a[0] + "-" + ee + "-" + i;
                                    }
                                    String bb = start_time;

                                    String f_timestamp = aa + " " + bb;

                                    long millisSinceEpoch = epochConverter(f_timestamp);

                                    helper.insertData(millisSinceEpoch, mPickedColor, title, aa, bb, dd_id, 1, 1);
                                }
                            }

                            helper.delete(id);
                            Message.message(getApplicationContext(), "Event Updated");
                        } catch (Exception e) {
                            Message.message(getApplicationContext(), e.getMessage());
                        }

                    }


                    Intent intent3 = new Intent(getApplicationContext(), Event_list.class);
                    startActivity(intent3);
                    finish();


                }
                catch (Exception e)
                {
                    Message.message(getApplicationContext(),e.getMessage());
                }
            }
        });



    }

    public int getCropId(String crop){

        String temp1 = crop.replace(")","");
        String temp2 = temp1.replace("(","-");
        String[] temp3 = temp2.split("-");
        String aa = temp3[0];
        aa=aa.replace(" ","");
        Cursor dbres5 = helper.getCropId(aa);

        String temp_id="";
        while (dbres5.moveToNext()) {

            temp_id=String.format(dbres5.getString(0));

        }
        int a = Integer.parseInt(temp_id);

        return a;

    }

    public long epochConverter(String f_timestamp){


        String input = f_timestamp.replace( " " , "T" );
        LocalDateTime ldt = LocalDateTime.parse( input ) ;


        ZoneId z = ZoneId.of( "Asia/Singapore" ) ;
        ZonedDateTime zdt = ldt.atZone( z ) ;
        Instant instant = zdt.toInstant() ;
        long millisSinceEpoch = instant.toEpochMilli() ;

        return millisSinceEpoch;
    }

    @Override
    public void onBackPressed() {
        Intent intent3 = new Intent(getApplicationContext(),Event_list.class);
        startActivity(intent3);
        finish();

    }
}