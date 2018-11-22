package com.coocaa.pro.manage.common;


import com.coocaa.fire.utils.PropertiesUtil;

public class Constant {
	//获取配置文件
	private static PropertiesUtil propertiesUtils;
	
	static{
		propertiesUtils = new PropertiesUtil();
	}
	
	/** 用户上一个URL **/
	public final static String COOKIE_URL = "prev_url";
	/** 验证码 **/
	public final static String SESSION_CAPTCHA = "sessionCaptcha";
	/** 登录用户 **/
	public final static String SESSION_LOGIN_USER = "session_login_user";
	
	/** 根目录 **/
	public final String UPLOAD_ROOT = propertiesUtils.getString("upload.root");
	/** URL前缀 **/
	public final String UPLOAD_URL = propertiesUtils.getString("upload.url");
	/** 上传文件路径 **/
	public final String UPLOAD_PATH = propertiesUtils.getString("upload.path");
	/** 上传图片大小 **/
	public final String UPLOAD_PIC_SIZE = propertiesUtils.getString("upload.pic.size");


	/**
	 * devListCheck需要调用到的接口地址
	 */
	public static String DEVICEINFOURL ="http://movie.tc.skysrt.com/v1/getDeviceInfo";

	public static String POLICYINFO = "http://movie.tc.skysrt.com/v1/getPolicy";

	public static String VOOLEMAINPAGE = "http://sky.tvos.skysrt.com/Framework/tvos/index.php";

	public static String IQIYIMAINPAGE = "http://movie.tc.skysrt.com/Framework/tvos/index.php";

	public static String IQIYINEWMAINPAGE = "http://movie.tc.skysrt.com/v1/getPanel";

	public static String TXMAINPAGE = "http://movie.tc.skysrt.com/Framework/tvos/index.php";

	/**
	 * 影视接口地址
	 */
//	public static String CACHE_CLEAR_URL ="http://movie.tc.skysrt.com/cache/clear";

	/**
	 * 审核权限编号
	 */
	public static Integer AUDIT = 29;
}
