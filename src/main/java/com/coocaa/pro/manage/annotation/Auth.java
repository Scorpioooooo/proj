package com.coocaa.pro.manage.annotation;


import com.coocaa.pro.manage.common.AuthEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface Auth {
    /**
     * 是否验证登陆 true=验证 ,false = 不验证
     *
     * @return
     */
    boolean verifyLogin() default true;

    /**
     * 是否验证权限，默认为false
     *
     * @return
     */
    boolean verifyAuthority() default false;

    /**
     * 验证权限的类型
     *
     * @return
     */
    AuthEnum.AuthorityEnum authorityType() default AuthEnum.AuthorityEnum.BROWSER;

}
