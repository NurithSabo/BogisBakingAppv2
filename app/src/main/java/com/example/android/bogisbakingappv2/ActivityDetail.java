package com.example.android.bogisbakingappv2;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Bogi on 2018. 05. 05..
 */

public class ActivityDetail extends ActivityMain {

    public String name;
    LinearLayout lay;
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
        lay = (LinearLayout) findViewById(R.id.reszletes_lepes_layout);

        if (getSupportActionBar() != null && getIntent().getExtras() != null) {
            name = getIntent().getExtras().getString(ActivityMain.NAME);
            setTitle(name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        else{System.out.println("böeeeeeeeeee");}

       //https://stackoverflow.com/questions/31277979/why-is-it-necessary-to-check-savedinstancestate-inside-of-oncreate
         if(savedInstanceState == null)
            {
                if( isTablet == false)
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
