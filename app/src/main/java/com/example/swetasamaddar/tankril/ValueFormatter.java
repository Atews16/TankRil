package com.example.swetasamaddar.tankril;

/**
 * Created by SWETA.SAMADDAR on 18-05-2018.
 */
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class ValueFormatter implements IValueFormatter {
    private DecimalFormat dform;

    public ValueFormatter (){
        dform=new DecimalFormat("######.0");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        return dform.format(value)+"%";
    }
}
