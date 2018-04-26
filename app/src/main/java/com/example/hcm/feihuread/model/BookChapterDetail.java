package com.example.hcm.feihuread.model;

import java.io.Serializable;

/**
 * Created by hcm on 2018/4/26.
 */

public class BookChapterDetail implements Serializable {
    //当前章节的链接
    private String currentChapterHref;
    //当前章节的标题
    private String currentChapterTitle;

    public BookChapterDetail() {

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
}
