package com.example.puhon_sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class GoalsNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "GoalNotification")
                .setSmallIcon(R.drawable.reminder_notif)
                .setContentTitle("Goals")
                .setContentText("Your goal is due today. Would you like to accomplish this?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(123,builder.build());

    }
}
