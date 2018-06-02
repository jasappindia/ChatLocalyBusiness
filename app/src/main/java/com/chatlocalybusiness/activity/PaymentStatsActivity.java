package com.chatlocalybusiness.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.api.ApiClient;
import com.chatlocalybusiness.api.ApiServices;
import com.chatlocalybusiness.apiModel.PaymentStatsModel;
import com.chatlocalybusiness.apiModel.QuarterlyPaymentStatsModel;
import com.chatlocalybusiness.apiModel.StatsByCustomerModel;
import com.chatlocalybusiness.barchart.DayAxisValueFormatter;
import com.chatlocalybusiness.barchart.MyAxisValueFormatter;
import com.chatlocalybusiness.barchart.XYMarkerView;
import com.chatlocalybusiness.reportsTable.MainFragment;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * Created by windows on 3/23/2018.
 */

public class PaymentStatsActivity extends AppCompatActivity implements View.OnClickListener, OnChartValueSelectedListener {

    private LinearLayout ll_year, ll_salesby;
    RelativeLayout rl_reports;
    private ImageView iv_calendar, iv_arrowBack, iv_pichart;
    private TextView tv_salesby, tv_year;
    private ChatBusinessSharedPreference preference;
    protected BarChart mChart;
    public static String salesby = "Earnings by month";
    int xAxisrange = 11;
    int year;
    String month;
    private ProgressBar progressBar;

    MainFragment mainFragment;
    private EditText searchField;
    private Spinner moodFilter, genderFilter, itemsPerPage;
    public ImageButton previousButton, nextButton;
    public EditText pageNumberField;
    public TextView tablePaginationDetails, tv_month;
    public LinearLayout ll_month;

    private List<PaymentStatsModel.PaymentStat> paymentMonth;
    private PieChart piChart;

