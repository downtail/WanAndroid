package com.downtail.wanandroid.ui.project.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.downtail.wanandroid.R;
import com.downtail.wanandroid.ui.mine.entity.WebsiteResponse;
import com.downtail.wanandroid.ui.project.entity.ArticleMultipleEntity;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.utils.ImageLoader;

import org.jetbrains.annotations.NotNull;

public class ArticleAdapter extends BaseMultiItemQuickAdapter<ArticleMultipleEntity, BaseViewHolder> {

    public ArticleAdapter() {
        addItemType(ArticleMultipleEntity.TEXT, R.layout.item_article_text);
        addItemType(ArticleMultipleEntity.IMAGE, R.layout.item_article_image);
        addItemType(ArticleMultipleEntity.EXTRA, R.layout.item_article_extra);
        addItemType(ArticleMultipleEntity.WEBSITE, R.layout.item_article_website);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ArticleMultipleEntity articleMultipleEntity) {
        int itemViewType = baseViewHolder.getItemViewType();
        ArticleResponse article = articleMultipleEntity.getArticle();
        if (itemViewType == ArticleMultipleEntity.TEXT) {
            String author = article.getAuthor();
            if (TextUtils.isEmpty(author)) {
                baseViewHolder.setText(R.id.tvAuthor, article.getShareUser());
            } else {
                baseViewHolder.setText(R.id.tvAuthor, author);
            }
            int type = article.getType();
            if (type == 0) {
                baseViewHolder.setGone(R.id.ivHot, true);
            } else if (type == 1) {
                baseViewHolder.setGone(R.id.ivHot, false);
            }
            baseViewHolder.setText(R.id.tvTitle, article.getTitle());
            String desc = article.getDesc();
            if (TextUtils.isEmpty(desc)) {
                baseViewHolder.setGone(R.id.tvDescription, true);
            } else {
                baseViewHolder.setGone(R.id.tvDescription, false);
                baseViewHolder.setText(R.id.tvDescription, desc.replace("<p>", "").replace("</p>", "").trim());
            }
            baseViewHolder.setText(R.id.tvDate, article.getNiceShareDate());
            baseViewHolder.getView(R.id.ivCollect).setSelected(article.isCollect());
        } else if (itemViewType == ArticleMultipleEntity.IMAGE) {
            ImageLoader.loadRoundedImage(getContext(), baseViewHolder.getView(R.id.ivThumbnail), article.getEnvelopePic(), 5, R.drawable.default_project_img);
            String author = article.getAuthor();
            if (TextUtils.isEmpty(author)) {
                baseViewHolder.setText(R.id.tvAuthor, article.getShareUser());
            } else {
                baseViewHolder.setText(R.id.tvAuthor, author);
            }
            int type = article.getType();
            if (type == 0) {
                baseViewHolder.setGone(R.id.ivHot, true);
            } else if (type == 1) {
                baseViewHolder.setGone(R.id.ivHot, false);
            }
            baseViewHolder.setText(R.id.tvTitle, article.getTitle());
            String desc = article.getDesc();
            if (TextUtils.isEmpty(desc)) {
                baseViewHolder.setGone(R.id.tvDescription, false);
            } else {
                baseViewHolder.setGone(R.id.tvDescription, true);
                baseViewHolder.setText(R.id.tvDescription, desc);
            }
            baseViewHolder.setText(R.id.tvDate, article.getNiceShareDate());
            baseViewHolder.getView(R.id.ivCollect).setSelected(article.isCollect());
        } else if (itemViewType == ArticleMultipleEntity.WEBSITE) {
            WebsiteResponse website = articleMultipleEntity.getWebsite();
            if (website != null) {
                baseViewHolder.setText(R.id.tvNameValue, website.getName());
                baseViewHolder.setText(R.id.tvLinkValue, website.getLink());
                baseViewHolder.getView(R.id.ivCollect).setSelected(true);
            }
        }
    }
}
