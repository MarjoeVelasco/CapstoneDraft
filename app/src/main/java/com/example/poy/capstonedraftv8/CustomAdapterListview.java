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
        private TextView name,date_event,time,color,month;
        private LinearLayout contain;

        public PlayerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            date_event = (TextView) view.findViewById(R.id.date_event);
            time = (TextView) view.findViewById(R.id.time);
            color = (TextView) view.findViewById(R.id.color);
            month = (TextView) view.findViewById(R.id.month);

            contain = (LinearLayout) view.findViewById(R.id.contain);




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


        holder.contain.setBackgroundResource(R.mipmap.rice3);


        holder.date_event.setText(player.getEvent_date());
        holder.date_event.setTextColor(Color.WHITE);

        String temp_date=player.getEvent_date();

        String[] temp_month=temp_date.split("-");
        int month=Integer.parseInt(temp_month[1]);

        //holder.month.setText(getMonthName(month));
        holder.month.setTextColor(Color.WHITE);


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
