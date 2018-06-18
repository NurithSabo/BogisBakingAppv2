package com.example.android.bogisbakingappv2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bogi on 2018. 05. 06..
 */

public class AdapterShowSteps extends RecyclerView.Adapter<AdapterShowSteps.RecipeSteps> {

    private final Context mContext;
    private OnItemClickListener mListener;
    private ArrayList<DataStep> mSteps = new ArrayList<>();


    @NonNull
    @Override
    public RecipeSteps onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recy_steps,parent,false);
        return new RecipeSteps(v);
    }


    @Override
    public void onBindViewHolder(@NonNull RecipeSteps holder, int position) {
        DataStep currentStep;
        try {
            currentStep = mSteps.get(position);
            String shortStep = currentStep.getShortDescription();

            holder.mTextView.setText(shortStep);
            holder.mImageView.setImageResource(R.drawable.arrow_right);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if(mSteps != null){
        return  mSteps.size();}
        else{return 1;}
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdapterShowSteps(Context mContext, ArrayList<DataStep> mSteps) {
        this.mContext = mContext;
        this.mSteps = mSteps;
    }


    public class RecipeSteps extends RecyclerView.ViewHolder {

        public final TextView mTextView;
        public final ImageView mImageView;

        public RecipeSteps(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.step_description);
            mImageView = itemView.findViewById(R.id.arrow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    }
