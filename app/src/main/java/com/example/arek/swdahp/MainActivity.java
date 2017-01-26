package com.example.arek.swdahp;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Property;
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

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

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
    EditText minHorsePowerEditText;
    @BindView(R.id.max_horse_power)
    EditText maxHorsePowerEditText;
    @BindView(R.id.min_kilometers_done)
    EditText minKilometersDone;
    @BindView(R.id.max_kilometers_done)
    EditText maxKilometersDone;

    public double comfort;

    public double minProductionYearDouble;
    public double maxProductionYearDouble;
    public double bestResults [];

    private String comfortLevel;

    private ArrayList<String> companyArrayList;
    private ArrayList<String> minProductionYearArrayList;
    private ArrayList<String> maxProductionYearArrayList;

    private ArrayAdapter companyArrayAdapter;
    private ArrayAdapter minProductionYearArrayAdapter;
    private ArrayAdapter maxProductionYearArrayAdapter;
    private String companyText;
    private double safetyLevel;;


    public CarSpecification [] cars  = new CarSpecification[7];

    public  MainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setupViews();



        updateComfortLevel(comfortSeekBar.getProgress());

        comfortSeekBar.setOnSeekBarChangeListener(this);
        maxProductionYear.setEnabled(false);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (checkShit()){
//                    gatherData();
//                }else{
//                    Toast.makeText(getApplicationContext(),"Wszystkie pola muszą być wypełnione ",
//                            Toast.LENGTH_LONG).show();
//                }
                gatherData();
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
        companyArrayList.add("Mercedes");
        companyArrayList.add("BMW");
        companyArrayList.add("Audi");
        companyArrayList.add("Volvo");

        minProductionYearArrayList =  new ArrayList<String>(Arrays.asList("2011","2012","2013","2014","2015","2016"));

        maxProductionYearArrayList = new ArrayList<String>(Arrays.asList("2011","2012","2013","2014","2015","2016"));

        companyArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, companyArrayList);
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

        maxProductionYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String minProdString = (String) maxProductionYear.getSelectedItem();
                maxProductionYearDouble = Double.parseDouble(minProdString);
