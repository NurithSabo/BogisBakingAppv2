package com.example.android.bogisbakingappv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by Bogi on 2018. 05. 06..
 */

public class FragmentDetail extends Fragment implements Adapter2ShowSteps.OnItemClickListener{

    ArrayList<DataIngredient> hozzavalok = ActivityMain.ingredList;;
    TextView ingList;
    public static ArrayList<DataStep> lepesek;
    private Adapter2ShowSteps mAdapterShowSteps;
    private Context mContext;
    RecyclerView mRecyclerview;
    public String STEP = "step";
    public static DataStep lepeske;
    public static int currentSelection;
    public static String video;
    FragmentStep fs = new FragmentStep();


    public ArrayList<DataStep> getLepesek() {
        return lepesek;
    }

    public FragmentDetail(){}

    public void ingredients(ArrayList<DataIngredient> lista, TextView textView)
    {
        textView.setText(null);
        try {
            for (int i = 0; i < lista.size(); i++) {
                textView.append(lista.get(i).getIngredientName() + ": " +
                        lista.get(i).getQuantity() + " " +
                        lista.get(i).getMeasure().toLowerCase() + "\n");

            }
        }
        catch (NullPointerException nPE)
        {
            Log.e("kaki","van a palacsintában");
            nPE.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ingList = (TextView) view.findViewById(R.id.recipe_ingredients);

            ingredients(hozzavalok,ingList);
           // Log.e("hozzavalok:", ""+hozzavalok.get(0).getIngredientName());


        lepesek = ActivityMain.stepList;
        mContext = getActivity().getApplicationContext();

        mRecyclerview = view.findViewById(R.id.recipe_details_steps);
        mRecyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager manager;
        mAdapterShowSteps = new Adapter2ShowSteps(mContext,lepesek);
        mRecyclerview.setAdapter(mAdapterShowSteps);
        mAdapterShowSteps.setOnItemClickListener(this);
        manager = new LinearLayoutManager(mContext);
        mRecyclerview.setLayoutManager(manager);


        try {
            if(lepesek.get(FragmentStep.tempSelection).getVideoUrl()!=null
                    || lepesek.get(FragmentStep.tempSelection).getVideoUrl()!="")
            {
            fs.setVideoString(lepesek.get(FragmentStep.tempSelection).getVideoUrl());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return view;
    }



    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(mContext, ActivityStep.class);
        DataStep clickedLepes = null;
        try {
            clickedLepes = lepesek.get(position);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        intent.putExtra(STEP, clickedLepes);
        //Log.e("lepes", clickedLepes.getDescription());
        lepeske = clickedLepes;
        currentSelection = position;


        if(!ActivityMain.tabletSize)
        {
            startActivity(intent);
        }

   else{

        if(clickedLepes != null)
        {
            video = clickedLepes.getVideoUrl();

            if(clickedLepes.getVideoUrl()!=null || clickedLepes.getVideoUrl()!=""){
                fs.setVideoString(clickedLepes.getVideoUrl());

                 FragmentStep fragmentStep = new FragmentStep();
                 FragmentManager manager = getFragmentManager();
                 manager.beginTransaction()
                .replace(R.id.content_frame_recipe_steps,fragmentStep)
                .commit();
        FragmentStep.tempSelection = position;

       //FragmentStep.lepes.setText(clickedLepes.getDescription());

            }
        }
    }
    }
}
