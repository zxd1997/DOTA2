package com.example.zxd1997.dota2.Beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;

public class Cast implements Parcelable {
    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };
    private String id;
    private int time;
    private SpannableStringBuilder t;
    public Cast(int time, String id, int type) {
        this.type = type;
        this.id = id;
        this.time = time;
    }

    private int type;
    public Cast(int time, SpannableStringBuilder t, int type) {
        this.type = type;
        this.t = t;
        this.time = time;
    }

    public Cast() {

    }

    protected Cast(Parcel in) {
        time = in.readInt();
        id = in.readString();
        type = in.readInt();
    }

    public void setId(String id) {
        this.id = id;
    }

    public SpannableStringBuilder getT() {
        return t;
    }

    public void setT(SpannableStringBuilder t) {
        this.t = t;
    }

    public int getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(time);
        dest.writeString(id);
        dest.writeInt(type);
    }
}
