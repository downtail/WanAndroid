package com.downtail.wanandroid.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Word {

    @Id(autoincrement = true)
    private Long id;

    @Unique
    @NotNull
    private String keyword;
    private long createTime;

    @Generated(hash = 1778304768)
    public Word(Long id, @NotNull String keyword, long createTime) {
        this.id = id;
        this.keyword = keyword;
        this.createTime = createTime;
    }

    @Generated(hash = 3342184)
    public Word() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

}
