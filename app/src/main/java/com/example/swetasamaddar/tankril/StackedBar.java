package com.example.swetasamaddar.tankril;

/**
 * Created by SWETA.SAMADDAR on 18-05-2018.
 */
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class StackedBar implements IAxisValueFormatter {

    private DecimalFormat dform;
    public StackedBar()
    {
        dform=new DecimalFormat("######.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return dform.format(value)+"%";
    }
}
