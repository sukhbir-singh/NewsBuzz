package com.newsbuzz;


import android.os.Parcel;
import android.os.Parcelable;

public class NewsItem implements Parcelable{
    public String title,link_more,pubDate,description,link_image,category;

    public NewsItem(String title, String link_more, String pubDate, String description, String link_image,String category) {
        this.title = title;
        this.link_more = link_more;
        this.pubDate = pubDate;
        this.description = description;
        this.link_image = link_image;
        this.category=category;
    }

    protected NewsItem(Parcel in) {
        title=in.readString();
        link_more=in.readString();
        pubDate=in.readString();
        description=in.readString();
        link_image=in.readString();
    }

    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(link_more);
        parcel.writeString(pubDate);
        parcel.writeString(description);
        parcel.writeString(link_image);
    }
}
