package com.applozic.mobicomkit.uiwidgets.conversation;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.applozic.mobicomkit.uiwidgets.R;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.applozic.mobicomkit.uiwidgets.conversation.chatLocaly.ChatLocallyAppLozicPrefrence;

import java.util.List;

/**
 * Created by reytum on 19/3/16.
 */
public class MultimediaOptionsGridView {
    public PopupWindow showPopup;
    FragmentActivity context;
    GridView multimediaOptions;
    private Uri capturedImageUri;
  private ChatLocallyAppLozicPrefrence chatLocallyAppLozicPrefrence;
    public MultimediaOptionsGridView(FragmentActivity context, GridView multimediaOptions) {
        this.context = context;
        this.multimediaOptions = multimediaOptions;
        chatLocallyAppLozicPrefrence=new ChatLocallyAppLozicPrefrence(context);

    }

    public void setMultimediaClickListener(final List<String> keys) {
        capturedImageUri = null;

        multimediaOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                executeMethod(keys.get(position));
            }
        });
    }

    public void executeMethod(String key) {
        if (key.equals(context.getResources().getString(R.string.location))) {
            ((ConversationActivity) context).processLocation();
        } else if (key.equals(context.getString(R.string.camera))) {
            if(chatLocallyAppLozicPrefrence.getSendPhoto().equalsIgnoreCase("1")) {
                ((ConversationActivity) context).isTakePhoto(true);
                ((ConversationActivity) context).processCameraAction();
            }else Toast.makeText(context,R.string.chatlocaly_permission_denied, Toast.LENGTH_SHORT).show();
        } else if (key.equals(context.getString(R.string.file))) {
            if(chatLocallyAppLozicPrefrence.getSendPhoto().equalsIgnoreCase("1")) {
                ((ConversationActivity) context).isAttachment(true);
            ((ConversationActivity) context).processAttachment();
            }else Toast.makeText(context,R.string.chatlocaly_permission_denied, Toast.LENGTH_SHORT).show();
        } else if (key.equals(context.getString(R.string.audio))) {
            if(chatLocallyAppLozicPrefrence.getSendAudio().equalsIgnoreCase("1")) {
            ((ConversationActivity) context).showAudioRecordingDialog();
            }else Toast.makeText(context,R.string.chatlocaly_permission_denied, Toast.LENGTH_SHORT).show();
        } else if (key.equals(context.getString(R.string.video))) {
            if(chatLocallyAppLozicPrefrence.getSendVideo().equalsIgnoreCase("1")) {
            ((ConversationActivity) context).isTakePhoto(false);
            ((ConversationActivity) context).processVideoRecording();
            }else Toast.makeText(context,R.string.chatlocaly_permission_denied, Toast.LENGTH_SHORT).show();
        } else if (key.equals(context.getString(R.string.contact))) {
            ((ConversationActivity) context).processContact();
        } else if (key.equals(context.getString(R.string.contact))) {
            new ConversationUIService(context).sendPriceMessage();
        }
        multimediaOptions.setVisibility(View.GONE);
    }
}