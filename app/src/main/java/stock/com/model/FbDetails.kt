package stock.com.model


import org.json.JSONException
import org.json.JSONObject
import stock.com.constant.Tags
import stock.com.utils.AppDelegate

class FbDetails {
    private var profilePicUrl: String? = null

    fun getFacebookDetail(string: String): SocialModel {
        val fb_detail_getSet = SocialModel()
        try {
            AppDelegate.LogT("message+" + string + "")
            val `object` = JSONObject(string)
            if (`object`.has("picture")) {
                profilePicUrl = `object`.getJSONObject("picture").getJSONObject("data").getString("url")
                //                if (profilePicUrl.contains("&")) {
                //                    profilePicUrl = profilePicUrl.replace("&", "~");
                //                }
            }
            AppDelegate.LogT("profilePicUrl===" + profilePicUrl!!)
            fb_detail_getSet.social_id = (`object`.getString(Tags.id))
            fb_detail_getSet.first_name = (`object`.getString(Tags.first_name))
            fb_detail_getSet.last_name = (`object`.getString(Tags.last_name))
            if (`object`.has(Tags.birthday)) {
                fb_detail_getSet.birthday = (`object`.getString(Tags.birthday))
            }
            if (`object`.has(Tags.email))
                fb_detail_getSet.email = (`object`.getString(Tags.email))
//            fb_detail_getSet.setGender=(`object`.getString(Tags.gender))
            fb_detail_getSet.name = (`object`.getString(Tags.name))
//            fb_detail_getSet.socialType = "facebook"
            fb_detail_getSet.image = (profilePicUrl!! + "")
            fb_detail_getSet.type = "1"
        } catch (e: JSONException) {
            AppDelegate.LogE(e)
        }

        return fb_detail_getSet
    }
}
