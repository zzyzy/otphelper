package com.paperfly.otphelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtpListener {
    private String mSender;
    private String mMessageFormat;

    public OtpListener(String sender, String messageFormat) {
        mSender = sender;
        mMessageFormat = messageFormat;
    }

    public boolean isRightSender(String sender) {
        return mSender.equals(sender);
    }

    public Otp getOtpFromMessage(String message) {
        Pattern pattern = Pattern.compile(mMessageFormat);
        Matcher matcher = pattern.matcher(message);
        Otp otp = new Otp();

        if (matcher.find()) {
            otp.setText(matcher.group(1));
        }

        return otp;
    }
}
