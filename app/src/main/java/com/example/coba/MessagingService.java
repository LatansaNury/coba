package com.example.coba;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MessagingService extends FirebaseMessagingService {
    private static final int NOTIFICATION_ID = 0;
    private static final String NOTIFICATION_CHANNEL_ID = "CHANNEL_1";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String messageTitle = remoteMessage.getData().get("title");
        String messageBody = remoteMessage.getData().get("body");

        if(messageTitle.equals("Fall")){
            Log.d("sfs", "onMessageReceived: "+remoteMessage.getNotification());
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            long[] v = {500, 1000};
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            builder.setContentTitle("Warning")
                    .setContentText("Fall Detected!!!")
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
                    .setPriority(Notification.PRIORITY_MAX);
            //.setVibrate(new long[]{Notification.DEFAULT_VIBRATE})

            Intent resultIntent = new Intent(this, Notif.class);
            PendingIntent pendingIntent = PendingIntent
                    .getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
                    );
            builder.setContentIntent(pendingIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                String channelID = BuildConfig.APPLICATION_ID;
                NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, BuildConfig.APPLICATION_ID, importance);
                channel.setDescription(channelID);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            Notification mNotifi = builder.build();
            mNotifi.flags |= Notification.FLAG_INSISTENT;
            notificationManager.notify(NOTIFICATION_ID, mNotifi);
        }
        if(messageTitle.equals("Warning")){
            Log.d("sfs", "onMessageReceived: "+remoteMessage.getNotification());
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            long[] v = {500, 1000};
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            builder.setContentTitle("Warning")
                    .setContentText("Emergency....")
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
                    .setPriority(Notification.PRIORITY_MAX);
            //.setVibrate(new long[]{Notification.DEFAULT_VIBRATE})

            Intent resultIntent = new Intent(this, Notif.class);
            PendingIntent pendingIntent = PendingIntent
                    .getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
                    );
            builder.setContentIntent(pendingIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                String channelID = BuildConfig.APPLICATION_ID;
                NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, BuildConfig.APPLICATION_ID, importance);
                channel.setDescription(channelID);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            Notification mNotifi = builder.build();
            mNotifi.flags |= Notification.FLAG_INSISTENT;
            notificationManager.notify(NOTIFICATION_ID, mNotifi);
        }
//        String messageTitle = remoteMessage.getNotification().getTitle();
//        String messageBody = remoteMessage.getNotification().getBody();
        //notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

//    @Override
//    public void onCreate() {
//        FirebaseMessaging.getInstance().subscribeToTopic("android");
//    }
}
