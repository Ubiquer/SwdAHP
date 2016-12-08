package com.example.arek.swdahp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;

public class ScoresActivity extends AppCompatActivity {

    @BindView(R.id.example_car_icon)
    ImageView exampleCarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
    }
}
