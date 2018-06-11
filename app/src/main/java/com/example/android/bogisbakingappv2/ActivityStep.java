package com.example.android.bogisbakingappv2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Bogi on 2018. 05. 08..
 */

public class ActivityStep extends AppCompatActivity{
    public String name;
    private FragmentStep fragmentStep = new FragmentStep();
    private FragmentManager manager2 = getSupportFragmentManager();

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void loadStep()
    {
        manager2.beginTransaction()
                .add(R.id.content_frame_recipe_steps,fragmentStep)
                .commit();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(savedInstanceState==null) {
        loadStep(); }
    }
}
