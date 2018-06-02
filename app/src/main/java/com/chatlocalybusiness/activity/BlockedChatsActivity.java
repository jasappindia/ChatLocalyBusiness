package com.chatlocalybusiness.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterForBlockedChat;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.BlockThreadListModel;
import com.chatlocalybusiness.apiModel.ChatThreadListModel;
import com.chatlocalybusiness.apiModel.ResponseModel;
import com.chatlocalybusiness.fragments.ChatTagsFragment;
import com.chatlocalybusiness.fragments.ChatThreadFragment;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shiv on 12/20/2017.
 */

public class BlockedChatsActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewGroup rl_main;
    private ProgressBar progressBar;
    ChatBusinessSharedPreference preference;
    private RelativeLayout rl_noInternet;
    ImageView iv_noInternet;
    TextView tv_tryAgain;

    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(BlockedChatsActivity.this,0,rl_main);
    }
    private RecyclerView rv_blockedChats;
    private LinearLayoutManager layoutManager;
    private AdapterForBlockedChat adapterForBlockedChat;
    private LinearLayout ll_filter,ll_sort;
    private ImageView iv_arrowBack;
    private TextView tv_filter,tv_sort;
    private AlertDialog dialog;

    SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blockedstats);
        preference=new ChatBusinessSharedPreference(this);
        rl_main=(ViewGroup)findViewById(R.id.rl_main);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        iv_arrowBack=(ImageView)findViewById(R.id.iv_arrowBack);
        ll_filter=(LinearLayout)findViewById(R.id.ll_filter);
        ll_sort=(LinearLayout)findViewById(R.id.ll_sort);
        tv_filter=(TextView) findViewById(R.id.tv_filter);
        tv_sort=(TextView) findViewById(R.id.tv_sort);
        tv_sort=(TextView) findViewById(R.id.tv_sort);
        tv_tryAgain=(TextView) findViewById(R.id.tv_tryAgain);
        iv_noInternet=(ImageView) findViewById(R.id.iv_noInternet);
        rl_noInternet=(RelativeLayout) findViewById(R.id.rl_noInternet);
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipeRefresh);

        tv_tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChatThreadApi();
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getChatThreadApi();

            }
        });
        rv_blockedChats=(RecyclerView)findViewById(R.id.rv_blockedChats);
        layoutManager=new LinearLayoutManager(this);

        ll_filter.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        ll_sort.setOnClickListener(this);
        getChatThreadApi();

