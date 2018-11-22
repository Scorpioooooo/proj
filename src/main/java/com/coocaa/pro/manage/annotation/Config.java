package com.coocaa.pro.manage.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface Config {

	/**
	 * 菜单模块编号
	 * 当前类需要通过那个菜单ELID来验证其操作权限
	 * @return
	 */
	String[] menuElId() default "";
}