    private QuarterlyPaymentStatsModel.PaymentStats paymentStats;
    private List<StatsByCustomerModel.PaymentStat> customerPaymentStats;
    private ImageView iv_noStats;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_stats);
        salesby = "Earnings by month";

        preference = new ChatBusinessSharedPreference(PaymentStatsActivity.this);
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        init(year);
//        initPiChart();
        getMOthlyStats();
    }

    public void init(int year) {
        progressDialog = Utill.showloader(this);

        mChart = (BarChart) findViewById(R.id.mChart);
        piChart = (PieChart) findViewById(R.id.piChart);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ll_month = (LinearLayout) findViewById(R.id.ll_month);
        ll_year = (LinearLayout) findViewById(R.id.ll_year);
        ll_salesby = (LinearLayout) findViewById(R.id.ll_salesby);
        rl_reports = (RelativeLayout) findViewById(R.id.ll_reports);
        iv_calendar = (ImageView) findViewById(R.id.iv_calendar);
        tv_salesby = (TextView) findViewById(R.id.tv_salesby);
        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_year = (TextView) findViewById(R.id.tv_year);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        iv_noStats = (ImageView) findViewById(R.id.iv_noStats);
        iv_pichart = (ImageView) findViewById(R.id.iv_pichart);
        mChart = (BarChart) findViewById(R.id.mChart);
        tv_year.setText(String.valueOf(year));
        tv_month.setText(String.valueOf(month));

              /* reports table */


        View tableControls = findViewById(R.id.table_controls);
        searchField = (EditText) findViewById(R.id.query_string);
        moodFilter = (Spinner) findViewById(R.id.mood_spinner);
        genderFilter = (Spinner) findViewById(R.id.gender_spinner);
        itemsPerPage = (Spinner) findViewById(R.id.items_per_page_spinner);

        previousButton = (ImageButton) findViewById(R.id.previous_button);
        nextButton = (ImageButton) findViewById(R.id.next_button);
        pageNumberField = (EditText) findViewById(R.id.page_number_text);
        tablePaginationDetails = (TextView) findViewById(R.id.table_details);
        tableControls.setVisibility(View.GONE);


        ll_salesby.setOnClickListener(this);
        ll_year.setOnClickListener(this);
        ll_year.setOnClickListener(this);
        ll_month.setOnClickListener(this);
        iv_arrowBack.setOnClickListener(this);

    }

    public void initPiChart() {

        piChart.setVisibility(View.VISIBLE);
        piChart.setUsePercentValues(true);
        piChart.getDescription().setEnabled(false);
        piChart.setExtraOffsets(5, 10, 5, 5);

        piChart.setDragDecelerationFrictionCoef(0.95f);

//        piChart.setCenterTextTypeface(mTfLight);
//        piChart.setCenterText(generateCenterSpannableText());

        piChart.setDrawHoleEnabled(true);
        piChart.setHoleColor(Color.WHITE);

        piChart.setTransparentCircleColor(Color.WHITE);
        piChart.setTransparentCircleAlpha(110);

//        piChart.setHoleRadius(58f);
        piChart.setHoleRadius(0f);
        piChart.setTransparentCircleRadius(0f);
//        piChart.setTransparentCircleRadius(61f);

        piChart.setDrawCenterText(true);

        piChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        piChart.setRotationEnabled(true);
        piChart.setHighlightPerTapEnabled(true);

        // piChart.setUnit(" €");
        // piChart.setDrawUnitsInChart(true);

        // add a selection listener
        piChart.setOnChartValueSelectedListener(this);

        setPichartData(4, 100);

        piChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // piChart.spin(2000, 0, 360);

        Legend l = piChart.getLegend();

        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        // entry label styling
        piChart.setEntryLabelColor(Color.WHITE);
//        piChart.setEntryLabelTypeface(mTfRegular);
        piChart.setEntryLabelTextSize(12f);
    }

    public Dialog onCreateDialogSingleChoice(final String[] array, final TextView textView, final String string) {

        //Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(string).setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                String state = array[which];
                textView.setText(state);
                setData(state, string);
            }
        });

        return builder.create();
    }

    public Dialog onCreateDialogSingleMonth(final String[] array, final TextView textView, final String string) {

        //Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(string).setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                String state = array[which];
                textView.setText(state);
                getStatsByCustomer();
            }
        });

        return builder.create();
    }

    public Dialog onCreateDialogSingleYear(final String[] array, final TextView textView, final String string) {

        //Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(string).setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                String state = array[which];
                textView.setText(state);
                if (salesby.equalsIgnoreCase("Earnings by customer")) {
                    getStatsByCustomer();
                } else if (salesby.equalsIgnoreCase("Earnings by month")) {
                    getMOthlyStats();
                } else if (salesby.equalsIgnoreCase("Earnings by quarter")) {
                    getStatsQuarterly();
                }
            }
        });

        return builder.create();
    }

    public void setData(String state, String str) {

        if (state.equalsIgnoreCase("Earnings by quarter")) {
            salesby = "Earnings by quarter";
            xAxisrange = 3;
            ll_month.setVisibility(View.GONE);
            getStatsQuarterly();

        } else if (state.equalsIgnoreCase("Earnings by month")) {
            salesby = "Earnings by month";
            ll_month.setVisibility(View.GONE);
            xAxisrange = 11;
            getMOthlyStats();

        } else {
            mChart.setVisibility(View.GONE);
        }

        if (str.equalsIgnoreCase("Sales By") && state.equalsIgnoreCase("Earnings by customer")) {
            salesby = "Earnings by customer";
            iv_calendar.setImageResource(R.drawable.customer_24dp);
            ll_month.setVisibility(View.VISIBLE);

            getStatsByCustomer();
        } else {
            ll_month.setVisibility(View.GONE);
            iv_calendar.setImageResource(R.drawable.calendar);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_arrowBack:
                onBackPressed();
                break;

            case R.id.ll_year:
                String[] year_list = getResources().getStringArray(R.array.years_list);
                Dialog dialog1 = onCreateDialogSingleYear(year_list, tv_year, "Select Year");
                dialog1.show();
                break;
            case R.id.ll_month:
                String[] month_list = getResources().getStringArray(R.array.month_list);
                Dialog dialog_month = onCreateDialogSingleMonth(month_list, tv_month, "Select Month");
                dialog_month.show();
                break;
            case R.id.ll_salesby:
                String[] salesby_array = getResources().getStringArray(R.array.sales_by_array);
                Dialog dialog2 = onCreateDialogSingleChoice(salesby_array, tv_salesby, "Sales By");
                dialog2.show();
                break;
        }
    }

    public void initchart(int xAxisrange) {

        mChart.setVisibility(View.VISIBLE);
        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setHorizontalScrollBarEnabled(true);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(xAxisrange + 1);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        /*YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
//        rightAxis.setTypeface(mTfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setMaxWidth(0f);
        */

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        setData(xAxisrange, 50);
        mChart.invalidate();
    }

    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        if (xAxisrange == 11) {
            for (int i = 1; i <= paymentMonth.size(); i++) {
                yVals1.add(new BarEntry(i, Float.parseFloat(paymentMonth.get(i - 1).getSumofEarnings())));
            }
        } else {
            yVals1.add(new BarEntry(1, Float.parseFloat(paymentStats.getQuarter1().getSumofEarnings())));
            yVals1.add(new BarEntry(2, Float.parseFloat(paymentStats.getQuarter2().getSumofEarnings())));
            yVals1.add(new BarEntry(3, Float.parseFloat(paymentStats.getQuarter3().getSumofEarnings())));
            yVals1.add(new BarEntry(4, Float.parseFloat(paymentStats.getQuarter4().getSumofEarnings())));

        }
        BarDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            set1.setLabel("The year " + tv_year.getText().toString());
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year " + tv_year.getText().toString());
//            set1 = new BarDataSet(yVals1, "");

