package com.chatlocalybusiness.apiModel;

import android.net.Uri;

/**
 * Created by ashok on 5/2/18.
 */

public class RingToneListModel {

    private  String ringtoneTitle;
    private  String ringtoneListUri;

    public RingToneListModel(String ringtoneTitle, Uri ringtoneListUri) {
        this.ringtoneTitle = ringtoneTitle;
        this.ringtoneListUri = String.valueOf(ringtoneListUri);
    }

    public String getRingtoneTitle() {
        return ringtoneTitle;
    }

    public void setRingtoneTitle(String ringtoneTitle) {
        this.ringtoneTitle = ringtoneTitle;
    }

    public String getRingtoneListUri() {
        return ringtoneListUri;
    }

    public void setRingtoneListUri(String ringtoneListUri) {
        this.ringtoneListUri = ringtoneListUri;
    }
}
