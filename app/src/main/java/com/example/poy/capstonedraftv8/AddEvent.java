package com.example.poy.capstonedraftv8;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;


public class AddEvent extends AppCompatActivity {

    myDbAdapter helper;
    TextView pane,pane2,r1,time,desc;
    TextView btn;
    Button save,cancel;
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
        desc = (TextView) findViewById(R.id.desc);
   //     btn = (TextView) findViewById(R.id.textView3);
        Intent intent = getIntent();
//Get the USERNAME passed from IntentExampleActivity
        String date_event = (String) intent.getSerializableExtra("date_event");
//Set text for greetMsg TextView
        pane.setText(date_event);
        pane2.setText(date_event);

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

                title=desc.getText().toString();

                if(start_date.equals(end_date))
                {

                    String f_timestamp=start_date+" "+start_time;




                /*Instant a = Instant.now();
                ZonedDateTime b = ZonedDateTime.now(ZoneId.of("Asia/Singapore"));*/

                /*LocalDateTime.parse(           // Parse into an object representing a date with a time-of-day but without time zone and without offset-from-UTC.
                        "2014/10/29 18:10:45"      // Convert input string to comply with standard ISO 8601 format.
                                .replace( " " , "T" )      // Replace SPACE in the middle with a `T`.
                                .replace( "/" , "-" )      // Replace SLASH in the middle with a `-`.
                )
                        .atZone(                       // Apply a time zone to provide the context needed to determine an actual moment.
                                ZoneId.of( "Asia/Singapore" ) // Specify the time zone you are certain was intended for that input.
                        )                              // Returns a `ZonedDateTime` object.
                        .toInstant()                   // Adjust into UTC.
                        .toEpochMilli();
                */

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
                    helper.insertData(millisSinceEpoch,mPickedColor,title,start_date,start_time,dd_id);

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

                    if(d>c)
                    {
                        int dif=d-c;

                        for(int i=c;i<=d;i++)
                        {
                            String aa=a[0]+"-"+a[1]+"-"+i;
                            String bb=start_time;

                            String f_timestamp=aa+" "+bb;
                            String input = f_timestamp.replace( " " , "T" );
                            LocalDateTime ldt = LocalDateTime.parse( input ) ;


                            ZoneId z = ZoneId.of( "Asia/Singapore" ) ;
                            ZonedDateTime zdt = ldt.atZone( z ) ;
                            Instant instant = zdt.toInstant() ;
                            long millisSinceEpoch = instant.toEpochMilli() ;

                            helper.insertData(millisSinceEpoch,mPickedColor,title,aa,bb,dd_id);
                        }
                    }
                    else
                    {

                    }

                    Message.message(getApplicationContext(),""+c+" to "+d);


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
