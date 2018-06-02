package com.chatlocalybusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterForSettingsOption;
import com.chatlocalybusiness.utill.BasicUtill;

/**
 * Created by Shiv on 12/20/2017.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onResume() {
        super.onResume();
        ViewGroup rl_main=(ViewGroup)findViewById(R.id.rl_main);

        new BasicUtill().CheckStatus(SettingsActivity.this,0,rl_main);
    }
    private ImageView iv_arrowBack ;
    private RecyclerView rv_settingsOptions;
    private LinearLayoutManager layoutManager;
    private AdapterForSettingsOption adapterForSettingsOption;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);
        iv_arrowBack.setOnClickListener(this);
        setRv_settingsOptions();

    }


      public void setRv_settingsOptions()
      {
          rv_settingsOptions=(RecyclerView)findViewById(R.id.rv_settingsOptions);
          layoutManager=new LinearLayoutManager(this);
          adapterForSettingsOption=new AdapterForSettingsOption(this);
          rv_settingsOptions.setLayoutManager(layoutManager);
          rv_settingsOptions.setAdapter(adapterForSettingsOption);
      }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;

        }
    }


}
