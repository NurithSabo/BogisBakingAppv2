package com.example.android.bogisbakingappv2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity implements Adapter1ShowMainScreen.OnItemClickListener{

    public static final String ID = "0";
    public static final String NAME = "name";
    public static final String INGREDIENTS = "ing";
    public static final String STEPS = "steps";
    public static final ArrayList<DataStep> STEP = new ArrayList<>();
    public static final String SERVINGS = "1";
    public static final String IMAGE_URL = "imageUrl";

    public static boolean tabletSize;

    private RecyclerView mRecyclerview;
    private Adapter1ShowMainScreen mAdapter1ShowMainScreen;
    private ArrayList<DataRecipe> mRecipeList;
    private RequestQueue mRequestQueue;

    private ProgressBar progBar;

    public static ArrayList<DataIngredient> ingredList;
    public static ArrayList<DataStep> stepList;

    private void loadImageData(){
        mRecipeList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJsonForImage();

    }

    private void parseJsonForImage(){
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try
                        {
                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONObject eredmeny = response.getJSONObject(i);
                                String id = eredmeny.getString("id");
                                String name = eredmeny.getString("name");

                                JSONArray jsonArrayIngredients = eredmeny.getJSONArray("ingredients");
                                ArrayList<DataIngredient> ingredients = new ArrayList<>();

                                for (int j = 0; j < jsonArrayIngredients.length(); j++)
                                {
                                    JSONObject ingredient = jsonArrayIngredients.getJSONObject(j);
                                    String mennyiseg = ingredient.getString("quantity");
                                    String meret = ingredient.getString("measure");
                                    String hozzavalo = ingredient.getString("ingredient");

                                    ingredients.add(new DataIngredient(Double.parseDouble(mennyiseg), meret, hozzavalo));
  // Log.e("ing",""+ingredients.get(j).getIngredientName());
                                }

                                JSONArray jsonArraySteps = eredmeny.getJSONArray("steps");
                                ArrayList<DataStep> steps = new ArrayList<>();

                                for(int j = 0; j < jsonArraySteps.length(); j++)
                                {
                                    JSONObject step = jsonArraySteps.getJSONObject(j);
                                    String stepId = step.getString("id");
                                    String shortDescription = step.getString("shortDescription");
                                    String description = step.getString("description");
                                    String videoUrl = step.getString("videoURL");
                                    String thumbnailURL = step.getString("thumbnailURL");

                                    steps.add(new DataStep(Integer.parseInt(stepId),shortDescription,description,videoUrl,thumbnailURL));

                                }

                                String servings = eredmeny.getString("servings");
                                String image = eredmeny.getString("image");

                                mRecipeList.add(new DataRecipe
                                        (id, name, ingredients, steps, Integer.parseInt(servings), image));

                            }
                            mAdapter1ShowMainScreen = new Adapter1ShowMainScreen(ActivityMain.this, mRecipeList);
                            mRecyclerview.setAdapter(mAdapter1ShowMainScreen);
                            mAdapter1ShowMainScreen.setOnItemClickListener(ActivityMain.this);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyHiba",error.getMessage());

                {Log.e("kkk", "Nincs hálóóóóóóóóóóóóóóóóóóóóóóóóóóóóóóóóó");}
                //error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        loadImageData();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerview = (RecyclerView) findViewById(R.id.recipesList);
        mRecyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager manager;

        progBar = findViewById(R.id.progressBar);
        tabletSize = getResources().getBoolean(R.bool.isTablet);

        String answer = "";
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                answer="You are connected to a WiFi Network";
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                answer="You are connected to a Mobile Network";
        }
        else{

            answer = "No internet Connectivity";}

        Toast.makeText(ActivityMain.this, answer,
                Toast.LENGTH_LONG).show();
        Log.e("llll",answer+"");

        if (tabletSize)
        {
            progBar.setVisibility(View.INVISIBLE);
            manager = new GridLayoutManager(this,3);

        }
        else
        {
            progBar.setVisibility(View.INVISIBLE);

            manager = new LinearLayoutManager(this);
        }
        loadImageData();
        mRecyclerview.setLayoutManager(manager);

    }


    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(this, ActivityDetail.class);
        DataRecipe clicked = mRecipeList.get(position);

        intent.putExtra(NAME, clicked.getName());
        ingredList = clicked.getIngredients();
        stepList = clicked.getSteps();

        intent.putExtra(INGREDIENTS, ingredList);
        intent.putExtra(STEPS, stepList);
        startActivity(intent);
    }
}
