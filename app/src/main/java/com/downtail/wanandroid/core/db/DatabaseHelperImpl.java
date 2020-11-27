package com.downtail.wanandroid.core.db;

import com.downtail.wanandroid.entity.db.Author;
import com.downtail.wanandroid.entity.db.AuthorDao;
import com.downtail.wanandroid.entity.db.DaoSession;
import com.downtail.wanandroid.entity.db.Word;
import com.downtail.wanandroid.entity.db.WordDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DatabaseHelperImpl implements DatabaseHelper {

    private final DaoSession daoSession;

    @Inject
    public DatabaseHelperImpl(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public Observable<List<Author>> getAuthorList() {
        AuthorDao authorDao = daoSession.getAuthorDao();
        List<Author> authors = authorDao.queryBuilder()
                .orderAsc(AuthorDao.Properties.Id)
                .list();
        return Observable.just(authors);
    }

    @Override
    public void saveAuthorList(List<Author> data) {
        AuthorDao authorDao = daoSession.getAuthorDao();
        authorDao.insertOrReplaceInTx(data);
    }

    @Override
    public Observable<Long> saveRecentWord(String keyword, long createTime) {
        WordDao wordDao = daoSession.getWordDao();
        Word word = new Word();
        word.setKeyword(keyword);
        word.setCreateTime(createTime);
        long id = wordDao.insertOrReplace(word);
        return Observable.just(id);
    }

    @Override
    public Observable<List<Word>> getRecentWord() {
        WordDao wordDao = daoSession.getWordDao();
        List<Word> list = wordDao.queryBuilder()
                .orderDesc(WordDao.Properties.CreateTime)
                .list();
        return Observable.just(list);
    }
}
