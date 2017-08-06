package com.paperfly.otphelper;

import android.os.Parcel;
import android.os.Parcelable;

public class OtpParcelable extends Otp implements Parcelable {
    public static final Parcelable.Creator<OtpParcelable> CREATOR
            = new Parcelable.Creator<OtpParcelable>() {
        public OtpParcelable createFromParcel(Parcel in) {
            return new OtpParcelable(in);
        }

        public OtpParcelable[] newArray(int size) {
            return new OtpParcelable[size];
        }
    };

    public OtpParcelable(Otp otp) {
        setText(otp.getText());
        setDate(otp.getDate());
    }

    private OtpParcelable(Parcel in) {
        setText(in.readString());
        setDate(in.readString());
    }

    public Otp getOtp() {
        return new Otp(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(getText());
        out.writeString(getDate());
    }
}
