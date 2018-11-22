package com.coocaa.pro.manage.exception;

public class ErrorEnum {

    /**  
     * <p>define error infos</p> 
     * <b>date：</b>2014-4-28 上午9:03:28<br/> 
     *
     */
    int     errorcode;
    String  errormsg;
    public ErrorEnum(int code, String msg){
        this.errorcode = code;
        this.errormsg  = msg;
    }
    static ErrorEnum NOERROR                =  new ErrorEnum(0, "功能正常");
    static ErrorEnum UNCORECTSDKVERSION     =  new ErrorEnum(1, "SDK版本不兼容");
    static ErrorEnum UNSUITALBCORE          =  new ErrorEnum(2, "内核不兼容");
    static ErrorEnum UNSUITALBSCREENSIZE    =  new ErrorEnum(3, "屏幕尺寸不兼容");
    static ErrorEnum UNSUITALBLANGUAGE      =  new ErrorEnum(4, "语言不兼容");
    static ErrorEnum UNSUITABLEDES          =  new ErrorEnum(5, "Densities 不兼容");
    static ErrorEnum EMPTYPAK               =  new ErrorEnum(6, "非ANDROID应用");

    static int ERRORAPK = 99;
    static  int TOCHECK = 1;
    static int FIRSTTESTOKAPK =9;
    static int CORRECTAPK =10;
    
}
