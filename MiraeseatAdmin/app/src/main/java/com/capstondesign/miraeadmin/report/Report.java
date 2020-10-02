package com.capstondesign.miraeadmin.report;

import android.os.Parcel;
import android.os.Parcelable;

import com.capstondesign.miraeadmin.inquiry.Inquiry;

public class Report implements Parcelable {
    private String documentID;
    private String userEmail;
    private String targetReview;
    private String reportContext;
    private String isChecked;
    private String reportTime;

    public Report() { }

    public Report(String documentID, String userEmail, String targetReview, String reportContext, String isChecked, String reportTime) {
        this.documentID = documentID;
        this.userEmail = userEmail;
        this.targetReview = targetReview;
        this.reportContext = reportContext;
        this.isChecked = isChecked;
        this.reportTime = reportTime;
    }

    public Report(Parcel src) {
        documentID = src.readString();
        userEmail = src.readString();
        targetReview = src.readString();
        reportContext = src.readString();
        isChecked = src.readString();
        reportTime = src.readString();
    }

    public String getDocumentID() {
        return documentID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getTargetReview() {
        return targetReview;
    }

    public String getReportContext() {
        return reportContext;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getReportTime() {
        return reportTime;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Report createFromParcel(Parcel src) {
            return new Report(src);
        }

        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(documentID);
        dest.writeString(userEmail);
        dest.writeString(targetReview);
        dest.writeString(reportContext);
        dest.writeString(isChecked);
        dest.writeString(reportTime);
    }
}
