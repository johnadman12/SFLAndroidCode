package stock.com.ui.login.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.content_signup.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.application.FantasyApplication
import stock.com.constant.IntentConstant
import stock.com.constant.PrefConstant
import stock.com.constant.Tags
import stock.com.data.Prefs
import stock.com.model.FbDetails
import stock.com.model.SocialModel
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.pojo.SignupPojo
import stock.com.ui.signup.activity.OTPActivity
import stock.com.ui.signup.activity.PasswordActivity
import stock.com.ui.signup.activity.SignUpActivity
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.networkUtils.NetworkUtils
import java.util.*

class LoginActivity : BaseActivity(), View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_Next -> {

                if (et_email.text.toString().isEmpty())
                    AppDelegate.showToast(this, getString(R.string.enter_phone_number))
                else {
                    if (et_email.text.toString().contains("@")) {
                        startActivity(
                            Intent(this, PasswordActivity::class.java)
                                .putExtra("email", et_email.text.toString().trim())
                        )
                    } else {
                        if (NetworkUtils.isConnected()) {
                            phoneLoginApi()
                        } else {
                            Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
                        }

                    }

                }
                /*else if (et_email.text.toString().contains("@") && ValidationUtil.isEmailValid(et_email.text.toString())) {
                    AppDelegate.showToast(this, getString(R.string.valid_email))
                } else if (ValidationUtil.isPhoneValid(et_email.text.toString())) {
                    AppDelegate.showToast(this, getString(R.string.valid_phone_number))
                } else
                    if (NetworkUtils.isConnected()) {
                        callLoginApi()
                    } else
                        AppDelegate.showToast(this, getString(R.string.error_network_connection))*/
            }
            R.id.txt_Signup -> {
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
            R.id.facebookLoginButton -> {
                faceBookLogin()
            }
            R.id.googleLoginButton -> {
                googlePlusLogin()
            }
        }
    }

    fun phoneLoginApi() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.phoneLogin(
            et_email.text.toString().trim(), "1", getFromPrefsString(Tags.FCMtoken).toString()

        )
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
                        saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
                        saveIntoPrefsString(StockConstant.ACCESSTOKEN, response.body()!!.token!!)
                        startActivity(
                            Intent(this@LoginActivity, OTPActivity::class.java)
                                .putExtra("phoneNumber", et_email.text.toString().trim())
                        )
                    }
                    displayToast(response.body()!!.message)
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<SignupPojo>?, t: Throwable?) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        StockConstant.ACTIVITIES.add(this)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText(R.string.login)
        btn_Next.setOnClickListener(this)
        txt_Signup.setOnClickListener(this)
        facebookLoginButton.setOnClickListener(this)
        googleLoginButton.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (FacebookSdk.isInitialized()) {
            AppDelegate.LogT("INITIALIZED facebook sdk")
            disconnectFromFacebook()
        }
        if (mAuth != null)
            mAuth = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    lateinit var socialModel: SocialModel
    var callbackManager: CallbackManager? = null
    var isCalledOnce: Boolean? = false
    private val RC_SIGN_IN = 9001
    internal var request_code = 102

    fun faceBookLogin() {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "user_birthday", "email"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    AppDelegate.LogDB("login success" + loginResult.accessToken + "")
                    AppDelegate.LogT("onSuccess = " + loginResult.accessToken + "")
                    AppDelegate.showProgressDialog(this@LoginActivity)
                    var fb_LoginToken: String = loginResult.accessToken.toString()
                    val request = GraphRequest.newMeRequest(
                        loginResult.accessToken,
                        object : GraphRequest.GraphJSONObjectCallback {
                            override fun onCompleted(`object`: JSONObject, response: GraphResponse?) {
                                // Application code
                                if (response != null) {
                                    socialModel = SocialModel()
                                    socialModel.isSocial = "0"
                                    socialModel = FbDetails().getFacebookDetail(response.jsonObject.toString())
                                    AppDelegate.LogT("Facebook details==" + socialModel + "")
                                    AppDelegate.hideProgressDialog(this@LoginActivity)
                                    Prefs(this@LoginActivity).putStringValueinTemp(Tags.social_id, socialModel.fb_id)
                                    checkUserVerify(socialModel,"1")
                                }
                            }
                        })
                    val parameters = Bundle()
                    parameters.putString(
                        "fields",
                        "first_name,last_name,email,id,name,gender,birthday,picture.type(large)"
                    )
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    AppDelegate.LogDB("login cancel")

                    if (AccessToken.getCurrentAccessToken() != null)
                        LoginManager.getInstance().logOut()
                    if (!isCalledOnce!!) {
                        isCalledOnce = true
                        faceBookLogin()
                    }
                }

                override fun onError(exception: FacebookException) {
                    AppDelegate.LogDB("login error = " + exception.message)
                    if (exception.message!!.contains("CONNECTION_FAILURE")) {
                        AppDelegate.hideProgressDialog(this@LoginActivity)
                    } else if (exception is FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut()
                            if (!isCalledOnce!!) {
                                isCalledOnce = true
                                faceBookLogin()
                            }
                        }
                    }
                }
            })

    }

    private var mAuth: FirebaseAuth? = null
    private var mGoogleApiClient: GoogleApiClient? = null

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        AppDelegate.LogT("firebaseAuthWithGoogle:" + acct!!.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    AppDelegate.LogT("signInWithCredential:success")
                    val user = mAuth!!.currentUser
                    socialModel = SocialModel()
                    socialModel.email = user?.email!!
                    socialModel.isSocial = "0"
                    socialModel.social_id = user.uid
                    socialModel.first_name = user.displayName!!
                    socialModel.image = user.photoUrl.toString()
                    checkUserVerify(socialModel,"2")
                    signOut()
                } else {
                    AppDelegate.LogT("signInWithCredential:failure" + task.exception)
                    Toast.makeText(
                        this@LoginActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    private fun checkUserVerify(socialModel: SocialModel, type:String) {
//      {"fb_id":"124587","google_id":"","language":"en","mobile_number":"9955214155","name":"Nidhi","email":"nidhimittal@m_mailinator.com"}
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.socialLogin(
            type,
            socialModel.social_id,
            socialModel.email,
            socialModel.name,
            "1",
            getFromPrefsString(Tags.FCMtoken).toString()
        )
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
                        saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
                        saveIntoPrefsString(StockConstant.ACCESSTOKEN, response.body()!!.token!!)

                        if(response.body()!!.firstTimeRegister.equals("0")){
                            startActivity(
                                Intent(this@LoginActivity, DashBoardActivity::class.java)
                                    .putExtra(IntentConstant.DATA, socialModel)
                            )
                        }else{
                            startActivity(
                                Intent(this@LoginActivity, SignUpActivity::class.java)
                                    .putExtra(IntentConstant.DATA, socialModel)
                            )
                        }


                    }
                    displayToast(response.body()!!.message)
                } else {
                    startActivity(
                        Intent(this@LoginActivity, SignUpActivity::class.java)
                            .putExtra(IntentConstant.DATA, socialModel)
                    )
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<SignupPojo>?, t: Throwable?) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    private fun googlePlusLogin() {
//        AppDelegate.LogT("getString(R.string.default_web_client_id==?"+getString(R.string.default_web_client_id))

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        if (mGoogleApiClient == null || !mGoogleApiClient!!.isConnected) {
            try {
                mGoogleApiClient = GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this@LoginActivity /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build()
            } catch (e: Exception) {
                AppDelegate.LogE(e)
            }
        }
        mAuth = FirebaseAuth.getInstance()
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    fun disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return // already logged out
        }
        GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, GraphRequest
            .Callback {
                @Override
                fun onCompleted(graphResponse: GraphResponse) {
                    LoginManager.getInstance().logOut()
                    AppDelegate.LogT("Logout from facebook")
                }
            }).executeAsync()
    }

    private fun signOut() {
        if (mGoogleApiClient != null || !mGoogleApiClient!!.isConnected) {
            mAuth!!.signOut()
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            AppDelegate.LogT("resultCode==>" + resultCode)
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount
                firebaseAuthWithGoogle(account)
            }
        } else {
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
        }

    }

}

