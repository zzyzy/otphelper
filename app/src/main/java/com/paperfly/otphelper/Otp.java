package com.paperfly.otphelper;

import com.google.common.base.Strings;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Otp {
    public String text;

    public Otp() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Otp(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isValid() {
        return !Strings.isNullOrEmpty(text);
    }
}
