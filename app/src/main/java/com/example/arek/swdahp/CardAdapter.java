package com.example.arek.swdahp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Created by Patryk on 26.01.2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    public CarSpecification [] cars = new CarSpecification[7];
    private LayoutInflater inflator;
    private AppCompatActivity activity;
    public int[] images = new int[cars.length];


    public CardAdapter(CarSpecification [] cars, String companyText){
        this.cars = cars;
        switch (companyText) {
            case "Mercedes":
                images[0] = R.drawable.car0;
                images[1] = R.drawable.car1;
                images[2] = R.drawable.car2;
                images[3] = R.drawable.car3;
                images[4] = R.drawable.car4;
                images[5] = R.drawable.car5;
                break;
            case "BMW":
                images[0] = R.drawable.car6;
                images[1] = R.drawable.car7;
                images[2] = R.drawable.car8;
                images[3] = R.drawable.car9;
                images[4] = R.drawable.car10;
                images[5] = R.drawable.car11;
                break;
            case "Audi":
                images[0] = R.drawable.car12;
                images[1] = R.drawable.car13;
                images[2] = R.drawable.car14;
                images[3] = R.drawable.car15;
                images[4] = R.drawable.car16;
                images[5] = R.drawable.car17;
                break;
            case "Volvo":
                images[0] = R.drawable.car18;
                images[1] = R.drawable.car19;
                images[2] = R.drawable.car20;
                images[3] = R.drawable.car21;
                images[4] = R.drawable.car22;
                images[5] = R.drawable.car23;
                break;
            }
        }



    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card, parent, false);
        CardViewHolder cwh = new CardViewHolder(v);
        return cwh;

    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {


        holder.carImage.setBackgroundResource(images[position]);
        holder.modelName.setText("Model: "+cars[position].getModelName());
        holder.horsePower.setText("Moc: "+String.valueOf(cars[position].returnParameter("horsePower")) );
        holder.safety.setText("Bezpieczeństwo: "+String.valueOf(cars[position].returnParameter("safetyLevel")) );
        holder.cost.setText("Koszt: "+String.valueOf(cars[position].returnParameter("cost")) );
        holder.year.setText("Rocznik: "+String.valueOf(cars[position].returnParameter("year")) );
        holder.comfort.setText("Poziom komfortu:: "+String.valueOf(cars[position].returnParameter("comfort")) );
        holder.kilometersDone.setText("Przebieg: "+String.valueOf(cars[position].returnParameter("kilometersDone")) );
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

}
