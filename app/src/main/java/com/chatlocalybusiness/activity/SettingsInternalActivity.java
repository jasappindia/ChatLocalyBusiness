package com.chatlocalybusiness.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.SettingsInternalModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shiv on 12/20/2017.
 */
public class SettingsInternalActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll_newWoker;

    @Override
    protected void onResume() {
        super.onResume();
        if (FeaturesSettingsActivity.featureSettings == 1)
            getBusinessWorkerList();
        else {
            ViewGroup rl_main = (ViewGroup) findViewById(R.id.rl_main);
            new BasicUtill().CheckStatus(SettingsInternalActivity.this, 0, rl_main);
        }
    }

    private ImageView iv_arrowBack, iv_remove;
    private RecyclerView rv_internalSubadmin;
    private RecyclerView rv_internalworker;
    private RecyclerView rv_remaining_manager;
    private RecyclerView rv_remaining_worker;
    private LinearLayoutManager layoutManagersubadmin;
    private LinearLayoutManager layoutManagerworker;
    private LinearLayoutManager layoutManage_noMnager;
    private LinearLayoutManager layoutManager_nowWrker;
    private AdapterForInternalSubadmin adapterForInternalSubadmin;
    private AdapterForInternalWorker adapterForInternalWorker;
    private RemainingManagerAdapter remainingManagerAdapter;
    private RemainingWorkerAdapter remainingWorkerAdapter;
    private Utill utill;
    private ChatBusinessSharedPreference preference;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsinternal);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ll_newWoker = (LinearLayout) findViewById(R.id.ll_newWoker);
        preference = new ChatBusinessSharedPreference(this);
        utill = new Utill();
        iv_arrowBack.setOnClickListener(this);
        getBusinessWorkerList();

    }

    public void setRvNoManager(SettingsInternalModel.BusinessUsers businessManagerList) {
        rv_remaining_manager = (RecyclerView) findViewById(R.id.rv_remaining_manager);
        layoutManage_noMnager = new LinearLayoutManager(this);
        remainingManagerAdapter = new RemainingManagerAdapter(this, businessManagerList);
        rv_remaining_manager.setLayoutManager(layoutManage_noMnager);
        rv_remaining_manager.setAdapter(remainingManagerAdapter);
    }
  public void setRvNoWorker(SettingsInternalModel.BusinessUsers businessManagerList) {
      rv_remaining_worker = (RecyclerView) findViewById(R.id.rv_remaining_worker);
      layoutManager_nowWrker = new LinearLayoutManager(this);
      remainingWorkerAdapter = new RemainingWorkerAdapter(this, businessManagerList);
      rv_remaining_worker.setLayoutManager(layoutManager_nowWrker);
      rv_remaining_worker.setAdapter(remainingWorkerAdapter);
    }
  public void setRvSubadmin(List<SettingsInternalModel.BusinessManager> businessManagerList) {
        rv_internalSubadmin = (RecyclerView) findViewById(R.id.rv_internalSubadmin);
        layoutManagersubadmin = new LinearLayoutManager(this);
        adapterForInternalSubadmin = new AdapterForInternalSubadmin(this, businessManagerList);
        rv_internalSubadmin.setLayoutManager(layoutManagersubadmin);
        rv_internalSubadmin.setAdapter(adapterForInternalSubadmin);
    }

    public void setRvWorker(List<SettingsInternalModel.BusinessWorker> businessWorkerList) {
        rv_internalworker = (RecyclerView) findViewById(R.id.rv_internalworker);
        layoutManagerworker = new LinearLayoutManager(this);
        adapterForInternalWorker = new AdapterForInternalWorker(this, businessWorkerList);
        rv_internalworker.setLayoutManager(layoutManagerworker);
        rv_internalworker.setAdapter(adapterForInternalWorker);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
        }
    }

    public class AdapterForInternalSubadmin extends RecyclerView.Adapter<AdapterForInternalSubadmin.MyViewHolder> {
        Context context;

        List<SettingsInternalModel.BusinessManager> businessManagerList;

        public AdapterForInternalSubadmin(Context context, List<SettingsInternalModel.BusinessManager> businessManagerList) {
            this.context = context;
            this.businessManagerList = businessManagerList;
        }

        @Override
        public AdapterForInternalSubadmin.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_settings_internal, parent, false);
            return new AdapterForInternalSubadmin.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AdapterForInternalSubadmin.MyViewHolder holder, int position) {

            holder.tv_empName.setText(businessManagerList.get(position).getBFullName());
            holder.tv_designation.setText("~ " + businessManagerList.get(position).getDesignation());

        }

        @Override
        public int getItemCount() {
            if (businessManagerList != null)
                return businessManagerList.size();
            else
                return 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_empName, tv_designation;
            RelativeLayout rl_subadmin;
            ImageView iv_remove;


            public MyViewHolder(View itemView) {

                super(itemView);
                rl_subadmin = (RelativeLayout) itemView.findViewById(R.id.rl_subadmin);
                tv_designation = (TextView) itemView.findViewById(R.id.tv_designation);
                tv_empName = (TextView) itemView.findViewById(R.id.tv_empName);
                iv_remove = (ImageView) ((Activity) context).findViewById(R.id.iv_remove);
                rl_subadmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, FeaturesSettingsActivity.class);
                        intent.putExtra(Constants.Employee, businessManagerList.get(getAdapterPosition()).getBFullName());
                        intent.putExtra(Constants.EmployeeID, businessManagerList.get(getAdapterPosition()).getBUserId());
                        intent.putExtra(Constants.DESIGNATION, businessManagerList.get(getAdapterPosition()).getDesignation());

                        context.startActivity(intent);
                    }
                });

            }
        }
    }

    private class AdapterForInternalWorker extends RecyclerView.Adapter<AdapterForInternalWorker.MyViewHolder> {
        Context context;
        List<SettingsInternalModel.BusinessWorker> businessWorkerList;

        public AdapterForInternalWorker(Context context, List<SettingsInternalModel.BusinessWorker> businessWorkerList) {
            this.context = context;
            this.businessWorkerList = businessWorkerList;

        }


        @Override
        public AdapterForInternalWorker.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_settings_internal, parent, false);
            return new AdapterForInternalWorker.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AdapterForInternalWorker.MyViewHolder holder, int position) {
            holder.tv_empName.setText(businessWorkerList.get(position).getBFullName());
            holder.tv_designation.setText("~ " + businessWorkerList.get(position).getDesignation());

        }

        @Override
        public int getItemCount() {
            if (businessWorkerList != null)
                return businessWorkerList.size();
            else
                return 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_empName, tv_designation;
            RelativeLayout rl_subadmin;
            ImageView iv_remove;


            public MyViewHolder(View itemView) {

                super(itemView);
                rl_subadmin = (RelativeLayout) itemView.findViewById(R.id.rl_subadmin);
                tv_designation = (TextView) itemView.findViewById(R.id.tv_designation);
                tv_empName = (TextView) itemView.findViewById(R.id.tv_empName);
                iv_remove = (ImageView) ((Activity) context).findViewById(R.id.iv_remove);
                rl_subadmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, FeaturesSettingsActivity.class);
                        intent.putExtra(Constants.Employee, businessWorkerList.get(getAdapterPosition()).getBFullName());
                        intent.putExtra(Constants.EmployeeID, businessWorkerList.get(getAdapterPosition()).getBUserId());
                        intent.putExtra(Constants.DESIGNATION, businessWorkerList.get(getAdapterPosition()).getDesignation());

                        context.startActivity(intent);
                    }
                });

            }
        }
    }

    public void getBusinessWorkerList() {
        progressBar.setVisibility(View.VISIBLE);
        //    business_api/business_user_list?encrypt_key=gdsgd&b_user_id=2&b_id=2

        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "shvfk");
        param.put("b_user_id", preference.getUserId());
        param.put("b_id", String.valueOf(preference.getBusinessId()));

        Call<SettingsInternalModel> call = apiServices.getBusinessWorkerList(param);
        call.enqueue(new Callback<SettingsInternalModel>() {
            @Override
            public void onResponse(Call<SettingsInternalModel> call, Response<SettingsInternalModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        List<SettingsInternalModel.BusinessManager> businessManagerList = response.body().getData().getBusinessUsers().getBusinessManager();
                        List<SettingsInternalModel.BusinessWorker> businessWorkerList = response.body().getData().getBusinessUsers().getBusinessWorker();



                        if (businessManagerList != null)
                            setRvSubadmin(businessManagerList);
                        if (businessWorkerList != null)
                            setRvWorker(businessWorkerList);
/*

                        if (businessManagerList == null && businessWorkerList != null)
                            ll_newWoker.setVisibility(View.VISIBLE);
                        else ll_newWoker.setVisibility(View.GONE);

*/

                        setRvNoManager(response.body().getData().getBusinessUsers());
                        setRvNoWorker(response.body().getData().getBusinessUsers());
                        FeaturesSettingsActivity.featureSettings = 0;
                    }
                }


            }

            @Override
            public void onFailure(Call<SettingsInternalModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SettingsInternalActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private class RemainingManagerAdapter extends RecyclerView.Adapter<RemainingManagerAdapter.MyViewHolder> {
        Context context;
        SettingsInternalModel.BusinessUsers businessUsers;

        public RemainingManagerAdapter(Context context, SettingsInternalModel.BusinessUsers businessUsers) {
            this.context = context;
            this.businessUsers = businessUsers;

        }


        @Override
        public RemainingManagerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_no_manager, parent, false);
            return new RemainingManagerAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RemainingManagerAdapter.MyViewHolder holder, int position) {
            holder.tv_empName.setText("New Sub Admin");
            holder.tv_designation.setText("~ " + "Manager");

        }

        @Override
        public int getItemCount() {
            if (businessUsers != null)
                return businessUsers.getRemaining_managers_limit();
            else
                return 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_empName, tv_designation;
            RelativeLayout rl_subadmin;
            ImageView iv_remove;


            public MyViewHolder(View itemView) {

                super(itemView);
                rl_subadmin=(RelativeLayout)findViewById(R.id.rl_subadmin);
                tv_designation = (TextView) itemView.findViewById(R.id.tv_designation);
                tv_empName = (TextView) itemView.findViewById(R.id.tv_empName);
                iv_remove = (ImageView) ((Activity) context).findViewById(R.id.iv_remove);
                rl_subadmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        Toast.makeText(SettingsInternalActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }

    private class RemainingWorkerAdapter extends RecyclerView.Adapter<RemainingWorkerAdapter.MyViewHolder> {
        Context context;
        SettingsInternalModel.BusinessUsers businessUsers;

        public RemainingWorkerAdapter(Context context,SettingsInternalModel.BusinessUsers businessUsers) {
            this.context = context;
            this.businessUsers = businessUsers;

        }


        @Override
        public RemainingWorkerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_no_manager, parent, false);
            return new RemainingWorkerAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RemainingWorkerAdapter.MyViewHolder holder, int position) {
            holder.tv_empName.setText("New Worker");
            holder.tv_designation.setText("~ " + "Worker");

        }

        @Override
        public int getItemCount() {
            if (businessUsers != null)
                return businessUsers.getRemaining_workers_limit();
            else
                return 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_empName, tv_designation;
            RelativeLayout rl_subadmin;
            ImageView iv_remove;


            public MyViewHolder(View itemView) {

                super(itemView);
                rl_subadmin=(RelativeLayout)findViewById(R.id.rl_subadmin);

                tv_designation = (TextView) itemView.findViewById(R.id.tv_designation);
                tv_empName = (TextView) itemView.findViewById(R.id.tv_empName);
                iv_remove = (ImageView) ((Activity) context).findViewById(R.id.iv_remove);
                rl_subadmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        Toast.makeText(SettingsInternalActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }

}


