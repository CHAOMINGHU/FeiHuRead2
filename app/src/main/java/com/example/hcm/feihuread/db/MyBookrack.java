package com.example.hcm.feihuread.db;

import org.litepal.crud.DataSupport;

/**
 * Created by hcm on 2018/4/17.
 */

public class MyBookrack extends DataSupport {
    String bookName;
    String bookAuthor;
    String bookHref;
    String bookCover;

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookHref() {
        return bookHref;
    }

    public void setBookHref(String bookHref) {
        this.bookHref = bookHref;
    }


}
