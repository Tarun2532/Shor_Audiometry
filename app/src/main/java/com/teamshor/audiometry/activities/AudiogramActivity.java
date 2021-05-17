package com.teamshor.audiometry.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.teamshor.audiometry.R;

import java.util.ArrayList;

//import lecho.lib.hellocharts.view.LineChartView;

public class AudiogramActivity extends AppCompatActivity {

    //LineChartView lineChartView;
    LineChart lineChart;
    Button btn_hmpage;
    TextView timestamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audiogram);
        ArrayList<Entry> values_r= new ArrayList<>();
        ArrayList<Entry> values_l= new ArrayList<>();

        //lineChartView = findViewById(R.id.chart);
        lineChart=findViewById(R.id.lineChart);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        values_l = (ArrayList<Entry>) getIntent().getSerializableExtra("Audiogram Values Left");
        values_r = (ArrayList<Entry>) getIntent().getSerializableExtra("Audiogram Values Right");
        btn_hmpage=findViewById(R.id.btn_homepage);
        btn_hmpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AudiogramActivity.this, TestActivity.class));
            }
        });
        timestamp=findViewById(R.id.gram_timestamp);
        timestamp.setText(getIntent().getStringExtra("Date and Time"));


        startProcess(values_l,values_r);

    }
    private void startProcess(ArrayList<Entry> values_l, ArrayList<Entry> values_r)
    {



        LimitLine limit1= new LimitLine(118f,"Profound");
        limit1.setLineWidth(2f);
        limit1.enableDashedLine(10f,10f,0f);
        limit1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        limit1.setTextSize(9f);
        limit1.setTextColor(Color.BLACK);

        LimitLine limit2= new LimitLine(90f,"Severe");
        limit2.setLineWidth(2f);
        limit2.enableDashedLine(10f,10f,0f);
        limit2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        limit2.setTextSize(9f);
        limit2.setTextColor(Color.BLACK);

        // limit3.setLineColor(R.color.red);

        LimitLine limit4= new LimitLine(70f,"Moderate");
        limit4.setLineWidth(2f);
        limit4.enableDashedLine(10f,10f,0f);
        limit4.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        limit4.setTextSize(9f);
        limit4.setTextColor(Color.BLACK);

        //limit4.setLineColor(R.color.yellow);

        LimitLine limit5= new LimitLine(40f,"Mild");
        limit5.setLineWidth(2f);
        limit5.enableDashedLine(10f,10f,10f);
        limit5.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        limit5.setTextSize(9f);
        limit5.setTextColor(Color.BLACK);

        //limit5.setLineColor(R.color.cyan);

        LimitLine limit6= new LimitLine(25f,"Normal");
        //limit6.setLineColor(R.color.blue);
        limit6.setLineWidth(2f);
        limit6.enableDashedLine(10f,10f,0f);
        limit6.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        limit6.setTextSize(9f);
        limit6.setTextColor(Color.BLACK);


        YAxis leftaxis=lineChart.getAxisLeft();
        leftaxis.removeAllLimitLines();
        leftaxis.addLimitLine(limit1);
        leftaxis.addLimitLine(limit2);
        leftaxis.addLimitLine(limit4);
        leftaxis.addLimitLine(limit5);
        leftaxis.addLimitLine(limit6);
        leftaxis.setAxisMaximum(140f);
        leftaxis.setAxisMinimum(-10f);
        leftaxis.enableGridDashedLine(10f,10f,10f);
        leftaxis.setDrawLimitLinesBehindData(true);
        leftaxis.setTextColor(Color.BLACK);

        leftaxis.setInverted(true);

        leftaxis.setDrawGridLines(false); //added
        lineChart.getAxisRight().setEnabled(false);









        LineDataSet set1=new LineDataSet(values_l,"leftEar");
        set1.setFillAlpha(110);
        set1.setColor(Color.BLUE);
        set1.setLineWidth(4f);
        set1.setValueTextColor(Color.BLACK);
        set1.setColor(Color.BLUE);
        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        //dataSets.add(set1);

        LineDataSet set2=new LineDataSet(values_r,"RightEar");
        set2.setFillAlpha(110);
        set2.setColor(Color.RED);
        set2.setValueTextColor(Color.BLACK);
        set2.setLineWidth(4f);
        //ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);


        LineData data=new LineData(dataSets);
        lineChart.setData(data);
        lineChart.getLegend().setCustom(new ArrayList<>());
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.getDescription().setText("X AXIS: DB LEVELS\n Y AXIS: FREQUENCY");
        lineChart.getDescription().setTextColor(Color.WHITE);
        lineChart.getAxisLeft().setTextColor(Color.BLACK); // left y-axis
        lineChart.getXAxis().setTextColor(Color.BLACK);
        lineChart.getLegend().setTextColor(Color.WHITE);

        lineChart.animateXY(3000,3000);


    }



    public static class MyAxisValueFormator extends ValueFormatter implements IAxisValueFormatter {
        private final String[] values;

        public MyAxisValueFormator(String[] values)
        {
            this.values=values;
        }
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return values[(int)value];
        }
    }
}
