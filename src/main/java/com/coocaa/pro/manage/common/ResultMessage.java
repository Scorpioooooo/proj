package com.coocaa.pro.manage.common;

import com.coocaa.fire.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ResultMessage {
	
	/**
	 *
	 * 成功提示信息
	 *
	 * @param message
	 *
	 */
	public static String success(String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		if(StringUtils.isNotBlank(message)){
			result.put("msg", message);
		}else{
			result.put("msg", "操作成功！");
		}
		return JsonUtils.obj2Json(result);
	}
	
	/**
	 *
	 * 成功提示信息
	 *
	 * @param message
	 *
	 */
	public static void success(String message, Map<String, Object> map) {
		map.put("success", true);
		if(StringUtils.isNotBlank(message)){
			map.put("msg", message);
		}else{
			map.put("msg", "操作成功！");
		}
	}

	/**
	 *
	 * 失败提示信息
	 *
	 * @param message
	 *
	 */
	public static String fail(String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", false);
		if(StringUtils.isNotBlank(message)){
			result.put("msg", message);
		}else{
			result.put("msg", "操作失败！");
		}
		return JsonUtils.obj2Json(result);
	}

	/**
	 *
	 * 失败提示信息
	 *
	 * @param message
	 *
	 */
	public static void fail(String message, Map<String, Object> map) {
		map.put("success", false);
		if(StringUtils.isNotBlank(message)){
			map.put("msg", message);
		}else{
			map.put("msg", "操作失败！");
		}
	}

}
