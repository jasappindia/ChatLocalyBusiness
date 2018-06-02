package com.chatlocalybusiness.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.EditBusinessSetupActivity;
import com.chatlocalybusiness.activity.NewBusinessSetupActivity;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.apiModel.BusinessLocationModel;
import com.chatlocalybusiness.apiModel.CityListModel;
import com.chatlocalybusiness.apiModel.LocalityListModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.GPSTracker;
import com.chatlocalybusiness.utill.Utill;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chatlocalybusiness.activity.BusinessProfileActivity.businessProfileApi;

/**
 * Created by Shiv on 12/12/2017.
 */
@SuppressLint("ValidFragment")
public class LocationFragment extends Fragment implements View.OnClickListener {

    private Button btn_done;
    private EditText et_city, et_locality, et_pincode, et_address, et_landmark;
    private ImageView iv_localityStar, iv_pinStar, iv_addressStar;
    private ProgressBar progressBar;
    private LinearLayout ll_locLayout;
    private ChatBusinessSharedPreference prefrence;
    private Utill utill;
    private String city1 = "";
    private String city2 = "";
    private String locality = "";
    private List<CityListModel.CityList> cityList;
    private List<LocalityListModel.LocalityList> localityList;
    private int cityIdPostition;
    private int localityIdPosition = -1;

    private int cityflag = 0, localityflag = 0, pincodeflag = 0, addressflag = 0;
    public static double lat, lng;
    private BusinessInfoModelNew.BusinessDetail info;
    ImageView iv_checkLocation;
    private RelativeLayout rl_business, rl_category, rl_location;
    NewBusinessSetupActivity newBusinessSetupActivity;
    EditBusinessSetupActivity editBusinessSetupActivity;

    public LocationFragment(NewBusinessSetupActivity newBusinessSetupActivity) {
        this.newBusinessSetupActivity = newBusinessSetupActivity;
    }

