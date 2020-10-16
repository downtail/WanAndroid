package com.downtail.wanandroid.entity.local;

/**
 * WanAndroid
 * Created by downtail on 2020/10/13
 */
public class AuthorEntity {

    private int authorId;
    private String author;
    private String thumb;
    private String description;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
