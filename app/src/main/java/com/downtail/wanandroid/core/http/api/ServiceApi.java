package com.downtail.wanandroid.core.http.api;

import com.downtail.wanandroid.app.Constants;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.ui.project.entity.Paging;
import com.downtail.wanandroid.ui.service.ServiceResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceApi {

    @GET(Constants.SERVICE_COLUMN)
    Observable<BaseResponse<List<ServiceResponse>>> getServiceColumnData();

    @GET(Constants.CLIENT_ARTICLE)
    Observable<BaseResponse<Paging<ArticleResponse>>> getClientArticleData(@Path("id") int id, @Path("page") int page);

    @GET(Constants.CLIENT_KEYWORD)
    Observable<BaseResponse<Paging<ArticleResponse>>> getClientArticleByKeyword(@Path("id") int id, @Path("page") int page, @Query("k") String keyword);
}
