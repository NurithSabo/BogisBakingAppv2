package com.example.android.bogisbakingappv2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Bogi on 2018. 05. 05..
 */

public class Adapter1ShowMainScreen extends RecyclerView.Adapter<Adapter1ShowMainScreen.ImageViewHolder>
{
    private final Context mContext;
    private OnItemClickListener mListener;
    private ArrayList<DataRecipe> mRecipes = new ArrayList<>();


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recy_recipes,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        DataRecipe currentRecipe = mRecipes.get(position);
        String imageUrl = currentRecipe.getImageUrl();
        String cakeName = currentRecipe.getName();


        final int radius = 10;
        final int margin = 10;

        final Transformation transformation = new RoundedCornersTransformation(radius, margin);
        holder.mTextView.setText(cakeName);

        if(!imageUrl.isEmpty())
        {
            Picasso.with(mContext)
                    .load(imageUrl)
                    .transform(transformation)
                    .into(holder.mImageView);
        }
        else
        {
            Picasso.with(mContext)
                    .load(R.drawable.cupcake)
                    .transform(transformation)
                    .into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount()
    {
        return mRecipes.size();
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public Adapter1ShowMainScreen(Context context, ArrayList<DataRecipe> mListOfImages) {
        mContext = context;
        mRecipes = mListOfImages;
    }

    public Adapter1ShowMainScreen(Context mContext) {
        this.mContext = mContext;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mImageView;
        public final TextView mTextView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mTextView = itemView.findViewById(R.id.recipe_name);

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
