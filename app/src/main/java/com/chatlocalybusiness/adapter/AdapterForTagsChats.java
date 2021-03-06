package com.chatlocalybusiness.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.applozic.mobicomkit.channel.database.ChannelDatabaseService;
import com.applozic.mobicommons.commons.core.utils.DateUtils;
import com.applozic.mobicommons.people.channel.Channel;
import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.apiModel.ChatThreadListModel;
import com.chatlocalybusiness.chat.ApplozicBridge;
import com.chatlocalybusiness.fragments.ChatTagsFragment;
import com.chatlocalybusiness.fragments.ChatThreadFragment;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;

import java.util.List;

/**
 * Created by windows on 12/20/2017.
 */
public class AdapterForTagsChats extends RecyclerView.Adapter<AdapterForTagsChats.MyViewHolder> {
    Context context;
    List<ChatThreadListModel.MessageList> messageList;
    ChatBusinessSharedPreference prefrence;
    int adapterPosition;
    private MessageDatabaseService messageDatabaseService;
    private ChatTagsFragment chatTagFragment;
    int messageUnReadCount = 0;
    public static int threadPosition = 0;
    private AlertDialog alertdialog;

    public AdapterForTagsChats(Context context, List<ChatThreadListModel.MessageList> messageList, ChatTagsFragment chatTagFragment) {
        this.context = context;
        this.messageList = messageList;
        prefrence = new ChatBusinessSharedPreference(context);
        messageDatabaseService = new MessageDatabaseService(context);
        this.chatTagFragment = chatTagFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_tags, parent, false);
        return new AdapterForTagsChats.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_userName.setText(messageList.get(position).getFullName());
        holder.tv_lastuserName.setText(messageList.get(position).getToFullName() + " : ");
        messageUnReadCount = 0;
        try {
            final Channel channel = ChannelDatabaseService.getInstance(context).getChannelByChannelKey(messageList.get(position).getGroupId());

            messageUnReadCount = messageDatabaseService.getUnreadMessageCountForChannel(channel.getKey());
        } catch (Exception ex) {
        }
        if (messageUnReadCount > 0) {
            holder.tv_unreadChatNo.setVisibility(View.VISIBLE);
            holder.tv_unreadChatNo.setText(String.valueOf(messageUnReadCount));

        } else holder.tv_unreadChatNo.setVisibility(View.INVISIBLE);

        if (!messageList.get(position).getResponseBy().trim().equals("")) {
            holder.tv_liveUsers.setText(messageList.get(position).getResponseBy());
        } else holder.tv_liveUsers.setText(R.string.str_noone_response);

        if (messageList.get(position).getBusinessNotification().equals("1"))
            holder.iv_notifyIcon.setImageResource(R.drawable.notification_blue);
        else holder.iv_notifyIcon.setImageResource(R.drawable.notification_grey);

        if (messageList.get(position).getContentType() == 0) {
            holder.tv_lastchat.setVisibility(View.VISIBLE);
            holder.ll_contentTypeAttachment.setVisibility(View.GONE);
            holder.tv_lastchat.setText(messageList.get(position).getMessage());
        } else {
            holder.ll_contentTypeAttachment.setVisibility(View.VISIBLE);
            holder.tv_lastchat.setVisibility(View.GONE);
        }
// holder.tv_unreadChatNo.setText(messageList.get(position).get);

        if (DateUtils.isSameDay(messageList.get(position).getCreatedAtTime()))
            holder.tv_lastChatTime.setText(String.valueOf(DateUtils.getFormattedDate(messageList.get(position).getCreatedAtTime())));
        else if (DateUtils.isYesterday(messageList.get(position).getCreatedAtTime()))
            holder.tv_lastChatTime.setText("yesterday");
        else
            holder.tv_lastChatTime.setText(BasicUtill.getDate(messageList.get(position).getCreatedAtTime()));

