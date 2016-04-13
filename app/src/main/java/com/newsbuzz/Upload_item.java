package com.newsbuzz;


import android.os.Parcel;
import android.os.Parcelable;

public class Upload_item implements Parcelable{
    public String title,description,image;

    public Upload_item(String title, String description,String image) {
        this.title = title;
        this.description = description;
        this.image=image;
    }

    protected Upload_item(Parcel in) {
        title = in.readString();
        description = in.readString();
        image = in.readString();
    }

    public static final Creator<Upload_item> CREATOR = new Creator<Upload_item>() {
        @Override
        public Upload_item createFromParcel(Parcel in) {
            return new Upload_item(in);
        }

        @Override
        public Upload_item[] newArray(int size) {
            return new Upload_item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(image);
    }
}
