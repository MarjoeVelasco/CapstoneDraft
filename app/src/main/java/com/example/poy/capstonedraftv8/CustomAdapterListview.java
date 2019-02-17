package com.example.poy.capstonedraftv8;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterListview extends RecyclerView.Adapter<CustomAdapterListview.PlayerViewHolder>{


    public ArrayList<DataModelEventList> events;

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView name,date_event,time,color,month,crop_name,variety;
        private LinearLayout contain;
        private ImageView info,crop;

        public PlayerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            date_event = (TextView) view.findViewById(R.id.date_event);
            time = (TextView) view.findViewById(R.id.time);
            color = (TextView) view.findViewById(R.id.color);
            info = (ImageView)view.findViewById(R.id.info);
            contain = (LinearLayout) view.findViewById(R.id.contain);

            crop_name = (TextView) view.findViewById(R.id.crop_name);
            variety = (TextView) view.findViewById(R.id.variety);
            crop = (ImageView)view.findViewById(R.id.crop);




        }
    }

    public CustomAdapterListview(ArrayList<DataModelEventList> players) {
        this.events = players;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row, parent, false);

        return new PlayerViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {

        DataModelEventList player = events.get(position);
        holder.name.setText(player.getEvent_name());
        holder.name.setTextColor(Color.WHITE);

        holder.time.setText(player.getEvent_time());
        holder.time.setTextColor(Color.WHITE);

        holder.crop_name.setText("Crop Name: \t"+player.getCrop_name());
        holder.crop_name.setTextColor(Color.WHITE);

        holder.variety.setText("Variety: \t\t\t\t"+player.getVariety());
        holder.variety.setTextColor(Color.WHITE);
        String crop_type=player.getCrop();

        if(crop_type.equalsIgnoreCase("rice"))
        {
            holder.crop.setImageResource(R.drawable.ricev2);
        }
        else if (crop_type.equalsIgnoreCase("onion"))
        {
            holder.crop.setImageResource(R.drawable.onionv2);
        }

        holder.contain.setBackgroundResource(R.mipmap.rice3);

        int a = player.getIcon();

        if(a==1)
        {
            holder.info.setImageResource(R.drawable.land_iconv2);

        }
        else if(a==2)
        {
            holder.info.setImageResource(R.drawable.crop_iconv2);
        }
        else if(a==3)
        {
            holder.info.setImageResource(R.drawable.care_iconv2);
        }
        else if(a==4)
        {
            holder.info.setImageResource(R.drawable.pest_iconv2);
        }
        else if(a==5)
        {
            holder.info.setImageResource(R.drawable.harvest_iconv2);
        }
        else if(a==6)
        {
            holder.info.setImageResource(R.drawable.others_iconv2);
        }
        holder.info.setScaleType(ImageView.ScaleType.FIT_XY);

        holder.date_event.setText(player.getEvent_date());
        holder.date_event.setTextColor(Color.WHITE);

        String temp_date=player.getEvent_date();

        String[] temp_month=temp_date.split("-");
        int month=Integer.parseInt(temp_month[1]);

        //holder.month.setText(getMonthName(month));



    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    public String getMonthName (int month){
        String monthName="";

        switch (month)
        {
            case 1:
            monthName="January";
            break;

            case 2:
            monthName="February";
            break;

            case 3:
            monthName="March";
            break;

            case 4:
                monthName="April";
                break;

            case 5:
                monthName="May";
                break;

            case 6:
                monthName="June";
                break;

            case 7:
                monthName="July";
                break;

            case 8:
                monthName="August";
                break;

            case 9:
                monthName="September";
                break;

            case 10:
                monthName="October";
                break;

            case 11:
                monthName="November";
                break;

            case 12:
                monthName="December";
                break;
            default:
                monthName="No Month";
             break;

        }

        return monthName;
    }
}
