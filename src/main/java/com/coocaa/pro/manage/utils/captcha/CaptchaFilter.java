package com.coocaa.pro.manage.utils.captcha;

import com.coocaa.fire.utils.HttpSessionUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CaptchaFilter implements Filter {


    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        //获取验证码
        if (servletPath.matches("/captcha.jpg")) {
            response.setContentType("image/jpeg");
            //禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            //参数：宽、高、字符数、干扰量
            CaptchaProductor vCode = new CaptchaProductor(64, 22, 4, 220);


            //根据token保存验证码内容
            CaptchaBean bean = new CaptchaBean();
            bean.setCaptcha(vCode.getCode());
            bean.setCreateTime(new Date());
            HttpSessionUtils.setSessionValue(request, "sessionCaptcha", bean);
            vCode.write(response.getOutputStream());
            return;
        }
    }
}