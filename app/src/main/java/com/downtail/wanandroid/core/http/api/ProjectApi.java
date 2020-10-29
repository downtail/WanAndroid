package com.downtail.wanandroid.core.http.api;

import com.downtail.wanandroid.app.Constants;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.entity.response.ArticleResponse;
import com.downtail.wanandroid.entity.response.CategoryResponse;
import com.downtail.wanandroid.entity.response.Paging;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectApi {

    @GET(Constants.PROJECT_COLUMN)
    Observable<BaseResponse<List<CategoryResponse>>> getProjectCategoryData();

    @GET(Constants.PROJECT_ARTICLE)
    Observable<BaseResponse<Paging<ArticleResponse>>> getProjectArticleData(@Path("page") int page, @Query("cid") int cid);
}
