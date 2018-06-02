package com.chatlocalybusiness.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.getterSetterModel.BuyerInfoModel;
import com.chatlocalybusiness.apiModel.ProductListModel;
import com.chatlocalybusiness.imagecrop.Log;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by windows on 2/12/2018.
 */

public class BuyerInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_mobile, et_buyerName, et_discription, et_billDate, et_duedate;
    private EditText et_buyerAddLine1, et_buyerAddLine2, et_pincode, et_city, et_state;
    private LinearLayout ll_next;
    private ImageView iv_arrowBack;
    private TextView tv_title;
    private Utill utill;
    private ChatBusinessSharedPreference preference;
    private List<ProductListModel.PublishProduct> productLists;
    private Calendar myCalendar;
    private ViewGroup rl_main;
    public static int buyerInfo = 0;
    private int productsCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buyerinfo);
        rl_main = (ViewGroup) findViewById(R.id.rl_main);
        myCalendar = Calendar.getInstance();
        init();
//        getProductList();


    }

    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(BuyerInfoActivity.this, 0, rl_main);
        new SyncTask().execute();
    }

    public void init() {
        utill = new Utill();
        preference = new ChatBusinessSharedPreference(BuyerInfoActivity.this);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_buyerName = (EditText) findViewById(R.id.et_buyerName);
        et_discription = (EditText) findViewById(R.id.et_discription);
        et_billDate = (EditText) findViewById(R.id.et_billDate);
        et_duedate = (EditText) findViewById(R.id.et_duedate);
        et_buyerAddLine1 = (EditText) findViewById(R.id.et_buyerAddLine1);
        et_buyerAddLine2 = (EditText) findViewById(R.id.et_buyerAddLine2);
        et_pincode = (EditText) findViewById(R.id.et_pincode);
        et_city = (EditText) findViewById(R.id.et_city);
        et_state = (EditText) findViewById(R.id.et_state);
        updateLabelBillDate(et_billDate);
        billDate=myCalendar.getTime();

        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_next = (LinearLayout) findViewById(R.id.ll_next);
        et_state.setOnClickListener(this);
        et_billDate.setOnClickListener(this);
        et_duedate.setOnClickListener(this);
        ll_next.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);
    }

    public boolean isEmpty() {
        if (et_mobile.getText().toString().trim().isEmpty() || et_mobile.getText().toString().trim().equals("") || (et_mobile.getText().toString().length() < 10)) {
            et_mobile.setError("Enter 10  digits mobile number");
            return true;
        } else if (et_buyerName.getText().toString().trim().isEmpty() || et_buyerName.getText().toString().trim().equals("")) {
            et_buyerName.setError("Enter buyer's name");
            return true;
        } else if (et_buyerAddLine1.getText().toString().trim().isEmpty() || et_buyerAddLine1.getText().toString().trim().equals("")) {
            et_buyerAddLine1.setError("Enter buyer's address");
            return true;
        } else if (et_city.getText().toString().trim().isEmpty() || et_city.getText().toString().trim().equals("")) {
            et_city.setError("Enter buyer's city");
            return true;
        } /*else if (et_state.getText().toString().trim().isEmpty() || et_state.getText().toString().trim().equals("")) {
            et_state.setError("Enter state's name ");
            return true;
        }*/ else if (et_billDate.getText().toString().trim().isEmpty() || et_billDate.getText().toString().trim().equals("")) {
            et_billDate.setError("Enter date of bill ");
            return true;
        }
  /* else if (et_duedate.getText().toString().trim().isEmpty() || et_duedate.getText().toString().trim().equals("")) {
            et_duedate.setError("Enter due date of bill ");
            return true;
        } */
            return false;

    }

    public BuyerInfoModel saveBuyerInfo() {
        BuyerInfoModel buyerInfoModel = new BuyerInfoModel();

        buyerInfoModel.setBuyerMobile(et_mobile.getText().toString().trim());
        buyerInfoModel.setBuyerId(preference.getChatClientId());
        buyerInfoModel.setBuyerName(et_buyerName.getText().toString().trim());
//        buyerInfoModel.setBillCategoryDiscription(et_discription.getText().toString().trim());
        buyerInfoModel.setBillDate(BasicUtill.getDateFormatLocal(et_billDate.getText().toString().trim()));
        buyerInfoModel.setDueDate(BasicUtill.getDateFormatLocal(et_duedate.getText().toString()));
        buyerInfoModel.setBuyerAddressLine1(et_buyerAddLine1.getText().toString().trim());
        buyerInfoModel.setBuyerAddressLine2(et_buyerAddLine2.getText().toString().trim());

        buyerInfoModel.setBuyerCity(et_city.getText().toString().trim());
        buyerInfoModel.setBuyerState(et_state.getText().toString().trim());
        buyerInfoModel.setBuyerPincode(et_pincode.getText().toString().trim());

        android.util.Log.e("Format_Date", BasicUtill.getDateFormatLocal(et_billDate.getText().toString().trim()));


        return buyerInfoModel;
    }

    public Dialog onCreateDialogSingleChoice() {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] stateArray = getResources().getStringArray(R.array.state_list);
        builder.setTitle("Select State")

// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setItems(stateArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
//                        plan=planList.get(which).getSpName();
                        String state = stateArray[which];
//                        post[0] =which;
                        et_state.setText(state);

                    }
                });

        return builder.create();
    }

    Date billDate, dueDate;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.et_state:
                Dialog dialog = onCreateDialogSingleChoice();
                dialog.show();
                break;
            case R.id.ll_next:
                if (isEmpty()) {
                } else {
                    BuyerInfoModel buyerInfoModel = saveBuyerInfo();
                    buyerInfo = 0;
                    if (new Utill().isConnected(BuyerInfoActivity.this)) {
                        if (productLists != null) {
                            Intent intent = new Intent(BuyerInfoActivity.this, AddItemBillActivity.class);
                            intent.putExtra(Constants.PRODUCT_SERIALIZABLE, (Serializable) productLists);
                            intent.putExtra(Constants.BUYER_INFO, (Serializable) buyerInfoModel);
                            startActivityForResult(intent, Constants.FINISH);
                        } else if (productLists == null && productsCount == 1)
                            Toast.makeText(this, "No Products Available", Toast.LENGTH_SHORT).show();

                        else {
                            getProductList();
                            Toast.makeText(this, "something went wrong, try again", Toast.LENGTH_SHORT).show();

                        }
                    } else
                        Toast.makeText(this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.et_billDate:
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelBillDate(et_billDate);
                        billDate = myCalendar.getTime();

                    }

                };
                new DatePickerDialog(BuyerInfoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.et_duedate:
                DatePickerDialog.OnDateSetListener duedate = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelDueDate(et_duedate);
                        dueDate = myCalendar.getTime();
                    }

                };
                new DatePickerDialog(BuyerInfoActivity.this, duedate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED)
            if (buyerInfo == 0)
                BasicUtill.sendIntent(BuyerInfoActivity.this);


    }

    private void updateLabelBillDate(EditText editText) {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (dueDate != null) {
            if (myCalendar.getTime().before(dueDate))
                editText.setText(sdf.format(myCalendar.getTime()));
            else {
                Toast.makeText(this, "Due Date can't be before Bill Date", Toast.LENGTH_SHORT).show();
                et_billDate.setText("");
                et_billDate.setHint("Select Bill Date");
                et_duedate.setText("");
                et_duedate.setHint("Select Due Date");
            }
        }
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelDueDate(EditText editText) {
        String myFormat = "dd/MM/yy";
        //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (billDate != null) {
            if (myCalendar.getTime().after(billDate))
                editText.setText(sdf.format(myCalendar.getTime()));
            else{
                Toast.makeText(this, "Due Date can't be before Bill Date", Toast.LENGTH_SHORT).show();
                editText.setText("");
                editText.setHint("Select Due Date");

            }
        } else
            Toast.makeText(this, "Select Bill Date first", Toast.LENGTH_SHORT).show();

    }

    class SyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            getProductList();

            return null;
        }
    }

    public void getProductList() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", Constants.Encryption_Key);
        params.put("b_user_id", preference.getUserId());
        params.put("b_id", String.valueOf(preference.getBusinessId()));


        Call<ProductListModel> call = apiServices.getProductList(params);
        call.enqueue(new Callback<ProductListModel>() {
            @Override
            public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {

                if (response.isSuccessful()) {
                    productsCount = 1;
                    if (response.body().getData().getResultCode().equals("1")) {
                        productLists = response.body().getData().getProductList().getPublishProduct();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListModel> call, Throwable t) {

            }
        });

    }
}
