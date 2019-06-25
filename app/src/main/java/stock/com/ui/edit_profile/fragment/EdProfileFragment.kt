package stock.com.ui.edit_profile.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment

import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.UserPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import android.graphics.Bitmap
import java.io.File

import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.provider.MediaStore
import android.content.Intent
import stock.com.utils.networkUtils.UtilityMethods

import androidx.core.content.FileProvider.getUriForFile

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import android.util.Base64OutputStream
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_country_list.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import stock.com.R
import stock.com.interfaces.SelectDialogInterface
import stock.com.ui.pojo.BasePojo
import stock.com.utils.Utility
import stock.com.utils.custom.AllImagePathUtil
import stock.com.utils.networkUtils.NetworkUtils
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.lang.Exception
import java.net.URI
import java.net.URISyntaxException


class EdProfileFragment : BaseFragment(), View.OnClickListener, SelectDialogInterface {


    //image
    private var fileUpload: File? = null
    private var thumbnail_camera: Bitmap? = null
    private var choice_list: ArrayList<String>? = null
    private var mCurrentPhotoPath: String? = null
    private var mHighQualityImageUri: Uri? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        choice_list = ArrayList();
        choice_list!!.add(resources.getString(R.string.take_photo));
        choice_list!!.add(resources.getString(R.string.choose_existing_photo));

        //initViews()
        getProfile();
        profile_image.setOnClickListener {

            if (UtilityMethods.hasStoragePermission(activity!!)) {
                UtilityMethods.showSelectDialog(
                    activity!!,
                    choice_list!!,
                    this,
                    getResources().getString(R.string.select_option),
                    0
                );
            }
        }

        tv_apply.setOnClickListener {
            if (et_biography.equals("")) {
                displayToast(resources.getString(R.string.please_enter_bio),"warning")
            } else {
                if (NetworkUtils.isConnected()) {
                    updateDetails()
                } else {
                    displayToast(resources.getString(R.string.error_network_connection),"error")
                }

            }
        }

