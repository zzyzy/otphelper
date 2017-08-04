package com.paperfly.otphelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class SmsReceiver extends BroadcastReceiver {
    private ArrayList<OtpListener> listeners;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listeners == null || listeners.isEmpty()) return;

        Bundle bundle = intent.getExtras();

        if (bundle == null) return;

        Object[] pdus = (Object[]) bundle.get("pdus");

        if (pdus == null) return;

        for (Object pdu : pdus) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);

            for (OtpListener listener : listeners) {
                String sender = smsMessage.getDisplayOriginatingAddress();
                String messageBody = smsMessage.getMessageBody();

                if (!listener.isRightSender(sender)) continue;

                Log.i("SMSRECEIVER", "ISRIGHTSENDER");

                Otp otp = listener.getOtpFromMessage(messageBody);

                Log.i("SMSRECEIVER", sender);
                Log.i("SMSRECEIVER", messageBody);

                if (!otp.isValid()) continue;

                Log.i("SMSRECEIVER", otp.getText());

                OtpRepository otpRepo = new OtpRepository();
                otpRepo.setOtp(otp);

                Toast.makeText(context, otp.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void bindListeners(ArrayList<OtpListener> listeners) {
        this.listeners = listeners;
    }
}