//        setRecycleView(messageLists);
    }
    public void setRecycleView(List<BlockThreadListModel.MessageList> messageLists)
    {

        adapterForBlockedChat=new AdapterForBlockedChat(this,messageLists,this);
        rv_blockedChats.setLayoutManager(layoutManager);
        rv_blockedChats.setAdapter(adapterForBlockedChat);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.ll_filter:
                dialog =filterDialog();
              /*  dialog.show();
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.discription_bg));*/
                break;
            case R.id.ll_sort:
                dialog=  sortDialog();

                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;

        }
    }

    public AlertDialog sortDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        final String[] array={"Recent First","Most Bills"};
        int position=0;

        if(tv_sort.getText().toString().trim().equalsIgnoreCase(array[0]))
            position=0;
        else if(tv_sort.getText().toString().trim().equalsIgnoreCase(array[1]))
            position=1;

        builder.setTitle("Sort").setCancelable(true);
        builder.setSingleChoiceItems(array,position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                tv_sort.setText(array[i]);
                getChatThreadApi();

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        }
        return dialog;
    }
    public AlertDialog filterDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final String[] array={"All Threads","By You","By Who"};

        int position=0;

        if(tv_filter.getText().toString().trim().equalsIgnoreCase(array[0]))
            position=0;
        else if(tv_filter.getText().toString().trim().equalsIgnoreCase(array[1]))
            position=1;

        else if(tv_filter.getText().toString().trim().equalsIgnoreCase(array[2]))
            position=2;

        builder.setTitle("Filter").setCancelable(true);

        builder.setSingleChoiceItems(array,position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialog.dismiss();
                tv_filter.setText(array[i]);
                getChatThreadApi();

            }
        });
        final AlertDialog dialog=builder.create();
        dialog.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        }

        return dialog;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void getChatThreadApi()
    {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
        HashMap<String,String> param=new HashMap<>();
        param.put("encrypt_key","jgb");
        param.put("b_user_id",preference.getUserId());
        param.put("user_id","0B"+preference.getUserId());
        param.put("device_key",preference.getDeviceKey());
        param.put("sort_by",tv_sort.getText().toString());
        param.put("filter_by",tv_filter.getText().toString());

        Call<BlockThreadListModel> call=apiServices.getBlockedList(param);
        call.enqueue(new Callback<BlockThreadListModel>() {
            @Override
            public void onResponse(Call<BlockThreadListModel> call, Response<BlockThreadListModel> response) {
                rl_noInternet.setVisibility(View.GONE);

                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful())
                {
                    if(response.body().getData().getResultCode().equals("1"))
                    {
                        List<BlockThreadListModel.MessageList> messageLists=response.body().getData().getMessageList();
                        if(messageLists!=null)
                            if(messageLists.size()<1)
                            {
                                rl_noInternet.setVisibility(View.VISIBLE);
                                iv_noInternet.setImageResource(R.drawable.no_blocked_stats);
                            }

                        setRecycleView(messageLists );

                    }
                  else  if(response.body().getData().getMessageList()==null)
                    {
                        rl_noInternet.setVisibility(View.VISIBLE);
                        iv_noInternet.setImageResource(R.drawable.no_blocked_stats);
                    }


                }
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<BlockThreadListModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                rl_noInternet.setVisibility(View.VISIBLE);
//                Toast.makeText(BlockedChatsActivity.this, "check internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void  blockUserApi(final Context context, final int position, final List<BlockThreadListModel.MessageList> messageList, String block_remove)
    {
        progressBar.setVisibility(View.VISIBLE);

        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
        HashMap<String,String> param=new HashMap<>();
        param.put("encrypt_key","fgusdhf");
        param.put("chat_group_id",messageList.get(position).getChatGroupId());
        param.put("b_user_id",preference.getUserId());
        param.put("b_id",String.valueOf(preference.getBusinessId()));
        param.put("block_type",block_remove);

        Call<ResponseModel> call=apiServices.blockusers(param);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful())
                {
                    if(response.body().getData().getResultCode().equals("1"))
                    {
                        Toast.makeText(context,"Successfully blocked user",Toast.LENGTH_SHORT).show();

                        messageList.remove(position);
                        adapterForBlockedChat.notifyData(messageList);
                        ChatThreadFragment.untagCount=1;
                        ChatTagsFragment.tagCount=1;
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(context,"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void  tagPeopleApi(final Context context, final int position, final List<BlockThreadListModel.MessageList> messageList)
    {
        progressBar.setVisibility(View.VISIBLE);
//   customer_tag?encrypt_key=fgfdsg&tag_type=tag&c_user_id=1&b_user_id=1&b_id=1&

        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
        HashMap<String,String> param=new HashMap<>();
        param.put("encrypt_key","fgusdhf");
        param.put("b_id",String.valueOf(preference.getBusinessId()));
        param.put("b_user_id",preference.getUserId());
        param.put("chat_group_id",messageList.get(position).getChatGroupId());
        param.put("tag_type","tag");

        Call<ResponseModel> call=apiServices.tagPeople(param);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful())
                {
                    if(response.body().getData().getResultCode().equals("1"))
                    {
                        Toast.makeText(context,"Successfully tagged user",Toast.LENGTH_SHORT).show();
                        ChatTagsFragment.tagCount=1;

                        if(  messageList.get(position).getBusinessTags().equals("0"))
                            messageList.get(position).setBusinessTags("1");
                        else
                            messageList.get(position).setBusinessTags("0");
                        adapterForBlockedChat.notifyData(messageList);


//                        messageList.remove(position);
//                        adapterForChatThreads.notifyData(messageList);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(context,"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void notification_on(final Context context, final int adapterPosition, final List<BlockThreadListModel.MessageList> messageList) {
        progressBar.setVisibility(View.VISIBLE);
//   customer_tag?encrypt_key=fgfdsg&tag_type=tag&c_user_id=1&b_user_id=1&b_id=1&

        ApiServices apiServices= ApiClient.getClient().create(ApiServices.class);
        HashMap<String,String> param=new HashMap<>();
        param.put("encrypt_key","fgusdhf");
        param.put("b_id",String.valueOf(preference.getBusinessId()));
        param.put("b_user_id",preference.getUserId());
        param.put("c_user_id",messageList.get(adapterPosition).getCUserId());
        if(messageList.get(adapterPosition).getBusinessNotification().equals("0"))
            param.put("flag_type","add");
        else param.put("flag_type","remove");
        param.put("chat_group_id",messageList.get(adapterPosition).getChatGroupId());

        Call<ResponseModel> call=apiServices.notification_on(param);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful())
                {
                    if(response.body().getData().getResultCode().equals("1"))
                    {
                        Toast.makeText(context,response.body().getData().getMessage(),Toast.LENGTH_SHORT).show();
                        if(  messageList.get(adapterPosition).getBusinessNotification().equals("0"))
                            messageList.get(adapterPosition).setBusinessNotification("1");
                        else
                            messageList.get(adapterPosition).setBusinessNotification("0");
                        adapterForBlockedChat.notifyData(messageList);
                        ChatTagsFragment.tagCount=1;

                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(context,"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }


}
