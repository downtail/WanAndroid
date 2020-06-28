package com.downtail.wanandroid.core.http.api;

import com.downtail.wanandroid.app.Constants;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.ui.home.BannerResponse;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.ui.project.entity.Paging;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HomeApi {

    @GET(Constants.BANNER_URL)
    Observable<BaseResponse<List<BannerResponse>>> getBannerData();

    @GET(Constants.ADVANCED)
    Observable<BaseResponse<List<ArticleResponse>>> getAdvancedArticleData();

    @GET(Constants.HOME_ARTICLE)
    Observable<BaseResponse<Paging<ArticleResponse>>> getHomeArticleData(@Path("page") int page);
}
