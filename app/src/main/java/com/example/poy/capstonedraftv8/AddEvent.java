package com.example.poy.capstonedraftv8;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import android.app.AlarmManager;
import android.app.PendingIntent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class AddEvent extends AppCompatActivity {

    myDbAdapter helper;
    TextView pane,pane2,r1,time,desc;
    TextView task_title,title2;
    Button save,cancel;
    RadioGroup crop_type;
    RadioButton onion,rice;
    String crop_type_choice="rice";

    Spinner desc_choice,crop_name;
    ImageButton landPrep,crop,care,pest,harvest,others;
    int checker=1;
    int icon=1;

    private Context mContext;
    public static int mPickedColor = Color.WHITE;

    public static String title="n/a";

    public static String start_date;
    public static String end_date;
    public static String start_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_add_event);

        helper = new myDbAdapter(this);
        save=(Button)findViewById(R.id.save);
        cancel=(Button)findViewById(R.id.cancel);
        pane = (TextView) findViewById(R.id.textView);
        pane2 = (TextView) findViewById(R.id.textView7);
        time = (TextView) findViewById(R.id.time);
        r1 = (TextView) findViewById(R.id.r1);
        desc = (EditText) findViewById(R.id.desc);

        task_title = (TextView)findViewById(R.id.task_title);
        title2 = (TextView)findViewById(R.id.title2);


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
   //     btn = (TextView) findViewById(R.id.textView3);
        Intent intent = getIntent();
//Get the USERNAME passed from IntentExampleActivity
        String date_event = (String) intent.getSerializableExtra("date_event");
