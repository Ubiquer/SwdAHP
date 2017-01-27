package com.example.arek.swdahp;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Property;
import android.view.MenuItem;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

import butterknife.BindView;

public class ScoresActivity extends AppCompatActivity {

    @BindView(R.id.example_car_icon)
    ImageView exampleCarImageView;

    public CarSpecification [] cars  = new CarSpecification[7];
    public CarSpecification [] carsShuffeled  = new CarSpecification[7];
    public int [] newIndices = new int[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        Intent intent = getIntent();
        String companyText = intent.getStringExtra("companyText");
        double [] bestResults = intent.getDoubleArrayExtra("bestResults");

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
                break;
        }
        newIndices = insertionSort(bestResults);
        for (int i=cars.length-1 ; i>=0; i--) {
            carsShuffeled[i] = cars[newIndices[i]];
        }

        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        CardAdapter cardAdapterr = new CardAdapter(cars, companyText);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(cardAdapterr);

//        rv.setHasFixedSize(true);

    }
    public static double[] copyOf(double[] original, int newLength) {
        double[] copy = new double[newLength];
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }
    public static int[] insertionSort(double[] arr){
        int[] indices = new int[arr.length];
        indices[0] = 0;
        for(int i=1;i<arr.length;i++){
            int j=i;
            for(;j>=1 && arr[j]<arr[j-1];j--){
                double temp = arr[j];
                arr[j] = arr[j-1];
                indices[j]=indices[j-1];
                arr[j-1] = temp;
            }
            indices[j]=i;
        }
        return indices;//indices of sorted elements
    }



}

