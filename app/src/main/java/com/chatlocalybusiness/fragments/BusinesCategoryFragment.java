package com.chatlocalybusiness.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import com.bumptech.glide.Glide;
import com.chatlocalybusiness.R;

import com.chatlocalybusiness.activity.EditBusinessSetupActivity;
import com.chatlocalybusiness.activity.NewBusinessSetupActivity;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.BusinessCatModel;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.apiModel.CategoryListModel;
import com.chatlocalybusiness.apiModel.SubcriptionPlanListModel;
import com.chatlocalybusiness.getterSetterModel.BusinessSetupDetails;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chatlocalybusiness.activity.BusinessProfileActivity.businessProfileApi;

/**
 * Created by SHIV on 12/7/2017.
 */
@SuppressLint("ValidFragment")

public class BusinesCategoryFragment extends Fragment implements View.OnClickListener {

    private String plan;
    private EditText et_subscriptionPlan, et_cat1, et_cat2;
    private Utill utill;
    private Button btn_done;
    private ChatBusinessSharedPreference preference;
    private ProgressBar progressBar;
    private LinearLayout ll_category;
    private List<SubcriptionPlanListModel.SpPlanList> planList;
    private List<CategoryListModel.SubCategoryList> categoryList;
    private HashMap<String, String> catList;
    private String subId;
    private BusinessInfoModelNew.BusinessDetail info;
    ImageView iv_checkCategory;
    private RelativeLayout rl_business, rl_category, rl_location;
    private int spPlanPosition = -1;
    private int catPosition1 = -1;
    private int catPosition2 = -1;
    NewBusinessSetupActivity newBusinessSetupActivity;
    EditBusinessSetupActivity editBusinessSetupActivity;
    public BusinesCategoryFragment(NewBusinessSetupActivity newBusinessSetupActivity) {
        this.newBusinessSetupActivity = newBusinessSetupActivity;
    }

    public BusinesCategoryFragment(EditBusinessSetupActivity editBusinessSetupActivity) {
        this.editBusinessSetupActivity=editBusinessSetupActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriptionplan, container, false);
        utill = new Utill();
        preference = new ChatBusinessSharedPreference(getActivity());
        catList = new HashMap<>();
        ll_category = (LinearLayout) view.findViewById(R.id.ll_category);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        et_subscriptionPlan = (EditText) view.findViewById(R.id.et_subscriptionPlan);
        et_cat1 = (EditText) view.findViewById(R.id.et_cat1);
        et_cat2 = (EditText) view.findViewById(R.id.et_cat2);
        btn_done = (Button) view.findViewById(R.id.btn_done);
        iv_checkCategory = (ImageView) getActivity().findViewById(R.id.iv_checkCategory);
        rl_business = (RelativeLayout) getActivity().findViewById(R.id.rl_business);
        rl_category = (RelativeLayout) getActivity().findViewById(R.id.rl_category);
        rl_location = (RelativeLayout) getActivity().findViewById(R.id.rl_location);

        et_cat1.setOnClickListener(this);
        et_cat2.setOnClickListener(this);
        et_subscriptionPlan.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        setBtn_doneClickable(false);

        getSubscriptionPlanList();
//        getCategoryList();
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

