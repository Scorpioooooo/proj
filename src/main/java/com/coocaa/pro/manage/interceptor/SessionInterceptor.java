package com.coocaa.pro.manage.interceptor;


import com.coocaa.fire.utils.HttpSessionUtils;
import com.coocaa.fire.utils.JsonUtils;
import com.coocaa.pro.manage.annotation.Auth;
import com.coocaa.pro.manage.annotation.Config;
import com.coocaa.pro.manage.common.Constant;
import com.coocaa.pro.manage.entity.SysRunEntity;
import com.coocaa.pro.manage.entity.SysUserEntity;
import com.coocaa.pro.manage.service.sys.SysAdminMgrService;
import com.coocaa.pro.manage.service.sys.SysRunService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * 拦截器，拿来做鉴权
 * */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = Logger.getLogger(SessionInterceptor.class);

    @Autowired(required = false)
    private SysRunService sysRunService;
    @Autowired(required = false)
    private SysAdminMgrService sysAdminMgrService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
            //notVerified(request, response, "非法请求");
            //return false;
        }
        HandlerMethod method = (HandlerMethod) handler;
        Auth auth = method.getMethod().getAnnotation(Auth.class);
        //判断是否需要登录验证
        if (auth == null || auth.verifyLogin()) {
            SysUserEntity loginUser = (SysUserEntity) HttpSessionUtils.getSessionValue(request, Constant.SESSION_LOGIN_USER);
            //判断用户是否登录
            if (null == loginUser) {
                notVerified(request, response, "登录超时，请重新登录。");
                return false;
            }
            //用户操作权限验证
            if (auth != null && auth.verifyAuthority()) {
                if (loginUser.getSuperAdmin() == 0) {//非超级管理员需验证操作权限
                    Config config = method.getMethod().getDeclaringClass().getAnnotation(Config.class);
                    Boolean beFilter = false;
                    if (config != null) {
                        Integer menuId = sysAdminMgrService.getMenuIdByElid(config.menuElId());
                        List<SysRunEntity> sysRuns = sysRunService.queryMenuRunsByMenuId(menuId, loginUser.getRoles(), false);
                        String runId = auth.authorityType().toString().toLowerCase();
                        for (SysRunEntity sysRunEntity : sysRuns) {
                            if (StringUtils.containsIgnoreCase(runId, sysRunEntity.getRunComm())) {
                                beFilter = true;
                                break;
                            }
                        }
                    }
                    if (!beFilter) {
                        notVerified(request, response, "没有操作权限");
                        return false;
                    }
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    @SuppressWarnings("static-access")
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
//		if(ex != null){
//			String msg = "";
//			if (ex instanceof OriginException) {
//				msg = ex.getMessage();
//			}else if (ex instanceof NullPointerException) {
//				msg = "空指针异常";
//			}else if (ex instanceof IOException) {
//				msg = "文件读写异常";
//			}else {
//				msg = ex.toString();
//			}
//			logger(request, handler, ex);
//			response.setStatus(response.SC_SERVICE_UNAVAILABLE);
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("success", false);
//			result.put("msg", msg);
//			// 传统的登录页面
//			response.setContentType("application/json");
//			Gson gson = new Gson();
//
//			//设置页面不缓存
//			response.setHeader("Pragma", "No-cache");
//			response.setHeader("Cache-Control", "no-cache");
//			response.setCharacterEncoding("UTF-8");
//			PrintWriter out= null;
//			out = response.getWriter();
//			out.print(gson.toJson(result));
//			out.flush();
//			out.close();
//		}else{
//			super.afterCompletion(request, response, handler, ex);
//		}
//        response.setHeader("Access-Control-Allow-Credentials","true");
//        //response.setHeader("Access-Control-Allow-Origin","http://localhost:8082");
//        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");


        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 记录日志
     *
     * @param request
     * @param handler
     * @param ex
     */
    @SuppressWarnings("unchecked")
    public void logger(HttpServletRequest request, Object handler, Exception ex) {
        StringBuffer msg = new StringBuffer();
        msg.append("异常拦截日志");
        msg.append("[uri＝").append(request.getRequestURI()).append("]");
        Enumeration<String> enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String name = (String) enumer.nextElement();
            String[] values = request.getParameterValues(name);
            msg.append("[").append(name).append("=");
            if (values != null) {
                int i = 0;
                for (String value : values) {
                    i++;
                    msg.append(value);
                    if (i < values.length) {
                        msg.append("｜");
                    }

                }
            }
            msg.append("]");
        }
        log.error(msg, ex);
    }

    private void notVerified(HttpServletRequest request, HttpServletResponse response, String msg) throws Exception {
        //ajax请求
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", false);
        result.put("msg", msg);
        // 传统的登录页面
        response.setContentType("application/json");
        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(JsonUtils.obj2Json(result));
        out.flush();
        out.close();
    }
}
