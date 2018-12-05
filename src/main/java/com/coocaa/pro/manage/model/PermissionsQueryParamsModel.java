package com.coocaa.pro.manage.model;

/**
 * <p>类的详细说明</p>
 *
 * @author xuchen
 * @author 其他作者姓名
 * @version 1.00 2017/1/16 xuchen 创建
 * <p>
 * <p>1.01 YYYY/MM/DD 修改者姓名 修改内容说明</p>
 */
public class PermissionsQueryParamsModel extends BaseModel {
    private int openIdType = 1;         //openID类型 1：酷开openID   2:thirdOpenID
    private String openId = "";         //openID
    private int permissionsType = 1;    //权益类型  1：会员权益(默认)   2：影片权益
    private int sourceType = 1;         //来源类型  1：本地数据库(默认)  2：第三方接口
    private String sourceSign = "";     //源标识    全部：""(默认)  奇异果VIP：yinhe   鼎级剧场：7  影视VIP：6  腾讯体育:36
    private String movieId = "";        //影片ID

    public int getOpenIdType() {
        return openIdType;
    }

    public void setOpenIdType(int openIdType) {
        this.openIdType = openIdType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getPermissionsType() {
        return permissionsType;
    }

    public void setPermissionsType(int permissionsType) {
        this.permissionsType = permissionsType;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceSign() {
        return sourceSign;
    }

    public void setSourceSign(String sourceSign) {
        this.sourceSign = sourceSign;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "PermissionsQueryParamsModel{" +
                "openIdType=" + openIdType +
                ", openId='" + openId + '\'' +
                ", permissionsType=" + permissionsType +
                ", sourceType=" + sourceType +
                ", sourceSign='" + sourceSign + '\'' +
                ", movieId='" + movieId + '\'' +
                '}';
    }
}