    public LocationFragment(EditBusinessSetupActivity editBusinessSetupActivity) {
        this.editBusinessSetupActivity = editBusinessSetupActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        prefrence = new ChatBusinessSharedPreference(getActivity());
        utill = new Utill();
        ll_locLayout = (LinearLayout) view.findViewById(R.id.ll_locLayout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        btn_done = (Button) view.findViewById(R.id.btn_done);
        et_city = (EditText) view.findViewById(R.id.et_city);
        et_locality = (EditText) view.findViewById(R.id.et_locality);
        et_pincode = (EditText) view.findViewById(R.id.et_pincode);
        et_address = (EditText) view.findViewById(R.id.et_address);
        et_landmark = (EditText) view.findViewById(R.id.et_landmark);
        iv_localityStar = (ImageView) view.findViewById(R.id.iv_localityStar);
        iv_pinStar = (ImageView) view.findViewById(R.id.iv_pinStar);
        iv_addressStar = (ImageView) view.findViewById(R.id.iv_addressStar);
        iv_checkLocation = (ImageView) getActivity().findViewById(R.id.iv_checkLocation);

        rl_business = (RelativeLayout) getActivity().findViewById(R.id.rl_business);
        rl_category = (RelativeLayout) getActivity().findViewById(R.id.rl_category);
        rl_location = (RelativeLayout) getActivity().findViewById(R.id.rl_location);


        btn_done.setOnClickListener(this);
        et_city.setOnClickListener(this);
        et_locality.setOnClickListener(this);
        et_locality.setClickable(false);
        setBtn_doneClickable(false);
        getCityList();
//          getLocation();
        disableLayouts(false);
        return view;

    }

    public void disableLayouts(boolean b) {
        rl_location.setClickable(b);
        rl_category.setClickable(b);
        rl_business.setClickable(b);
    }

    public void setValues() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            info = (BusinessInfoModelNew.BusinessDetail) bundle.getSerializable(Constants.BUSINESS_INFO);
            if (info != null) {
                if( info.getCityName() !=null){
                    et_city.setText(info.getCityName());
                et_pincode.setText(info.getPinCode());
                et_locality.setText(info.getLocalityName());
                et_address.setText(info.getAddress());

                for (int i = 0; i < cityList.size(); i++) {
                    if (info.getCityName().equalsIgnoreCase(cityList.get(i).getName())) {
                        cityIdPostition = i;
                        break;
                    }
                }

                if (info.getLandmark() != null)
                    et_landmark.setText(info.getLandmark());
                setBtn_doneClickable(true);
            }            }
        }
    }

     /*  public boolean getLocation()
    {
        boolean b=checkLocationPermission();
        if( b )
        {
            LocationManager  locationManager = (LocationManager)getActivity().getSystemService( Context.LOCATION_SERVICE );
            Criteria criteria = new Criteria();
            criteria.setAccuracy( Criteria.ACCURACY_COARSE );
            String  provider = locationManager.getBestProvider( criteria, true );

            try {
                //Request location updates:
//             locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
                GPSTracker gpsTracker=new GPSTracker(getActivity());
              LocationFragment.lat = gpsTracker.getLatitude();
              LocationFragment.lng = gpsTracker.getLongitude();

            }
            catch(Exception ex)
            {
                Toast.makeText(getActivity(),"Enable your Location",Toast.LENGTH_SHORT).show();
            }
        }
       return b;
    }*/

    public void setFocusedeView() {
        et_locality.setFocusable(true);
        et_locality.setClickable(true);
          /*et_pincode.setFocusable(true);
          et_pincode.setClickable(true);
          et_address.setFocusable(true);
          et_address.setClickable(true);
          et_landmark.setFocusable(true);
          et_landmark.setClickable(true);*/
        et_pincode.setEnabled(true);
        et_locality.setEnabled(true);
        et_address.setEnabled(true);
        et_landmark.setEnabled(true);

        iv_localityStar.setVisibility(View.VISIBLE);
        iv_pinStar.setVisibility(View.VISIBLE);
        iv_addressStar.setVisibility(View.VISIBLE);

        et_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 6) {
                    pincodeflag = 1;
                    if (cityflag == 1 && localityflag == 1 && pincodeflag == 1 && addressflag == 1)
                        setBtn_doneClickable(true);
                } else {
                    pincodeflag = 0;
                    setBtn_doneClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 4) {
                    addressflag = 1;
                    if (cityflag == 1 && localityflag == 1 && pincodeflag == 1 && addressflag == 1)
                        setBtn_doneClickable(true);
                } else {
                    addressflag = 0;
                    setBtn_doneClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void set_notFocusedView() {
        et_locality.setFocusable(false);
        et_locality.setClickable(false);
        et_pincode.setFocusable(false);
        et_pincode.setClickable(false);
        et_address.setFocusable(false);
        et_address.setClickable(false);
        et_landmark.setFocusable(false);
        et_landmark.setClickable(false);

        iv_localityStar.setVisibility(View.GONE);
        iv_pinStar.setVisibility(View.GONE);
        iv_addressStar.setVisibility(View.GONE);
    }

    public void setBtn_doneClickable(boolean b) {
        if (b) {
//            btn_done.setClickable(b);
//            btn_done.setFocusable(b);
            btn_done.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            btn_done.setBackgroundResource(R.drawable.blue_btn_bg);

        } else {
//            btn_done.setClickable(b);
//            btn_done.setFocusable(b);
            btn_done.setTextColor(ContextCompat.getColor(getActivity(), R.color.light_gray));
            btn_done.setBackgroundResource(R.drawable.gray_btn_bg);

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_done:
//            if(getLocation())
//            {
//                if(lat!=0.0 && lng!=0.0)
                if (!isEmpty())
                    try {
                        submitLocationApi(et_pincode.getText().toString().trim(), et_address.getText().toString().trim(), et_landmark.getText().toString().trim(), String.valueOf(lat),
                                String.valueOf(lng), String.valueOf(prefrence.getBusinessId()));
                    }
                    catch(Exception ex)
                    {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
          /*  else {
                  Toast.makeText(getActivity()," Couldn't find your location,try again",Toast.LENGTH_SHORT).show();
                  getLocation();
                }*/
//            }
                break;
            case R.id.et_locality:
//              if(!et_city.getText().toString().trim().equalsIgnoreCase("")){
                if (localityList != null && localityList.size() > 0) {
                    Dialog dialogLocality = onCreateDialogSingleChoiceLocality();
                    dialogLocality.show();
                } else
                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();

                break;
            case R.id.et_city:
                if (cityList != null && cityList.size() > 0) {
                    Dialog dialogCity = onCreateDialogSingleChoiceCity();
                    dialogCity.show();
                } else
                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void replacefragment() {
        Fragment fragment;

        if ((fragment = getFragmentManager().findFragmentByTag(NewBusinessSetupActivity.LOCATION_FRAG)) != null) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
//            getFragmentManager().beginTransaction()
//                    .setCustomAnimations(R.anim.rotate_in,R.anim.rotate_out).
//                    remove(fragment)
//                    .commit();
            NewBusinessSetupActivity.locationCheck = 1;
          /*  CheckBox check_location=(CheckBox)getActivity().findViewById(R.id.check_location);
            check_location.setClickable(false);*/
            if (NewBusinessSetupActivity.locationCheck == 1 && NewBusinessSetupActivity.businessCheck == 1 && NewBusinessSetupActivity.categoryCheck == 1) {
                Button btn_next = (Button) getActivity().findViewById(R.id.btn_next);
                btn_next.setClickable(true);
                btn_next.setFocusable(true);
                btn_next.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                btn_next.setBackgroundResource(R.drawable.blue_btn_bg);

            }
        }
    }

    public Dialog onCreateDialogSingleChoiceCity() {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//Source of the data in the DIalog
        final int[] arrayPosition = new int[1];
        String[] array = new String[cityList.size()];
        for (int i = 0; i < cityList.size(); i++) {
            array[i] = cityList.get(i).getName();
        }
        builder.setTitle("Select City")
// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        city2 = cityList.get(which).getName();
                        arrayPosition[0] = which;
                        city1 = et_city.getText().toString();
                    }
                })

// Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
// User clicked OK, so save the result somewhere
// or return them to the component that opened the dialog
                        if (!city2.equalsIgnoreCase("")) {
                            et_city.setText(city2);
                            if (!city1.equals(city2))
                                et_locality.setText("");

                            cityflag = 1;

                            cityIdPostition = arrayPosition[0];
                            setFocusedeView();
                            getLocalityList();
                        }

//                   else set_notFocusedView();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public Dialog onCreateDialogSingleChoiceLocality() {

//        Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        Source of the data in the DIalog
        final int[] arrayPosition = new int[1];
        String[] array = new String[localityList.size()];
        for (int i = 0; i < localityList.size(); i++) {
            array[i] = localityList.get(i).getName();
        }
        builder.setTitle("Select Locality")
// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        locality = localityList.get(which).getName();
                        arrayPosition[0] = which;
                    }
                })

// Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
// User clicked OK, so save the result somewhere
// or return them to the component that opened the dialog
                        if (!locality.equalsIgnoreCase("")) {
                            et_locality.setText(locality);
                            localityflag = 1;
                            localityIdPosition = arrayPosition[0];
                            setFocusedeView();
                        }
//                        else set_notFocusedView();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public void getCityList() {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", Constants.Encryption_Key);
        params.put("b_user_id", prefrence.getUserId());
        Call<CityListModel> call = apiServices.getCityList(params);
        call.enqueue(new Callback<CityListModel>() {
            @Override
            public void onResponse(Call<CityListModel> call, Response<CityListModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        cityList = response.body().getData().getCityList();
                        ll_locLayout.setVisibility(View.VISIBLE);
                        setValues();

                    } else
                        Toast.makeText(getActivity(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CityListModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLocalityList() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", Constants.Encryption_Key);
        params.put("b_user_id", prefrence.getUserId());
        params.put("city_id", cityList.get(cityIdPostition).getCityId());
        Call<LocalityListModel> call = apiServices.getLocalityList(params);
        call.enqueue(new Callback<LocalityListModel>() {
            @Override
            public void onResponse(Call<LocalityListModel> call, Response<LocalityListModel> response) {


                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        localityList = response.body().getData().getLocalityList();
                        ll_locLayout.setVisibility(View.VISIBLE);

                    } else
                        Toast.makeText(getActivity(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LocalityListModel> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void submitLocationApi(String pincode, String address, String landmark, String lattitude, String longitude, String bId) {

      /*
      *   country=india&loc_id=1&
      *   city_id=1&pin_code=302010&address=gfdg&landmark=ghdfgk&latitude=25.25&longitude=75.66&
      * b_id=3&encrypt_key=dfg&b_user_id=1
      *
      * */
        if (localityIdPosition == -1)
            for (int i = 0; i < localityList.size(); i++) {
                if (info.getLocalityName().equalsIgnoreCase(localityList.get(i).getName())) {
                    localityIdPosition = i;
                    break;
                }
            }
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", Constants.Encryption_Key);
        params.put("b_user_id", prefrence.getUserId());
        params.put("city_id", cityList.get(cityIdPostition).getCityId());
        params.put("pin_code", pincode);
        params.put("address", address);
        params.put("landmark", landmark);
        params.put("latitude", lattitude);
        params.put("longitude", longitude);
        params.put("b_id", bId);
        params.put("country", "India");
        params.put("loc_id", localityList.get(localityIdPosition).getLocId());
        params.put("complated_steps", prefrence.getCompletedStep());

        Call<BusinessLocationModel> call = apiServices.postBusinessLocation(params);
        call.enqueue(new Callback<BusinessLocationModel>() {
            @Override
            public void onResponse(Call<BusinessLocationModel> call, Response<BusinessLocationModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
//                        prefrence.saveCompletedStep("7");
                        disableLayouts(true);

                        iv_checkLocation.setVisibility(View.VISIBLE);
                        businessProfileApi = 1;

                        if (info == null) {
                            BusinessInfoModelNew businessInfoModelNew = new BusinessInfoModelNew();
                            info = businessInfoModelNew.new BusinessDetail();
                        }
                        info.setCityName(et_city.getText().toString());
                        info.setPinCode(et_pincode.getText().toString());
                        info.setLocalityName(et_locality.getText().toString());
                        info.setAddress(et_address.getText().toString());
                        info.setLandmark(et_landmark.getText().toString());
                        if (newBusinessSetupActivity != null)
                            newBusinessSetupActivity.setData(info);
                        else editBusinessSetupActivity.setData(info);
                        replacefragment();

                    } else {
                        Toast.makeText(getActivity(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<BusinessLocationModel> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });

    }


    //////////////////////////////location codes///////////////////////

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location permmission is required to complete this process")
//                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setCancelable(true)
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {


            return true;
        }
    }

    public boolean isEmpty() {
        if (et_city.getText().toString().isEmpty() || et_city.getText() == null) {
            Toast.makeText(getActivity(), "Select City", Toast.LENGTH_SHORT).show();
            return true;
        } else if (et_pincode.getText().toString().isEmpty() || et_pincode.getText() == null) {
            Toast.makeText(getActivity(), "Enter 6 digit pin code", Toast.LENGTH_SHORT).show();
            return true;

        } else if (et_locality.getText().toString().isEmpty() || et_locality.getText() == null) {
            Toast.makeText(getActivity(), "Select Locality", Toast.LENGTH_SHORT).show();
            return true;
        } else if (et_address.getText().toString().isEmpty() || et_address.getText() == null) {
            Toast.makeText(getActivity(), "Enter Address", Toast.LENGTH_SHORT).show();
            return true;
        } else if (et_address.getText() != null && et_address.getText().toString().length() < 5) {
            Toast.makeText(getActivity(), "Address length must not be less than 3 letters", Toast.LENGTH_SHORT).show();
            return true;
        } else if (et_pincode.getText() != null) {
            if (et_pincode.getText().toString().length() < 6) {
                Toast.makeText(getActivity(), "Enter 6 digit pin code", Toast.LENGTH_SHORT).show();
                return true;

            } else return false;
        } else return false;
    }


}