/*  private fun checkUserVerify(socialModel: SocialModel) {
//      {"fb_id":"124587","google_id":"","language":"en","mobile_number":"9955214155","name":"Nidhi","email":"nidhimittal@m_mailinator.com"}
        val loginRequest = HashMap<String, String>()
        loginRequest["fb_id"] = socialModel.fb_id
        loginRequest["google_id"] = socialModel.google_id
        loginRequest["name"] = socialModel.first_name
        loginRequest["email"] = socialModel.email
        loginRequest["mobile_number"] = ""
        loginRequest["language"] = FantasyApplication.getInstance().getLanguage()
        GlobalScope.launch(Dispatchers.Main) {
            AppDelegate.showProgressDialog(this@LoginActivity)
            try {
                val request = ApiClient.getClient()!!.(loginRequest)
                val response = request.await()
                AppDelegate.LogT("Response=>" + response);
                AppDelegate.hideProgressDialog(this@LoginActivity)
                if (response.response!!.status) {
                    AppDelegate.showToast(this@LoginActivity, response.response!!.message)
                    startActivity(
                        Intent(this@LoginActivity, DashBoardActivity::class.java)
                            .putExtra(IntentConstant.DATA, response.response!!.data!!)
//                            .putExtra(IntentConstant.MOBILE, response.response!!.data!!.phone)
//                            .putExtra(IntentConstant.USER_ID, response.response!!.data!!.user_id)
                    )

                } else {
                    startActivity(
                        Intent(this@LoginActivity, SignUpActivity::class.java)
                            .putExtra(IntentConstant.DATA, socialModel)GoogleSignInOptions
//                            .putExtra(IntentConstant.MOBILE, response.response!!.data!!.phone)
//                            .putExtra(IntentConstant.USER_ID, response.response!!.data!!.user_id)
                    )

                }
            } catch (exception: Exception) {
                AppDelegate.hideProgressDialog(this@LoginActivity)
            }
        }
    }  */