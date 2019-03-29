package stock.com.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    private static SharedPreferences file;
    private  static SharedPreferences.Editor editor;
    private static SessionManager sessionManager=null;

    private static String FLAG="flag";
    private static String TOKEN="token";

    private static String STORE_NAME="store_name";

    private static String STORE_ID="store_id";
    private static String DOMAIN_NAME="domain";
    private static String USER_NAME="username";
    private static String INTERVAL="api_interval";
    private static String PRINT_COPIES="print_copies";
    private static String PASSWORD="password";
    private static String STORE_ADDRESS="store_address";



    public static SessionManager getInstance(Context context){
        file= PreferenceManager.getDefaultSharedPreferences(context);
        editor=file.edit();
        if(sessionManager==null){
            sessionManager=new SessionManager();
        }
        return sessionManager;
    }


    public void setFlag(boolean b) {
        editor.putBoolean(FLAG,b);
        editor.commit();
    }
    public boolean getFlag(){
        return file.getBoolean(FLAG,false);
    }
    public void setToken(String token){
        editor.putString(TOKEN,token);
        editor.commit();
    }
    public String getToken(){
        return file.getString(TOKEN,"");
    }

    public String getStoreId(){
        return file.getString(STORE_ID,"");
    }
    public String getUserName(){
        return file.getString(USER_NAME,"");
    }
    public String getPassword(){
        return file.getString(PASSWORD,"");
    }
    public String getPrintCopies(){
        return file.getString(PRINT_COPIES,"");
    }

}
