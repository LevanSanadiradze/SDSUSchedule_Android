package com.systemcorp.sdsu.schedule.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by giorgi on 1/21/18.
 */

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        String text = remoteMessage.getNotification().getBody();

//        showNotification(text);
    }

//    private void showNotification(String body) {
//
//        Intent intent = new Intent(this, LoggedInActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
//                .setContentTitle("Schedule")
//                .setContentText(body)
//                .setAutoCancel(true)
//                .setSound(notificationSound)
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.drawable.ic_sandiego);
//
//        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0,builder.build());
//    }
}