//                Log.i("rok", minProdString);
//                Log.d("rok double ", minProductionYearDouble +" ");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void getStarsValue() {

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(getApplicationContext(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
                safetyLevel = Double.parseDouble(String.valueOf(ratingBar.getRating()));
            }
        });
    }


    public void ahpStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                criteria[0] = 1;
                criteria[1] = 3;
                criteria[2] = 4;
                criteria[3] = 5;
                criteria[4] = 7;
                criteria[5] = 9;
                //mercedesy (horsePower, safetyLevel, cost, year, comfort, kilometersDone);
                switch(companyText){
                    case "Mercedes":
                    cars[0] = new CarSpecification("Mercedes Klasa C  S205", 230,81,340000,2014,90,3000);
                    cars[1] = new CarSpecification("Mercedes Klasa C Coupe C205", 285,77,364000,2015,88,12000);
                    cars[2] = new CarSpecification("Mercedes Klasa C W205", 320,89,289000,2013,95,31000);
                    cars[3] = new CarSpecification("Mercedes Klasa E Coupe C207", 220,72,310000,2014,82,55000);
                    cars[4] = new CarSpecification("Mercedes Klasa E W212", 245,79,230000,2012,88,69000);
                    cars[5] = new CarSpecification("Mercedes Klasa E TS213", 260,85,390000,2016,95,7000);
                        break;

                    case "BMW":
                //BMW
                cars[0] = new CarSpecification("BMW Seria 5 G30", 370,84,450000,2017,90,1000);
                cars[1] = new CarSpecification("BMW Seria 5 F10", 190,63,210000,2012,75,89000);
                cars[2] = new CarSpecification("BMW Seria 5 Touring F11", 215,68,244000,2014,82,59000);
                cars[3] = new CarSpecification("BMW Seria 4 Gran Coupe F36", 260,58,195000,2012,51,95000);
                cars[4] = new CarSpecification("BMW Seria 4 Cabrio F33", 320,49,280000,2014,77,33000);
                cars[5] = new CarSpecification("BMW Seria 4 Coupe F32", 300,88,390000,2016,95,81000);
                        break;

                    case "Audi":
                //Audi
                cars[0] = new CarSpecification("Audi A6 RS6 Avant 4G", 430,72,389000,2017,81,2000);
                cars[1] = new CarSpecification("Audi A6 Allroad", 250,79,160000,2011,88,90000);
                cars[2] = new CarSpecification("Audi A6 VAN S6 Avant", 300,91,220000,2013,99,79000);
                cars[3] = new CarSpecification("Audi A7 RS7 Sportback", 510,65,330000,2015,78,33000);
                cars[4] = new CarSpecification("Audi A7 S7", 190,66,280000,2016,69,28000);
                cars[5] = new CarSpecification("Audi A7 VAN S7", 210,80,210000,2012,92,54000);
                        break;

                    case "Volvo":
                //Volvo
                cars[0] = new CarSpecification("Volvo S60", 130,45,100000,2016,58,10000);
                cars[1] = new CarSpecification("Volvo S90", 190,68,199000,2017,77,8000);
                cars[2] = new CarSpecification("Volvo V60", 175,74,120000,2013,60,90000);
                cars[3] = new CarSpecification("Volvo V90", 150,60,170000,2016,89,35000);
                cars[4] = new CarSpecification("Volvo XC60", 210,85,90000,2011, 80, 90000);
                cars[5] = new CarSpecification("Volvo CX90", 250,90,210000,2015,98,79000);
                        break;
                    default:
                        cars[0] = new CarSpecification("Mercedes Klasa C  S205", 230,81,340000,2014,90,3000);
                        cars[1] = new CarSpecification("Mercedes Klasa C Coupe C205", 285,77,364000,2015,88,12000);
                        cars[2] = new CarSpecification("Mercedes Klasa C W205", 320,89,289000,2013,95,31000);
                        cars[3] = new CarSpecification("Mercedes Klasa E Coupe C207", 220,72,310000,2014,82,55000);
                        cars[4] = new CarSpecification("Mercedes Klasa E W212", 245,79,230000,2012,88,69000);
                        cars[5] = new CarSpecification("Mercedes Klasa E TS213", 260,85,390000,2016,95,7000);
                }



                AHP ahp = new AHP();
                ahp.process(criteria, cars, userParametersMin, userParametersMax);
                bestResults = ahp.bestResult();
                final Intent intentSecond = new Intent(MainActivity.this, ScoresActivity.class);
                intentSecond.putExtra("companyText", companyText);
                intentSecond.putExtra("bestResults", bestResults);
                startActivity(intentSecond);
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
        comfort = Double.parseDouble(String.valueOf(seekBar.getProgress()));

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


    private void gatherData(){

//        userParametersMin[0] = Double.parseDouble(minHorsePowerEditText.getText().toString());
//        userParametersMax[0] = Double.parseDouble(maxHorsePowerEditText.getText().toString());
//        userParametersMin[1] = safetyLevel*20;
//        userParametersMax[1] = (safetyLevel*20)+10;
//        userParametersMin[2] = Double.parseDouble(minValueEditText.getText().toString());
//        userParametersMax[2] = Double.parseDouble(maxValueEditText.getText().toString());
//        userParametersMin[3] = minProductionYearDouble;
//        userParametersMax[3] = maxProductionYearDouble;
//        userParametersMin[4] = comfort;
//        userParametersMax[4] = comfort + 10;
//        userParametersMin[5] = Double.parseDouble(minKilometersDone.getText().toString());
//        userParametersMax[5] = Double.parseDouble(maxKilometersDone.getText().toString());

        userParametersMin[0] = 100;
        userParametersMax[0] = 180;
        userParametersMin[1] = 60;
        userParametersMax[1] = 80;
        userParametersMin[2] = 100000;
        userParametersMax[2] = 180000;
        userParametersMin[3] = 2012;
        userParametersMax[3] = 2016;
        userParametersMin[4] = 70;
        userParametersMax[4] = 90;
        userParametersMin[5] = 1000;
        userParametersMax[5] = 50000;

        ahpStart();

    }

    public boolean checkShit(){

        if( maxValueEditText.getText().toString().equals("") || minValueEditText.getText().toString().equals("")
                || maxKilometersDone.getText().toString() == "" || minKilometersDone == null || ratingBar.getRating() == 0
                || minHorsePowerEditText.getText().toString() == "" || maxHorsePowerEditText.getText().toString() == "" ||
                companySpinner.getSelectedItem() == null || minProductionYear.getSelectedItem() == null
                || maxProductionYear.getSelectedItem() == null){

            return false;

        }else{

            return true;
        }



    }

}
