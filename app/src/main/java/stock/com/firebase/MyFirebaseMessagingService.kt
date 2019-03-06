package stock.com.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    var unread = 0
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Log.e("Remote Message=", remoteMessage!!.data.toString())
//        var data = sendNotif(remoteMessage.data)
//        unread = Prefs.getInstance(this).getIntValue(PrefConstant.UNREAD_COUNT,0)
//        unread += 1
//        Prefs.getInstance(this).putIntValue(PrefConstant.UNREAD_COUNT, unread)
//        ShortcutBadger.applyCount(this, Prefs.getInstance(this).getIntValue(PrefConstant.UNREAD_COUNT,0))
//        generateNotification(data)
    }
    /*pendingCart
    orderPlaced
    orderCancelled
    productAvailable
    general
    deliveryStatusChange
    deliveryBoyAssigned*/

//    object NotificationType {
//        const val pendingCart = "pendingCart"
//        const val orderPlaced = "orderPlaced"
//        const val orderCancelled = "orderCancelled"
//        const val productAvailable = "productAvailable"
//        const val general = "general"
//        const val deliveryStatusChange = "deliveryStatusChange"
//        const val deliveryBoyAssigned = "deliveryBoyAssigned"
//        const val OrderDelivered="OrderDelivered"
//    }
//
//    private fun generateNotification(data: PNModel) {
//
//        val largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_foreground)
//        val notificationManager = getSystemService(
//                Context.NOTIFICATION_SERVICE) as NotificationManager
//        val mBuilder = NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
//
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText(data.message)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true)
//                .setLargeIcon(largeIcon)
//                .setStyle(NotificationCompat.BigTextStyle().bigText(data.message))
//                .setContentIntent(setNotificationIntent(data))
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mBuilder.setSmallIcon(R.mipmap.ic_launcher)
//            mBuilder.color = ContextCompat.getColor(this, R.color.colorPrimary)
//        } else
//            mBuilder.setSmallIcon(R.mipmap.ic_launcher)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Create the NotificationChannel
//            val name = getString(R.string.default_notification_channel_name)
//            val description = getString(R.string.default_notification_channel_description)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val mChannel = NotificationChannel(getString(R.string.default_notification_channel_id), name, importance)
//            mChannel.description = description
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            notificationManager.createNotificationChannel(mChannel)
//        }
////        val id = (System.currentTimeMillis() * (Math.random() * 100).toInt()).toInt()
//        notificationManager.notify(getRandomNumer(), mBuilder.build())
//
//        when {
//            data.notification_type.equals(NotificationType.orderCancelled) ||
//                    data.notification_type.equals(NotificationType.deliveryStatusChange) ||
//                    data.notification_type.equals(NotificationType.deliveryBoyAssigned)
//            -> try {
//                if (Prefs.getInstance(this).userdata!!.user_id.isEmpty()) {
//                } else {
//                    val intent2 = Intent()
//                    intent2.action = "UPDATE_STATUS"
//                    sendBroadcast(intent2)
//                }
//            } catch (e: Exception) {
//            }
//        }
//
//    }
//
//
//    private fun setNotificationIntent(data: PNModel): PendingIntent {
//        var notificationIntent: Intent? = null
//        if (data.notification_type.equals(NotificationType.pendingCart)) {
//            notificationIntent = Intent(this, CartActivity::class.java)
//        } else if (data.notification_type.equals(NotificationType.orderPlaced)) {
//            notificationIntent = Intent(this, MyOrderDetailActivity::class.java)
//            notificationIntent.putExtra(IntentConstant.ORDER_ID, data.item_id)
//        } else if (data.notification_type.equals(NotificationType.orderCancelled)) {
//            notificationIntent = Intent(this, MyOrderDetailActivity::class.java)
//            notificationIntent.putExtra(IntentConstant.ORDER_ID, data.item_id)
//        } else if (data.notification_type.equals(NotificationType.productAvailable)) {
//            notificationIntent = Intent(this, ProductDetailActivity::class.java)
//            notificationIntent.putExtra(IntentConstant.PRODUCT_ID, data.item_id)
//        } else if (data.notification_type.equals(NotificationType.deliveryStatusChange)) {
//            notificationIntent = Intent(this, MyOrderDetailActivity::class.java)
//            notificationIntent.putExtra(IntentConstant.ORDER_ID, data.item_id)
//        }else if (data.notification_type.equals(NotificationType.OrderDelivered)) {
//            notificationIntent = Intent(this, MyOrderDetailActivity::class.java)
//            notificationIntent.putExtra(IntentConstant.ORDER_ID, data.item_id)
//        } else if (data.notification_type.equals(NotificationType.deliveryBoyAssigned)) {
//            notificationIntent = Intent(this, MyOrderDetailActivity::class.java)
//            notificationIntent.putExtra(IntentConstant.ORDER_ID, data.item_id)
//        } else if (data.notification_type.equals(NotificationType.general)) {
////          notificationIntent!!.putExtra(IntentConstant.ORDER_ID, data.item_id)
//            notificationIntent = Intent(this, NotificationActivity2::class.java)
//        }
//
////      val notificationIntent = Intent(this, ProductDetailActivity::class.java)
//        notificationIntent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        val parentIntent = Intent(this, HomeActivity::class.java)
//        parentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        /* create stack builder if you want to open parent activty on notification click*/
//        val stackBuilder = TaskStackBuilder.create(this)
//        stackBuilder.addParentStack(HomeActivity::class.java)
////        notificationIntent.putExtra(IntentConstant.ORDER_ID, data.item_id)
//        notificationIntent.putExtra(IntentConstant.FROM, 1)
//        notificationIntent.putExtra(IntentConstant.NOTIFICATION_ID, data.notification_id)
//        /* add all notification to stack*/
//        stackBuilder.addNextIntent(parentIntent)
//        stackBuilder.addNextIntent(notificationIntent)
//        /* get pending intent from stack builder*/
//        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)
//    }
//
//    private fun sendNotif(data: Map<String, String>): PNModel {
//        var notificationsModel = PNModel()
//        notificationsModel.notification_type = data["notification_type"]
//        notificationsModel.notification_id = data["notification_id"]
//        var jsonObject = JSONObject(data["payload"])
//        notificationsModel.second_user_id = jsonObject.getString("second_user_id")
//        notificationsModel.image = jsonObject.getString("image")
//        notificationsModel.profile_image = jsonObject.getString("profile_image")
//        notificationsModel.amount = jsonObject.getString("amount")
//        notificationsModel.user_id = jsonObject.getString("user_id")
//        notificationsModel.item_id = jsonObject.getString("item_id")
//        notificationsModel.last_name = jsonObject.getString("last_name")
//        notificationsModel.title = jsonObject.getString("title")
//        notificationsModel.first_name = jsonObject.getString("first_name")
//        notificationsModel.badge = Integer.parseInt(data["badge"])
//        notificationsModel.message = data["message"]
//        Log.e("Notification==>", notificationsModel.toString())
//        return notificationsModel
//    }
//
//    fun getRandomNumer(): Int {
//        val r = Random()
//        return r.nextInt(80 - 65) + 65
//    }
//
//    private fun isAppIsInBackground(context: Context): Boolean {
//        var isInBackground = true
//        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
//            val runningProcesses = am.runningAppProcesses
//            for (processInfo in runningProcesses) {
//                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                    for (activeProcess in processInfo.pkgList) {
//                        if (activeProcess == context.packageName) {
//                            isInBackground = false
//                        }
//                    }
//                }
//            }
//        } else {
//            val taskInfo = am.getRunningTasks(1)
//            val componentInfo = taskInfo[0].topActivity
//            if (componentInfo.packageName == context.packageName) {
//                isInBackground = false
//            }
//        }
//        return isInBackground
//    }
}
