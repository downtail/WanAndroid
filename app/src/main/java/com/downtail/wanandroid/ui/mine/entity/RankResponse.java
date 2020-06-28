package com.downtail.wanandroid.ui.mine.entity;

public class RankResponse {


    /**
     * coinCount : 16112
     * level : 162
     * rank : 1
     * userId : 20382
     * username : g**eii
     */

    private int coinCount;
    private int level;
    private String rank;
    private int userId;
    private String username;
    private boolean showAnim = true;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isShowAnim() {
        return showAnim;
    }

    public void setShowAnim(boolean showAnim) {
        this.showAnim = showAnim;
    }
}
