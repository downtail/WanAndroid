package com.downtail.wanandroid.core.http.api;

import com.downtail.wanandroid.app.Constants;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.entity.response.BannerResponse;
import com.downtail.wanandroid.entity.response.ArticleResponse;
import com.downtail.wanandroid.entity.response.Paging;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface HomeApi {

    @GET(Constants.BANNER_URL)
    Observable<BaseResponse<List<BannerResponse>>> getBannerData();

    @GET(Constants.ADVANCED)
    Observable<BaseResponse<List<ArticleResponse>>> getAdvancedArticleData();

    @GET(Constants.HOME_ARTICLE)
    Observable<BaseResponse<Paging<ArticleResponse>>> getHomeArticleData(@Path("page") int page);

    @Streaming
    @GET
    @Headers("shouldInterceptProgress:true")
    Observable<ResponseBody> downloadLatestApk(@Url String url);
}
