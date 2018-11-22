package com.coocaa.pro.manage.common;

/**
 * 鉴权枚举
 * @author Administrator
 *
 */
public class AuthEnum {
	/**
 	 * 操作枚举
 	 */
 	public static enum AuthorityEnum {
 		BROWSER(1, "查询"), ADD(2,"增加"),EDIT(3,"修改"),DELETE(4,"删除"),SAVE(5,"保存"),AUDIT(10,"审核"),SHELVES(11,"上架"),SOLDOUT(12,"下架"),SYNCHRON(13,"同步");
		public int key;
		public String value;
		private AuthorityEnum(int key, String value) {
			this.key = key;
			this.value = value;
		}
		public static AuthorityEnum get(int key) {
			AuthorityEnum[] values = AuthorityEnum.values();
			for (AuthorityEnum object : values) {
				if (object.key == key) {
					return object;
				}
			}
			return null;
		}
		public static String getName(int key) {
			AuthorityEnum[] values = AuthorityEnum.values();
			for (AuthorityEnum object : values) {
				if (object.key == key) {
					return object.value;
				}
			}
			return null;
		}
	}
}
