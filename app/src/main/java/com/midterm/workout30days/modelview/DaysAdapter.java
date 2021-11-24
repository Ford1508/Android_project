package com.midterm.workout30days.modelview;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.midterm.workout30days.R;
import com.midterm.workout30days.model.Day;
import com.midterm.workout30days.view.MainActivity;
import com.midterm.workout30days.view.ExerciseActivity;

import java.util.ArrayList;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder>{

    public Context mContext;

    public DaysAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public DaysAdapter(ArrayList<Day> list) {
        this.list = list;
    }

    ArrayList<Day> list;

    MainActivity mainActivity;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day = list.get(position);
        if(day == null)
        {
            return;
        }
        holder.txtDay.setText(Integer.toString(day.getId()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlphaAnimation cardViewClick = new AlphaAnimation(1F, 0.8F);
                view.startAnimation(cardViewClick);
                Intent intent = new Intent(view.getContext(), ExerciseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("day_id", day.getId());
                bundle.putString("day_content", day.getContent());
                bundle.putString("day_excercises", day.getExercises());
                bundle.putBoolean("day_completed", day.isComplete());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        if(day.isComplete() == true)
        {
            holder.cardView.setBackgroundColor(Color.YELLOW);
        }
    }

    @Override
    public int getItemCount() {
        if(list != null)
        {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtDay;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txt_day);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
