package com.midterm.workout30days.modelview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.midterm.workout30days.R;
import com.midterm.workout30days.model.Excercise;
import com.midterm.workout30days.view.ExerciseDetailsActivity;

import java.util.ArrayList;

public class ExcerciseAdapter extends RecyclerView.Adapter<ExcerciseAdapter.ViewHolder>{
    ArrayList<Excercise> list;
    Context context;
    public View view;

    public ExcerciseAdapter(Context context) {
        this.context = context;
    }

    public ExcerciseAdapter(ArrayList<Excercise> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list == null)
        {
            return;
        }
        Excercise excercise = list.get(position);
        holder.txtExerciseName.setText(excercise.getContent());
        Glide.with(view.getContext()).load(excercise.getGif()).into(holder.imageViewExercise);
        holder.cardViewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ExerciseDetailsActivity.class);
                AlphaAnimation cardViewClick = new AlphaAnimation(1F, 0.8F);
                view.startAnimation(cardViewClick);
                intent.putExtra("id_key", Integer.toString(excercise.getId()));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null)
        {
            return 0;
        }
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewExercise;
        TextView txtExerciseName;
        CardView cardViewExercise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewExercise = itemView.findViewById(R.id.image_view);
            txtExerciseName = itemView.findViewById(R.id.txt_practise_name);
            cardViewExercise = itemView.findViewById(R.id.card_view_practise);
        }
    }
}
