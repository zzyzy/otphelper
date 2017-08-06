package com.paperfly.otphelper;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class OtpIntentService extends IntentService {
    public static final String EXTRA_PARAM_OTP = OtpIntentService.class.getCanonicalName() + ".EXTRA_PARAM_OTP";
    public static final String EXTRA_PARAM_SENDER = OtpIntentService.class.getCanonicalName() + ".EXTRA_PARAM_SENDER";
    public static final String CHANNEL1 = OtpIntentService.class.getCanonicalName() + ".CHANNEL1";
    private static final String TAG = OtpIntentService.class.getSimpleName();

    public OtpIntentService() {
        super("OtpIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final Otp otp = new Otp((Otp) intent.getParcelableExtra(EXTRA_PARAM_OTP));
            final String sender = intent.getStringExtra(EXTRA_PARAM_SENDER);

            handleOtpReceived(otp);

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(getApplicationContext(), CHANNEL1)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle(String.format("Saved OTP from %s", sender))
                            .setContentText(otp.getText());
            int notificationId = 1;
            NotificationManager notifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notifyMgr != null)
                notifyMgr.notify(notificationId, builder.build());
        }
    }

    private void handleOtpReceived(Otp otp) {
        OtpRepository otpRepo = new OtpRepository();
        otpRepo.setOtp(otp);
    }
}
