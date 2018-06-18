package com.example.android.bogisbakingappv2;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Bogi on 2018. 05. 05..
 */

public class AdapterShowMainScreen extends RecyclerView.Adapter<AdapterShowMainScreen.ImageViewHolder>
{
    private final Context mContext;
    private OnItemClickListener mListener;
    private ArrayList<DataRecipe> mRecipes = new ArrayList<>();


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recy_recipes,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        final DataRecipe currentRecipe = mRecipes.get(position);
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


        ImageButton mImageButton = holder.imageButton;
        if(mImageButton!=null) {
            mImageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                   Toast.makeText(mContext, currentRecipe.getName()+" is added to your widget", Toast.LENGTH_LONG).show();

                    //widget:
                    ArrayList<DataIngredient> ingredientsWidget = currentRecipe.getIngredients();
                    StringBuilder builder = new StringBuilder();
                    for (int i=0; i< ingredientsWidget.size(); i++)
                    {
                        builder.append(
                                ingredientsWidget.get(i).quantity +" "+
                                        ingredientsWidget.get(i).measure +" of "+
                                        ingredientsWidget.get(i).ingredientName + "\n");
                    }

                    SharedPreferences preferences = mContext.getSharedPreferences("Recipe", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("ingredientsWidget", builder.toString());

                    editor.putString("title",currentRecipe.getName());
                    editor.apply();

                    int[] ids = AppWidgetManager.getInstance(((ActivityMain)mContext).getApplication()).getAppWidgetIds
                            (new ComponentName(((ActivityMain)mContext).getApplication(), WidgetCake.class));
                    WidgetCake myWidget = new WidgetCake();
                    myWidget.onUpdate(((ActivityMain)mContext).getBaseContext(), AppWidgetManager.getInstance(((ActivityMain)mContext).getBaseContext()),ids);
                }

            });
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

    public AdapterShowMainScreen(Context context, ArrayList<DataRecipe> mListOfImages) {
        mContext = context;
        mRecipes = mListOfImages;
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mImageView;
        public final TextView mTextView;
        public ImageButton imageButton;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mTextView = itemView.findViewById(R.id.recipe_name);
            imageButton = itemView.findViewById(R.id.heart_button);

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
