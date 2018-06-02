package com.chatlocalybusiness.api;

import com.chatlocalybusiness.apiModel.AddProductsModel;
import com.chatlocalybusiness.apiModel.AddServiceModel;
import com.chatlocalybusiness.apiModel.AdminContactMOdel;
import com.chatlocalybusiness.apiModel.AdminVerifyModel;
import com.chatlocalybusiness.apiModel.AppealModel;
import com.chatlocalybusiness.apiModel.BlockThreadListModel;
import com.chatlocalybusiness.apiModel.PaymentStatsModel;
import com.chatlocalybusiness.apiModel.ProServiceCountModel;
import com.chatlocalybusiness.apiModel.ProductDetailModel;
import com.chatlocalybusiness.apiModel.QuarterlyPaymentStatsModel;
import com.chatlocalybusiness.apiModel.ResponseModel;
import com.chatlocalybusiness.apiModel.BrandListModel;
import com.chatlocalybusiness.apiModel.BusinessBannerModel;
import com.chatlocalybusiness.apiModel.BusinessCatModel;
import com.chatlocalybusiness.apiModel.BusinessDetailsModel;
import com.chatlocalybusiness.apiModel.BusinessInfoModelNew;
import com.chatlocalybusiness.apiModel.BusinessLocationModel;
import com.chatlocalybusiness.apiModel.CategoryListModel;
import com.chatlocalybusiness.apiModel.ChatThreadListModel;
import com.chatlocalybusiness.apiModel.CheckStatusModel;
import com.chatlocalybusiness.apiModel.CityListModel;
import com.chatlocalybusiness.apiModel.CurrentPlanModel;
import com.chatlocalybusiness.apiModel.DeleteImagesModel;
import com.chatlocalybusiness.apiModel.FeatureSettingsModel;
import com.chatlocalybusiness.apiModel.GetFeaturesSettingsModel;
import com.chatlocalybusiness.apiModel.InvoiceModel;
import com.chatlocalybusiness.apiModel.InvoiceOrderDetailModel;
import com.chatlocalybusiness.apiModel.InvoiceOrderListModel;
import com.chatlocalybusiness.apiModel.LocalityListModel;
import com.chatlocalybusiness.apiModel.OtpVerifyModel;
import com.chatlocalybusiness.apiModel.ProductListModel;
import com.chatlocalybusiness.apiModel.RegisterModel;
import com.chatlocalybusiness.apiModel.RegisterPaytmModel;
import com.chatlocalybusiness.apiModel.SendPromoModel;
import com.chatlocalybusiness.apiModel.ServiceListModel;
import com.chatlocalybusiness.apiModel.SettingsInternalModel;
import com.chatlocalybusiness.apiModel.StatsByCustomerModel;
import com.chatlocalybusiness.apiModel.SubcriptionPlanListModel;
import com.chatlocalybusiness.apiModel.UpdateOrderStatusModel;
import com.chatlocalybusiness.apiModel.UpdateProfileModel;
import com.chatlocalybusiness.apiModel.UserProfileModel;
import com.chatlocalybusiness.apiModel.WorkerOtpVerifyModel;
import com.chatlocalybusiness.apiModel.WorkerRegisterModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Shiv on 12/7/2017.
 */

public interface ApiServices {

