package com.downtail.wanandroid.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * WanAndroid
 * Created by downtail on 2020/10/13
 */
@Entity
public class Author {

    @Id(autoincrement = true)
    Long id;
    @Unique
    @NotNull
    int authorId;
    String name;
    String thumb;
    String description;

    @Generated(hash = 470542212)
    public Author(Long id, int authorId, String name, String thumb,
                  String description) {
        this.id = id;
        this.authorId = authorId;
        this.name = name;
        this.thumb = thumb;
        this.description = description;
    }

    @Generated(hash = 64241762)
    public Author() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
