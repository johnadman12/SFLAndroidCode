package stock.com.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import stock.com.utils.SessionManager;

public class MyFireBaseMessengingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SessionManager.getInstance(this).setToken(s);
    }
}
