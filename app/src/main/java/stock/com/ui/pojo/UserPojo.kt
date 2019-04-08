package stock.com.ui.pojo

class UserPojo :BasePojo() {

    var user:User?=null;

    inner class User{
        var id:String="";
        var username:String="";
        var biography:String="";
        var email:String="";
        var phone_number:String="";
        var profile_image:String="";
        var address:String="";
        var country_id:String="";
        var zipcode:String="";

    }
}