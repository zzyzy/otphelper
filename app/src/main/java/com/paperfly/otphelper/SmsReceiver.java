package com.paperfly.otphelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    private static final String NO_LISTENERS = "There are no listeners available";
    private static final String NO_SUCH_LISTENER = "No listener for this sender";
    private static Map<String, OtpListener> mListeners;

    public SmsReceiver() {
        mListeners = new HashMap<>();
        mListeners.put(
                "DBS Bank",
                new OtpListener(
                        "DBS Bank",
                        "Enter (\\d*) \\(OTP\\) to access your Online Banking services."
                )
        );
    }

    public static void bindListeners(Map<String, OtpListener> listeners) {
        mListeners = listeners;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mListeners == null || mListeners.isEmpty()) {
            Toast.makeText(context, NO_LISTENERS, Toast.LENGTH_SHORT).show();
            Log.i(TAG, NO_LISTENERS);
            return;
        }

        Bundle bundle = intent.getExtras();

        if (bundle == null) return;

        Object[] pdus = (Object[]) bundle.get("pdus");

        if (pdus == null) return;

        for (Object pdu : pdus) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);

            String sender = smsMessage.getDisplayOriginatingAddress();
            String messageBody = smsMessage.getMessageBody();
            Log.i(TAG, sender);
            Log.i(TAG, messageBody);

            if (!mListeners.containsKey(sender)) {
                Log.i(TAG, NO_SUCH_LISTENER);
                continue;
            }

            OtpListener listener = mListeners.get(sender);
            Log.i(TAG, listener.isRightSender(sender) ? "True" : "False");

            if (!listener.isRightSender(sender)) continue;

            Otp otp = listener.getOtpFromMessage(messageBody);
            Log.i(TAG, otp.getText());

            if (!otp.isValid()) continue;

            OtpParcelable otpParcelable = new OtpParcelable(otp);
            Intent otpIntent = new Intent(context, OtpIntentService.class);
            otpIntent.putExtra(OtpIntentService.EXTRA_PARAM1, otpParcelable);
            context.startService(otpIntent);
        }
    }
}
