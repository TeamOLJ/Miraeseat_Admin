package com.capstondesign.miraeadmin;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.ServerTimestamp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Inquiry implements Parcelable {
    private String documentID;
    private String userName;
    private String userEmail;
    private String inquiryTitle;
    private String inquiryContext;
    private String isChecked;
    private String inquiryTime;

    SimpleDateFormat convert_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Inquiry() {}

    public Inquiry(String documentID, String userName, String userEmail, String inquiryTitle, String inquiryContext, String isChecked, String inquiryTime) {
        this.documentID = documentID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.inquiryTitle = inquiryTitle;
        this.inquiryContext = inquiryContext;
        this.isChecked = isChecked;
        this.inquiryTime = inquiryTime;
    }

    public Inquiry(Parcel src) {
        documentID = src.readString();
        userName = src.readString();
        userEmail = src.readString();
        inquiryTitle = src.readString();
        inquiryContext = src.readString();
        isChecked = src.readString();
        inquiryTime = src.readString();
    }

    public String getDocumentID() {
        return documentID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getInquiryTitle() {
        return inquiryTitle;
    }

    public String getInquiryContext() {
        return inquiryContext;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getInquiryTime() {
        return inquiryTime;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Inquiry createFromParcel(Parcel src) {
            return new Inquiry(src);
        }

        public Inquiry[] newArray(int size) {
            return new Inquiry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(documentID);
        dest.writeString(userName);
        dest.writeString(userEmail);
        dest.writeString(inquiryTitle);
        dest.writeString(inquiryContext);
        dest.writeString(isChecked);
        dest.writeString(inquiryTime);
    }
}
