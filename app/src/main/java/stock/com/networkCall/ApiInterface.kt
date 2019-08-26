package stock.com.networkCall

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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
        @Field("country_id") country_id: String,
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
        @Field("country_id") country_id: String,
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
    fun resendOtp(@Field("phone_number") phone_number: String, @Field("country_id") country_id: String):
            Call<SignupPojo>

    @FormUrlEncoded
    @POST("search/market")
    fun searchCrypto(
        @Field("market_type") market_type: String, @Field("search") search: String, @Field("user_id") user_id: String
        ,
        @Field("page") page: String, @Field("limit") limit: String
    ): Call<MarketList>

    @FormUrlEncoded
    @POST("search/market")
    fun searchCurrency(
        @Field("market_type") market_type: String, @Field("search") search: String, @Field("user_id") user_id: String
        ,
        @Field("page") page: String, @Field("limit") limit: String
    ): Call<CurrencyPojo>

    @FormUrlEncoded
    @POST("search/stock")
    fun searchExchange(
        @Field("exchange_id") exchange_id: String, @Field("search") search: String, @Field("user_id") user_id: String,
        @Field("market_type") market_type: String
        ,
        @Field("page") page: String, @Field("limit") limit: String
    ): Call<StockTeamPojo>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/resend_request_otp")
    fun resendRequestOtp(
        @Field("phone_number") phone_number: String,
        @Field("country_id") country_id: String,
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
        @Field("country_id") country_id: String,
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
    fun verify_otp_new(
        @Field("user_id") userid: String, @Field("otp") otp: String,
        @Field("country_id") country_id: String,
        @Field("phone_number") phone_number: String
    ): Call<SignupPojo>


    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("users/request_otp")
    fun requestOtp(
        @Field("user_id") userid: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("country_id") country_id: String,
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
    fun changePassword(
        @Header("x-access-token") token: String, @Field("user_id") userid: String, @Field("old_password") old_password: String, @Field(
            "new_password"
        ) new_password: String
    ): Call<BasePojo>


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
        @Field("country_id") country_id: String,
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
    fun getContestList(
        @Header("x-access-token") token: String,
        @Query("type") type: String
    ): Call<LobbyContestPojo>


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
    fun getFilterList(
        @Field("user_id") user_id: String,
        @Field("market_type") marketType: String
    ): Call<FilterPojo>

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

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("contest/GetContestsDetail")
    fun getContestDetail(
        @Field("contest_id") contest_id: String, @Field("user_id") user_id: String,
        @Field("status") status: String,
        @Field("market_id") market_id: String
    ): Call<ContestDetail>

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("contest/GetContestsStatus")
    fun getContestScore(
        @Header("x-access-token") token: String,
        @Field("contest_id") contest_id: String,
        @Field("user_id") user_id: String,
        @Field("market_id") market_id: String,
        @Field("page") page: String,
        @Field("limit") limit: String
    ): Call<Scores>

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
    fun getWatchList(
        @Header("x-access-token") token: String,
        @Field("user_id") userid: String,
        @Field("asset_type") asset_type: String,
        @Field("sector") sector: String,
        @Field("market") market: String,
        @Field("country_id") country_id: String,
        @Field("page") page: String, @Field("limit") limit: String
    ): Call<WatchlistPojo>

    @FormUrlEncoded
    // @Header("Content-Type: application/x-www-form-urlencoded")
    @POST("search/watchlist")
    fun getSearchWatchList(
        @Header("x-access-token") token: String,
        @Field("user_id") userid: String,
        @Field("search") search: String,
        @Field("page") page: String,
        @Field("limit") limit: String
    ): Call<WatchlistPojo>

    @FormUrlEncoded
    @POST("stock/remove_watch")
    fun removeWatch(@Header("x-access-token") token: String, @Field("user_id") user_id: String, @Field("id") id: String): Call<BasePojo>;

    @FormUrlEncoded
    @POST("stock/get_filter")
    fun getWatchListFilter(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String
    ): Call<WatchListFilterPojo>;

    @FormUrlEncoded
    @POST("stock/get_market_filter")
    fun getMarketTypeFilter(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String
    ): Call<MarketTypeFilters>;


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
        ) zipcode: String,
        @Field("phone_number") phone_number: String, @Field("country_id") country_id: String
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
    fun updateProfile(
        @Header("x-access-token") token: String, @Part("user_id")
        user_id: RequestBody, @Part("biography") biography: RequestBody, @Part file: MultipartBody.Part?
    ): Call<BasePojo>

    @FormUrlEncoded
    @POST("stock/stock_list")
    fun getStockList(
        @Header("x-access-token") token: String, @Field("exchange_id") exchange_id: Int,
        @Field("user_id") user_id: Int, @Field("sector") Sector: String,
        @Field("page") page: String, @Field("limit") limit: String
    ): Call<StockTeamPojo>

    @FormUrlEncoded
    @POST("stock/wizard_stock_list")
    fun getWizardStockList(
        @Header("x-access-token") token: String, @Field("exchange_id") exchange_id: String,
        @Field("user_id") user_id: String
    ): Call<StockTeamPojo>


    @FormUrlEncoded
    @POST("marketdata/marketdata_list")
    fun getMarketList(
        @Header("x-access-token") token: String, @Field("market_id") exchange_id: String,
        @Field("user_id") user_id: String,
        @Field("page") page: String, @Field("limit") limit: String
    ): Call<MarketList>

    @FormUrlEncoded
    @POST("marketdata/marketdata_list")
    fun getCurrencyList(
        @Header("x-access-token") token: String, @Field("market_id") exchange_id: String,
        @Field("user_id") user_id: String,
        @Field("page") page: String, @Field("limit") limit: String
    ): Call<CurrencyPojo>

    @FormUrlEncoded
    @POST("marketdata/wizard_market_list")
    fun getMarketWizardList(
        @Header("x-access-token") token: String, @Field("market_id") exchange_id: String,
        @Field("user_id") user_id: String
    ): Call<MarketList>


    @FormUrlEncoded
    @POST("stock/add_watch")
    fun addStockWatch(
        @Header("x-access-token") token: String, @Field("stock_id") stock_id: Int,
        @Field("user_id") user_id: String
    ): Call<BasePojo>


    @FormUrlEncoded
    @POST("marketdata/add_watch")
    fun addCurrencyToWatch(
        @Header("x-access-token") token: String,
        @Field("md_id") mdId: Int,
        @Field("user_id") user_id: String,
        @Field("status") status: String
    ): Call<BasePojo>

    @GET("contest/GetContestsList")
    fun getContest(
        @Header("x-access-token") token: String, @Query("status") status: String,
        @Query("user_id") user_id: String
    ): Call<LobbyContestPojo>

    @FormUrlEncoded
    @POST("team/my_team")
    fun getMyTeams(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String,
        @Field("exchange_id") exchange_id: String,
        @Field("page") page: String, @Field("limit") limit: String
    ): Call<MyTeamsPojo>

    @FormUrlEncoded
    @POST("usercontest/GetContestsList")
    fun getCreatedContest(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String
    ): Call<CreateContest>

    @FormUrlEncoded
    @POST("usercontest/GetMyInvitedList")
    fun getInvitedContest(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String
    ): Call<InvitedList>

    @FormUrlEncoded
    @POST("usercontest/invitation_accept")
    fun acceptInvitation(
        @Header("x-access-token") token: String,
        @Field("manage_invitation_Id") manage_invitation_Id: String,
        @Field("invitation_status") invitation_status: String
    ): Call<BasePojo>


    @FormUrlEncoded
    @POST("marketteam/my_team")
    fun getMyMarketTeams(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String,
        @Field("market_id") marketId: String,
        @Field("page") page: String, @Field("limit") limit: String
    ): Call<MyTeamsPojo>

    @FormUrlEncoded
    @POST("marketdata/data_list")
    fun getMarketData(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String,
        @Field("market_status") marketStatus: String,
        @Field("sector") sector: String,
        @Field("exchanges") exchanges: String,
        @Field("country_id") country_id: String,
        @Field("name") name: String,
        @Field("page") page: String,
        @Field("limit") limit: String
    ): Call<MarketData>

    @FormUrlEncoded
    @POST("marketdata/data_list")
    fun getCurrencyData(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String,
        @Field("market_status") marketStatus: String,
        @Field("page") page: String,
        @Field("limit") limit: String
    ): Call<CurrencyPojo>


    @FormUrlEncoded
    @POST("usercontest/user_friend_list")
    fun getFriendsList(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String,
        @Field("page") page: String, @Field("limit") limit: String
    ): Call<FriendsList>

    @FormUrlEncoded
    @POST("usercontest/add_friend")
    fun addToFriends(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String,
        @Field("friend_id") friend_id: String,
        @Field("friend_status") friend_status: String
    ): Call<BasePojo>

    @FormUrlEncoded
    @POST("usercontest/friend_accept")
    fun acceptRequest(
        @Header("x-access-token") token: String,
        @Field("manage_friend_Id") user_id: String,
        @Field("friend_status") friend_status: String
    ): Call<BasePojo>


    @FormUrlEncoded
    @POST("usercontest/user_pending_list")
    fun getPendingFriends(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String
    ): Call<PendingList>

    @FormUrlEncoded
    @POST("search/user_search")
    fun getHomeSearch(
        @Header("x-access-token") token: String,
        @Field("search") search: String
    ): Call<ResponseBody>


    @FormUrlEncoded
    @POST("usercontest/user_invited_list")
    fun getAllUserToInvite(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String,
        @Field("contest_id") contest_id: String
        , @Field("page") page: String, @Field("limit") limit: String
    ): Call<InviteData>


    @FormUrlEncoded
    @POST("usercontest/user")
    fun getAllUserSearch(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String,
        @Field("contest_id") contest_id: String
        , @Field("page") page: String,
        @Field("limit") limit: String,
        @Field("search") search: String
    ): Call<InviteData>


    @FormUrlEncoded
    @POST("usercontest/send_invitation")
    fun sendInvitation(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String,
        @Field("friend_id") friend_id: String,
        @Field("contest_id") contest_id: String,
        @Field("contest_by") contest_by: String
    ): Call<BasePojo>


    @FormUrlEncoded
    @POST("users/get_otheruser_profile")
    fun getOthersProfile(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String,
        @Field("friend_id") friend_id: String
    ): Call<OtherProfile>

    @FormUrlEncoded
    @POST("/api/marketteam/edit_team_name")
    fun saveTeamName(
        @Header("x-access-token") token: String,
        @Field("team_id") teamId: Int,
        @Field("user_team_name") teamname: String
    ): Call<BasePojo>


    /*  @FormUrlEncoded
      @POST("team/create_team")
      fun saveTeam(
          @Header("x-access-token") token: String, @Field("contest_id") contest_id: String,
          @Field("team_id") team_id: String, @Field("stocks") stock: String,
          @Field("user_id") user_id: String
      ): Call<BasePojo>
  */

    @POST("team/create_team")
    fun saveTeam(
        @Header("x-access-token") token: String, @Body stocks: JsonObject
    ): Call<BasePojo>

    @POST("marketteam/create_team")
    fun saveMarketTeam(
        @Header("x-access-token") token: String, @Body stocks: JsonObject
    ): Call<BasePojo>

    @POST("team/join_team")
    fun joinWithTeamId(
        @Header("x-access-token") token: String, @Body stocks: JsonObject
    ): Call<BasePojo>

    @POST("team/edit_team")
    fun editTeam(
        @Header("x-access-token") token: String, @Body stocks: JsonObject
    ): Call<BasePojo>

    @POST("marketteam/edit_team")
    fun editMarketTeam(
        @Header("x-access-token") token: String, @Body stocks: JsonObject
    ): Call<BasePojo>

    @POST("usercontest/user_contestlist")
    fun getExchangeForContest(
        @Header("x-access-token") token: String
    ): Call<ContestList>

    @POST("stock/get_sector")
    fun getSectorList(
        @Header("x-access-token") token: String
    ): Call<SectorListPojo>


    @GET("comments/commentList")
    fun getComments(
        @Header("x-access-token") token: String,
        @Query("stockid") stock_id: Int,
        @Query("user_id") user_id: String
    ): Call<Comments>

    @GET("comments/market_comments_list")
    fun getCommentsMArket(
        @Header("x-access-token") token: String,
        @Query("marketId") marketid: Int,
        @Query("marketType") marketType: String,
        @Query("user_id") user_id: String
    ): Call<Comments>

    @FormUrlEncoded
    @POST("comments/commentPost")
    fun postComments(
        @Header("x-access-token") token: String,
        @Field("stockid") stock_id: Int,
//        @Field("comment_id") comments_id: Int,
        @Field("user_id") user_id: String,
        @Field("comments") comments: String
    ): Call<Comments>

    @FormUrlEncoded
    @POST("comments/market_comments_post")
    fun postCommentsMarket(
        @Header("x-access-token") token: String,
        @Field("marketId") marketid: Int,
        @Field("marketType") marketType: String,
        @Field("user_id") user_id: String,
        @Field("comments") comments: String
    ): Call<Comments>

    @FormUrlEncoded
    @POST("users/statistics_profile")
    fun getStatistics(
        @Header("x-access-token") token: String,
        @Field("user_id") user_id: String
    ): Call<StatisticsPojo>


    @FormUrlEncoded
    @POST("usercontest/contest_winningBreakup")
    fun getWinningPercentage(
        @Header("x-access-token") token: String,
        @Field("prize_pool") prize_pool: String,
        @Field("user_id") user_id: String,
        @Field("contest_size") comments: String,
        @Field("entry_fee") entry_fee: String
    ): Call<WinningList>

    @FormUrlEncoded
    @POST("usercontest/user_code_join")
    fun codeJoinContest(
        @Header("x-access-token") token: String,
//        @Field("user_id") user_id: String,
        @Field("invitecode") comments: String
    ): Call<BasePojo>

    @FormUrlEncoded
    @POST("marketdata/asset_detail")
    fun getAssestData(
        @Header("x-access-token") token: String,
        @Field("asset_id") comments: String,
        @Field("market_type") market_type: String
    ): Call<AssestData>

    @FormUrlEncoded
    @POST("marketdata/currency_detail")
    fun getCurrencyDetail(
        @Header("x-access-token") token: String,
        @Field("asset_id") comments: String,
        @Field("market_type") market_type: String
    ): Call<CurrencyDetail>


    @FormUrlEncoded
    @POST("usercontest/user_contestcreate")
    fun createUserContest(
        @Header("x-access-token") token: String,
        @Field("total_winning") total_winning: String,
        @Field("user_id") user_id: String,
        @Field("exchange_id") exchange_id: String,
        @Field("market_id") market_id: String,
        @Field("contest_size_id") contest_size_id: String,
        @Field("contest_size") contest_size: String,
        @Field("contests_winner") contests_winner: String,
        @Field("join_multiple") join_multiple: String,
        @Field("ucontest_name") ucontest_name: String,
        @Field("contest_type") contest_type: String,
        @Field("schedule_start") schedule_start: String,
        @Field("schedule_end") schedule_end: String,
        @Field("entry_fees") entry_fee: String,
        @Field("admin_commission") admin_commission: String
    ): Call<BasePojo>


    @GET("comments/commentLikes")
    fun likeComment(
        @Query("comment_id") comments_id: Int,
        @Query("user_id") user_id: String
    ): Call<BasePojo>


    @GET("stories")
    fun getNews(
        @Query("identifier_type") identifier_type: String, @Query("identifiers") identifiers: String,
        @Query("categories") categories: String,
        @Query("min_cityfalcon_score") min_cityfalcon_score: String, @Query("order_by") order_by: String,
        @Query("time_filter") time_filter: String,
        @Query("all_languages") all_languages: Boolean,
        @Query("access_token") access_token: String
    ): Call<CityfalconNewsPojo>


    @GET("stories")
    fun getHomeNews(
        @Query("topic_classes") topic_classes: String,
        @Query("categories") categories: String,
        @Query("min_cityfalcon_score") min_cityfalcon_score: String, @Query("order_by") order_by: String,
        @Query("time_filter") time_filter: String,
        @Query("all_languages") all_languages: Boolean,
        @Query("access_token") access_token: String
    ): Call<CityfalconNewsPojo>


    @GET("v1/cryptocurrency/ohlcv/historical")
    fun getChartApi(
        @Header("Accepts") Accepts: String,
        @Header("X-CMC_PRO_API_KEY") api_key: String,
        @Query("symbol") symbol: String,
        @Query("time_start") time_start: String,
        @Query("time_end") min_cityfalcon_score: String

    ): Call<CandlesticChartmarket>

    /* @Headers("Content-Type: application/json")

     @POST(ApiConstant.social_signup)
     fun social_signup(@Body signupRequest: SignUpRequest): Deferred<SignUpResponse>*/

}