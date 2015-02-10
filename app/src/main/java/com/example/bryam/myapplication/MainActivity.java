package com.example.bryam.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class MainActivity extends ActionBarActivity {

    private PieChartView pieChart;
    private PieChartData data;
    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasLabelForSelected = false;
    private int buenas = 5, malas = 2, noresp = 3, total = 0;

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(MainActivity.this, value.getValue() + " %", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieChart = (PieChartView) findViewById(R.id.pieChart);

        buenas = 5;
        malas = 2;
        noresp = 3;
        total=buenas+malas+noresp;

        pieChart.setOnValueTouchListener(new ValueTouchListener());
        toggleLabels();
        generateData();
}
    private void toggleLabels() {
        hasLabels = !hasLabels;
        if (hasLabels) {
            hasLabelForSelected = false;
            pieChart.setValueSelectionEnabled(hasLabelForSelected);
            if (hasLabelsOutside) {
                pieChart.setCircleFillRatio(0.7f);
            } else {
                pieChart.setCircleFillRatio(1.0f);
            }
        }
        generateData();
    }

    /*Reinicia los valores de las variables*/
    public void resetCounts() {
        buenas = 0;
        malas = 0;
        noresp = 0;
    }

    private void generateData() {
        int numValues = 3;	// Numero de particiones y/o variables
        List<SliceValue> values = new ArrayList<SliceValue>();
	/*Definimos el tamano (mediante un valor porcentual referente a cierta variable) y el color que tendra*/
        if (buenas > 0) {
            SliceValue sliceValueBuenas = new SliceValue((float) buenas * 100 / total, getResources().getColor(R.color.green_light));
            values.add(sliceValueBuenas);
        }
        if (malas > 0) {
            SliceValue sliceValueMalas = new SliceValue((float) malas * 100 / total, getResources().getColor(R.color.pink_light));
            values.add(sliceValueMalas);
        }
        if (noresp > 0) {
            SliceValue sliceValueNulas = new SliceValue((float) noresp * 100 / total, getResources().getColor(R.color.yellow_light));
            values.add(sliceValueNulas);
        }
        data = new PieChartData(values);
        data.setHasLabels(hasLabels); // Muesta el valor de la particion
        pieChart.setPieChartData(data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}