package com.debam.absensi.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.debam.absensi.R;
import com.debam.absensi.activity.ReceiveNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Debam on 9/7/2016.
 */

public class MyFcmListenerService  extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String from = remoteMessage.getFrom();
        Map data = remoteMessage.getData();

        Log.e("data", data.toString());

        String title = data.get("title").toString();
        String message = data.get("message").toString();
        String matkul = data.get("matkul").toString();
        String waktu = data.get("waktu").toString();
        String ruang = data.get("ruang").toString();

        Log.e("title", title);
        Log.e("message", message);
        Log.e("matkul", matkul);
        Log.e("waktu", waktu);
        Log.e("ruang", ruang);

        showSmallNotification2(title, message, matkul, waktu, ruang);

        Log.e("data", data.toString());
    }

    private void showSmallNotification2(String title, String message, String matkul, String waktu, String ruang) {

        Intent i = new Intent(this, ReceiveNotification.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("waktu", waktu);
        i.putExtra("matkul", matkul);
        i.putExtra("ruang",ruang);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        inboxStyle.addLine(message);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setTicker(title)
                .setWhen(0)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setSound(sound)
//                .setStyle(inboxStyle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification.build());
    }
}