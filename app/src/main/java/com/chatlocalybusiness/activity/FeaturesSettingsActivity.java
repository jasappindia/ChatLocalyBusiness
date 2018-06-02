package com.chatlocalybusiness.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.FeatureSettingsModel;
import com.chatlocalybusiness.apiModel.GetFeaturesSettingsModel;
import com.chatlocalybusiness.apiModel.ResponseModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SHIV on 12/22/2017.
 */
public class FeaturesSettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private String employeeId, employeeName;
    public static int featureSettings = 0;
    private AlertDialog alertdialog;

    @Override
    protected void onResume() {
        super.onResume();
        ViewGroup rl_main = (ViewGroup) findViewById(R.id.rl_main);
        new BasicUtill().CheckStatus(FeaturesSettingsActivity.this, 0, rl_main);

    }

    private Utill utill;
    private ChatBusinessSharedPreference preference;
    private ImageView iv_arrowBack, iv_deleteIcon;
    private CheckBox cb_sendMsz, cb_sendPhoto, cb_sendVideo, cb_sendAudio,
            cb_sendBill, cb_editBill, cb_blockthread, cb_editBusiness,
            cb_editOverview, cb_addProducts, cb_addServices, cb_unblockthread, cb_tagThread, cb_untagThread;
    private Button btn_save;
    private int sendMessage = 0, sendVideo = 0, sendAudio = 0, sendPhoto = 0, sendBill = 0,
            editBill = 0, addSevices = 0, blockThread = 0, editBusiness = 0, editOverview = 0,
            addProducts = 0,tagThread=0,untagThread=0,unblockThread=0;
    private TextView tv_titleBar, tv_designation;
    private LinearLayout ll_features, ll_designation;
    private ProgressBar progressBar;
    private GetFeaturesSettingsModel.UserSetting apiResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);
        init();

        if (getIntent().getExtras() != null) {
            employeeId = getIntent().getExtras().getString(Constants.EmployeeID);
            employeeName = getIntent().getExtras().getString(Constants.Employee);
            String designation = getIntent().getExtras().getString(Constants.DESIGNATION);
            tv_titleBar.setText(employeeName);
            tv_designation.setText(designation);
        }
        cbInit();

        getFeatures();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSettings();

            }
        });
        iv_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void init() {
        utill = new Utill();
        preference = new ChatBusinessSharedPreference(this);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        btn_save = (Button) findViewById(R.id.btn_save);
        tv_titleBar = (TextView) findViewById(R.id.tv_titleBar);
        tv_designation = (TextView) findViewById(R.id.tv_designation);
        ll_features = (LinearLayout) findViewById(R.id.ll_features);
        ll_designation = (LinearLayout) findViewById(R.id.ll_designation);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_deleteIcon = (ImageView) findViewById(R.id.iv_deleteIcon);

        ll_designation.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
        iv_deleteIcon.setOnClickListener(this);
    }

    public void cbInit() {
        cb_sendMsz = (CheckBox) findViewById(R.id.cb_sendMsz);
        cb_sendPhoto = (CheckBox) findViewById(R.id.cb_sendPhoto);
        cb_sendVideo = (CheckBox) findViewById(R.id.cb_sendVideo);
        cb_sendAudio = (CheckBox) findViewById(R.id.cb_sendAudio);
        cb_sendBill = (CheckBox) findViewById(R.id.cb_sendBill);
        cb_editBill = (CheckBox) findViewById(R.id.cb_editBill);
        cb_blockthread = (CheckBox) findViewById(R.id.cb_blockthread);
        cb_editBusiness = (CheckBox) findViewById(R.id.cb_editBusiness);
        cb_editOverview = (CheckBox) findViewById(R.id.cb_editOverview);
        cb_addProducts = (CheckBox) findViewById(R.id.cb_addProducts);
        cb_addServices = (CheckBox) findViewById(R.id.cb_addServices);
        cb_untagThread = (CheckBox) findViewById(R.id.cb_untagThread);
        cb_tagThread = (CheckBox) findViewById(R.id.cb_tagThread);
        cb_unblockthread = (CheckBox) findViewById(R.id.cb_unblockthread);

        cb_sendMsz.setOnCheckedChangeListener(this);
        cb_sendPhoto.setOnCheckedChangeListener(this);
        cb_sendVideo.setOnCheckedChangeListener(this);
        cb_sendAudio.setOnCheckedChangeListener(this);
        cb_sendBill.setOnCheckedChangeListener(this);
        cb_editBill.setOnCheckedChangeListener(this);
        cb_blockthread.setOnCheckedChangeListener(this);
        cb_editBusiness.setOnCheckedChangeListener(this);
        cb_editOverview.setOnCheckedChangeListener(this);
        cb_addProducts.setOnCheckedChangeListener(this);
        cb_addServices.setOnCheckedChangeListener(this);
        cb_untagThread.setOnCheckedChangeListener(this);
        cb_tagThread.setOnCheckedChangeListener(this);
        cb_unblockthread.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()) {
            case R.id.cb_sendMsz:
                if (b) sendMessage = 1;
                else sendMessage = 0;

                break;
            case R.id.cb_sendPhoto:
                if (b) sendPhoto = 1;
                else sendPhoto = 0;

                break;
            case R.id.cb_sendVideo:
                if (b) sendVideo = 1;
                else sendVideo = 0;

                break;
            case R.id.cb_sendAudio:
                if (b) sendAudio = 1;
                else sendAudio = 0;

                break;
            case R.id.cb_sendBill:
                if (b) sendBill = 1;
                else sendBill = 0;

                break;
            case R.id.cb_editBill:
                if (b) editBill = 1;
                else editBill = 0;

                break;
            case R.id.cb_blockthread:
                if (b) blockThread = 1;
                else blockThread = 0;

                break;
            case R.id.cb_editBusiness:
                if (b) editBusiness = 1;
                else editBusiness = 0;

                break;
            case R.id.cb_addServices:
                if (b) addSevices = 1;
                else addSevices = 0;

                break;
            case R.id.cb_addProducts:
                if (b) addProducts = 1;
                else addProducts = 0;

                break;
            case R.id.cb_editOverview:
                if (b) editOverview = 1;
                else editOverview = 0;
                break;
            case R.id.cb_tagThread:
                if (b) tagThread = 1;
                else tagThread = 0;
                break;
            case R.id.cb_untagThread:
                if (b) untagThread = 1;
                else untagThread = 0;
                break;
            case R.id.cb_unblockthread:
                if (b) unblockThread = 1;
                else unblockThread = 0;
                break;
        }
    }

    public void getFeatures() {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", Constants.Encryption_Key);
//        params.put("b_user_id",preference.getUserId());
        params.put("b_user_id", employeeId);

        Call<GetFeaturesSettingsModel> call = apiServices.getFeaturesSetiings(params);
        call.enqueue(new Callback<GetFeaturesSettingsModel>() {
            @Override
            public void onResponse(Call<GetFeaturesSettingsModel> call, Response<GetFeaturesSettingsModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        ll_features.setVisibility(View.VISIBLE);
                        apiResponse = response.body().getData().getUserSetting();
                        setCheckBox(apiResponse);
                        featureSettings = 1;
                    } else
                        Toast.makeText(FeaturesSettingsActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFeaturesSettingsModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(FeaturesSettingsActivity.this, "Check Internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setCheckBox(GetFeaturesSettingsModel.UserSetting apiResponse) {
        if (apiResponse.getSendMessage().equalsIgnoreCase("1")) {
            cb_sendMsz.setChecked(true);
            sendMessage = 1;
        } else {
            cb_sendMsz.setChecked(false);
            sendMessage = 0;
        }
        if (apiResponse.getSendVideo().equalsIgnoreCase("1")) {
            cb_sendVideo.setChecked(true);
            sendVideo = 1;
        } else {
            cb_sendVideo.setChecked(false);
            sendVideo = 0;
        }
        if (apiResponse.getSendAudio().equalsIgnoreCase("1")) {
            cb_sendAudio.setChecked(true);
            sendAudio = 1;
        } else {
            cb_sendAudio.setChecked(false);
            sendAudio = 0;
        }
        if (apiResponse.getSendBill().equalsIgnoreCase("1")) {
            cb_sendBill.setChecked(true);
            sendBill = 1;
        } else {
            cb_sendBill.setChecked(false);
            sendBill = 0;
        }
        if (apiResponse.getEditBill().equalsIgnoreCase("1")) {
            cb_editBill.setChecked(true);
            editBill = 1;
        } else {
            cb_editBill.setChecked(false);
            editBill = 0;
        }
        if (apiResponse.getEditBusinessInfo().equalsIgnoreCase("1")) {
            cb_editBusiness.setChecked(true);
            editBusiness = 1;
        } else {
            cb_editBusiness.setChecked(false);
            editBusiness = 0;
        }
        if (apiResponse.getSendPhoto().equalsIgnoreCase("1")) {
            cb_sendPhoto.setChecked(true);
            sendPhoto = 1;
        } else {
            cb_sendPhoto.setChecked(false);
            sendPhoto = 0;
        }
        if (apiResponse.getBlockThead().equalsIgnoreCase("1")) {
            cb_blockthread.setChecked(true);
            blockThread = 1;
        } else {
            cb_blockthread.setChecked(false);
            blockThread = 0;
        }
        if (apiResponse.getAddProducts().equalsIgnoreCase("1")) {
            cb_addProducts.setChecked(true);
            addProducts = 1;
        } else {
            cb_addProducts.setChecked(false);
            addProducts = 0;
        }
        if (apiResponse.getAddServices().equalsIgnoreCase("1")) {
            cb_addServices.setChecked(true);
            addSevices = 1;
        } else {
            cb_addServices.setChecked(false);
            addSevices = 0;
        }
        if (apiResponse.getEditOverview().equalsIgnoreCase("1")) {
            cb_editOverview.setChecked(true);
            editOverview = 1;
        } else {
            cb_editOverview.setChecked(false);
            editOverview = 0;
        }
        if(apiResponse.getTagThread().equalsIgnoreCase("1"))
        {
            cb_tagThread.setChecked(true);
            tagThread=1;
        }else {
            cb_tagThread.setChecked(false);
            tagThread=0;

        }
        if(apiResponse.getUnTagThread().equalsIgnoreCase("1"))
        {
            cb_untagThread.setChecked(true);
            untagThread=1;
        }else {
            cb_untagThread.setChecked(false);
            untagThread=0;

        }
        if(apiResponse.getUnBlockThread().equalsIgnoreCase("1"))
        {
            cb_unblockthread.setChecked(true);
            unblockThread=1;
        }else {
            cb_unblockthread.setChecked(false);
            unblockThread=0;

        }
    }

    public void updateSettings() {
        /*http://192.168.0.60/chatlocaly/business_api/user_setting_update?encrypt_key=dgdfg&
        b_user_id=49&b_id=18&designation=Sub%20admin&send_message=1&send_photo=1&send_video=1&
        send_audio=1&send_bill=1&edit_bill=1&block_thead=1&edit_business_profile=1&
        edit_receive_payment=1&paytm_login=1&*/

        progressBar.setVisibility(View.VISIBLE);

        ApiServices apiservices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", Constants.Encryption_Key);
        params.put("b_user_id", employeeId);
        params.put("b_id", String.valueOf(preference.getBusinessId()));
        params.put("designation", tv_designation.getText().toString());
        params.put("send_message", String.valueOf(sendMessage));
        params.put("send_photo", String.valueOf(sendPhoto));
        params.put("send_video", String.valueOf(sendVideo));
        params.put("send_audio", String.valueOf(sendAudio));
        params.put("send_bill", String.valueOf(sendBill));
        params.put("edit_bill", String.valueOf(editBill));
        params.put("block_thead", String.valueOf(blockThread));
        params.put("edit_business_info", String.valueOf(editBusiness));
        params.put("add_services", String.valueOf(addSevices));
        params.put("add_products", String.valueOf(addProducts));
        params.put("edit_overview", String.valueOf(editOverview));

        params.put("unblock_thread", String.valueOf(editOverview));
        params.put("tag_thread", String.valueOf(editOverview));
        params.put("untag_thread", String.valueOf(editOverview));

        Call<FeatureSettingsModel> call = apiservices.updateFeaturesSetiings(params);
        call.enqueue(new Callback<FeatureSettingsModel>() {
            @Override
            public void onResponse(Call<FeatureSettingsModel> call, Response<FeatureSettingsModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {

                        Toast.makeText(FeaturesSettingsActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                        featureSettings = 1;

                    } else
                        Toast.makeText(FeaturesSettingsActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FeatureSettingsModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(FeaturesSettingsActivity.this, "Check Internet Connection  ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.iv_deleteIcon:
                deleteOrder("Delete " + tv_designation.getText().toString().trim() + " From Business !");
                break;
            case R.id.ll_designation:
//                editNAmeAlert();
                break;
        }
    }

    public void deleteOrder(final String title) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//           builder.setTitle("Are you sure ?");
        builder.setTitle(title);
        builder.setMessage("Are you sure?").
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeUser();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertdialog.dismiss();
            }
        });
        alertdialog = builder.create();
        alertdialog.setCancelable(false);
        alertdialog.show();
    }

    public void removeUser() {
        //    ?encrypt_key=dfgdsg&delete_user_id=6&b_user_id=2&b_id=2
        progressBar.setVisibility(View.VISIBLE);

        ApiServices apiservices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", Constants.Encryption_Key);
        params.put("b_user_id", preference.getUserId());
        params.put("b_id", String.valueOf(preference.getBusinessId()));
        params.put("delete_user_id", employeeId);
        Call<ResponseModel> call = apiservices.removeUser(params);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equals("1")) {
                        Toast.makeText(FeaturesSettingsActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                        featureSettings = 1;
                    } else
                        Toast.makeText(FeaturesSettingsActivity.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(FeaturesSettingsActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void designationAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_designation, null);
        builder.setView(view);

        Button btn_done = (Button) view.findViewById(R.id.btn_done);
        final EditText et_designation = (EditText) view.findViewById(R.id.et_designation);
        et_designation.setText(tv_designation.getText().toString());

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_designation.getText().toString().trim() != null && !et_designation.getText().toString().trim().equals("")) {
                    tv_designation.setText(et_designation.getText().toString());
                }

                alertdialog.dismiss();

            }
        });

        alertdialog = builder.create();
        alertdialog.show();
        alertdialog.setCancelable(true);
    }
}
