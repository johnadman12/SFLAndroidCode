package stock.com.networkCall

import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import stock.com.ui.pojo.*


interface ApiInterface {

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/register")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("invite_code") invite_code: String,
        @Field("phone_number") phone_number: String,
        @Field("dob") dob: String,
        @Field("username") name: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("device_type") device_type: String,
        @Field("device_token") device_token: String,
        @Field("notification") notification: String,
        @Field("termAcceptance") termAcceptance: String,
        @Field("type") type: String
    ): Call<SignupPojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/register")
    fun registerSocial(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("invite_code") invite_code: String,
        @Field("phone_number") phone_number: String,
        @Field("dob") dob: String,
        @Field("username") name: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("device_type") device_type: String,
        @Field("device_token") device_token: String,
        @Field("notification") notification: String,
        @Field("termAcceptance") termAcceptance: String,
        @Field("social_id") social_id: String,
        @Field("social_type") social_type: String,
        @Field("type") type: String
    ): Call<SignupPojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/verify_otp")
    fun otpVerify(
        @Field("user_id") user_id: String,
        @Field("otp") otp: String
    ): Call<SignupPojo>


    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/resend_otp")
    fun resendOtp(@Field("phone_number") phone_number: String): Call<SignupPojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/resend_request_otp")
    fun resendRequestOtp(
        @Field("phone_number") phone_number: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("user_id") userid: String
    ): Call<SignupPojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/forgot_password")
    fun forgot_pass(
        @Field("email") email: String
        , @Field("username") username: String,
        @Field("phone_number") phonenumber: String
    ): Call<BasePojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/dob_verify")
    fun dob_verify(
        @Field("user_id") userid: String,
        @Field("dob") dob: String
    ): Call<SignupPojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/forgot_verify_otp")
    fun forgot_verify_otp(
        @Field("user_id") userid: String,
        @Field("otp") otp: String
    ): Call<SignupPojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/new_verify_phone_otp")
    fun verify_otp_new(@Field("user_id") userid: String, @Field("otp") otp: String,@Field("phone_number")phone_number:String): Call<SignupPojo>



    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/request_otp")
    fun requestOtp(
        @Field("user_id") userid: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("phone_number") phone_number: String
    ): Call<SignupPojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/reset_password")
    fun resetPassword(
        @Field("user_id") userid: String,
        @Field("password") password: String
    ): Call<SignupPojo>


    @FormUrlEncoded
    @POST("users/change_password")
    fun changePassword(@Header("x-access-token") token: String,@Field("user_id") userid: String,@Field("old_password") old_password: String,@Field("new_password")new_password:String): Call<BasePojo>








    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_type") device_type: String,
        @Field("device_token") device_token: String
    ): Call<SignupPojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/phone_login")
    fun phoneLogin(
        @Field("phone_number") email: String,
        @Field("password") password: String,
        @Field("device_type") device_type: String,
        @Field("device_token") device_token: String
    ): Call<SignupPojo>

    @GET("https://raw.githubusercontent.com/yasiriqbal776/AndroidHelper/master/countries.json")
    fun getCountry(): Call<BasePojo>

    @Headers(
        "content-type: application/x-www-form-urlencoded"
    )
    @GET("contest/GetFeaturedContestsList")
    fun getFeatureContentlist(): Call<HomePojo>

    @Headers("content-type: application/x-www-form-urlencoded")
    @GET("contest/news")
    fun getLatestNewslist(@Header("x-access-token") token: String): Call<NewsPojo>

    @Headers("content-type: application/x-www-form-urlencoded")
    @GET("contest/GetContestsList")
    fun getContestList(@Header("x-access-token") token: String): Call<LobbyContestPojo>


    @Headers("content-type: application/x-www-form-urlencoded")
    @GET("contest/exchange_list")
    fun getExchangelist(): Call<ExchangeList>

    @Headers("content-type: application/x-www-form-urlencoded")
    @GET("contest/GetTrainingContestsList")
    fun getTrainingContest(): Call<TrainingPojo>

    @Headers("content-type: application/x-www-form-urlencoded")
    @GET("users/getsociallinks")
    fun getSocialLink(): Call<SocialLinkPojo>


    @Headers("content-type: application/x-www-form-urlencoded")
    @GET("offers/index")
    fun getOfferList(): Call<OffersPojo>




    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("contest/get_filter")
    fun getFilterList(@Field("user_id") user_id: String): Call<FilterPojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("contest/filter_contest")
    fun setContestFilter(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String,
        @Field("category_id") categoryId: String,
        @Field("exchange_id") exchange_id: String,
        @Field("country_id") country_id: String,
        @Field("min_value") min_value: String,
        @Field("max_value") max_value: String
    ): Call<LobbyContestPojo>

    @Headers("content-type: application/x-www-form-urlencoded")
    @GET("contest/news_detail/{id}")
    fun getNewsDetail(@Path("id") newsId: String): Call<NewsDetailPojo>

    /* @Headers("Content-Type: application/json")
     @POST(ApiConstant.signup)
     fun signup(@Body signupRequest: SignUpRequest): Deferred<SignUpResponse>

     @Headers("Content-Type: application/json")
     @POST(ApiConstant.verify_otp)
     fun verify_otp(@Body signupRequest: VerifyOtpRequest): Deferred<OtpVerifyResponse>

     @Headers("Content-Type: application/json")
     @POST(ApiConstant.resend_otp)
     fun resend_otp(@Body request: Map<String, String>): Deferred<OtpVerifyResponse>

     @Headers("Content-Type: application/json")
     @POST(ApiConstant.login)
     fun login(@Body request: Map<String, String>): Deferred<OtpVerifyResponse>
 */ @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("users/social_login")
    fun socialLogin(
        @Field("type") type: String,
        @Field("social_id") social_id: String,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("device_type") device_type: String,
        @Field("device_token") device_token: String
    ): Call<SignupPojo>


    @FormUrlEncoded
    // @Header("Content-Type: application/x-www-form-urlencoded")
    @POST("stock/feature_stock")
    fun getWatchList(@Header("x-access-token") token: String, @Field("user_id") userid: String): Call<StockPojo>

    @FormUrlEncoded
    @POST("stock/remove_watch")
    fun removeWatch(@Header("x-access-token") token: String, @Field("user_id") user_id: String, @Field("id") id: String): Call<BasePojo>;

    @FormUrlEncoded
    @POST("stock/get_filter")
    fun getWatchListFilter(@Header("x-access-token") token: String, @Field("user_id") user_id: String): Call<WatchListFilterPojo>;


    @FormUrlEncoded
    @POST("users/logout")
    fun logOut(@Header("x-access-token") token: String, @Field("user_id") user_id: String): Call<BasePojo>


    @FormUrlEncoded
    @POST("users/get_profile")
    fun getProfile(@Header("x-access-token") token: String, @Field("user_id") user_id: String): Call<UserPojo>


    @FormUrlEncoded
    @POST("users/update_profile_details")
    fun updateProfileDetails(
        @Header("x-access-token") token: String, @Field("user_id") user_id: String, @Field("address") address: String, @Field(
            "zipcode"
        ) zipcode: String, @Field("phone_number") phone_number: String, @Field("country_id") country_id: String?
    ): Call<BasePojo>


    @Headers("content-type: application/x-www-form-urlencoded")
    @GET("cms/pages/faq")
    fun faq(): Call<WebViewPojo>

    @Headers("content-type: application/x-www-form-urlencoded")
    @GET("cms/pages/rules-and-winnings")
    fun rulesAndWinning(): Call<WebViewPojo>

    @Headers("content-type: application/x-www-form-urlencoded")
    @GET("cms/pages/how-to-play")
    fun howToPlay(): Call<WebViewPojo>

    @Headers("content-type: application/x-www-form-urlencoded")
    @GET("contest/country_list")
    fun getCountryList(): Call<Country>

    @Multipart
    @POST("users/update_profile")
    fun updateProfile(@Header("x-access-token") token: String, @Part("user_id") user_id: RequestBody, @Part("biography") biography: RequestBody, @Part file: MultipartBody.Part?): Call<BasePojo>

    @FormUrlEncoded
    @POST("stock/stock_list")
    fun getStockList(@Header("x-access-token") token: String, @Field("exchange_id") exchange_id: String,
                     @Field("user_id") user_id: String): Call<StockTeamPojo>

    @FormUrlEncoded
    @POST("stock/wizard_stock_list")
    fun getWizardStockList(@Header("x-access-token") token: String, @Field("exchange_id") exchange_id: String,
                           @Field("user_id") user_id: String): Call<StockTeamPojo>


    /* @Headers("Content-Type: application/json")

     @POST(ApiConstant.social_signup)
     fun social_signup(@Body signupRequest: SignUpRequest): Deferred<SignUpResponse>*/

}