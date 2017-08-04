package com.paperfly.otphelper;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;

public class SmsReceiverService extends Service {
    private final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private static boolean mIsRunning;
    private SmsReceiver mReceiver;

    public SmsReceiverService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mIsRunning) {
            mReceiver = new SmsReceiver();

            ArrayList<OtpListener> listeners = new ArrayList<>();
            listeners.add(new OtpListener("DBS Bank", "Enter (\\d*) \\(OTP\\) to access your Online Banking services."));
            mReceiver.bindListeners(listeners);
            Toast.makeText(this, "Listeners bound", Toast.LENGTH_SHORT).show();

            registerReceiver(mReceiver, new IntentFilter(ACTION));
            Toast.makeText(this, "Receiver registered", Toast.LENGTH_SHORT).show();

            mIsRunning = true;

            return START_STICKY;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mIsRunning = false;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        mIsRunning = false;
        super.onDestroy();
    }

    public static boolean IsRunning() {
        return mIsRunning;
    }
}
