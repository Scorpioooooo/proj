package com.coocaa.pro.manage.config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by sang on 2017/12/28. q
 */
@Component
public class CustomMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        return null;
    }

    //    @Autowired
//    MenuService menuService;
//    AntPathMatcher antPathMatcher = new AntPathMatcher();
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object o) {
//        String requestUrl = ((FilterInvocation) o).getRequestUrl();
//        List<Menu> allMenu = menuService.getAllMenu();
//        for (Menu menu : allMenu) {
//            if (antPathMatcher.match(menu.getUrl(), requestUrl)
//                    &&menu.getRoles().size()>0) {
//                List<Role> roles = menu.getRoles();
//                int size = roles.size();
//                String[] values = new String[size];
//                for (int i = 0; i < size; i++) {
//                    values[i] = roles.get(i).getName();
//                }
//                return SecurityConfig.createList(values);
//            }
//        }
//        //没有匹配上的资源，都是登录访问
//        return SecurityConfig.createList("ROLE_LOGIN");
//    }
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
