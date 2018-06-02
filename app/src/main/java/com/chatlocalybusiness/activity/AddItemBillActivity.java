package com.chatlocalybusiness.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.Applozic;
import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterForAutoComplete;
import com.chatlocalybusiness.getterSetterModel.AddItemBillModel;
import com.chatlocalybusiness.apiModel.ProductListModel;
import com.chatlocalybusiness.getterSetterModel.BuyerInfoModel;
import com.chatlocalybusiness.sharesprefrence.ChatBusinessSharedPreference;
import com.chatlocalybusiness.utill.BasicUtill;
import com.chatlocalybusiness.utill.Constants;
import com.chatlocalybusiness.utill.Utill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by windows on 2/12/2018.
 */
public class AddItemBillActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    @Override
    protected void onResume() {
        super.onResume();
        new BasicUtill().CheckStatus(AddItemBillActivity.this, 0,rl_addItem);
    }
    ViewGroup rl_addItem;
    private AutoCompleteTextView et_itemName;
    private EditText et_itemDiscription, et_qty, et_unit, et_salesRate;
    private TextView tv_totalPrice, tv_discount, tv_discountValue, tv_tax, tv_taxValue, tv_MRPAmout;
    private ImageView iv_arrowBack;
    private CheckBox cb_service;
    private ChatBusinessSharedPreference preference;
    private Utill utill;
    private LinearLayout ll_next, ll_previous;
    private List<ProductListModel.PublishProduct> productLists;
    private List<ProductListModel.PublishProduct> productLists2;
    private int adapterPosition = -1;
    private AlertDialog taxDialog;
    private TextView tax_3, tax_5, tax_12, tax_18, tax_28, tv_cancel, tv_ok;
    private EditText et_cgst, et_sgst;
    private double taxAmount;
    private int tax_include = 0;
    private int mannualDiscount = -1;
    List<AddItemBillModel.ItemDetails> itemDetailsList;
    private String add_Edit = "ADD_ITEM";
    private int editPosition;
    private BuyerInfoModel buyerInfoModel;
    String price;
    String percentDiscount="0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_additem);

        rl_addItem=(ViewGroup)findViewById(R.id.rl_addItem);
        Bundle b = getIntent().getExtras();
        if(productLists2==null)
        productLists2=new ArrayList<>();
        else productLists2.clear();
        if (b != null) {
            productLists = (List<ProductListModel.PublishProduct>) b.getSerializable(Constants.PRODUCT_SERIALIZABLE);
            productLists2.addAll(productLists);
            buyerInfoModel = (BuyerInfoModel) b.getSerializable(Constants.BUYER_INFO);

      /*      if (productLists != null) {
                String[] brandArray = new String[productLists.size()];
                for (int i = 0; i < productLists.size(); i++) {
                    brandArray[i] = productLists.get(i).getProductName();
                }
                init();
            }*/
        init();
        }

        getValues();
    }

    public void getValues() {
        Bundle b = getIntent().getExtras();
        if (b != null)
            itemDetailsList = (List<AddItemBillModel.ItemDetails>) getIntent().getExtras().getSerializable(Constants.PRODUCT_DETAIL);
        if (itemDetailsList == null)
            itemDetailsList = new ArrayList<AddItemBillModel.ItemDetails>();
        else {
            add_Edit = b.getString(Constants.ADD_EDIT_ITEM);
            editPosition = b.getInt(Constants.POSITION);
            if (add_Edit.equals("EDIT_ITEM")) {
                editValues(editPosition);
            }
        }
    }

    private void editValues(int i) {
        et_itemName.setText(itemDetailsList.get(i).getItemName());
        if (itemDetailsList.get(i).getDiscription() != null)
            et_itemDiscription.setText(itemDetailsList.get(i).getDiscription());
        et_salesRate.setText(itemDetailsList.get(i).getRate());
        et_qty.setText(itemDetailsList.get(i).getQty());
        et_qty.setEnabled(true);

        tv_totalPrice.setText(itemDetailsList.get(i).getTotalPrice());
        et_itemDiscription.setText(itemDetailsList.get(i).getDiscription());
        et_unit.setText(itemDetailsList.get(i).getUnit());

        tv_discount.setText("Discount    " + itemDetailsList.get(i).getDiscountPercentage() + "%");
        tv_discountValue.setText("-" + itemDetailsList.get(i).getDiscount());
        tv_taxValue.setText(itemDetailsList.get(i).getTaxTotal());
        setTaxBtnEnabled(true);
//        setMrp();
        tv_MRPAmout.setText(itemDetailsList.get(i).getMrp());
    }

    private void setValues(String price,String percentDiscount) {
//        et_salesRate.setText(productLists.get(i).getPrice());
        et_salesRate.setText(price);
        et_qty.setEnabled(true);

        if (mannualDiscount < 0) {
            et_qty.setText("1");

//            tv_discount.setText("Discount    " + productLists.get(i).getDiscount() + "%");
            tv_discount.setText("Discount    " +percentDiscount+ "%");
//            double discount = (Double.parseDouble(productLists.get(i).getDiscount()) * Double.parseDouble(tv_totalPrice.getText().toString())) / 100;
            double discount = (Double.parseDouble(percentDiscount) * Integer.parseInt(et_qty.getText().toString())*Double.parseDouble(et_salesRate.getText().toString())) / 100;
            tv_discountValue.setText("-" + roundOff(discount));

        } else {

            tv_discount.setText("Discount    " + mannualDiscount + "%");
            double discount = (Integer.parseInt(et_qty.getText().toString())*mannualDiscount * Double.parseDouble(et_salesRate.getText().toString())) / 100;
            tv_discountValue.setText("-" + roundOff(discount));

        }
//        int qty=Integer.parseInt(et_qty.getText().toString());
//        double rate=Double.parseDouble(et_salesRate.getText().toString());
//        tv_totalPrice.setText(String.valueOf( (qty* rate)+Double.parseDouble(tv_discountValue.getText().toString())));
//
        setTaxBtnEnabled(true);
        setMrp();
    }

    public Dialog onCreateDialogSingleChoice(final String[] array, final TextView textView, final String string) {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select"+ string).setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                String state = array[which];
                if (string.equalsIgnoreCase("unit"))
                    textView.setText(state);
                else {
                    textView.setText("Discount    " + state + "%");
                    mannualDiscount = Integer.parseInt(state);
                    try {
                        setValues(price, percentDiscount);
                    }
                    catch (Exception ex){}
                }
            }
        });

        return builder.create();
    }

    public void init() {
        preference = new ChatBusinessSharedPreference(this);
        utill = new Utill();
        et_itemName = (AutoCompleteTextView) findViewById(R.id.et_itemName);
        et_itemDiscription = (EditText) findViewById(R.id.et_itemDiscription);
        et_salesRate = (EditText) findViewById(R.id.et_salesRate);
        et_qty = (EditText) findViewById(R.id.et_qty);
        et_unit = (EditText) findViewById(R.id.et_unit);
        tv_totalPrice = (TextView) findViewById(R.id.tv_totalPrice);
        tv_discount = (TextView) findViewById(R.id.tv_discount);
        tv_discountValue = (TextView) findViewById(R.id.tv_discountValue);
        tv_tax = (TextView) findViewById(R.id.tv_tax);
        tv_taxValue = (TextView) findViewById(R.id.tv_taxValue);
        tv_MRPAmout = (TextView) findViewById(R.id.tv_MRPAmout);
        iv_arrowBack = (ImageView) findViewById(R.id.iv_arrowBack);
        ll_next = (LinearLayout) findViewById(R.id.ll_next);
        ll_previous = (LinearLayout) findViewById(R.id.ll_previous);

        iv_arrowBack.setOnClickListener(this);
        ll_next.setOnClickListener(this);
        tv_tax.setOnClickListener(this);
        ll_previous.setOnClickListener(this);
        et_unit.setOnClickListener(this);
        tv_discount.setOnClickListener(this);
        setTaxBtnEnabled(false);
        et_qty.addTextChangedListener(this);

        /*   set custom Adapter for autocompletetext */
        et_itemName.setThreshold(1);
        if(productLists2!=null) {
            AdapterForAutoComplete adapter = new AdapterForAutoComplete(this, R.layout.acitivity_additem, R.id.tv_filtered, productLists2);
            et_itemName.setAdapter(adapter);
            et_itemName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ProductListModel.PublishProduct selected = (ProductListModel.PublishProduct) adapterView.getAdapter().getItem(i);
                    price = selected.getPrice();
                    percentDiscount = selected.getDiscount();
                    String price = productLists2.get(i).getPrice();
                    mannualDiscount = -1;
                    adapterPosition = i;
                    et_itemDiscription.setEnabled(true);
                    setValues(price, percentDiscount);
                }
            });
        }
        /*   set on focus changeListener if not selected any product all values must be zero*/

        et_itemName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    // on focus off
                    String str = et_itemName.getText().toString();

                    ListAdapter listAdapter = et_itemName.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); i++) {
                        ProductListModel.PublishProduct temp = (ProductListModel.PublishProduct) listAdapter.getItem(i);
                        if (str.compareTo(temp.getProductName()) == 0) {
                            return;
                        }
                    }
                    adapterPosition = -1;
                    et_salesRate.setText("");
                    et_salesRate.setHint("Sales Rate");
                    et_salesRate.setEnabled(false);
                    et_itemName.setText("");
                    et_itemName.setHint("Item Name");
                    et_qty.setText("");
                    et_qty.setHint("Quantity");
                    tv_totalPrice.setText("0");
                    et_qty.setEnabled(false);
                    et_itemDiscription.setEnabled(false);
                    tv_discount.setText("Discount    0%");
                    tv_discountValue.setText("0");
                    tv_MRPAmout.setText("0");
                    Toast.makeText(AddItemBillActivity.this, "No such Item found in Products", Toast.LENGTH_SHORT).show();
                    setTaxBtnEnabled(false);
                    setMrp();

                }
            }
        });
    }

    public void setTaxBtnEnabled(boolean b) {
        if (b) {
            tv_tax.setTextColor(getResources().getColor(R.color.white));
            tv_tax.setBackgroundResource(R.drawable.blue_btn_bg);
            tv_tax.setEnabled(true);
            tv_discount.setTextColor(getResources().getColor(R.color.white));
            tv_discount.setBackgroundResource(R.drawable.blue_btn_bg);
            tv_discount.setEnabled(true);
        } else {
            tv_tax.setTextColor(getResources().getColor(R.color.light_gray));
            tv_tax.setBackgroundResource(R.drawable.gray_btn_bg);
            tv_tax.setEnabled(false);
            tv_taxValue.setText("0");

            tv_discount.setTextColor(getResources().getColor(R.color.light_gray));
            tv_discount.setBackgroundResource(R.drawable.gray_btn_bg);
            tv_discount.setEnabled(false);
            tv_discount.setText("Discount    0%");


        }

    }

    public double roundOff(double value) {
        double roundoffValue = Math.round(value * 100.0) / 100.0;
        return roundoffValue;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_next:

                if (isEmpty()) {
                } else {
                    if (add_Edit.equals("EDIT_ITEM")) {
                        editItemBill(editPosition);
                    } else saveItemBill();

                    Intent intent = new Intent(AddItemBillActivity.this, BillItemsListActivity.class);
                    intent.putExtra(Constants.PRODUCT_DETAIL, (Serializable) itemDetailsList);
                    intent.putExtra(Constants.PRODUCT_SERIALIZABLE, (Serializable) productLists);
                    intent.putExtra(Constants.BUYER_INFO, (Serializable) buyerInfoModel);
                    startActivityForResult(intent,Constants.FINISH);
                    finish();
                }
                break;
            case R.id.ll_previous:
                onBackPressed();
                break;
            case R.id.et_unit:
                String[] array = getResources().getStringArray(R.array.unit_list);
                Dialog dialog = onCreateDialogSingleChoice(array, et_unit, "Unit");
                dialog.show();

                break;
            case R.id.tv_tax:
                selectDisplayTax();
                break;
            case R.id.iv_arrowBack:
                onBackPressed();
                break;
            case R.id.tv_discount:
                String[] discountarray = getResources().getStringArray(R.array.discount_list);
                Dialog dialog2 = onCreateDialogSingleChoice(discountarray, tv_discount, "Discount");
                dialog2.show();
                break;
            case R.id.tax_3:
                onselectTaxChangeColor(tax_3, new TextView[]{tax_5, tax_12, tax_18, tax_28});
                break;
            case R.id.tax_5:
                onselectTaxChangeColor(tax_5, new TextView[]{tax_3, tax_12, tax_18, tax_28});

                break;
            case R.id.tax_12:
                onselectTaxChangeColor(tax_12, new TextView[]{tax_5, tax_3, tax_18, tax_28});

                break;
            case R.id.tax_18:
                onselectTaxChangeColor(tax_18, new TextView[]{tax_5, tax_12, tax_3, tax_28});

                break;
            case R.id.tax_28:
                onselectTaxChangeColor(tax_28, new TextView[]{tax_5, tax_12, tax_18, tax_3});
                break;
            case R.id.tv_ok:

                if (!et_cgst.getText().toString().equals("") || !et_sgst.getText().toString().equals("")) {
                    if (tax_include > 0) {
                        getTotalTax();
                        taxDialog.dismiss();
                        tv_taxValue.setText(roundOff(taxAmount) + "");
                        setMrp();
                    } else
                        Toast.makeText(this, "Select tax is Inclusive or Exclusive ", Toast.LENGTH_SHORT).show();


                } else Toast.makeText(this, "Enter any of tax ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_cancel:
                taxDialog.dismiss();
                break;
        }
    }

    @Override
    public void onBackPressed() {
      BasicUtill.sendIntent(AddItemBillActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode==Constants.FINISH)
                BasicUtill.sendIntent(AddItemBillActivity.this);
        }
    }
    int taxPercentage;

    private void getTotalTax() {

        if (!et_cgst.getText().toString().trim().equals("") && !et_sgst.getText().toString().trim().equals("")) {
            taxPercentage = Integer.parseInt(et_cgst.getText().toString().trim()) + Integer.parseInt(et_sgst.getText().toString().trim());
            if (tax_include == 1) {
                double totalamount = (Double.parseDouble(tv_totalPrice.getText().toString()) * 100) / (100 + taxPercentage);
                taxAmount = (totalamount * taxPercentage) / 100;
            } else
                taxAmount = (taxPercentage * Double.parseDouble(tv_totalPrice.getText().toString())) / 100;
            return;
        } else if (!et_sgst.getText().toString().trim().equals("")) {
            taxPercentage = Integer.parseInt(et_sgst.getText().toString().trim());
            if (tax_include == 1) {
                double totalamount = (Double.parseDouble(tv_totalPrice.getText().toString()) * 100) / (100 + taxPercentage);
                taxAmount = (totalamount * taxPercentage) / 100;
            } else

                taxAmount = (taxPercentage * Double.parseDouble(tv_totalPrice.getText().toString())) / 100;
            return;
        } else if (!et_cgst.getText().toString().trim().equals("")) {
            taxPercentage = Integer.parseInt(et_cgst.getText().toString().trim());
            if (tax_include == 1) {
                double totalamount = (Double.parseDouble(tv_totalPrice.getText().toString()) * 100) / (100 + taxPercentage);
                taxAmount = (totalamount * taxPercentage) / 100;
            } else
                taxAmount = (taxPercentage * Double.parseDouble(tv_totalPrice.getText().toString())) / 100;
            return;
        }
    }

    public boolean isEmpty() {
        if (et_itemName.getText().toString().trim().equals("")) {
            et_itemName.setError("Enter the item name");
            return true;
        } else if (et_qty.getText().toString().trim().equals("")) {
            et_qty.setError("Quantity can't be less than one");
            return true;
        }
      /* else if (et_unit.getText().toString().trim().equals("")) {
            et_unit.setError("select the unit");
            return true;
        } */else if (et_salesRate.getText().toString().trim().equals("")) {
            et_salesRate.setError("enter the sales rate");
            return true;
        }
        else if (!et_qty.getText().toString().trim().equals("")) {
            if(Integer.parseInt(et_qty.getText().toString().trim())<1) {
                et_qty.setError("Quantity can't be less than one");
            return true;
            }
            else return false;

        }
        else return false;
    }

    public void saveItemBill() {

        AddItemBillModel addItemBillModel = new AddItemBillModel();
        AddItemBillModel.ItemDetails addItemDetails = addItemBillModel.new ItemDetails();

        addItemDetails.setItemName(et_itemName.getText().toString().trim());
        addItemDetails.setItemUnit(et_unit.getText().toString().trim());
        addItemDetails.setQty(et_qty.getText().toString().trim());
        addItemDetails.setItemId(productLists2.get(adapterPosition).getProductId());
        addItemDetails.setRate(productLists2.get(adapterPosition).getPrice());
        addItemDetails.setTotalPrice(tv_totalPrice.getText().toString());
        addItemDetails.setMrp(tv_MRPAmout.getText().toString());
        addItemDetails.setDiscountPercentage(tv_discount.getText().toString());
        addItemDetails.setDiscount(tv_discountValue.getText().toString());
        addItemDetails.setTaxTotal(tv_taxValue.getText().toString());
        addItemDetails.setUnit(et_unit.getText().toString());
        addItemDetails.setTaxinclusive(tax_include);

     if(!tv_taxValue.getText().toString().trim().equals("0")) {
         addItemDetails.setCgst(et_cgst.getText().toString().trim());
         addItemDetails.setSgst(et_sgst.getText().toString().trim());
     }
     addItemDetails.setProductPrice(et_salesRate.getText().toString().trim());

        addItemDetails.setDiscription(et_itemDiscription.getText().toString());

        itemDetailsList.add(addItemDetails);
    }

    public void editItemBill(int position) {

        AddItemBillModel addItemBillModel = new AddItemBillModel();
        AddItemBillModel.ItemDetails addItemDetails = addItemBillModel.new ItemDetails();

        addItemDetails.setItemName(et_itemName.getText().toString().trim());
        addItemDetails.setItemUnit(et_unit.getText().toString().trim());
        addItemDetails.setQty(et_qty.getText().toString().trim());
        addItemDetails.setItemId(itemDetailsList.get(position).getItemId());
        addItemDetails.setRate(itemDetailsList.get(position).getRate());
        addItemDetails.setTotalPrice(tv_totalPrice.getText().toString());
        addItemDetails.setMrp(tv_MRPAmout.getText().toString());
        addItemDetails.setDiscountPercentage( tv_discount.getText().toString());
        addItemDetails.setDiscount(tv_discountValue.getText().toString());
        addItemDetails.setTaxTotal(tv_taxValue.getText().toString());
        addItemDetails.setUnit(et_unit.getText().toString());
        addItemDetails.setTaxinclusive(tax_include);
        addItemDetails.setCgst(et_cgst.getText().toString().trim());
        addItemDetails.setSgst(et_sgst.getText().toString().trim());
        addItemDetails.setProductPrice(et_salesRate.getText().toString().trim());

        addItemDetails.setDiscription(et_itemDiscription.getText().toString());


        itemDetailsList.remove(editPosition);
        itemDetailsList.add(editPosition, addItemDetails);

    }
       /*  on text change listener for quantity changed*/
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        try {
            onQtyChanged(charSequence.toString());
        } catch (Exception ex) {
            tv_totalPrice.setText("0");
            tv_discountValue.setText("0");
            tv_taxValue.setText("0");

        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    public void onQtyChanged(String charSequence) {

        if (add_Edit.equals("EDIT_ITEM")) {
            if (Integer.parseInt(charSequence.toString()) > 0) {
                if (mannualDiscount < 0) {
                    double discount = Integer.parseInt(charSequence.toString()) * Double.parseDouble(et_salesRate.getText().toString()) * Double.parseDouble(itemDetailsList.get(editPosition).getDiscountPercentage()) / 100;
                    tv_discountValue.setText("-" + roundOff(discount));
                } else {
                    double discount = Integer.parseInt(charSequence.toString()) * Double.parseDouble(et_salesRate.getText().toString()) * mannualDiscount / 100;
                    tv_discountValue.setText("-" + roundOff(discount));
                }

                tv_taxValue.setText("0");
                tv_totalPrice.setText(String.valueOf(Integer.parseInt(charSequence.toString()) * Double.parseDouble(et_salesRate.getText().toString()) + Double.parseDouble(tv_discountValue.getText().toString())));

            } else {
                tv_totalPrice.setText("0");
                tv_discountValue.setText("0");
                tv_taxValue.setText("0");

            }
        } else {
            if (Integer.parseInt(charSequence.toString()) > 0) {

                if (mannualDiscount < 0) {
                    double discount = Integer.parseInt(charSequence.toString()) * Double.parseDouble(et_salesRate.getText().toString()) * Double.parseDouble(productLists2.get(adapterPosition).getDiscount()) / 100;
                    tv_discountValue.setText("-" + roundOff(discount));
                } else {
                    double discount = Integer.parseInt(charSequence.toString()) * Double.parseDouble(et_salesRate.getText().toString()) * mannualDiscount / 100;
                    tv_discountValue.setText("-" + roundOff(discount));

                }
                tv_taxValue.setText("0");
                int qty = Integer.parseInt(et_qty.getText().toString());
              if(tax_include==2)
                tv_totalPrice.setText(String.valueOf(Integer.parseInt(charSequence.toString()) * Double.parseDouble(et_salesRate.getText().toString())
                        + Double.parseDouble(tv_discountValue.getText().toString())+qty*tax));
                 else  tv_totalPrice.setText(String.valueOf(Integer.parseInt(charSequence.toString()) * Double.parseDouble(et_salesRate.getText().toString())
                      + Double.parseDouble(tv_discountValue.getText().toString())));

            } else {
                tv_totalPrice.setText("0");
                tv_discountValue.setText("0");
                tv_taxValue.setText("0");

            }
        }
    }
    double tax = 0;
    public void setMrp() {
        try {
            double MrpAmount = Double.parseDouble(et_salesRate.getText().toString());
            if (tax_include == 2) {
                tax = (MrpAmount * taxPercentage) / 100;
            }
            int qty = Integer.parseInt(et_qty.getText().toString());
            double rate = Double.parseDouble(et_salesRate.getText().toString());
            tv_totalPrice.setText(String.valueOf((qty * rate) + Double.parseDouble(tv_discountValue.getText().toString()) + qty*tax));
            MrpAmount = MrpAmount + tax;
            tv_MRPAmout.setText(MrpAmount + "");
        } catch (Exception ex) {
        }

    }


    public void selectDisplayTax() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_gst, null);
        dialogBuilder.setView(dialogView);

        taxDialogInit(dialogView);

        taxDialog = dialogBuilder.create();
        taxDialog.show();
    }

    public void taxDialogInit(View dialogView) {
        tax_include = 0;
        et_cgst = (EditText) dialogView.findViewById(R.id.et_cgst);
        et_sgst = (EditText) dialogView.findViewById(R.id.et_sgst);
        tax_3 = (TextView) dialogView.findViewById(R.id.tax_3);
        tax_5 = (TextView) dialogView.findViewById(R.id.tax_5);
        tax_12 = (TextView) dialogView.findViewById(R.id.tax_12);
        tax_18 = (TextView) dialogView.findViewById(R.id.tax_18);
        tax_28 = (TextView) dialogView.findViewById(R.id.tax_28);
        tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
        tv_ok = (TextView) dialogView.findViewById(R.id.tv_ok);
        RadioGroup rg_tax = (RadioGroup) dialogView.findViewById(R.id.rg_tax);

        tv_ok.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tax_3.setOnClickListener(this);
        tax_5.setOnClickListener(this);
        tax_12.setOnClickListener(this);
        tax_18.setOnClickListener(this);
        tax_28.setOnClickListener(this);
        rg_tax.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.rb_exclusive)
                    tax_include = 2;
                else if (id == R.id.rb_inclusive)
                    tax_include = 1;
            }
        });

    }

    public void onselectTaxChangeColor(TextView textView, TextView[] textViews) {
        textView.setBackgroundResource(R.drawable.notify_circle_blue);
        textView.setTextColor(getResources().getColor(R.color.white));

        if (et_sgst.isFocused()) {
            et_sgst.setText(textView.getText().toString());
            et_cgst.requestFocus();
        } else if (et_cgst.isFocused()) {
            et_cgst.setText(textView.getText().toString());
            et_sgst.requestFocus();

        }

        for (TextView view : textViews) {

            view.setTextColor(getResources().getColor(R.color.lightblack));
            view.setBackgroundResource(R.drawable.tax_circle);

        }
    }

}