    @FormUrlEncoded
    @POST("check_business_status")
    Call<CheckStatusModel> checkStatus(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("business_admin_register")
    Call<RegisterModel> userRegister(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("business_worker_register")
    Call<WorkerRegisterModel> workerRegister(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("delete_user_to_business")
    Call<ResponseModel> removeUser(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("worker_verify_by_admin")
    Call<AdminContactMOdel> adminContactVerify(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("otp_verify")
    Call<OtpVerifyModel> otpVeifyApi(@FieldMap HashMap<String, String> param);


    @FormUrlEncoded
    @POST("worker_verify_password")
    Call<AdminVerifyModel> adminPassVerify(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("worker_otp_verify")
    Call<WorkerOtpVerifyModel> otpWorkerVeifyApi(@FieldMap HashMap<String, String> param);

    @Multipart
    @POST("b_profile_update")
    Call<UpdateProfileModel> updateProfile(@PartMap HashMap<String, RequestBody> param, @Part MultipartBody.Part image);

    @Multipart
    @POST("business_appeal")
    Call<AppealModel> appealApi(@PartMap HashMap<String, RequestBody> param, @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("city_list")
    Call<CityListModel> getCityList(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("locality_list")
    Call<LocalityListModel> getLocalityList(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("sp_plan_list")
    Call<SubcriptionPlanListModel> getSubscriptionPlan(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("promo_code_insert")
    Call<SendPromoModel> getMembership(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("sub_category_list")
    Call<CategoryListModel> getCategoryList(@FieldMap HashMap<String, String> param);

    @Multipart
    @POST("update_business")
    Call<BusinessDetailsModel> registerBusinessDetails(@PartMap HashMap<String, RequestBody> param,
                                                       @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("update_business_categories")
    Call<BusinessCatModel> postBusinessCat(@FieldMap HashMap<String, String> param);


    @FormUrlEncoded
    @POST("update_business_location")
    Call<BusinessLocationModel> postBusinessLocation(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("delete_business_images")
    Call<DeleteImagesModel> deleteImages(@FieldMap HashMap<String, String> param);

    @Multipart
    @POST("update_business_images")
    Call<BusinessBannerModel> postBusinessPhotos(@Part List<MultipartBody.Part> param,
                                                 @Part MultipartBody.Part image,
                                                 @PartMap HashMap<String, RequestBody> paramList);

    @FormUrlEncoded
    @POST("business_step_five")
    Call<RegisterPaytmModel> registerPaytm(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("current_subscription_plan")
    Call<CurrentPlanModel> currentPlanDetails(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("user_setting_update")
    Call<FeatureSettingsModel> updateFeaturesSetiings(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("get_user_setting")
    Call<GetFeaturesSettingsModel> getFeaturesSetiings(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("business_user_list")
    Call<SettingsInternalModel> getBusinessWorkerList(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("get_business_detail")
    Call<BusinessInfoModelNew> getBusiessDetail(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("product_list")
    Call<ProductListModel> getProductList(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("get_product_details")
    Call<ProductDetailModel> getProductdetail(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("service_list")
    Call<ServiceListModel> getServiceList(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("brand_list")
    Call<BrandListModel> getBrandList(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("get_threads_list")
    Call<ChatThreadListModel> getChatThread(@FieldMap HashMap<String, String> param);

//    /?encrypt_key=sdfsd&chat_group_id=1&b_user_id=2&b_id=2&block_type=block

    @FormUrlEncoded
    @POST("block_chat_group")
    Call<ResponseModel> blockusers(@FieldMap HashMap<String, String> param);

    //    get_block_threads_list?encrypt_key=hbdvc&b_user_id=64&user_id=0B64&device_key=9ce9a9d2-d2d8-4e12-8396-40844104e455
    @FormUrlEncoded
    @POST("get_block_threads_list")
    Call<BlockThreadListModel> getBlockedList(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("customer_tag")
    Call<ResponseModel> tagPeople(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("business_notification")
    Call<ResponseModel> notification_on(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("generate_order")
    Call<InvoiceModel> postInvoiceData(@FieldMap HashMap<String, String> param);


    @FormUrlEncoded
    @POST("send_order")
    Call<ResponseModel> sendOrderApi(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("get_user_profile")
    Call<UserProfileModel> getUserProfile(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("get_product_service_counts")
    Call<ProServiceCountModel> getProServiceCount(@FieldMap HashMap<String, String> param);


    @FormUrlEncoded
    @POST("order_list")
    Call<InvoiceOrderListModel> getInvoiceOderLst(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("delete_order")
    Call<ResponseModel> deleteOrder(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("get_order_detail")
    Call<InvoiceOrderDetailModel> getInvoiceOderDetail(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("update_order_detail")
    Call<UpdateOrderStatusModel> sendConfirmationId(@FieldMap HashMap<String, String> param);

    //    http://192.168.0.60/chatlocaly/business_api/get_sale_by_months?encrypt_key=s&b_user_id=64&b_id=35&year=2018
    @FormUrlEncoded
    @POST("get_sale_by_months")
    Call<PaymentStatsModel> getSalesByMonth(@FieldMap HashMap<String, String> param);

    //    http://192.168.0.60/chatlocaly/business_api/get_sale_by_quarter?encrypt_key=s&b_user_id=64&b_id=35&year=2018
    @FormUrlEncoded
    @POST("get_sale_by_quarter")
    Call<QuarterlyPaymentStatsModel> getSalesBYQuarter(@FieldMap HashMap<String, String> param);

    //http:get_sale_by_customer?encrypt_key=s&b_user_id=64&b_id=35&year=2018&month=December
    @FormUrlEncoded
    @POST("get_sale_by_customer")
    Call<StatsByCustomerModel> getSalesByCustomer(@FieldMap HashMap<String, String> param);

    @FormUrlEncoded
    @POST("set_notification_status")
    Call<ResponseModel> setNotificationStatus(@FieldMap HashMap<String, String> param);

    @Multipart
    @POST("add_product")
    Call<AddProductsModel> addProduct(@Part List<MultipartBody.Part> param,
                                      @Part MultipartBody.Part image,
                                      @PartMap HashMap<String, RequestBody> paramList);

    @Multipart
    @POST("update_product")
    Call<AddProductsModel> update_product(@Part List<MultipartBody.Part> param,
                                          @Part MultipartBody.Part image,
                                          @PartMap HashMap<String, RequestBody> paramList,
                                          @PartMap HashMap<String, RequestBody> deleteImageParam);

    @Multipart
    @POST("add_service")
    Call<AddServiceModel> addService(@Part List<MultipartBody.Part> param,
                                     @Part MultipartBody.Part image,
                                     @PartMap HashMap<String, RequestBody> paramList,
                                     @PartMap HashMap<String, RequestBody> brandNames);


    @Multipart
    @POST("update_service")
    Call<AddServiceModel> editService(@Part List<MultipartBody.Part> param,
                                      @Part MultipartBody.Part image,
                                      @PartMap HashMap<String, RequestBody> paramList,
                                      @PartMap HashMap<String, RequestBody> brandNames);
}
