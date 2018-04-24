package com.example.hcm.feihuread.model;

import java.io.Serializable;

/**
 * Created by hcm on 2018/3/26.
 */

public class BookDetil implements Serializable{
    private int id;
    private String bookName;
    private String author;
    private String cover;
    private String href;



    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
public BookDetil(){}
    public BookDetil(int id, String bookName, String author, String cover, String href) {
        this.id=id;
        this.bookName = bookName;
        this.author = author;
        this.cover = cover;
        this.href = href;
    }
}
