package com.paperfly.otphelper;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class OtpIntentService extends IntentService {
    public static final String EXTRA_PARAM1 = OtpIntentService.class.getCanonicalName() + ".EXTRA_PARAM1";
    private static final String TAG = OtpIntentService.class.getSimpleName();
    private Handler mHandler;

    public OtpIntentService() {
        super("OtpIntentService");
        mHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final Otp otp = new Otp((Otp) intent.getParcelableExtra(EXTRA_PARAM1));
            final Context ctx = this;

            handleOtpReceived(otp);

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ctx, otp.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleOtpReceived(Otp otp) {
        OtpRepository otpRepo = new OtpRepository();
        otpRepo.setOtp(otp);
    }
}
