package com.example.swetasamaddar.tankril;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class BarGraph extends AppCompatActivity {


    private BarChart stack;
    private String id="",mat="";
    float contain;
    private DatabaseReference mDatabase;
    private HashMap<String ,String>hasDetails;
    private Boolean got=false;

    //0:Crude 1:Natural Gas 2:Petrol


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);
        stack=(BarChart)findViewById(R.id.graph);
        hasDetails=new HashMap<>();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        Intent intent=getIntent();
        id=intent.getStringExtra("tankid");
        System.out.println("=======>"+id);
        if(!id.equals(""))
        {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println("===========================================================================");
                    System.out.println(dataSnapshot.child("tanks").child(id).getValue());

                    for(DataSnapshot ds:dataSnapshot.child("tanks").child(id).getChildren())
                    {
                        System.out.println(ds.getKey().toString()+"  "+ds.getValue().toString());
                        hasDetails.put(ds.getKey().toString(),ds.getValue().toString());


                    }
                    if(!hasDetails.isEmpty())
                    Values(hasDetails);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        int stored=0,max=0;

     //   contain=(stored/max)*100;

        stack.setMaxVisibleValueCount(100);





    }
    public void Values(HashMap<String,String>details)
    {
       float stored=Float.parseFloat(hasDetails.get("stored"));
       float max=Float.parseFloat(hasDetails.get("maximumcapacity"));
        mat=hasDetails.get("type");
        System.out.println(stored+"  "+max);
        contain=(stored/max)*100f;
        System.out.println("=============>"+contain);
        setData(1);
    }
    public void setData(int count){
        ArrayList<BarEntry> yValues=new ArrayList<>();
        float rem=100f-contain;
        System.out.println(contain);
        yValues.add(new BarEntry(1,new float[]{contain,rem}));
        BarDataSet set1;
        set1=new BarDataSet(yValues,"Tank storage");
        set1.setDrawIcons(false);
        set1.setStackLabels(new String[]{"Stored","Empty"});
        ArrayList<Integer>color=new ArrayList<>();

        switch(mat) {
            case "crude": color.add(ColorTemplate.rgb("#000000"));break;
            case "cng": color.add(ColorTemplate.rgb("#00FF00"));break;
            case "petrol": color.add(ColorTemplate.rgb("#FF0000"));break;
            default:color.add(ColorTemplate.rgb("#00FFFF"));break;
        }
        color.add(ColorTemplate.rgb("#AAAAAA"));

        set1.setColors(color);
        BarData data=new BarData(set1);
        data.setValueFormatter(new ValueFormatter());
        stack.setData(data);
        stack.setFitBars(true);
        stack.invalidate();
        stack.getDescription().setEnabled(false);
    }
}