            if (info != null)
                if (info.getCurrentSubscriptionPlan() != null) {
                    if (!info.getCurrentSubscriptionPlan().getSpId().equalsIgnoreCase("")) {
                        for (int i = 0; i < planList.size(); i++) {
                            if (info.getCurrentSubscriptionPlan().getSpName().equalsIgnoreCase(planList.get(i).getSpName())) {
                                spPlanPosition = i;
                                et_subscriptionPlan.setText(planList.get(i).getSpName());
                                subId = planList.get(i).getSpId();
                                break;
                            }
                        }
                        for (int i = 0; i < categoryList.size(); i++) {
                            if (info.getCurrentSubscriptionPlan().getCategoryOne().equalsIgnoreCase(categoryList.get(i).getSubCatId())) {
                                catPosition1 = i;
                                et_cat1.setText(categoryList.get(i).getSubCatName());
                                et_cat1.setEnabled(true);
                                catList.put("cat1", categoryList.get(i).getSubCatId());
                                break;
                            }
                        }
                    }
                    if (!info.getCurrentSubscriptionPlan().getCategoryTwo().equalsIgnoreCase("")) {

                        for (int i = 0; i < categoryList.size(); i++) {
                            if (info.getCurrentSubscriptionPlan().getCategoryTwo().equalsIgnoreCase(categoryList.get(i).getSubCatId())) {
                                catPosition2 = i;
                                et_cat2.setText(categoryList.get(i).getSubCatName());
                                et_cat1.setEnabled(true);
                                catList.put("cat2", categoryList.get(i).getSubCatId());
                                break;
                            }

                        }
                    }
                    setBtn_doneClickable(true);
                }
        }
    }

    public Dialog onCreateDialogSingleChoice(final String[] array, final String flag, int spPlanPosition) {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final int[] subsPlan = new int[1];
        final int[] post = {-1};
        builder.setTitle("Subscription Plan")

// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(array, spPlanPosition, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
//                        plan=planList.get(which).getSpName();
                        plan = array[which];
                        post[0] = which;
                    }
                })

// Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (post[0] > (-1)) {
                            if (flag.equalsIgnoreCase("plan")) {
                                et_subscriptionPlan.setText(plan);
                                subId = planList.get(post[0]).getSpId();
                                if (planList.get(post[0]).getCategories().equalsIgnoreCase("1")) {
                                    et_cat1.setEnabled(true);
                                    et_cat2.setEnabled(false);
                                } else {
                                    et_cat1.setEnabled(true);
                                    et_cat2.setEnabled(true);
                                    subsPlan[0] = post[0];
                                }
                            } else if (flag.equalsIgnoreCase("cat1")) {
                                et_cat1.setText(plan);
                                catList.put("cat1", categoryList.get(post[0]).getSubCatId());
//                            if(planList.get(subsPlan[0]).getCategories().equalsIgnoreCase("1"))
                                setBtn_doneClickable(true);
                            } else if (flag.equalsIgnoreCase("cat2")) {
                                et_cat2.setText(plan);
                                setBtn_doneClickable(true);
                                catList.put("cat2", categoryList.get(post[0]).getSubCatId());
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_subscriptionPlan:
                if (planList != null && planList.size() > 0) {
                    String[] array = new String[planList.size()];
                    for (int i = 0; i < planList.size(); i++) {
                        array[i] = planList.get(i).getSpName();
                    }
                    Dialog dialog = onCreateDialogSingleChoice(array, "plan", spPlanPosition);
                    dialog.show();
                } else
                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();

                break;
            case R.id.et_cat1:
                if (categoryList != null && categoryList.size() > 0) {
                    String[] array = new String[categoryList.size()];
                    for (int i = 0; i < categoryList.size(); i++) {
                        array[i] = categoryList.get(i).getSubCatName();
                    }
                    Dialog dialog = onCreateDialogSingleChoice(array, "cat1", catPosition1);
                    dialog.show();
                } else
                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();

                break;
            case R.id.et_cat2:
                if (categoryList != null && categoryList.size() > 0) {
                    String[] array = new String[categoryList.size()];
                    for (int i = 0; i < categoryList.size(); i++) {
                        array[i] = categoryList.get(i).getSubCatName();
                    }
                    Dialog dialog = onCreateDialogSingleChoice(array, "cat2", catPosition2);
                    dialog.show();
                } else
                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_done:
                updateBusinessCat();
                break;
        }
    }

    private void replacefragment() {
        Fragment fragment;
        if ((fragment = getFragmentManager().findFragmentByTag(NewBusinessSetupActivity.CATEGORY_FRAG)) != null) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).remove(fragment).commit();
