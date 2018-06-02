package com.chatlocalybusiness.fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.MobiComKitConstants;
import com.applozic.mobicomkit.api.conversation.MobiComMessageService;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.applozic.mobicomkit.api.notification.MuteNotificationAsync;
import com.applozic.mobicomkit.api.notification.MuteNotificationRequest;
import com.applozic.mobicomkit.broadcast.BroadcastService;
import com.applozic.mobicomkit.feed.ApiResponse;
import com.applozic.mobicomkit.uiwidgets.conversation.MobiComKitBroadcastReceiver;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.applozic.mobicomkit.uiwidgets.conversation.chatLocaly.ChatLocallyAppLozicPrefrence;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.HomeActivity;
import com.chatlocalybusiness.adapter.AdapterForChatThreads;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.ResponseModel;
import com.chatlocalybusiness.apiModel.ChatThreadListModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 12/19/2017.
 */
public class ChatThreadFragment extends Fragment {


    RecyclerView rv_threads;
    LinearLayoutManager layoutManager;
    AdapterForChatThreads adapterForChatThreads;
    ChatBusinessSharedPreference preference;
    ProgressBar progressBar;
    private MessageDatabaseService messageDatabaseService;
    public static int untagCount = 0;
    private boolean _hasLoadedOnce = false; // your boolean field
    SwipeRefreshLayout swipeRefresh;
    RelativeLayout rl_noInternet;
    TextView tv_tryAgain;
    private ImageView iv_noInternet;
    List<ChatThreadListModel.MessageList> messageLists;
    int unreadCount = 0;
    long millisecond;
    private MuteNotificationRequest muteNotificationRequest;


    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);


   /*     if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                _hasLoadedOnce = true;*/
        if (untagCount > 0) {
            getChatThreadApi();

//                }
//            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatthread, container, false);
        this.messageDatabaseService = new MessageDatabaseService(getActivity());
        preference = new ChatBusinessSharedPreference(getActivity());
        rv_threads = (RecyclerView) view.findViewById(R.id.rv_threads);
        layoutManager = new LinearLayoutManager(getActivity());
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        rl_noInternet = (RelativeLayout) view.findViewById(R.id.rl_noInternet);
        tv_tryAgain = (TextView) view.findViewById(R.id.tv_tryAgain);
        iv_noInternet = (ImageView) view.findViewById(R.id.iv_noInternet);

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
        getChatThreadApi();

        return view;
    }

    BroadcastReceiver unreadCountBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MobiComKitConstants.APPLOZIC_UNREAD_COUNT.equals(intent.getAction())) {
                unreadCount = new MessageDatabaseService(context).getTotalUnreadCount();
                if (getContext() != null)
                    getChatThreadApi();
                //Update unread count in UI
               /* if (adapterForChatThreads != null)

                    adapterForChatThreads.notifyDataSetChanged();*/
            }
        }
    };

    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(unreadCountBroadcastReceiver, new IntentFilter(MobiComKitConstants.APPLOZIC_UNREAD_COUNT));

        if (MobiComMessageService.chatupdate == 1) {

            getChatThreadApi();

        }
        if (ConversationActivity.UNREAD_COUNT == 1) {
            if (adapterForChatThreads != null)
                adapterForChatThreads.notifyDataSetChanged();
            ConversationActivity.UNREAD_COUNT = 0;
            HomeActivity.setThreadTabCount(new MessageDatabaseService(getActivity()).getUnreadConversationCount());
        } else {
            ViewGroup drawer_layout = (ViewGroup) getActivity().findViewById(R.id.drawer_layout);
            new BasicUtill().CheckStatus(getActivity(), 0, drawer_layout);
        }
        if (untagCount > 0) {
            getChatThreadApi();
        }
        if (ConversationActivity.MESSAGE_SENT == 1) {
            ChatLocallyAppLozicPrefrence chatLocallyAppLozicPrefrence = new ChatLocallyAppLozicPrefrence(getActivity());
            if (messageLists != null) {
                findPosition(Integer.parseInt((preference.getChatGroupId())));
                if (position != -1) {
                    messageLists.get(position).setToFullName(preference.getUser_NAME());
                    messageLists.get(position).setContentType(chatLocallyAppLozicPrefrence.getMessageContentType());
                    messageLists.get(position).setMessage(chatLocallyAppLozicPrefrence.getMessage());
                    messageLists.get(position).setCreatedAtTime(chatLocallyAppLozicPrefrence.getMessageTime());

                    messageLists.add(0, messageLists.get(position));
                    messageLists.remove(position + 1);
                    adapterForChatThreads.notifyData(messageLists);
                    ConversationActivity.MESSAGE_SENT = 0;
                    position = -1;
                }
            }
        }
    }

    int position = -1;

    public void findPosition(int chatGroupId) {
        for (int i = 0; i < messageLists.size(); i++) {
            if (chatGroupId == messageLists.get(i).getGroupId()) {
                position = i;
                break;
            }

        }
    }

    public void setRecyclerView(List<ChatThreadListModel.MessageList> messageList) {
        adapterForChatThreads = new AdapterForChatThreads(getActivity(), messageList, this);
        rv_threads.setAdapter(adapterForChatThreads);
        rv_threads.setLayoutManager(layoutManager);


//        registerForContextMenu(rv_threads);
    }

    public void getChatThreadApi() {
        if (MobiComMessageService.chatupdate == 1)
            MobiComMessageService.chatupdate = 0;
        else if (unreadCount > 0)
            unreadCount = 0;
        else
            progressBar.setVisibility(View.VISIBLE);

        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "jgb");
        param.put("b_user_id", preference.getUserId());
        param.put("user_id", "0B" + preference.getUserId());
        param.put("device_key", preference.getDeviceKey());

        Call<ChatThreadListModel> call = apiServices.getChatThread(param);
        call.enqueue(new Callback<ChatThreadListModel>() {
            @Override
            public void onResponse(Call<ChatThreadListModel> call, Response<ChatThreadListModel> response) {
                rl_noInternet.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        messageLists = response.body().getData().getMessageList();
                        untagCount = 0;
                        setRecyclerView(messageLists);
                        if (messageLists != null)
                            if (messageLists.size() < 1) {
                                rl_noInternet.setVisibility(View.VISIBLE);
                                iv_noInternet.setImageResource(R.drawable.no_threads);
                            }
                    } else if (response.body().getData().getMessageList() == null) {
                        rl_noInternet.setVisibility(View.VISIBLE);
                        iv_noInternet.setImageResource(R.drawable.no_threads);
                    }
                }
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ChatThreadListModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);

                rl_noInternet.setVisibility(View.VISIBLE);
