package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SocialLinkPojo :BasePojo() {

    @SerializedName("socialLinks")
    @Expose
    var socialLinksList:ArrayList<socialLinks>?=null

    inner class socialLinks{
        @SerializedName("name")
        @Expose
        var name:String="";
        @SerializedName("value")
        @Expose
        var value:String="";

    }
}