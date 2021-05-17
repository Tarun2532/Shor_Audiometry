package com.teamshor.audiometry.sourcefiles;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

public class Mycolors extends BarDataSet {


    private List<Float> credits;

    Mycolors(List<BarEntry> yVals, String label, List<Float> credits) {
        super(yVals, label);
        this.credits = credits;
    }

    @Override
    public int getColor(int index){
        float c = credits.get(index);

        if (c < 8){
            return mColors.get(0);
        }
        else if (c < 13) {
            return mColors.get(1);
        }
        else {
            return mColors.get(2);
        }
    }

}
