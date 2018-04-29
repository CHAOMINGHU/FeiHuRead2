package com.example.hcm.feihuread.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hcm.feihuread.fragment.GetChapterFunction;

import java.io.Serializable;

/**
 * Created by hcm on 2018/4/26.
 */

public class BookChapterDetail implements Parcelable {
    //当前章节的链接
    private String currentChapterHref;
    //当前章节的标题
    private String currentChapterTitle;

    public BookChapterDetail() {
         super();
    }

    protected BookChapterDetail(Parcel in) {
        currentChapterHref = in.readString();
        currentChapterTitle = in.readString();
    }



    public String getCurrentChapterHref() {
        return currentChapterHref;
    }

    public void setCurrentChapterHref(String currentChapterHref) {
        this.currentChapterHref = currentChapterHref;
    }

    public String getCurrentChapterTitle() {
        return currentChapterTitle;
    }

    public void setCurrentChapterTitle(String currentChapterTitle) {
        this.currentChapterTitle = currentChapterTitle;
    }

    public BookChapterDetail(String currentChapterHref, String currentChapterTitle) {
        this.currentChapterHref = currentChapterHref;
        this.currentChapterTitle = currentChapterTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(currentChapterHref);
        parcel.writeString(currentChapterTitle);
    }
    public static final Creator<BookChapterDetail> CREATOR = new Creator<BookChapterDetail>() {
        @Override
        public BookChapterDetail createFromParcel(Parcel in) {

            BookChapterDetail b=new BookChapterDetail();
            b.setCurrentChapterHref(in.readString());
            b.setCurrentChapterTitle(in.readString());

            return b;
        }

        @Override
        public BookChapterDetail[] newArray(int size) {
            return new BookChapterDetail[size];
        }
    };
}