        tv_cancel.setOnClickListener {
            activity!!.finish();
        }

    }


    private fun updateDetails() {
        var requestFile: RequestBody;
        var body: MultipartBody.Part? = null;
        try {
            requestFile = RequestBody.create(MediaType.parse("image/jpeg"), fileUpload);
            body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        } catch (ee: Exception) {

        }

        var token = RequestBody.create(MediaType.parse("text/plain"), getFromPrefsString(StockConstant.ACCESSTOKEN));
        var userId =
            RequestBody.create(MediaType.parse("text/plain"), getFromPrefsString(StockConstant.USERID).toString());
        var biography = RequestBody.create(MediaType.parse("text/plain"), et_biography.getText().toString());
        RequestBody.create(MediaType.parse("multipart/form- data"), token.toString());


        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.updateProfile(getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), userId, biography, body)
        // val call: Call<BasePojo> = apiService.updateProfile(getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), getFromPrefsString(StockConstant.USERID).toString(), et_biography.text.toString(),file)
        call.enqueue(object : Callback<BasePojo> {
            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                Log.d("Update profile", "---" + response.body().toString())
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status.equals("1")) {
                        //setData(response.body()!!.user!!)
                        displayToast(response.body()!!.message,"warning")
                    } else if (response.body()!!.status.equals("2")) {
                        appLogout()
                    } else {
                        displayToast(response.body()!!.message,"warning")
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error),"error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                //  Log.d("profileupdate","---"+t.localizedMessage)
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong),"error")
                d.dismiss()
            }
        })
    }

    override fun onClick(v: View?) {
    }

    fun getProfile() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<UserPojo> = apiService.getProfile(
            getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
            getFromPrefsString(StockConstant.USERID).toString()
        )
        call.enqueue(object : Callback<UserPojo> {
            override fun onResponse(call: Call<UserPojo>, response: Response<UserPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status.equals("1")) {
                        setData(response.body()!!.user!!)
                        ll_main.visibility = View.VISIBLE;
                    } else if (response.body()!!.status.equals("2")) {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error),"error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<UserPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong),"error")
                d.dismiss()
            }
        })
    }

    private fun setData(user: UserPojo.User) {
        et_user_name.setText(user.username);
        et_user_name.isEnabled = false;
        et_biography.setText(user.biography);
        Glide.with(context!!).load(user.profile_image).into(profile_image)

    }

    private fun captureImageCamera() {
        if (UtilityMethods.hasStoragePermission(activity!!)) {
            mHighQualityImageUri = generateTimeStampPhotoFileUri()
            Log.d("imageUri", "==$mHighQualityImageUri")
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri)
            startActivityForResult(intent, StockConstant.REQUEST_CAMERA)
        }
    }

    private fun generateTimeStampPhotoFileUri(): Uri? {
        var photoFileUri: Uri? = null
        val outputDir = getPhotoDirectory()
        if (outputDir != null) {
            val photoFile = File(outputDir, System.currentTimeMillis().toString() + ".jpg")
            photoFile.delete()
            mCurrentPhotoPath = "file:" + photoFile.getAbsolutePath()
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                photoFileUri = FileProvider.getUriForFile(
                    activity!!,
                    context!!.getPackageName() + ".provider",
                    photoFile
                )
            } else
                photoFileUri = Uri.fromFile(photoFile)
        }
        return photoFileUri
    }

    private fun getPhotoDirectory(): File? {
        var outputDir: File? = null;
        var externalStorageStagte: String = Environment.getExternalStorageState();
        if (externalStorageStagte.equals(Environment.MEDIA_MOUNTED)) {
            var photoDir: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            outputDir = File(photoDir, getString(stock.com.R.string.app_name));
            if (!outputDir.exists())
                if (!outputDir.mkdirs()) {
                    outputDir = null;
                }
        }
        return outputDir;
    }

    private fun choosePhotoFromGallary() {
        var intent = Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), StockConstant.REQUEST_GALLARY);
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.REQUEST_GALLARY && resultCode == Activity.RESULT_OK) {
            onSelectFromGalleryResult(data!!);
        } else if (requestCode == StockConstant.REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            onCaptureImageResult();
        }
    }

    private fun onCaptureImageResult() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            var imageUri = Uri.parse(mCurrentPhotoPath);
            fileUpload = File(imageUri.getPath());
            Log.d("file_path", "==" + imageUri.getPath());
        } else {
            var file_path = mHighQualityImageUri!!.getPath();
            fileUpload = File(file_path);
            Log.d("file_path", "==" + file_path);
        }

        var bitmap: Bitmap? = null;
        try {
            bitmap = decodeFile(fileUpload!!);
            thumbnail_camera = bitmap;
            profile_image.setImageBitmap(bitmap);
        } catch (e: Exception) {
            displayToast("Failed to load","error")
        }
    }

    private fun decodeFile(f: File): Bitmap? {
        try {
            // Decode image size
            var o = BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(FileInputStream(f), null, o);

            // The new size we want to scale to
            var REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            var scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2;
            }

            // Decode with inSampleSize
            var o2 = BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(FileInputStream(f), null, o2);
        } catch (e: FileNotFoundException) {
        }
        return null;
    }


    private fun onSelectFromGalleryResult(data: Intent) {
        if (data != null) {
            var allImagePathUtil = AllImagePathUtil();
            //Start new work
            try {
                var uri = URI(data.getData().toString());
                if ("content".equals(uri.getScheme())) {
                    if ("com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority())) {
                        allImagePathUtil.getImagePath(activity, data.getData());
                        var bitmap = allImagePathUtil.getImageBitmap();
                        if (bitmap != null) {
                            fileUpload =
                                allImagePathUtil.persistImage(bitmap, System.currentTimeMillis().toString(), activity);
                            thumbnail_camera = allImagePathUtil.getImageBitmap();
                            profile_image.setImageBitmap(thumbnail_camera);
                        }
                    } else {
                        var imagePath = allImagePathUtil!!.getImagePath(activity, data.getData());
                        if (imagePath != null) {
                            fileUpload = File(imagePath);
                            thumbnail_camera = allImagePathUtil.getImageBitmap();
                            profile_image.setImageBitmap(thumbnail_camera);
                        }
                    }
                } else {
                    var imagePath = allImagePathUtil.getImagePath(activity, data.getData());
                    if (imagePath != null) {
                        fileUpload = File(imagePath);
                        thumbnail_camera = allImagePathUtil.getImageBitmap();
                        profile_image.setImageBitmap(thumbnail_camera);
                    }
                }
            } catch (e: URISyntaxException) {
                e.printStackTrace();
            }
        }
    }

    override fun selectedPosition(view_clicked: Int, position: Int) {
        var result = Utility.checkPermission(activity);
        if (position == 0) {
            if (result) {
                captureImageCamera();
            }
        } else {
            if (result)
                choosePhotoFromGallary();
        }
    }


}