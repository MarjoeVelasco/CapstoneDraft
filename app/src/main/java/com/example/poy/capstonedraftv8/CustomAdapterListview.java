package com.example.poy.capstonedraftv8;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterListview extends RecyclerView.Adapter<CustomAdapterListview.PlayerViewHolder>{


    public ArrayList<DataModelEventList> events;

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView name,date_event,time,color;
        private LinearLayout contain;

        public PlayerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            date_event = (TextView) view.findViewById(R.id.date_event);
            time = (TextView) view.findViewById(R.id.time);
            color = (TextView) view.findViewById(R.id.color);
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
        holder.time.setText(player.getEvent_time());
        holder.contain.setBackgroundColor(player.getColor());
        holder.date_event.setText(player.getEvent_date());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
