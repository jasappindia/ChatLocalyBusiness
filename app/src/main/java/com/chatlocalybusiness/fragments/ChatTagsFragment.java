package com.chatlocalybusiness.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
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
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.applozic.mobicomkit.uiwidgets.conversation.chatLocaly.ChatLocallyAppLozicPrefrence;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.HomeActivity;
import com.chatlocalybusiness.adapter.AdapterForTagsChats;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.ResponseModel;
import com.chatlocalybusiness.apiModel.ChatThreadListModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 12/19/2017.
 */
public class ChatTagsFragment extends Fragment {

    private RecyclerView rv_tags;
    private LinearLayoutManager layoutManager;
    private AdapterForTagsChats adapterForTagsChats;
    private ProgressBar progressBar;
    ChatBusinessSharedPreference preference;
    private boolean _hasLoadedOnce = false; // your boolean field
    public static int tagCount = 0;
    SwipeRefreshLayout swipeRefresh;
    RelativeLayout rl_noInternet;
    TextView tv_tryAgain;
    private ImageView iv_noInternet;
    ArrayList<ChatThreadListModel.MessageList> tag_messageLists;

    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);


      /*  if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                _hasLoadedOnce = true;*/
        if (tagCount > 0) {
            getChatThreadApi();

        }
//            }
//        }
    }

    int unreadCount = 0;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chattags, container, false);
        rv_tags = (RecyclerView) view.findViewById(R.id.rv_tags);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        preference = new ChatBusinessSharedPreference(getActivity());
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


    public void setRecyclerView(List<ChatThreadListModel.MessageList> messageList) {
        layoutManager = new LinearLayoutManager(getContext());
        adapterForTagsChats = new AdapterForTagsChats(getContext(), messageList, ChatTagsFragment.this);
        rv_tags.setAdapter(adapterForTagsChats);
        rv_tags.setLayoutManager(layoutManager);
    }

    public void getChatThreadApi() {
        if (MobiComMessageService.chatupdateTAg == 1)
            MobiComMessageService.chatupdateTAg = 0;
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
                        List<ChatThreadListModel.MessageList> messageLists = response.body().getData().getMessageList();
                        tag_messageLists = new ArrayList<>();
                        for (ChatThreadListModel.MessageList messageList : messageLists) {
                            if (messageList.getBusinessTags() != null)
                                if (messageList.getBusinessTags().equals("1"))
                                    tag_messageLists.add(messageList);

                        }
                        tagCount = 0;
                        setRecyclerView(tag_messageLists);
                        if (tag_messageLists != null)
                            if (tag_messageLists.size() < 1) {
                                rl_noInternet.setVisibility(View.VISIBLE);
                                iv_noInternet.setImageResource(R.drawable.no_tags);
                            }
                    } else if (response.body().getData().getMessageList() == null) {
                        rl_noInternet.setVisibility(View.VISIBLE);
                        iv_noInternet.setImageResource(R.drawable.no_tags);
                    }
                }
                swipeRefresh.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<ChatThreadListModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                rl_noInternet.setVisibility(View.VISIBLE);
//                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
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

//                        messageList.remove(position);
                        if (messageList.get(position).getIsBlocked()){
                            getChatThreadApi();
                            Toast.makeText(context, R.string.str_customer_blocked, Toast.LENGTH_SHORT).show();

                        }
                        else {
                            getChatThreadApi();
                            Toast.makeText(context, R.string.str_customer_unblocked, Toast.LENGTH_SHORT).show();

                        }
                        adapterForTagsChats.notifyData(messageList);
                        ChatThreadFragment.untagCount = 1;
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void unTagPeopleApi(final Context context, final int position, final List<ChatThreadListModel.MessageList> messageList) {
        progressBar.setVisibility(View.VISIBLE);
//   customer_tag?encrypt_key=fgfdsg&tag_type=tag&c_user_id=1&b_user_id=1&b_id=1&

        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "fgusdhf");
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("b_user_id", preference.getUserId());
        param.put("chat_group_id", messageList.get(position).getChatGroupId());
        param.put("tag_type", "Untag User");

        Call<ResponseModel> call = apiServices.tagPeople(param);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        Toast.makeText(context, R.string.str_customer_untaged, Toast.LENGTH_SHORT).show();
                        messageList.remove(position);
                        ChatThreadFragment.untagCount = 1;

                        adapterForTagsChats.notifyData(messageList);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(unreadCountBroadcastReceiver, new IntentFilter(MobiComKitConstants.APPLOZIC_UNREAD_COUNT));
        if (MobiComMessageService.chatupdateTAg == 1)
            getChatThreadApi();


        if (ConversationActivity.UNREAD_COUNT == 1) {
            adapterForTagsChats.notifyDataSetChanged();
            ConversationActivity.UNREAD_COUNT = 0;
        } else {
            ViewGroup drawer_layout = (ViewGroup) getActivity().findViewById(R.id.drawer_layout);
            new BasicUtill().CheckStatus(getActivity(), 0, drawer_layout);
        }
        if (ConversationActivity.MESSAGE_SENT_TAG == 1) {
            if (tag_messageLists != null) {
                findPosition(Integer.parseInt(preference.getChatGroupId()));
                ChatLocallyAppLozicPrefrence chatLocallyAppLozicPrefrence = new ChatLocallyAppLozicPrefrence(getActivity());
                if (position != -1) {
                    tag_messageLists.get(position).setToFullName(preference.getUser_NAME());
                    tag_messageLists.get(position).setContentType(chatLocallyAppLozicPrefrence.getMessageContentType());

                    tag_messageLists.get(position).setMessage(chatLocallyAppLozicPrefrence.getMessage());
                    tag_messageLists.get(position).setCreatedAtTime(chatLocallyAppLozicPrefrence.getMessageTime());

                    tag_messageLists.add(0, tag_messageLists.get(position));
                    tag_messageLists.remove(position + 1);
                    adapterForTagsChats.notifyDataSetChanged();
                    ConversationActivity.MESSAGE_SENT_TAG = 0;
                    position = -1;
                }
            }
        }
    }

    int position = -1;

    public void findPosition(int chatGroupId) {
        for (int i = 0; i < tag_messageLists.size(); i++) {
            if (chatGroupId == tag_messageLists.get(i).getGroupId()) {
                position = i;
                break;
            }

        }
    }

    public void notification_on(final Context context, final int adapterPosition, final List<ChatThreadListModel.MessageList> messageList) {
        progressBar.setVisibility(View.VISIBLE);
//   customer_tag?encrypt_key=fgfdsg&tag_type=tag&c_user_id=1&b_user_id=1&b_id=1&

        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", "fgusdhf");
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("b_user_id", preference.getUserId());
        param.put("c_user_id", messageList.get(adapterPosition).getCUserId());
        if (messageList.get(adapterPosition).getBusinessNotification().equals("0"))
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
                        Toast.makeText(context, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                        if (messageList.get(adapterPosition).getBusinessNotification().equals("0"))
                            messageList.get(adapterPosition).setBusinessNotification("1");
                        else messageList.get(adapterPosition).setBusinessNotification("0");
                        adapterForTagsChats.notifyData(messageList);
                        ChatThreadFragment.untagCount = 1;
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });

    }
}