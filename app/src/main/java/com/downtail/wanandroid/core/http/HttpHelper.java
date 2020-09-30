package com.downtail.wanandroid.core.http;

import com.downtail.wanandroid.ui.home.BannerResponse;
import com.downtail.wanandroid.ui.main.UserEntity;
import com.downtail.wanandroid.ui.mine.entity.RankResponse;
import com.downtail.wanandroid.ui.mine.entity.RecordResponse;
import com.downtail.wanandroid.ui.mine.entity.WebsiteResponse;
import com.downtail.wanandroid.ui.project.entity.ArticleResponse;
import com.downtail.wanandroid.ui.project.entity.CategoryResponse;
import com.downtail.wanandroid.ui.project.entity.Paging;
import com.downtail.wanandroid.ui.service.ServiceResponse;
import com.downtail.wanandroid.ui.system.entity.NavigationResponse;
import com.downtail.wanandroid.ui.system.entity.SystemResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


public interface HttpHelper {

    Observable<ResponseBody> downloadLatestApk(String url);

    Observable<BaseResponse<List<BannerResponse>>> getBannerData();

    Observable<BaseResponse<List<ArticleResponse>>> getAdvancedArticleData();

    Observable<BaseResponse<Paging<ArticleResponse>>> getHomeArticleData(int page);

    Observable<BaseResponse<List<SystemResponse>>> getSystemData();

    Observable<BaseResponse<List<NavigationResponse>>> getNavigationData();

    Observable<BaseResponse<List<CategoryResponse>>> getProjectCategoryData();

    Observable<BaseResponse<List<ServiceResponse>>> getServiceColumnData();

    Observable<BaseResponse<UserEntity>> login(String username, String password);

    Observable<BaseResponse<UserEntity>> register(String username, String password, String rePassword);

    Observable<BaseResponse<String>> logout();

    Observable<BaseResponse<Paging<ArticleResponse>>> getProjectArticleData(int page, int cid);

    Observable<BaseResponse<String>> confirmArticleCollect(int id);

    Observable<BaseResponse<String>> cancelArticleCollect(int id);

    Observable<BaseResponse<Paging<ArticleResponse>>> getCollectArticleData(int page);

    Observable<BaseResponse<WebsiteResponse>> collectWebsite(String name, String link);

    Observable<BaseResponse<List<WebsiteResponse>>> getCollectWebsiteData();

    Observable<BaseResponse<WebsiteResponse>> modifyWebsite(int id, String name, String link);

    Observable<BaseResponse<String>> cancelWebsiteCollect(int id);

    Observable<BaseResponse<Paging<RankResponse>>> getRankListData(int page);

    Observable<BaseResponse<RankResponse>> getRankData();

    Observable<BaseResponse<Paging<RecordResponse>>> getRecordData(int page);

    Observable<BaseResponse<Paging<ArticleResponse>>> getClientArticleData(int id, int page);

    Observable<BaseResponse<Paging<ArticleResponse>>> getClientArticleByKeyword(int id, int page, String keyword);
}
