package com.example.eventtracker;

import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class SMSNotifications {
    public static AlertDialog doubleButton(final MainActivity context) {
        // uses the builder class for creating a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.sms_notif_title).setIcon(R.drawable.sms_drawable)
                .setCancelable(false).setMessage(R.string.sms_message)
                .setPositiveButton(R.string.sms_enable, (dialog, arg1) -> {
                    Toast.makeText(context, "SMS Enable", Toast.LENGTH_LONG).show();
                    MainActivity.AllowSendSMS();
                    dialog.cancel();
                })
                .setNegativeButton(R.string.sms_disable, (dialog, arg1) -> {
                    Toast.makeText(context, "SMS Disable", Toast.LENGTH_LONG).show();
                    MainActivity.DenySendSMS();
                    dialog.cancel();
                });
        return builder.create();
    }
}