//            getFragmentManager().beginTransaction()
//                    .setCustomAnimations(R.anim.rotate_in,R.anim.rotate_out).
//                    remove(fragment)
//                    .commit();
            NewBusinessSetupActivity.categoryCheck = 1;
       /*     CheckBox check_Category=(CheckBox)getActivity().findViewById(R.id.check_Category);
            check_Category.setClickable(false);*/
            if (NewBusinessSetupActivity.locationCheck == 1 && NewBusinessSetupActivity.businessCheck == 1 && NewBusinessSetupActivity.categoryCheck == 1) {
                Button btn_next = (Button) getActivity().findViewById(R.id.btn_next);
                btn_next.setClickable(true);
                btn_next.setFocusable(true);
                btn_next.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                btn_next.setBackgroundResource(R.drawable.blue_btn_bg);

            }
        }
    }

    public void getSubscriptionPlanList() {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", Constants.Encryption_Key);
        param.put("b_user_id", preference.getUserId());
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("promo_code", "JOINNOW");

//        param.put("b_user_id","1");

        Call<SubcriptionPlanListModel> call = apiServices.getSubscriptionPlan(param);
        call.enqueue(new Callback<SubcriptionPlanListModel>() {
            @Override
            public void onResponse(Call<SubcriptionPlanListModel> call, Response<SubcriptionPlanListModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        planList = response.body().getData().getSpPlanList();

                        getCategoryList();

                    } else
                        Toast.makeText(getActivity(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubcriptionPlanListModel> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

            }
        });

    }

    public void getCategoryList() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", Constants.Encryption_Key);
        param.put("b_user_id", preference.getUserId());

        Call<CategoryListModel> call = apiServices.getCategoryList(param);
        call.enqueue(new Callback<CategoryListModel>() {
            @Override
            public void onResponse(Call<CategoryListModel> call, Response<CategoryListModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        categoryList = response.body().getData().getSubCategoryList();
                        ll_category.setVisibility(View.VISIBLE);
                        setValues();
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryListModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setBtn_doneClickable(boolean b) {
        if (b) {
            btn_done.setClickable(b);
            btn_done.setFocusable(b);
            btn_done.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            btn_done.setBackgroundResource(R.drawable.blue_btn_bg);

        } else {
            btn_done.setClickable(b);
            btn_done.setFocusable(b);
            btn_done.setTextColor(ContextCompat.getColor(getActivity(), R.color.light_gray));
            btn_done.setBackgroundResource(R.drawable.gray_btn_bg);
        }
    }

    public void updateBusinessCat() {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
        param.put("encrypt_key", Constants.Encryption_Key);
        param.put("b_user_id", preference.getUserId());
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("sp_id", subId);
        param.put("complated_steps", preference.getCompletedStep());

        for (int i = 0; i < catList.size(); i++) {
            if (i == 0)
                param.put("sub_cat_ids[" + 0 + "]", catList.get("cat1"));
            else param.put("sub_cat_ids[" + 1 + "]", catList.get("cat2"));
        }

        Call<BusinessCatModel> call = apiServices.postBusinessCat(param);
        call.enqueue(new Callback<BusinessCatModel>() {
            @Override
            public void onResponse(Call<BusinessCatModel> call, Response<BusinessCatModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
//                        preference.saveCompletedStep("5");
                        iv_checkCategory.setVisibility(View.VISIBLE);
                        businessProfileApi = 1;
                        if (info == null) {
                            BusinessInfoModelNew businessInfoModelNew = new BusinessInfoModelNew();
                            info = businessInfoModelNew.new BusinessDetail();
                        }
                        BusinessInfoModelNew businessInfoModelNew = new BusinessInfoModelNew();
                        BusinessInfoModelNew.CurrentSubscriptionPlan currentSubscriptionPlan = businessInfoModelNew.new CurrentSubscriptionPlan();


                        currentSubscriptionPlan.setSpName(et_subscriptionPlan.getText().toString());
                        currentSubscriptionPlan.setSpId(subId);
                        for (int i = 0; i < catList.size(); i++) {
                            if (i == 0)
                                currentSubscriptionPlan.setCategoryOne(catList.get("cat1"));
                            else currentSubscriptionPlan.setCategoryTwo(catList.get("cat2"));
                        }
                        info.setCurrentSubscriptionPlan(currentSubscriptionPlan);

                        if (newBusinessSetupActivity != null)
                            newBusinessSetupActivity.setData(info);
                        else editBusinessSetupActivity.setData(info);
                        disableLayouts(true);

                        replacefragment();

                    } else
                        Toast.makeText(getActivity(), response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<BusinessCatModel> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

            }
        });
    }
}