//                Toast.makeText(getActivity(), "check internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void blockUserApi(final Context context, final int position, final List<ChatThreadListModel.MessageList> messageList, String block_remove) {
        progressBar.setVisibility(View.VISIBLE);

        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "fgusdhf");
        param.put("chat_group_id", messageList.get(position).getChatGroupId());
        param.put("b_user_id", preference.getUserId());
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("block_type", block_remove);

        Call<ResponseModel> call = apiServices.blockusers(param);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        ChatTagsFragment.tagCount = 1;
//                        messageList.remove(position);
                        if (messageList.get(position).getIsBlocked()){
                            getChatThreadApi();

                            Toast.makeText(context, "Customer Blocked", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            getChatThreadApi();

                            Toast.makeText(context, "Customer Unblocked", Toast.LENGTH_SHORT).show();

                        }
                        adapterForChatThreads.notifyData(messageList);
                    } else
                        Toast.makeText(getActivity(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void tagPeopleApi(final Context context, final int position, final List<ChatThreadListModel.MessageList> messageList, String tag) {
        progressBar.setVisibility(View.VISIBLE);

        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "fgusdhf");
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("b_user_id", preference.getUserId());
        param.put("chat_group_id", messageList.get(position).getChatGroupId());
        param.put("tag_type", tag);

        Call<ResponseModel> call = apiServices.tagPeople(param);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        ChatTagsFragment.tagCount = 1;

                        if (messageList.get(position).getBusinessTags().equals("0")) {
                            messageList.get(position).setBusinessTags("1");
                            Toast.makeText(context, "Customer Taged", Toast.LENGTH_SHORT).show();

                        }else {
                            messageList.get(position).setBusinessTags("0");
                            Toast.makeText(context, "Customer Untaged", Toast.LENGTH_SHORT).show();

                        }adapterForChatThreads.notifyData(messageList);


//                        messageList.remove(position);
//                        adapterForChatThreads.notifyData(messageList);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void notification_on(final int adapterPosition, final List<ChatThreadListModel.MessageList> messageList) {
//      customer_tag?encrypt_key=fgfdsg&tag_type=tag&c_user_id=1&b_user_id=1&b_id=1&

        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "fgusdhf");
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("b_user_id", preference.getUserId());
        param.put("c_user_id", messageList.get(adapterPosition).getCUserId());
        if (messageList.get(adapterPosition).getBusinessNotification().equals("1"))
            param.put("flag_type", "add");
        else param.put("flag_type", "remove");
        param.put("chat_group_id", messageList.get(adapterPosition).getChatGroupId());

        Call<ResponseModel> call = apiServices.notification_on(param);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        Toast.makeText(getActivity(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                        if (messageList.get(adapterPosition).getBusinessNotification().equals("0"))
                            messageList.get(adapterPosition).setBusinessNotification("1");
                        else
                            messageList.get(adapterPosition).setBusinessNotification("0");
                        adapterForChatThreads.notifyData(messageList);
                        ChatTagsFragment.tagCount = 1;

                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void muteGroupChat(final int channelKey, final int adapterPosition, final List<ChatThreadListModel.MessageList> messageList) {

        final CharSequence[] items = {getString(com.applozic.mobicomkit.uiwidgets.R.string.eight_Hours), getString(com.applozic.mobicomkit.uiwidgets.R.string.one_week), getString(com.applozic.mobicomkit.uiwidgets.R.string.one_year)};
        Date date = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
        millisecond = date.getTime();
        progressBar.setVisibility(View.VISIBLE);

        final MuteNotificationAsync.TaskListener taskListener = new MuteNotificationAsync.TaskListener() {

            @Override
            public void onSuccess(ApiResponse apiResponse) {
//                Toast.makeText(getActivity(), "muted successfully", Toast.LENGTH_SHORT).show();
                notification_on(adapterPosition, messageList);


            }

            @Override
            public void onFailure(ApiResponse apiResponse, Exception exception) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCompletion() {
                progressBar.setVisibility(View.GONE);

            }
        };

        millisecond = millisecond + 31558000000L;
        muteNotificationRequest = new MuteNotificationRequest(channelKey, millisecond);
        MuteNotificationAsync muteNotificationAsync = new MuteNotificationAsync(getContext(), taskListener, muteNotificationRequest);
        muteNotificationAsync.execute((Void) null);


/*
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(com.applozic.mobicomkit.uiwidgets.R.string.mute_group_for))
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, final int selectedItem) {
                        if (selectedItem == 0) {
                            millisecond = millisecond + 28800000;
                        } else if (selectedItem == 1) {
                            millisecond = millisecond + 604800000;

                        } else if (selectedItem == 2) {
                            millisecond = millisecond + 31558000000L;
                        }

                        muteNotificationRequest = new MuteNotificationRequest(channelKey, millisecond);
                        muteNotificationRequest = new MuteNotificationRequest(channelKey);
                        MuteNotificationAsync muteNotificationAsync = new MuteNotificationAsync(getContext(), taskListener, muteNotificationRequest);
                        muteNotificationAsync.execute((Void) null);
                        dialog.dismiss();

                    }
                });
        AlertDialog alertdialog = builder.create();
        alertdialog.show();*/
    }

    public void umuteGroupChat(final int channelKey, final int adapterPosition, final List<ChatThreadListModel.MessageList> messageList) {
        Date date = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
        millisecond = date.getTime();
        progressBar.setVisibility(View.VISIBLE);

        final MuteNotificationAsync.TaskListener taskListener = new MuteNotificationAsync.TaskListener() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
//                Toast.makeText(getActivity(), "unmuted successfully", Toast.LENGTH_SHORT).show();
                notification_on(adapterPosition, messageList);
            }

            @Override
            public void onFailure(ApiResponse apiResponse, Exception exception) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCompletion() {
                progressBar.setVisibility(View.GONE);

            }
        };
        muteNotificationRequest = new MuteNotificationRequest(channelKey, millisecond);
        MuteNotificationAsync muteNotificationAsync = new MuteNotificationAsync(getContext(), taskListener, muteNotificationRequest);
        muteNotificationAsync.execute((Void) null);


    }

}
