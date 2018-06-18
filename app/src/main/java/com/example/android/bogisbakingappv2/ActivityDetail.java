package com.example.android.bogisbakingappv2;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;

/**
 * Created by Bogi on 2018. 05. 05..
 */

public class ActivityDetail extends ActivityMain {

    private String name;
    private LinearLayout lay;
    public DataStep lepes;


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    private void detailLoad()
    {
        FragmentDetail fragmentDetail = new FragmentDetail();
        FragmentManager manager1 = getSupportFragmentManager();
        manager1.beginTransaction()
                .add(R.id.content_frame_recipe_details,fragmentDetail)
                .commit();
    }

    private void stepLoad()
    {
            FragmentStep fragmentStep = new FragmentStep();
            FragmentManager manager1 = getSupportFragmentManager();
            manager1.beginTransaction()
                    .add(R.id.content_frame_recipe_steps, fragmentStep)
                    .commit();
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        boolean isTablet = ActivityMain.tabletSize;//getResources().getBoolean(R.bool.isTablet);
        lay = findViewById(R.id.reszletes_lepes_layout);

        if (getSupportActionBar() != null && getIntent().getExtras() != null) {
            name = getIntent().getExtras().getString(ActivityMain.NAME);
            setTitle(name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

       //https://stackoverflow.com/questions/31277979/why-is-it-necessary-to-check-savedinstancestate-inside-of-oncreate
         if(savedInstanceState == null)
            {
                if(!isTablet)
                    {
                        detailLoad();
                    }
                else
                    {
                        detailLoad();
                        stepLoad();
                    }
            }
    }
}
