package com.example.sayed.notificationchannelpractice;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int NOTI_PRIMARY1 = 1100;
    private static final int NOTI_PRIMARY2 = 1101;
    private static final int NOTI_SECONDARY1 = 1200;
    private static final int NOTI_SECONDARY2 = 1201;

    private MainUi ui; 
    private NotificationHelper notificationHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationHelper = new NotificationHelper(this);
        ui = new MainUi(findViewById(R.id.activity_main));
        
    }

    /**
     * Send activity notifications.
     *
     * @param id The ID of the notification to create
     * @param title The title of the notification
     */
    public void sendNotification(int id, String title){
        Notification.Builder nb = null;
        switch (id){
            case NOTI_PRIMARY1:
                nb= notificationHelper.getNotification(title,
                        getString(R.string.primary1_body));
                break;
            case NOTI_PRIMARY2:
                nb = notificationHelper.getNotification2(title,
                        getString(R.string.primary2_body));
                break;
            case NOTI_SECONDARY1:
                nb= notificationHelper.getNotification(title,
                        getString(R.string.secondary1_body));
                break;
            case NOTI_SECONDARY2:
                nb = notificationHelper.getNotification2(title,
                        getString(R.string.secondary2_body));
                break;
        }
        if (nb != null){
            notificationHelper.notify(id, nb);
        }
    }
    /**
     * Send Intent to load system Notification Settings for this app.
     */
    public void goToNotificationSettings(){
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(intent);
    }

    /**
     * Send intent to load system Notification Settings UI for a particular channel.
     *
     * @param channel Name of channel to configure
     */

    public void goToNotificationSettings(String channel){
        Intent intent= new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
        startActivity(intent);
    }
    /**
     * View model for interacting with Activity UI elements. (Keeps core logic for sample
     * seperate.)
     */
    class  MainUi implements View.OnClickListener{
        final TextView titlePrimary;
        final TextView titleSecondary;

        private MainUi(View root) {
            titlePrimary = root.findViewById(R.id.main_primary_title);
            ((Button)root.findViewById(R.id.main_primary_send1)).setOnClickListener(this);
            ((Button)root.findViewById(R.id.main_primary_send2)).setOnClickListener(this);
            ((ImageButton)root.findViewById(R.id.main_primary_config)).setOnClickListener(this);

            titleSecondary = root.findViewById(R.id.main_secondary_title);
            ((Button)root.findViewById(R.id.main_secondary_send1)).setOnClickListener(this);
            ((Button)root.findViewById(R.id.main_secondary_send2)).setOnClickListener(this);
            (root.findViewById(R.id.main_secondary_config)).setOnClickListener(this);

            (root.findViewById(R.id.btnA)).setOnClickListener(this);
        }
        private String getTitlePrimaryText(){
            if (titlePrimary != null){
                return titlePrimary.getText().toString();
            }
            return "";
        }
        private String getTitleSecondaryText() {
            if (titlePrimary != null) {
                return titleSecondary.getText().toString();
            }
            return "";
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.main_primary_send1:
                    sendNotification(NOTI_PRIMARY1, getTitlePrimaryText());
                    break;
                case R.id.main_primary_send2:
                    sendNotification(NOTI_PRIMARY2, getTitlePrimaryText());
                    break;
                case R.id.main_primary_config:
                    goToNotificationSettings(NotificationHelper.PRIMARY_CHANNEL);
                    break;

                case R.id.main_secondary_send1:
                    sendNotification(NOTI_SECONDARY1, getTitleSecondaryText());
                    break;
                case R.id.main_secondary_send2:
                    sendNotification(NOTI_SECONDARY2, getTitleSecondaryText());
                    break;
                case R.id.main_secondary_config:
                    goToNotificationSettings(NotificationHelper.SECONDARY_CHANNEL);
                    break;
                case R.id.btnA:
                    goToNotificationSettings();
                    break;
                default:
                    Log.e(TAG, "Unknown click event.");
                    break;
            }
        }
    }
}









