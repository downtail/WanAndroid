package com.downtail.wanandroid.entity.response;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ArticleMultipleEntity implements MultiItemEntity {

    private int itemType;
    public static final int TEXT = 0;
    public static final int IMAGE = 1;
    public static final int EXTRA = 2;
    public static final int WEBSITE = 3;

    private ArticleResponse article;
    private WebsiteResponse website;

    @Override
    public int getItemType() {
        return itemType;
    }

    public ArticleMultipleEntity(int itemType) {
        this.itemType = itemType;
    }

    public ArticleMultipleEntity(int itemType, ArticleResponse article) {
        this.itemType = itemType;
        this.article = article;
    }

    public ArticleMultipleEntity(WebsiteResponse website) {
        this.itemType = WEBSITE;
        this.website = website;
    }

    public ArticleResponse getArticle() {
        return article;
    }

    public WebsiteResponse getWebsite() {
        return website;
    }
}
