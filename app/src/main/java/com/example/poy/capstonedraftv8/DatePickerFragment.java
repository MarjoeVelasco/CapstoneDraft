package com.example.poy.capstonedraftv8;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        TextView tv = (TextView) getActivity().findViewById(R.id.textView);
        String ddate=tv.getText().toString();
        String[] a = ddate.split("-");
        int year = Integer.parseInt(a[0]);
        int month = Integer.parseInt(a[1])-1;
        int day =Integer.parseInt(a[2]);
        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
        TextView tv = (TextView) getActivity().findViewById(R.id.textView);
        month=month+1;

        String m=String.valueOf(month);
        String d=String.valueOf(day);
        if(month<=9)
        {
            m="0"+month;
        }
        if(day<=9)
        {
            d="0"+day;
        }
        String stringOfDate = year +"-"+m+"-"+d;
        tv.setText(stringOfDate);
    }
}