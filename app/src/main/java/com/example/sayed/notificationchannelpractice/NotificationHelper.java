package com.example.sayed.notificationchannelpractice;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class NotificationHelper extends ContextWrapper{
    /**
     * Helper class to manage notification channels, and create notifications.
     */
    public static final String PRIMARY_CHANNEL = "default";
    public static final String SECONDARY_CHANNEL = "second";
    private NotificationManager manager;
    /**
     * Registers notification channels, which can be used later by individual notifications.
     *
     //* @param ctx The application context
     */

    public NotificationHelper(Context ctx) {
        super(ctx);
        NotificationChannel channel1 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel1 = new NotificationChannel(PRIMARY_CHANNEL,
                    getString(R.string.noti_channel_default), NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setLightColor(Color.GREEN);
            channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(channel1);

            NotificationChannel channel2 = new NotificationChannel(SECONDARY_CHANNEL,
                    getString(R.string.noti_channel_second), NotificationManager.IMPORTANCE_HIGH);
            channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel2.setLightColor(Color.BLUE);
            getManager().createNotificationChannel(channel2);
        }
    }

    /**
     * Get a notification of type 1
     *
     * Provide the builder rather than the notification it's self as useful for making notification
     * changes.
     *
     // * @param title the title of the notification
     // * @param body the body text for the notification
     * @return the builder as it keeps a reference to the notification (since API 24)
     */
    public Notification.Builder getNotification(String title, String body){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            return  new Notification.Builder(getApplicationContext(), PRIMARY_CHANNEL)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setAutoCancel(true);

        }else{
            return  new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setAutoCancel(true);
        }
    }

    /**
     * Build notification for secondary channel.
     *
    // * @param title Title for notification.
    // * @param body Message for notification.
     * @return A Notification.Builder configured with the selected channel and details
     */
    public Notification.Builder getNotification2(String title, String body){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            return new Notification.Builder(getApplicationContext(), SECONDARY_CHANNEL)
                    .setContentTitle(title)
                    .setWhen(System.currentTimeMillis())
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);
        }else {
            return new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setWhen(System.currentTimeMillis())
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);
        }
    }

    /**
     * Send a notification.
     *
     * @param id The ID of the notification
     * @param notification The notification object
     */
    public void notify(int id, Notification.Builder notification){
        getManager().notify(id, notification.build());
    }

    /**
     * Get the notification manager.
     *
     * Utility method as this helper works with it a lot.
     *
     * @return The system service NotificationManager
     */

    private NotificationManager getManager(){

        if (manager == null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}
