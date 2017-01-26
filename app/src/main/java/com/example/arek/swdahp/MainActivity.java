package com.example.arek.swdahp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    double [] criteria = new double[6];
    double [] userParametersMin = new double[6];
    double [] userParametersMax = new double[6];

    private boolean userIsInteracting;

    @BindView(R.id.min_year_of_production)
    Spinner minProductionYear;
    @BindView(R.id.max_year_of_production)
    Spinner maxProductionYear;
    @BindView(R.id.comfort_seek_bar)
    AppCompatSeekBar comfortSeekBar;
    @BindView(R.id.search)
    FloatingActionButton searchButton;
    @BindView(R.id.company_spinner)
    Spinner companySpinner;
    @BindView(R.id.minValue)
    EditText minValueEditText;
    @BindView(R.id.maxValue)
    EditText maxValueEditText;
    @BindView(R.id.safety_ranking)
    RatingBar ratingBar;
    @BindView(R.id.car_icon)
    ImageButton imageButton;
    @BindView(R.id.comfort_text_view)
    TextView comfortPercentValue;
    @BindView(R.id.min_horse_power)
    EditText minHorsePower;
    @BindView(R.id.max_horse_power)
    EditText maxHorsePower;

    public double minCostValue;
    public double maxCostValue;

    public double minProductionYearDouble;
    public double maxProductionYearDouble;

    private String comfortLevel;

    private ArrayList<String> companyArrayList;
    private ArrayList<Integer> doorArrayList;
    private ArrayList<String> enginePowerArrayList;
    private ArrayList<String> minProductionYearArrayList;
    private ArrayList<String> maxProductionYearArrayList;

    private ArrayAdapter companyArrayAdapter;
    private ArrayAdapter doorArrayAdapter;
    private ArrayAdapter minProductionYearArrayAdapter;
    private ArrayAdapter maxProductionYearArrayAdapter;
    private String companyText;
    private String horsePowerIntervalText;
    private int safetyLevel;
    private int minValue;
    private int maxValue;

    CarSpecification carSpecification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setupViews();
        final Intent i = new Intent(this, ScoresActivity.class);

        updateComfortLevel(comfortSeekBar.getProgress());

        comfortSeekBar.setOnSeekBarChangeListener(this);
        maxProductionYear.setEnabled(false);

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

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }

    private void setupViews(){

        companyArrayList = new ArrayList<String>();
        companyArrayList.add("Wybierz markę");
        companyArrayList.add("Volkswagen");
        companyArrayList.add("Skoda");
        companyArrayList.add("Mercedes");
        companyArrayList.add("Lexus");
        companyArrayList.add("Fiat");
        companyArrayList.add("Ford");
        companyArrayList.add("Seat");
        companyArrayList.add("Mazda");
        companyArrayList.add("Toyota");

        minProductionYearArrayList =  new ArrayList<String>(Arrays.asList("2011","2012","2013","2014","2015","2016"));

        maxProductionYearArrayList = new ArrayList<String>(Arrays.asList("2011","2012","2013","2014","2015","2016"));

        companyArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, companyArrayList);
        doorArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, doorArrayList);
        minProductionYearArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,minProductionYearArrayList);
        maxProductionYearArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,maxProductionYearArrayList);

        minProductionYear.setAdapter(minProductionYearArrayAdapter);
        maxProductionYear.setAdapter(maxProductionYearArrayAdapter);
        companySpinner.setAdapter(companyArrayAdapter);

        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                companyText = (String) companySpinner.getSelectedItem();
                Log.d("rok double ", companyText +" ");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                companySpinner.setPrompt("Marka");
            }
        });


        minProductionYear.setSelection(0,false);
        minProductionYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String minProdString = (String) minProductionYear.getSelectedItem();
                minProductionYearDouble = Double.parseDouble(minProdString);
                Log.i("rok", minProdString);
                Log.d("rok double ", minProductionYearDouble +" ");
                maxProductionYear.setEnabled(true);

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


    public void ahpStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                CarSpecification [] cars  = new CarSpecification[7];

                //ponizej wybor marki (switch)
                cars[0] = new CarSpecification("autok", 45,22,67,1,24,45);
                cars[1] = new CarSpecification("autok", 30,25,31,90,62,32);
                cars[2] = new CarSpecification("autok", 12,24,32,64,32,13);
                cars[3] = new CarSpecification("autok", 17,56,14,86,45,55);
                cars[4] = new CarSpecification("autok", 10,67,1,77,42,44);
                cars[5] = new CarSpecification("autok", 14,89,89,64,12,48);


                criteria[0] = 1;
                criteria[1] = 3;
                criteria[2] = 4;
                criteria[3] = 5;
                criteria[4] = 7;
                criteria[5] = 9;

                userParametersMin[0] = 10;
                userParametersMax[0] = 15;
                userParametersMin[1] = 5;
                userParametersMax[1] = 6;
                userParametersMin[2] = 23;
                userParametersMax[2] = 25;
                userParametersMin[3] = 50;
                userParametersMax[3] = 55;
                userParametersMin[4] = 21;
                userParametersMax[4] = 25;
                userParametersMin[5] = 10;
                userParametersMax[5] = 14;
                AHP ahp = new AHP();
                ahp.process(criteria, cars, userParametersMin, userParametersMax);
            }
        }).start();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        updateComfortLevel(seekBar.getProgress());
//        Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

//        Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        updateComfortLevel(seekBar.getProgress());
//        Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();

    }

    private void updateComfortLevel(int comfort) {

        if(comfort<=25)
            comfortLevel = "niski komfort";
        else if(comfort>25 && comfort<=50)
            comfortLevel = "średni komfort";
        else if(comfort>50 && comfort<=75)
            comfortLevel = "dobry komfort";
        else comfortLevel = "wysoki komfort";

        comfortPercentValue.setText(comfortLevel);
    }


}