//Set text for greetMsg TextView
        pane.setText(date_event);
        pane2.setText(date_event);



        final  List<String> spinnerArrayCrop =  new ArrayList<String>();

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArrayCrop);

        Cursor dbres4 = helper.getAllDataCropSpes(crop_type_choice);
        if(dbres4.getCount() == 0)
        {

            spinnerArrayCrop.clear();
            spinnerArrayCrop.add("No Crops Yet");

            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            crop_name.setAdapter(adapter2);

        }
        else {
            spinnerArrayCrop.clear();
            while (dbres4.moveToNext()) {

                String crop = String.format(dbres4.getString(1));
                String variety = String.format(dbres4.getString(3));

                spinnerArrayCrop.add(crop + " (" + variety + ")");


            }
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            crop_name.setAdapter(adapter2);

        }

        crop_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rice:
                        spinnerArrayCrop.clear();
                        crop_type_choice="rice";
                        Cursor dbres2 = helper.getAllDataCropSpes(crop_type_choice);
                        if(dbres2.getCount() == 0)
                        {


                            spinnerArrayCrop.add("No Crops Yet");

                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            crop_name.setAdapter(adapter2);

                        }
                        else {
                            spinnerArrayCrop.clear();
                            while (dbres2.moveToNext()) {

                                String crop = String.format(dbres2.getString(1));
                                String variety = String.format(dbres2.getString(3));

                                spinnerArrayCrop.add(crop + " (" + variety + ")");


                            }
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            crop_name.setAdapter(adapter2);

                        }

                        break;

                    case R.id.onion:
                        crop_type_choice="onion";
                        spinnerArrayCrop.clear();
                        Cursor dbres3 = helper.getAllDataCropSpes(crop_type_choice);
                        if(dbres3.getCount() == 0)
                        {


                            spinnerArrayCrop.add("No Crops Yet");

                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            crop_name.setAdapter(adapter2);

                        }
                        else {

                            while (dbres3.moveToNext()) {

                                String crop = String.format(dbres3.getString(1));
                                String variety = String.format(dbres3.getString(3));

                                spinnerArrayCrop.add(crop + " (" + variety + ")");


                            }
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            crop_name.setAdapter(adapter2);

                        }


                        break;




                }
            }

        });






        final  List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Plowing");
        spinnerArray.add("Harrowing");
        spinnerArray.add("Levelling");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        desc_choice.setAdapter(adapter);

        title2.setVisibility(View.VISIBLE);
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

                title2.setVisibility(View.VISIBLE);
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

                title2.setVisibility(View.VISIBLE);
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

                title2.setVisibility(View.VISIBLE);
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

                title2.setVisibility(View.VISIBLE);
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

                title2.setVisibility(View.VISIBLE);
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

                title2.setVisibility(View.VISIBLE);
                desc.setVisibility(View.VISIBLE);
                desc_choice.setVisibility(View.GONE);

                checker=0;

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);
                finish();
            }
        });


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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_date=pane.getText().toString();
                end_date=pane2.getText().toString();
                start_time=time.getText().toString();


                if(checker==1)
                {
                    title=desc_choice.getSelectedItem().toString();
                }
                else{
                    title=desc.getText().toString();
                }


                if(start_date.equals(end_date))
                {

                    String f_timestamp=start_date+" "+start_time;


                    String input = f_timestamp.replace( " " , "T" );
                    LocalDateTime ldt = LocalDateTime.parse( input ) ;


                    ZoneId z = ZoneId.of( "Asia/Singapore" ) ;
                    ZonedDateTime zdt = ldt.atZone( z ) ;
                    Instant instant = zdt.toInstant() ;
                   long millisSinceEpoch = instant.toEpochMilli() ;

                    //time.setText(""+f_timestamp);

                    Cursor dbres4 = helper.getDateIdMax();
                    String d_id="";




                        while (dbres4.moveToNext()) {

                            d_id=String.format(dbres4.getString(0));
                            if(d_id.equals("0"))
                            {
                                d_id="0";
                            }

                        }


                    int dd_id=Integer.parseInt(d_id);
                    dd_id=dd_id+1;
                    //Message.message(getApplicationContext(),f_timestamp+" "+millisSinceEpoch);
                    String crop_name1 = crop_name.getSelectedItem().toString();

                    int crop_id=getCropId(crop_name1);

                    helper.insertData(millisSinceEpoch,mPickedColor,title,start_date,start_time,dd_id,icon,crop_id);

                    Cursor dbres16 = helper.getLastId();
                    String temp_last_id="";

                    while (dbres16.moveToNext()) {
                        temp_last_id=String.format(dbres16.getString(0));
                    }

                    Cursor dbres15 = helper.getCropData(String.valueOf(crop_id));

                    if(dbres15.getCount()!=0)
                    {

                        while (dbres15.moveToNext()) {

                            String crop_name = String.format(dbres15.getString(1));
                            String crop=String.format(dbres15.getString(2));
                            String temp_title=title+" of "+crop+"("+crop_name+")";

                            //NOTIF*************

                            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                            Intent notificationIntent = new Intent(getApplicationContext(), AlarmReceiver.class);

                            notificationIntent.putExtra("param", temp_title);
                            PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(),Integer.parseInt(temp_last_id), notificationIntent, PendingIntent.FLAG_ONE_SHOT);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, millisSinceEpoch, broadcast);

                            //END OF NOTIF*************

                        }
                    }


                }
                else
                {
                    Cursor dbres4 = helper.getDateIdMax();
                    String d_id="";




                    while (dbres4.moveToNext()) {

                        d_id=String.format(dbres4.getString(0));
                        if(d_id.equals("0"))
                        {
                            d_id="0";
                        }

                    }

                    int dd_id=Integer.parseInt(d_id);
                    dd_id=dd_id+1;

                    String[] a = start_date.split("-");
                    String[] b = end_date.split("-");

                    int c=Integer.parseInt(a[2]);
                    int d=Integer.parseInt(b[2]);

                    int e=Integer.parseInt(a[1]);
                    String ee;
                    if(e<=9)
                    {
                        ee="0"+e;
                    }
                    else
                    {
                        ee=String.valueOf(e);
                    }

                    if(d>c)
                    {

                        String ccc="";
                        String aa;
                        for(int i=c;i<=d;i++)
                        {
                            if(i<=9)
                            {
                            aa=a[0]+"-"+ee+"-"+"0"+i;
                            }
                            else
                            {
                             aa=a[0]+"-"+ee+"-"+i;
                            }
                            String bb=start_time;



                            String f_timestamp=aa+" "+bb;

                            String input = f_timestamp.replace( " " , "T" );
                            LocalDateTime ldt = LocalDateTime.parse( input ) ;


                            ZoneId z = ZoneId.of( "Asia/Singapore" ) ;
                            ZonedDateTime zdt = ldt.atZone( z ) ;
                            Instant instant = zdt.toInstant() ;
                            long millisSinceEpoch = instant.toEpochMilli() ;
                            //ccc=ccc+f_timestamp+" "+millisSinceEpoch+"\n";

                            String crop_name1 = crop_name.getSelectedItem().toString();

                            int crop_id=getCropId(crop_name1);

                           helper.insertData(millisSinceEpoch,mPickedColor,title,aa,bb,dd_id,icon,crop_id);
                        }
                        //Message.message(getApplicationContext(),ccc);

                    }
                    else
                    {
                        String ccc="";
                        String aa;
                        int s=d;
                        int l=c;
                        for(int i=d;i<=c;i++)
                        {
                            if(i<=9)
                            {
                                aa=a[0]+"-"+ee+"-"+"0"+i;
                            }
                            else
                            {
                                aa=a[0]+"-"+ee+"-"+i;
                            }
                            String bb=start_time;



                            String f_timestamp=aa+" "+bb;

                            String input = f_timestamp.replace( " " , "T" );
                            LocalDateTime ldt = LocalDateTime.parse( input ) ;


                            ZoneId z = ZoneId.of( "Asia/Singapore" ) ;
                            ZonedDateTime zdt = ldt.atZone( z ) ;
                            Instant instant = zdt.toInstant() ;
                            long millisSinceEpoch = instant.toEpochMilli() ;
                            //ccc=ccc+f_timestamp+" "+millisSinceEpoch+"\n";

                            String crop_name1 = crop_name.getSelectedItem().toString();

                            int crop_id=getCropId(crop_name1);

                            helper.insertData(millisSinceEpoch,mPickedColor,title,aa,bb,dd_id,icon,crop_id);
                        }
                    }





                }


                Intent intent3 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent3);
                finish();


            }
        });



        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get a GridView object from ColorPicker class
                GridView gv = (GridView) ColorPicker.getColorPicker(AddEvent.this);



                // Initialize a new AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEvent.this,R.style.MyAlertDialogTheme);

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



    private Point getScreenSize(){
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        //Display dimensions in pixels
        display.getSize(size);
        return size;
    }

    // Custom method to get status bar height in pixels
    public int getStatusBarHeight() {
        int height = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    public void viewdata(View view)
    {
        String data = helper.getData();
        Message.message(this,data);
    }

    @Override
    public void onBackPressed() {
        Intent intent3 = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent3);
        finish();

    }

}
