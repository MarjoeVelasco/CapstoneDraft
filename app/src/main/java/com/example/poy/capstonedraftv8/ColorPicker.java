package com.example.poy.capstonedraftv8;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

//import android.widget.RelativeLayout.LayoutParams;
//import android.view.ViewGroup.LayoutParams;

public class ColorPicker {
    private Context mContext;
    // GridView column width and TextView width, height in pixels
    private static int columnWidth = 100;



    public ColorPicker(Context context){
        this.mContext = context;
    }

    public static View getColorPicker(Context mContext){
        // Initialize a new GridView widget
        GridView gv = new GridView(mContext);

        // Specify the GridView layout parameters
        gv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // Define column width and number of columns
        gv.setNumColumns(GridView.AUTO_FIT);
        gv.setColumnWidth(columnWidth); // Should be same as TextView width and height

        // Define addition settings of GridView for design purpose

        gv.setHorizontalSpacing(0);
        gv.setVerticalSpacing(20);
        gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gv.setBackgroundColor(Color.TRANSPARENT);
        gv.setPadding(10, 40, 10, 40);
        gv.setGravity(Gravity.CENTER);



        // Get the ArrayList of HSV colors
        final ArrayList colors = HSVColors();

        // Create an ArrayAdapter using colors list
        ArrayAdapter<Integer> ad = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_list_item_1, colors) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the current view as a TextView
                TextView view = (TextView) super.getView(position, convertView, parent);
                GradientDrawable gd = new GradientDrawable();


                // Make the border rounded
                gd.setCornerRadius(20f); // border corner radius

                // Finally, apply the GradientDrawable as TextView background
                view.setBackground(gd);

                // Get the current color from list
                int currentColor = (int) colors.get(position);

                // Set the background color of TextView as current color
                view.setBackgroundColor(currentColor);

                // Set empty text in TextView
                view.setText("");


                // Set the layout parameters for TextView widget
                LayoutParams lp = new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
                );
                view.setLayoutParams(lp);

                // Get the TextView LayoutParams
                LayoutParams params = (LayoutParams) view.getLayoutParams();

                // Set the TextView width and height in pixels
                // Should be same as GridView column width
                params.width = columnWidth; // pixels
                params.height = columnWidth; // pixels

                // Set the TextView layout parameters
                view.setLayoutParams(params);
                view.requestLayout();

                // Return the TextView as current view
                return view;
            }
        };

        // Specify the GridView data source
        gv.setAdapter(ad);

        // return the GridView
        return gv;
    }

    // Custom method to generate hsv colors list
    public static ArrayList HSVColors(){
        ArrayList<Integer> colors= new ArrayList<>();

        // Loop through hue channel, saturation and light full


        // Loop through hue channel, different saturation and light full

            colors.add(HSVColor(219, 73, 68));
            colors.add(HSVColor(187 , 46, 71));
            colors.add(HSVColor(44, 85, 93));
            colors.add(HSVColor(357, 68, 84));
            colors.add(HSVColor(317, 29, 74));

             colors.add(HSVColor(201, 58, 93));
             colors.add(HSVColor(91 , 58, 78));
             colors.add(HSVColor(25, 34, 74));
             colors.add(HSVColor(0, 0, 0));
             colors.add(HSVColor(295, 7, 100));


        return colors;
    }

    // Create HSV color from values
    public static int HSVColor(float hue, float saturation, float black){
        /*
            Hue is the variation of color
            Hue range 0 to 360

            Saturation is the depth of color
            Range is 0.0 to 1.0 float value
            1.0 is 100% solid color

            Value/Black is the lightness of color
            Range is 0.0 to 1.0 float value
            1.0 is 100% bright less of a color that means black
        */
        int color = Color.HSVToColor(255,new float[]{hue,saturation,black});
        return color;
    }
}