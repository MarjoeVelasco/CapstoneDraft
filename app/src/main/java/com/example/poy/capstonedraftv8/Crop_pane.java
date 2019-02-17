package com.example.poy.capstonedraftv8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Crop_pane extends AppCompatActivity {

    ArrayList<DataModelCropList> dataModels2;
    private CustomAdapterCropList mAdapter;

    SwipeController swipeController = null;
    myDbAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_pane);

        helper = new myDbAdapter(this);


        dataModels2= new ArrayList<>();

        setPlayersDataAdapter();
        setupRecyclerView();




    }

    private void setPlayersDataAdapter() {

        Intent intent = getIntent();

        String crop_type= (String) intent.getSerializableExtra("crop_type");
        String status= (String) intent.getSerializableExtra("status");
        if(status.equals("success"))
        {
            Snackbar.make(findViewById(android.R.id.content),"Crop Updated",Snackbar.LENGTH_SHORT).show();
        }

        int crop_id;
        String crop_name;
        String crop;
        String variety;
        Cursor dbres = helper.getAllDataCropSpes(crop_type);
        if(dbres.getCount() == 0)
        {

            Snackbar.make(findViewById(android.R.id.content),"No Crops Yet",Snackbar.LENGTH_SHORT).show();

        }
        else {


            while (dbres.moveToNext()) {

                crop_id=Integer.parseInt(String.format(dbres.getString(0)));
                crop_name=String.format(dbres.getString(1));
                crop=String.format(dbres.getString(2));
                variety=String.format(dbres.getString(3));


                dataModels2.add(new DataModelCropList(crop_id,crop,crop_name,variety));

            }

            mAdapter= new CustomAdapterCropList(dataModels2);
        }

    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                DataModelCropList dataModel = dataModels2.get(position);
                final String id=String.valueOf(dataModel.getId());
                final int a=position;

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(

                        Crop_pane.this,R.style.MyAlertDialogTheme2);

                alertDialog.setTitle("DELETE");
                final TextView mess = new TextView(Crop_pane.this);
                mess.setText("\n\t\tAre you sure?");
                mess.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 9F, getApplicationContext().getResources().getDisplayMetrics()));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                mess.setLayoutParams(lp);
                alertDialog.setView(mess);

                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        Cursor dbres2 = helper.getCropChecker(id);
                        if(dbres2.getCount() == 0)
                        {
                            helper.deleteCrop(id);

                            Snackbar.make(findViewById(android.R.id.content),"Crop Deleted",Snackbar.LENGTH_LONG).show();
                            mAdapter.crops.remove(a);
                            mAdapter.notifyItemRemoved(a);
                            mAdapter.notifyItemRangeChanged(a, mAdapter.getItemCount());



                        }
                        else {

                            Snackbar.make(findViewById(android.R.id.content),"Deleting Failed, Crop is still in use",Snackbar.LENGTH_LONG).show();


                        }





                    }
                });

                alertDialog.setNegativeButton("Cancel",

                        new DialogInterface.OnClickListener() {

                            @Override

                            public void onClick(DialogInterface dialog,

                                                int which) {
                                dialog.cancel();



                            }

                        });





                alertDialog.show();
            }

            @Override
            public void onLeftClicked(int position) {
                DataModelCropList dataModel = dataModels2.get(position);
                final String id=String.valueOf(dataModel.getId());
                final int a=position;
                Intent intent =new Intent(getApplicationContext(),Crop_edit.class);
                intent.putExtra("crop_id",id);
                startActivity(intent);
                finish();


            }


        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),Crop_menu.class);
        startActivity(intent);
        finish();
    }
}
