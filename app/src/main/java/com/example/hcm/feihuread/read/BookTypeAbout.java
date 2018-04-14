package com.example.hcm.feihuread.read;

/**
 * Created by Administrator on 2018/4/6 0006.
 */

public class BookTypeAbout {
    String bookHref;
    String bookName;
    String theNewstChapter;
    String updateData;

    public BookTypeAbout() {

    }

    public String getBookHref() {
        return bookHref;
    }

    public void setBookHref(String bookHref) {
        this.bookHref = bookHref;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getTheNewstChapter() {
        return theNewstChapter;
    }

    public void setTheNewstChapter(String theNewstChapter) {
        this.theNewstChapter = theNewstChapter;
    }

    public String getUpdateData() {
        return updateData;
    }

    public void setUpdateData(String updateData) {
        this.updateData = updateData;
    }

    public BookTypeAbout(String bookHref, String bookName, String theNewstChapter, String updateData) {
        this.bookHref = bookHref;
        this.bookName = bookName;
        this.theNewstChapter = theNewstChapter;
        this.updateData = updateData;
    }
}
