package com.example.arek.swdahp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.search)
    FloatingActionButton searchButton;
    @BindView(R.id.company_spinner)
    MaterialBetterSpinner companySpinner;
    @BindView(R.id.door_amount_spinner)
    MaterialBetterSpinner doorAmountSpinner;
    @BindView(R.id.engine_power_spinner)
    MaterialBetterSpinner enginePowerSpinner;
    @BindView(R.id.safety_seek_bar)
    SeekBar safetySeekBar;
    @BindView(R.id.seek_bar_text_view)
    TextView seekBarTextView;
    @BindView(R.id.minValue)
    EditText minValueEditText;
    @BindView(R.id.maxValue)
    EditText maxValueEditText;

    private ArrayList<String> companyArrayList;
    private ArrayList<Integer> doorArrayList;
    private ArrayList<String> enginePowerArrayList;

    private ArrayAdapter companyArrayAdapter;
    private ArrayAdapter doorArrayAdapter;
    private ArrayAdapter enginePowerArrayAdapter;
    private String companyText;
    private String horsePowerIntervalText;
    private int safetyLevel;
    private int doorAmount;
    private int minValue;
    private int maxValue;

    CarSpecification carSpecification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupViews();
        final Intent i = new Intent(this, ScoresActivity.class);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatherData();
                startActivity(i);
            }
        });
    }

    private void setupViews(){

        companyArrayList = new ArrayList<String>();
        companyArrayList.add("Volkswagen");
        companyArrayList.add("Skoda");
        companyArrayList.add("Mercedes");
        companyArrayList.add("Lexus");
        companyArrayList.add("Fiat");
        companyArrayList.add("Ford");
        companyArrayList.add("Seat");
        companyArrayList.add("Mazda");
        companyArrayList.add("Toyota");

        doorArrayList = new ArrayList<Integer>();
        doorArrayList.add(3);
        doorArrayList.add(5);

        enginePowerArrayList = new ArrayList<String>();
        enginePowerArrayList.add("zakres 1");
        enginePowerArrayList.add("zakres 2");
        enginePowerArrayList.add("zakres 3");
        enginePowerArrayList.add("zakres 4");
        enginePowerArrayList.add("zakres 5");

        companyArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, companyArrayList);
        doorArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, doorArrayList);
        enginePowerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, enginePowerArrayList);

        companySpinner.setAdapter(companyArrayAdapter);
        doorAmountSpinner.setAdapter(doorArrayAdapter);
        enginePowerSpinner.setAdapter(enginePowerArrayAdapter);

//        String companyText;
        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                companyText = parent.getItemAtPosition(position).toString();
                carSpecification.setCompanyName(companyText);
                Log.i("selected ", companyText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        doorAmountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doorAmount = Integer.parseInt(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        enginePowerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    horsePowerIntervalText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        safetySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                seekBarTextView.setText("" + progress);
                seekBarTextView.setX(seekBar.getX() + val +seekBar.getThumbOffset() / 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarTextView.setVisibility(View.VISIBLE);
            }
        });

    }

    private void gatherData(){

        safetyLevel = Integer.parseInt(seekBarTextView.getText().toString());
        minValue = Integer.parseInt(minValueEditText.getText().toString());
        maxValue = Integer.parseInt(maxValueEditText.getText().toString());

        carSpecification = new CarSpecification(companyText,
                                                horsePowerIntervalText,
                                                safetyLevel, doorAmount,
                                                minValue, maxValue);
    }

}
