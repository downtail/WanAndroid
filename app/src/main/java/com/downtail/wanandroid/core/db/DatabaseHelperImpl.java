package com.downtail.wanandroid.core.db;

import com.downtail.wanandroid.entity.db.Author;
import com.downtail.wanandroid.entity.db.AuthorDao;
import com.downtail.wanandroid.entity.db.DaoSession;

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
}
