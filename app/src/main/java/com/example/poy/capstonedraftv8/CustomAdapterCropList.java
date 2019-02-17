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

public class CustomAdapterCropList extends RecyclerView.Adapter<CustomAdapterCropList.PlayerViewHolder> {

    public ArrayList<DataModelCropList> crops;

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView crop_id,crop_name,variety,pane1,pane2;
        private ImageView info;

        public PlayerViewHolder(View view) {
            super(view);

            info = (ImageView)view.findViewById(R.id.info);
            crop_id = (TextView) view.findViewById(R.id.crop_id);
            crop_name = (TextView) view.findViewById(R.id.crop_name);
            variety = (TextView) view.findViewById(R.id.crop_variety);
            pane1 = (TextView) view.findViewById(R.id.pane1);
            pane2 = (TextView) view.findViewById(R.id.pane2);



        }
    }

    public CustomAdapterCropList(ArrayList<DataModelCropList> players) { this.crops = players; }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crop_row, parent, false);

        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {

        DataModelCropList player = crops.get(position);

        holder.pane1.setText("Crop Name\t\t:");
        holder.pane2.setText("Variety\t\t\t\t\t:");

        holder.crop_id.setText(""+player.getId());
        holder.crop_name.setText(player.getCrop_name());
        holder.variety.setText(player.getVariety());
        String crop_type=player.getCrop();

        if(crop_type.equalsIgnoreCase("rice"))
        {
            holder.info.setImageResource(R.drawable.ricev2);
        }
        else if (crop_type.equalsIgnoreCase("onion"))
        {
            holder.info.setImageResource(R.drawable.onionv2);
        }



        //holder.month.setText(getMonthName(month));



    }

    @Override
    public int getItemCount() {
        return crops.size();
    }

}
