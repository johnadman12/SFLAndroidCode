package stock.com.networkCall

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.common.api.Api
import okhttp3.Request
import retrofit2.Response
import java.io.IOException
import java.io.InputStream
import java.security.*
import java.security.AccessController.getContext
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*
import stock.com.utils.networkUtils.NetworkUtils.isConnected




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

  /*  @JvmStatic
    fun getClient(context: Context): Retrofit? {

       this.context=context;

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(httpsURLConnection!!.setSSLSocketFactory(trustCert().getSocketFactory()))
                .client(ApiClient.okClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }*/


    @JvmStatic
    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(httpsURLConnection!!.setSSLSocketFactory(trustCert().getSocketFactory()))
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
            //.sslSocketFactory(trustCert()!!.socketFactory)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }


    }

    /* @JvmStatic
     private fun urlConnection():HttpsURLConnection{
         val urlConnection = url.openConnection() as HttpsURLConnection
         urlConnection.sslSocketFactory = sslSF
         val `in` = urlConnection.inputStream
         copyInputStreamToOutputStream(`in`, System.out)
     }*/

   /* @Throws(
        CertificateException::class,
        IOException::class,
        KeyStoreException::class,
        NoSuchAlgorithmException::class,
        KeyManagementException::class
    )

    private fun trustCert(): SSLContext? {
        val assetManager = context!!.assets
        val cf = CertificateFactory.getInstance("X.509")
        val ca = cf.generateCertificate(assetManager.open("COMODORSADomainValidationSecureServerCA.crt"))

        // Create a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)

        // Create a TrustManager that trusts the CAs in our KeyStore
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)

        // Create an SSLContext that uses our TrustManager
        val context = SSLContext.getInstance("TLS")
        context.init(null, tmf.trustManagers, null)
        return context
    }*/





/*class ApiClient {
    companion object {
        */
/**
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
    */
/**
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
