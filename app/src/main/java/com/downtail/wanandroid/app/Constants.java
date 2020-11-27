package com.downtail.wanandroid.app;

public class Constants {

    //-----------------------------------------------配置模块-----------------------------------------------
    //BASE_URL
    public static final String BASE_URL = "https://www.wanandroid.com/";
    //SQLiteDatabase
    public static final String DB_NAME = "WanAndroid.db";
    //SharedPreferences
    public static final String SHARED_PREFERENCES_NAME = "config";
    //登陆状态
    public static final String LOGIN_STATUS = "isLogin";
    //用户信息
    public static final String USER_INFO = "userInfo";

    //-----------------------------------------------常量模块-----------------------------------------------
    //轮播图
    public static final String BANNER_URL = "banner/json";
    //置顶文章
    public static final String ADVANCED = "article/top/json";
    //首页文章
    public static final String HOME_ARTICLE = "article/list/{page}/json";
    //体系
    public static final String SYSTEM_COLUMN = "tree/json";
    //导航
    public static final String NAVIGATION_COLUMN = "navi/json";
    //项目
    public static final String PROJECT_COLUMN = "project/tree/json";
    //公众号
    public static final String SERVICE_COLUMN = "wxarticle/chapters/json ";
    //登陆
    public static final String LOGIN = "user/login";
    //注册
    public static final String REGISTER = "user/register";
    //退出
    public static final String LOGOUT = "user/logout/json";
    //项目文章
    public static final String PROJECT_ARTICLE = "project/list/{page}/json";
    //收藏站内文章
    public static final String COLLECT_INNER = "lg/collect/{id}/json";
    //取消收藏站内文章
    public static final String CANCEL_INNER = "lg/uncollect_originId/{id}/json";
    //站内收藏
    public static final String INNER_COLLECT = "lg/collect/list/{page}/json";
    //收藏网址
    public static final String COLLECT_WEBSITE = "lg/collect/addtool/json";
    //网址收藏列表
    public static final String OUTER_COLLECT = "lg/collect/usertools/json";
    //修改网址
    public static final String MODIFY_WEBSITE = "lg/collect/updatetool/json";
    //删除网址收藏
    public static final String CANCEL_WEBSITE = "lg/collect/deletetool/json";
    //积分排行
    public static final String RANK_LIST = "coin/rank/{page}/json";
    //个人积分
    public static final String RANK = "lg/coin/userinfo/json";
    //积分记录
    public static final String RECORD_LIST = "lg/coin/list/{page}/json";
    //公众号文章
    public static final String CLIENT_ARTICLE = "wxarticle/list/{id}/{page}/json";
    //公众号关键字文章
    public static final String CLIENT_KEYWORD = "wxarticle/list/{id}/{page}/json?k=Java";
    //热词
    public static final String HOT_KEY = "hotkey/json";
    //搜索
    public static final String SEARCH = "article/query/{page}/json";
    //常用网站
    public static final String COMMON="friend/json";
}
