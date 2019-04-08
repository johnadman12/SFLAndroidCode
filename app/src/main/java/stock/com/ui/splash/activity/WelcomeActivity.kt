package stock.com.ui.splash.activity

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
import kotlinx.android.synthetic.main.activity_login_welcome.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.constant.IntentConstant
import stock.com.constant.Tags
import stock.com.data.Prefs
import stock.com.model.FbDetails
import stock.com.model.SocialModel
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.login.activity.LoginActivity
import stock.com.ui.pojo.SignupPojo
import stock.com.ui.signup.activity.SignUpActivity
import stock.com.utils.AppDelegate
import stock.com.utils.SessionManager
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.util.*


class WelcomeActivity : BaseActivity(), View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.txt_EnterCode -> {
                startActivity(Intent(this, SignUpActivity::class.java))
            }
            R.id.txt_Login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.btn_LetsPlay -> {
                var intent = (Intent(this, DashBoardActivity::class.java))
//                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, img_AppIcon , "img_logo")
                startActivity(intent/*, options.toBundle()*/)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_welcome)
        StockConstant.ACTIVITIES.add(this)
        initViews()

    }

    private fun initViews() {
        AppDelegate.printHashKey(this@WelcomeActivity)
        txt_EnterCode.setOnClickListener(this)
        txt_Login.setOnClickListener(this)
        btn_LetsPlay.setOnClickListener(this)
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
            .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    AppDelegate.LogDB("login success" + loginResult.accessToken + "")
                    AppDelegate.LogT("onSuccess = " + loginResult.accessToken + "")
                    AppDelegate.showProgressDialog(this@WelcomeActivity)
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
                                    AppDelegate.hideProgressDialog(this@WelcomeActivity)
                                    Prefs(this@WelcomeActivity).putStringValueinTemp(Tags.social_id, socialModel.fb_id)
                                    checkUserVerify(socialModel, "1")
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
                        AppDelegate.hideProgressDialog(this@WelcomeActivity)
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
                    socialModel.name = user.displayName!!
                    socialModel.image = user.photoUrl.toString()
                    checkUserVerify(socialModel, "2")
                    signOut()
                } else {
                    AppDelegate.LogT("signInWithCredential:failure" + task.exception)
                    Toast.makeText(
                        this@WelcomeActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    private fun checkUserVerify(socialModel: SocialModel, type: String) {
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
            SessionManager.getInstance(this@WelcomeActivity).token
        )
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (response.body()!!.firstTimeRegister.equals("0")) {
                            saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
                            saveIntoPrefsString(StockConstant.ACCESSTOKEN, response.body()!!.token!!)
                            saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
                            startActivity(
                                Intent(this@WelcomeActivity, DashBoardActivity::class.java)
                                    .putExtra(IntentConstant.DATA, socialModel)
                            )
                        } else {
                            startActivity(
                                Intent(this@WelcomeActivity, SignUpActivity::class.java)
                                    .putExtra(IntentConstant.DATA, socialModel)
                            )
                        }


                    }
                    displayToast(response.body()!!.message)
                } else {
                    startActivity(
                        Intent(this@WelcomeActivity, SignUpActivity::class.java)
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
                    .enableAutoManage(
                        this /* FragmentActivity */,
                        this@WelcomeActivity /* OnConnectionFailedListener */
                    )
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
