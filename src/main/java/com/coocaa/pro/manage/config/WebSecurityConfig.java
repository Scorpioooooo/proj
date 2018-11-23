package com.coocaa.pro.manage.config;

import com.coocaa.pro.manage.common.ResultMessage;
import com.coocaa.pro.manage.service.sys.SysUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sang on 2017/12/28.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
//TODO  change security set
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SysUserService userService;
    @Autowired
    CustomMetadataSource metadataSource;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    AuthenticationAccessDeniedHandler deniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**", "/login_p");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(metadataSource);
                o.setAccessDecisionManager(urlAccessDecisionManager);
                return o;
            }
        }).and().formLogin().loginPage("/login_p").loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password").failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException {
                resp.setContentType("application/json;charset=utf-8");
                String respBean = "";
                if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
                    respBean = ResultMessage.fail("账户名或者密码输入错误!");
                } else if (e instanceof LockedException) {
                    respBean = ResultMessage.fail("账户被锁定，请联系管理员!");
                } else if (e instanceof CredentialsExpiredException) {
                    respBean = ResultMessage.fail("密码过期，请联系管理员!");
                } else if (e instanceof AccountExpiredException) {
                    respBean = ResultMessage.fail("账户过期，请联系管理员!");
                } else if (e instanceof DisabledException) {
                    respBean = ResultMessage.fail("账户被禁用，请联系管理员!");
                } else {
                    respBean = ResultMessage.fail("登录失败!");
                }
                resp.setStatus(401);
                ObjectMapper om = new ObjectMapper();
                PrintWriter out = resp.getWriter();
                out.write(respBean);
                out.flush();
                out.close();
            }
        }).successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException {
                resp.setContentType("application/json;charset=utf-8");
                String respBean = ResultMessage.success("登录成功!");
                ObjectMapper om = new ObjectMapper();
                PrintWriter out = resp.getWriter();
//                out.write(om.writeValueAsString(respBean));
                out.write(respBean);
                out.flush();
                out.close();
            }
        }).permitAll().and().logout().permitAll().and().csrf().disable().exceptionHandling().accessDeniedHandler(deniedHandler);
    }
}