package com.example.arek.swdahp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Patryk on 26.01.2017.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.rank_and_image_card_view)
    CardView carImage;
    @BindView(R.id.model_name)
    TextView modelName;
    @BindView(R.id.horse_power)
    TextView horsePower;
    @BindView(R.id.safety_level)
    TextView safety;
    @BindView(R.id.cost_level)
    TextView cost;
    @BindView(R.id.year_production)
    TextView year;
    @BindView(R.id.kilometers_done)
    TextView kilometersDone;
    @BindView(R.id.comfort_level)
    TextView comfort;


    public CardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

}
