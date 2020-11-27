package com.downtail.wanandroid.core.http.api;

import com.downtail.wanandroid.app.Constants;
import com.downtail.wanandroid.core.http.BaseResponse;
import com.downtail.wanandroid.entity.local.Paging;
import com.downtail.wanandroid.entity.response.ArticleResponse;
import com.downtail.wanandroid.entity.response.BannerResponse;
import com.downtail.wanandroid.entity.response.CommonResponse;
import com.downtail.wanandroid.entity.response.HotEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

    @GET(Constants.HOT_KEY)
    Observable<BaseResponse<List<HotEntity>>> getHotKey();

    @FormUrlEncoded
    @POST(Constants.SEARCH)
    Observable<BaseResponse<Paging<ArticleResponse>>> getArticleByKeyword(@Path("page") int page, @Field("k") String keyword);

    @GET(Constants.COMMON)
    Observable<BaseResponse<List<CommonResponse>>> getCommonlyUsedWebsite();
}
