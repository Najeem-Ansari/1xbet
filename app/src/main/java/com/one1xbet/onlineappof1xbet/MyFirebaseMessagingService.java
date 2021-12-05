package com.one1xbet.onlineappof1xbet;

import static android.media.RingtoneManager.getDefaultUri;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    private static final String CHANNEL_ID = "com.kwik.charge.fcm";
    private static final String CHANNEL_NAME = "com.kwik.charge.fcm_name";
    NotificationManager manager;
    String message="", title="", userid;
    PendingIntent pendingIntent;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        // Check if message contains a data payload.
        Map<String, String> data = remoteMessage.getData();
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data: " + remoteMessage.getData().toString());
            title =data.get("title");
            message = data.get("body");
        }
        sendNotification(title,message,"");

}

    private void sendNotification(String title, String message, String image) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("notification", "notification");
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("imageUrl", image);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        int color = ContextCompat.getColor(this, android.R.color.transparent);
        Uri defaultSoundUri = getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(

             MyFirebaseMessagingService.this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setColor(color)
                .setPriority(Notification.PRIORITY_MAX)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        if (!image.equals("")) {
            try {
                Bitmap remote_picture = BitmapFactory.decodeStream(
                        (InputStream) new URL(image).getContent());

                notificationBuilder
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setStyle(new NotificationCompat.BigPictureStyle().setSummaryText(message))
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(remote_picture));
            } catch (Exception e) {
                sendNotification(title, message, "");
            }
        }
        int notificationId = (int) System.currentTimeMillis();
      //  NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        int notificationID = Integer.parseInt(new SimpleDateFormat("HHmmssSSS").format(new Date()));
        notificationManager.notify(notificationID /* ID of notification */, notificationBuilder.build());
      //  manager.notify(notificationId, notificationBuilder.build());
   //     sendCount(title, message, image);
    }

}
