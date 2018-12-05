package com.coocaa.pro.manage.config;

import com.coocaa.pro.manage.common.DateConverter;
import com.coocaa.pro.manage.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sang on 2018/1/2.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**","/userlogin/");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
