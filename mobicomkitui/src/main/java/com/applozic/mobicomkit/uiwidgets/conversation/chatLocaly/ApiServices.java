package com.applozic.mobicomkit.uiwidgets.conversation.chatLocaly;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by windows on 4/3/2018.
 */

public interface ApiServices {

    @FormUrlEncoded
    @POST("add_response_by_user")
    Call<ResponseByUserModel> addResponse(@FieldMap HashMap<String, String> param);


}
