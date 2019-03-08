package stock.com.networkCall

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.SignupPojo
import stock.com.ui.signup.apiRequest.SignUpRequest
import stock.com.ui.signup.apiRequest.VerifyOtpRequest
import stock.com.ui.signup.apiResponse.otpVerify.OtpVerifyResponse
import stock.com.ui.signup.apiResponse.signup.SignUpResponse


interface ApiInterface {

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/register")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("invite_code") invite_code: String,
        @Field("phone_number") phone_number: String,
        @Field("name") name: String,
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
    fun resendOtp(@Field("phone_number") phone_number: String): Call<BasePojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/forgot_password")
    fun forgot_pass(@Field("email") email: String): Call<BasePojo>

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
        @Field("device_type") device_type: String,
        @Field("device_token") device_token: String
    ): Call<SignupPojo>

    @GET("https://raw.githubusercontent.com/yasiriqbal776/AndroidHelper/master/countries.json")
    fun getCountry(): Call<BasePojo>

    @Headers(
        "content-type: application/x-www-form-urlencoded"
    )
    @GET("contest/GetFeaturedContestsList")
    fun getFeatureContentlist(@Header("x-access-token") token: String): Call<HomePojo>

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

    /* @Headers("Content-Type: application/json")

     @POST(ApiConstant.social_signup)
     fun social_signup(@Body signupRequest: SignUpRequest): Deferred<SignUpResponse>*/
}
