package com.example.hcm.feihuread.fragment;

import com.example.hcm.feihuread.model.BookChapterDetail;

import java.util.List;

/**
 * Created by hcm on 2018/4/28.
 */

public interface GetChapterFunction {
    void  getChapterDetail(List<BookChapterDetail> datas);
}
