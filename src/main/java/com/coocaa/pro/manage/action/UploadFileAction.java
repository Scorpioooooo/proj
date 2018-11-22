package com.coocaa.pro.manage.action;

import com.coocaa.admin.utils.ApkUtil;
import com.coocaa.admin.utils.CheckApks;
import com.coocaa.admin.utils.UploadifyUtils;
import com.coocaa.core.utils.Md5FileUtil;
import com.coocaa.core.utils.PinyinUtil;
import com.coocaa.fire.utils.PropertiesUtil;
import com.coocaa.fire.utils.exception.OriginException;
import com.coocaa.pro.manage.annotation.Auth;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadFileAction extends BasicAction {

	/** 根目录 **/
	final static String UPLOAD_ROOT;
	/** URL前缀 **/
	final static String UPLOAD_URL;

	/** URL前缀 **/
	final static String UPLOAD_PATH;

	/** URL前缀 **/
	final static String APP_FILE_PATH;

	/** URL前缀 **/
	final static String IMG_FILE_PATH;

	/** URL前缀 **/
	final static String APK_FILE_PATH;

	static {
		PropertiesUtil propertiesUtil = new PropertiesUtil();
		UPLOAD_ROOT = propertiesUtil.getString("upload.root");
		UPLOAD_URL = propertiesUtil.getString("upload.url");
		UPLOAD_PATH = propertiesUtil.getString("upload.path");
		APP_FILE_PATH= propertiesUtil.getString("cdn_file_url");
		IMG_FILE_PATH=propertiesUtil.getString("cdn_img_url");
		APK_FILE_PATH=propertiesUtil.getString("cdn_apk_url");
	}

	@Auth(verifyLogin = false)
	@RequestMapping(value = "/uploader", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadFile(HttpServletRequest request) {
		Boolean resizeImage = Boolean.valueOf(request.getParameter("resizeImage"));
		Map<String, Object> result = new HashMap<String, Object>();
		UploadifyUtils utils;
		try {
			utils = new UploadifyUtils(request, resizeImage);
			String newName = "/" + utils.getNewName(); // 文件名
			result.put("success", true);
			result.put("newName", newName);
			result.put("fileName", newName);
			result.put("url", UPLOAD_URL + newName);
			//扩展参数
			String extra = request.getParameter("extra");
			result.put("extra", extra == null ? "" : extra);
			result.putAll(utils.getResultMap());
		}
		catch (OriginException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getErrMeassage());
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "上传文件失败");
		}
		System.out.println("Result is "+renderToJson(result));
		return renderToJson(result);
	}

	/**
	 * APK文件上传
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Auth(verifyLogin = false)
	@RequestMapping(value = "/uploaderApkFile", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadApkFile(HttpServletRequest request) throws Exception {
		String newName = "";
		Map<String, Object> result = new HashMap<String, Object>();
		UploadifyUtils utils;
		try{
			utils= new UploadifyUtils(request,"apks",true);
			newName = "/" + utils.getNewName(); // 文件名
			result.put("success", true);
			result.put("newName", newName);
			result.put("fileName", newName);
			result.put("url", UPLOAD_URL + newName);

			ApkUtil apkUtil = new ApkUtil();
			String file_path = UPLOAD_PATH + utils.getFileTypePath() + newName;
			apkUtil =  CheckApks.analysisApks(file_path);
			File appFile=new File(file_path);
			String app_file_md5=Md5FileUtil.getMD5(appFile);
			// [0]:版本名称;[1]版本号;[2]包名;[3]要求安卓版本号
			//String[] apkInfo = AnalysisApk.unZip(UPLOAD_PATH+newName, "");
			result.put("appName",apkUtil.getApplicationLabel());
			result.put("appEngName", PinyinUtil.getPingYin(apkUtil.getApplicationLabel()));
			result.put("appVersionName", apkUtil.getVersionName());
			result.put("appVersionNo",apkUtil.getVersion());
			result.put("appPackageName",apkUtil.getPackName());
			result.put("androidVersion", apkUtil.getSdkversion());
			result.put("appFileSize", utils.getFileSize());
			result.put("appIcon", apkUtil.getIcons());
			result.put("appIconUrl", UPLOAD_URL + apkUtil.getIcons());
			result.put("isRootAuth", utils.isRootAuth());
			result.put("MD5",app_file_md5);
			/*if(utils.isRootAuth()){
				result.put("msg", "本平台不允许上传有系统权限的应用");
			}*/
		}
		catch (OriginException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getErrMeassage());
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "上传文件失败");
		}

		System.out.println("UPLOAD APK INfo:" + renderToJson(result));
		return renderToJson(result);
	}


	@Auth(verifyLogin = false)
	@RequestMapping(value = "/uploaderKE", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadFileKindEditor(HttpServletRequest request) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String newName = "";
			UploadifyUtils utils = new UploadifyUtils(request,false);
			newName = utils.getNewName(); // 文件名

			result.put("error", 0);
			result.put("url", UPLOAD_URL + newName);
		} catch (Exception e) {
			result.put("error", 0);
			result.put("message", "上传文件异常！");
		}
		return renderToJson(result);
	}

	@Auth(verifyLogin = false)
	@RequestMapping(value = "/uploaderPic", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploaderPic(HttpServletRequest request, String thumbnail) throws Exception {
		String newName = "";
		UploadifyUtils utils = new UploadifyUtils(request, thumbnail);
		newName = utils.getNewName(); // 文件名

		Map<String, Object> result = utils.getResultMap();
		result.put("success", true);
		result.put("newName", newName);
		result.put("url", UPLOAD_URL + newName);
		return renderToJson(result);
	}

	/**
	 *
	 * Description:上传so文件
	 * @param
	 * @return
	 * @throws Exception
	 * @author YJF
	 */
	@Auth(verifyLogin = false)
	@RequestMapping(value = "/uploadSoFile", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadSoFile(HttpServletRequest request) throws Exception {
		String so_md5=null;
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			UploadifyUtils utils = new UploadifyUtils(request,"so",true);
			String soFilePath=utils.getNewName();
			String soPath=UPLOAD_PATH + "/so/" + utils.getNewName();
			result.put("url", "/"+soFilePath);
			File soFile=new File(soPath);
			so_md5=Md5FileUtil.getMD5(soFile);

			result.put("DM5",so_md5);
			result.put("success", true);
			result.put("fileSize", utils.getFileSize());
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "上传文件异常！");
		}

		System.out.println("UPLOAD SO INfo:" + renderToJson(result));
		return renderToJson(result);
	}

	/**
	 *
	 * Description:上传视频文件
	 * @param
	 * @return
	 * @throws Exception
	 * @author YJF
	 */
	@Auth(verifyLogin = false)
	@RequestMapping(value = "/uploadVideo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadVideo(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			UploadifyUtils utils = new UploadifyUtils(request,true);
			String filePath=utils.getNewName();
			result.put("filePath", filePath);
			result.put("success", true);
			result.put("fileSize", utils.getFileSize());
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "上传文件异常！");
		}
		System.out.println("result is "+renderToJson(result));
		return renderToJson(result);
	}


	/**
	 *
	 * Description:上传文件
	 * @param
	 * @return
	 * @throws Exception
	 * @author YJF
	 */
	@Auth(verifyLogin = false)
	@RequestMapping(value = "/uploadAppFile", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadAppFile(HttpServletRequest request) throws Exception {
		String app_file_md5=null;
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			UploadifyUtils utils = new UploadifyUtils(request);
			String appFilePath=utils.getNewName();
			String extName=utils.getExtName();
			String appPath="";
			String url="";
			String fileCode="";

			appPath=UPLOAD_PATH +"/apk/doc/"+ appFilePath;
			url=APP_FILE_PATH+appFilePath;

			String fileName=utils.getFileName();
			File appFile=new File(appPath);
			app_file_md5=Md5FileUtil.getMD5(appFile);
			fileCode=extName.replace(".", "").toUpperCase();
			Long code=new Date().getTime();
			fileCode=fileCode+code.toString();

			result.put("fileCode", fileCode);
			result.put("link", appFilePath);
			result.put("url", url);
			result.put("fileName", fileName);
			result.put("extName", extName);
			result.put("MD5",app_file_md5);
			result.put("fileSize", utils.getFileSize());
			result.put("success", true);
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "上传文件异常！");
		}
		System.out.println("UPLOAD SO INfo:" + renderToJson(result));
		return renderToJson(result);
	}

	/**
	 * 上传应用大图标（用于游戏中心）
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 * @return String
	 * @exception
	 */
	@Auth(verifyLogin = false)
	@RequestMapping(value = "/uploadAppBigIcon", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadAppBigIcon(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		UploadifyUtils utils;
		try {
			utils = new UploadifyUtils(request, false);
			String newName = "/" + utils.getNewName(); // 文件名
			result.put("success", true);
			result.put("newName", newName);
			result.put("fileName", newName);
			result.put("url", UPLOAD_URL + newName);
			//扩展参数
			String extra = request.getParameter("extra");
			result.put("extra", extra == null ? "" : extra);
			result.putAll(utils.getResultMap());
		}
		catch (OriginException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getErrMeassage());
		}
		catch (IOException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "上传文件失败");
		}
		System.out.println("Result is "+renderToJson(result));
		return renderToJson(result);
	}


	/**
	 * 上传壁纸
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 * @return String
	 * @exception
	 */
	@Auth(verifyLogin = false)
	@RequestMapping(value = "/uploadWallpaper", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadWallpaper(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String str_is_category = request.getParameter("isCategory");
		int isCategory = str_is_category!=null? NumberUtils.toInt(str_is_category):1;
		UploadifyUtils utils;
		try {
			utils = new UploadifyUtils(request, isCategory);
			String newName = "/" + utils.getNewName(); // 文件名
			result.put("success", true);
			result.put("newName", newName);
			result.put("fileName", newName);
			result.put("fileSize", utils.getFileSize());
			result.put("url", UPLOAD_URL + newName);
			result.putAll(utils.getResultMap());
		}
		catch (OriginException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getErrMeassage());
		}
		catch (IOException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "上传文件失败");
		}
		System.out.println("Result is "+renderToJson(result));
		return renderToJson(result);
	}
}