//            set1.setDrawIcons(false);

//            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            set1.setColors(themeColor);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
//            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);
            data.setDrawValues(false);

            mChart.setData(data);

        }
    }

    public static final int[] themeColor = {
            rgb("#3579f0")
    };
    public static final int[] pichartColor1 = {
            rgb("#D6A3DC")
    };
    public static final int[] pichartColor2 = {
            rgb("#F7DB70")
    };
    public static final int[] pichartColor3 = {
            rgb("#EABEBF")
    };
    public static final int[] pichartColor4 = {
            rgb("#75CCE8")
    };
    public static final int[] pichartColor5 = {
            rgb("#A5DEE5")
    };

    public void setPichartData(int count, float range) {
        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < customerPaymentStats.get(0).getCustomers().size(); i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
//                    mParties[i % mParties.length],
                    customerPaymentStats.get(0).getCustomers().get(i).getCFullName()));
            getResources().getDrawable(R.drawable.star);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
//        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : pichartColor1)
            colors.add(c);

        for (int c : pichartColor2)
            colors.add(c);

        for (int c : pichartColor3)
            colors.add(c);

        for (int c : pichartColor4)
            colors.add(c);

        for (int c : pichartColor5)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        piChart.setData(data);

        // undo all highlights
        piChart.highlightValues(null);

        piChart.invalidate();
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    public void getMOthlyStats() {
//        progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
//        encrypt_key=sdfdsf&b_user_id=64&b_id=35&sale_by=Sales%20by%20month&year=2018
        param.put("encrypt_key", "sgdh");
        param.put("b_user_id", preference.getUserId());
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("sale_by", tv_salesby.getText().toString());
        param.put("year", tv_year.getText().toString());
        Call<PaymentStatsModel> call = apiServices.getSalesByMonth(param);
        call.enqueue(new Callback<PaymentStatsModel>() {
            @Override
            public void onResponse(Call<PaymentStatsModel> call, Response<PaymentStatsModel> response) {
                iv_noStats.setVisibility(View.GONE);
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        paymentMonth = response.body().getData().getPaymentStats();
                        rl_reports.setVisibility(View.VISIBLE);
                        piChart.setVisibility(View.GONE);
                        iv_pichart.setVisibility(View.GONE);

                        for(int i=0;i<12;i++)
                        {
                            if(!paymentMonth.get(i).getSumofEarnings().equalsIgnoreCase("0.00"))
                                  break;
                            if(paymentMonth.get(11).getSumofEarnings().equalsIgnoreCase("0.00"))
                            {
                                iv_noStats.setVisibility(View.VISIBLE);
                                return;
                            }

                        }
                        iv_noStats.setVisibility(View.GONE);

                        initchart(11);
                        replaceFragByMonth();
                    }
                }

            }

            @Override
            public void onFailure(Call<PaymentStatsModel> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(PaymentStatsActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void replaceFragByMonth() {
        mainFragment = null;
        mainFragment = new MainFragment();
        Bundle b = new Bundle();
        b.putSerializable(Constants.MONTHLY_STATS, (Serializable) paymentMonth);
        mainFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_container, mainFragment, mainFragment.getClass().getSimpleName()).commit();

    }

    public void replaceFragByQuarter() {
        mainFragment = null;
        mainFragment = new MainFragment();
        Bundle b = new Bundle();
        b.putSerializable(Constants.QUARTER_STATS, (Serializable) paymentStats);
        mainFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_container, mainFragment, mainFragment.getClass().getSimpleName()).commit();

    }

    public void replaceFragByCustomer() {
        mainFragment = null;
        mainFragment = new MainFragment();
        Bundle b = new Bundle();
        b.putSerializable(Constants.CUSTOMER_STATS, (Serializable) customerPaymentStats);
        mainFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_container, mainFragment, mainFragment.getClass().getSimpleName()).commit();

    }

    public void getStatsQuarterly() {
//        progressBar.setVisibility(View.VISIBLE);
progressDialog.show();
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
//        encrypt_key=sdfdsf&b_user_id=64&b_id=35&sale_by=Sales%20by%20month&year=2018
        param.put("encrypt_key", "sgdh");
        param.put("b_user_id", preference.getUserId());
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("sale_by", tv_salesby.getText().toString());
        param.put("year", tv_year.getText().toString());
        Call<QuarterlyPaymentStatsModel> call = apiServices.getSalesBYQuarter(param);
        call.enqueue(new Callback<QuarterlyPaymentStatsModel>() {
            @Override
            public void onResponse(Call<QuarterlyPaymentStatsModel> call, Response<QuarterlyPaymentStatsModel> response) {
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
                iv_noStats.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        paymentStats = response.body().getData().getPaymentStats();
                        piChart.setVisibility(View.GONE);
                        iv_pichart.setVisibility(View.GONE);

                        if(paymentStats.getQuarter1().getSumofEarnings().equalsIgnoreCase("0.00")&&
                                paymentStats.getQuarter2().getSumofEarnings().equalsIgnoreCase("0.00")&&
                                paymentStats.getQuarter3().getSumofEarnings().equalsIgnoreCase("0.00")&&
                                paymentStats.getQuarter4().getSumofEarnings().equalsIgnoreCase("0.00"))
                        {
                            iv_noStats.setVisibility(View.VISIBLE);
                        }
                       else {
                            iv_noStats.setVisibility(View.VISIBLE);
                            initchart(3);
                            replaceFragByQuarter();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<QuarterlyPaymentStatsModel> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();

                Toast.makeText(PaymentStatsActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getStatsByCustomer() {
//        progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();

        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        HashMap<String, String> param = new HashMap<>();
//        encrypt_key=sdfdsf&b_user_id=64&b_id=35&sale_by=Sales%20by%20month&year=2018
        param.put("encrypt_key", "sgdh");
        param.put("b_user_id", preference.getUserId());
        param.put("b_id", String.valueOf(preference.getBusinessId()));
        param.put("sale_by", tv_salesby.getText().toString());
        param.put("year", tv_year.getText().toString());
//        param.put("month", "December");
        param.put("month", tv_month.getText().toString());
        Call<StatsByCustomerModel> call = apiServices.getSalesByCustomer(param);
        call.enqueue(new Callback<StatsByCustomerModel>() {
            @Override
            public void onResponse(Call<StatsByCustomerModel> call, Response<StatsByCustomerModel> response) {
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {

                        customerPaymentStats = response.body().getData().getPaymentStats();
                        if (response.body().getData().getPaymentStats().get(0).getCustomers().size() == 0)
                            iv_noStats.setVisibility(View.VISIBLE);
                        else iv_noStats.setVisibility(View.GONE);
                        try {
                            if (customerPaymentStats.get(0).getCustomers().size() == 0)
                                iv_pichart.setVisibility(View.VISIBLE);
                            else iv_pichart.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            iv_pichart.setVisibility(View.VISIBLE);
                        }

                        initPiChart();
                        replaceFragByCustomer();
                    }
                }

            }

            @Override
            public void onFailure(Call<StatsByCustomerModel> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();

                Toast.makeText(PaymentStatsActivity.this, R.string.str_check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
