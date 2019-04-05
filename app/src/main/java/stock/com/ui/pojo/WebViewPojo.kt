package stock.com.ui.pojo

import com.google.gson.annotations.SerializedName

class WebViewPojo {

    var cms:CMS?=null;
    var status:String?=null;



    inner class CMS{
        var title:String?="";
        var description:String?=""
    }

}