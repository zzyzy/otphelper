package com.paperfly.otphelper;

import com.google.common.base.Strings;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Otp {
    String text;
    String date;

    public Otp() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Otp(String text, String date) {
        this.text = text;
        this.date = date;
    }

    public Otp(Otp otp) {
        this.text = otp.getText();
        this.date = otp.getDate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Exclude
    public boolean isValid() {
        return !Strings.isNullOrEmpty(text);
    }
}