        Glide.with(context).load(messageList.get(position).getCProfileImage()).into(holder.iv_profilePic);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                adapterPosition = position;
                return false;
            }
        });
    }


    public void notifyData(List<ChatThreadListModel.MessageList> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messageList != null)
            return messageList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        RelativeLayout rl_layout;
        ImageView iv_profilePic;
        TextView tv_userName, tv_lastuserName, tv_lastchat, tv_unreadChatNo, tv_lastChatTime, tv_liveUsers;
        LinearLayout ll_contentTypeAttachment;
        ImageView iv_notifyIcon;

        public MyViewHolder(View itemView) {
            super(itemView);

            ll_contentTypeAttachment = (LinearLayout) itemView.findViewById(R.id.ll_contentTypeAttachment);
            rl_layout = (RelativeLayout) itemView.findViewById(R.id.rl_layout);
            tv_lastChatTime = (TextView) itemView.findViewById(R.id.tv_lastChatTime);
            tv_unreadChatNo = (TextView) itemView.findViewById(R.id.tv_unreadChatNo);
            tv_lastchat = (TextView) itemView.findViewById(R.id.tv_lastchat);
            tv_lastuserName = (TextView) itemView.findViewById(R.id.tv_lastuserName);
            tv_userName = (TextView) itemView.findViewById(R.id.tv_userName);
            tv_liveUsers = (TextView) itemView.findViewById(R.id.tv_liveUsers);
            iv_profilePic = (ImageView) itemView.findViewById(R.id.iv_profilePic);
            iv_notifyIcon = (ImageView) itemView.findViewById(R.id.iv_notifyIcon);
            itemView.setOnCreateContextMenuListener(this);

            rl_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    threadPosition = getAdapterPosition();
                    if (!messageList.get(adapterPosition).getIsBlocked()) {
                        prefrence.setChatGroupID(String.valueOf(messageList.get(getAdapterPosition()).getGroupId()), messageList.get(getAdapterPosition()).getCUserId());
                        ApplozicBridge.launchIndividualGroupChat(context, String.valueOf(messageList.get(getAdapterPosition()).getGroupId()), messageList.get(getAdapterPosition()).getFullName()
                                , messageList.get(getAdapterPosition()).getFullName(), "0C" + messageList.get(getAdapterPosition()).getCUserId(), messageList.get(getAdapterPosition()).getChatGroupId());

                    }
                    else{
                        blockThreadAlert(context,getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Menu Otptions");
         if(!messageList.get(adapterPosition).getIsBlocked()){
            String mute = "";
            if (messageList.get(adapterPosition).getBusinessNotification().equals("0"))
                mute = "Unmute";
            else mute = "Mute";
            contextMenu.add(mute).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    chatTagFragment.notification_on(context, adapterPosition, messageList);

                    return false;
                }
            });
            contextMenu.add("Block").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (prefrence.getBlockThread().equalsIgnoreCase("1"))
                        chatTagFragment.blockUserApi(context, adapterPosition, messageList, "block");
                    else
                        Toast.makeText(context, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            contextMenu.add("Untag").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    chatTagFragment.unTagPeopleApi(context, adapterPosition, messageList);
                    return false;
                }
            });
         } else  if(messageList.get(adapterPosition).getBlockSide().equalsIgnoreCase("business")) {
             contextMenu.add("Unblock").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                 @Override
                 public boolean onMenuItemClick(MenuItem menuItem) {
                     if (prefrence.getBlockThread().equalsIgnoreCase("1"))
                         chatTagFragment.blockUserApi(context, adapterPosition, messageList, "unblock");
                     else
                         Toast.makeText(context, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();
                     return false;
                 }
             });
             contextMenu.add("Untag").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                 @Override
                 public boolean onMenuItemClick(MenuItem menuItem) {
                     chatTagFragment.unTagPeopleApi(context, adapterPosition, messageList);
                     return false;
                 }
             });
         }
         else{
             contextMenu.add("Untag").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                 @Override
                 public boolean onMenuItemClick(MenuItem menuItem) {
                     chatTagFragment.unTagPeopleApi(context, adapterPosition, messageList);
                     return false;
                 }
             });

         }
           /* contextMenu.add("Remove User").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    chatTagFragment.blockUserApi(context,adapterPosition,messageList,"remove");
                    return false;
                }
            });*/
           /* contextMenu.add("Delete Chat").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Toast.makeText(context, "Remove"+adapterPosition, Toast.LENGTH_SHORT).show();

                    return false;
                }
            });
*/

            //            MenuInflater inflater =((Activity)context).getMenuInflater();
//            inflater.inflate(R.menu.chat_thread_menu, contextMenu);

 /*           PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.getMenuInflater().inflate(R.menu.chat_thread_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
 */
        }
    }
    public void blockThreadAlert(final Context context, final int adapterPosition)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.dialog_threadblocked_alert,null);
        builder.setView(view);
        TextView blockHeading=(TextView)view.findViewById(R.id.tv_blockAlert);
        TextView tv_unblock=(TextView)view.findViewById(R.id.tv_unblock);
        if(messageList.get(adapterPosition).getBlockSide().equalsIgnoreCase("customer"))
            tv_unblock.setVisibility(View.GONE);
        else tv_unblock.setVisibility(View.VISIBLE);

        blockHeading.setText("Sorry! you can't chat, "+  messageList.get(adapterPosition).getBlockedWhom()+" has been blocked by "+messageList.get(adapterPosition).getBlockedBy());
        tv_unblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prefrence.getBlockThread().equalsIgnoreCase("1"))
                    chatTagFragment.blockUserApi(context,adapterPosition,messageList,"unblock");
                else Toast.makeText(context, R.string.str_permission_denied, Toast.LENGTH_SHORT).show();
                alertdialog.dismiss();

            }
        });
        alertdialog=builder.create();
        alertdialog.setCancelable(true);
        alertdialog.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertdialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialog_bg));
        }


    }
}
