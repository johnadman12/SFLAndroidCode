package stock.com.networkCall

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    @JvmStatic
    private var BASE_URL = ApiConstant.BASE_URL
    @JvmStatic
    private var BASE_URL_NEWS = ApiConstant.NEWS_URL
    @JvmStatic
    private var BASE_URL_CHART = ApiConstant.CHART_URL
    @JvmStatic
    private var retrofit: Retrofit? = null
    @JvmStatic
    private var retrofit2: Retrofit? = null
  @JvmStatic
    private var retrofit3: Retrofit? = null

    @JvmStatic
    fun getClient(): Retrofit? {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.client(UnsafeOkHttpClient.unsafeOkHttpClient)
                .client(ApiClient.okClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    @JvmStatic
    fun getClientNews(): Retrofit? {
        if (retrofit2 == null) {
            retrofit2 = Retrofit.Builder()
                .baseUrl(BASE_URL_NEWS)
                //.client(UnsafeOkHttpClient.unsafeOkHttpClient)
                .client(ApiClient.okClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit2
    }

    @JvmStatic
    fun getClientCharts(): Retrofit? {
        if (retrofit3 == null) {
            retrofit3 = Retrofit.Builder()
                .baseUrl(BASE_URL_CHART)
                //.client(UnsafeOkHttpClient.unsafeOkHttpClient)
                .client(ApiClient.okClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit3
    }


    @JvmStatic
    private fun okClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }
}
/*class ApiClient {
    companion object {
        *//**
         * create singleton for accessing variables
         *//*
        var mApiClient: ApiClient? = null
        var retrofit: Retrofit? = null

        val client: ApiClient
            get() {

                if (mApiClient == null) {
                    mApiClient = ApiClient()
                }
                return mApiClient as ApiClient
            }

    }
    *//**
     * this method will return instance ApiInterface
     *//*
    fun getRetrofitService(): ApiInterface {
//        var httpClient = OkHttpClient.Builder()
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        httpClient.interceptors().add(interceptor)

        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)
        clientBuilder.connectTimeout(10, TimeUnit.MINUTES)
        clientBuilder.readTimeout(10, TimeUnit.MINUTES)

        val gson =
            GsonBuilder()
                .setLenient()
                .create()
        return Retrofit.Builder()
            .baseUrl(ApiConstant.getBaseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clientBuilder.build())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ApiInterface::class.java)
    }


}*/
