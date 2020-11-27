package com.downtail.wanandroid.core.db;

import com.downtail.wanandroid.entity.db.Author;
import com.downtail.wanandroid.entity.db.Word;

import java.util.List;

import io.reactivex.Observable;

public interface DatabaseHelper {

    Observable<List<Author>> getAuthorList();

    void saveAuthorList(List<Author> data);

    Observable<Long> saveRecentWord(String keyword, long createTime);

    Observable<List<Word>> getRecentWord();
}
