package com.paperfly.otphelper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OtpRepository {
    private DatabaseReference mDatabase;

    public OtpRepository() {
        mDatabase = FirebaseDatabase.getInstance().getReference("otp");
    }

    public void setOtp(Otp otp) {
        mDatabase.setValue(otp.getText());
    }
}
