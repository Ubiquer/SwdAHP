package com.example.arek.swdahp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.cropToPadding;
import static android.R.attr.rating;

public class MainActivity extends AppCompatActivity {

    double [] criteria = new double[6];
    double [] userParametersMin = new double[6];
    double [] userParametersMax = new double[6];

    @BindView(R.id.search)
    FloatingActionButton searchButton;
    @BindView(R.id.company_spinner)
    MaterialBetterSpinner companySpinner;
    @BindView(R.id.engine_power_spinner)
    MaterialBetterSpinner enginePowerSpinner;
//    @BindView(R.id.safety_seek_bar)
//    SeekBar safetySeekBar;
//    @BindView(R.id.seek_bar_text_view)
//    TextView seekBarTextView;
    @BindView(R.id.minValue)
    EditText minValueEditText;
    @BindView(R.id.maxValue)
    EditText maxValueEditText;
    @BindView(R.id.safety_ranking)
    RatingBar ratingBar;
    @BindView(R.id.car_icon)
    ImageButton imageButton;

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
//                gatherData();
                startActivity(i);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ahpStart();
            }
        });

        getStarsValue();

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
//        doorAmountSpinner.setAdapter(doorArrayAdapter);
        enginePowerSpinner.setAdapter(enginePowerArrayAdapter);

//        String companyText;
        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                companyText = parent.getItemAtPosition(position).toString();
                carSpecification.setModelName(companyText);
                Log.i("selected ", companyText);

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

    }

    private void gatherData(){

//        safetyLevel = Integer.parseInt(seekBarTextView.getText().toString());
        minValue = Integer.parseInt(minValueEditText.getText().toString());
        maxValue = Integer.parseInt(maxValueEditText.getText().toString());

//        carSpecification = new CarSpecification(companyText,
//                                                horsePowerIntervalText,
//                                                safetyLevel, doorAmount,
//                                                minValue, maxValue);
    }

    public void getStarsValue() {

        //Performing action on Button Click
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(getApplicationContext(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ahpStart(){
        CarSpecification [] cars  = new CarSpecification[10];

        //ponizej wybor marki (switch)
        cars[0] = new CarSpecification("autok", 45,23,24,64,13,42);
        cars[1] = new CarSpecification("autok", 30,23,24,64,13,42);
        cars[2] = new CarSpecification("autok", 12,23,24,64,13,42);
        cars[3] = new CarSpecification("autok", 100,23,24,64,13,42);
        cars[4] = new CarSpecification("autok", 10,23,24,64,13,42);
        cars[5] = new CarSpecification("autok", 143,23,24,64,13,42);
        cars[6] = new CarSpecification("autok", 120,23,24,64,13,42);
        cars[7] = new CarSpecification("autok", 22,23,24,64,13,42);
        cars[8] = new CarSpecification("autok", 5,23,24,64,13,42);
        cars[9] = new CarSpecification("autok", 200,23,24,64,13,42);

        criteria[0] = 1;
        criteria[1] = 3;
        criteria[2] = 4;
        criteria[3] = 5;
        criteria[4] = 7;
        criteria[5] = 9;

        userParametersMin[0] = 10;
        userParametersMax[0] = 15;
        userParametersMin[1] = 5;
        userParametersMin[1] = 6;
        userParametersMin[2] = 23;
        userParametersMax[2] = 25;
        userParametersMin[3] = 50;
        userParametersMin[3] = 55;
        userParametersMin[4] = 21;
        userParametersMax[4] = 25;
        userParametersMin[5] = 10;
        userParametersMin[5] = 14;



        AHP ahp = new AHP();
        ahp.process(criteria, cars, userParametersMin, userParametersMax);


    }


}
