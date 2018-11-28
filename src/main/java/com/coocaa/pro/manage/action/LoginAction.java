package com.coocaa.pro.manage.action;

import com.coocaa.fire.utils.DateUtils;
import com.coocaa.fire.utils.HttpSessionUtils;
import com.coocaa.fire.utils.MD5Encode;
import com.coocaa.fire.utils.Utils;
import com.coocaa.fire.utils.captcha.CaptchaBean;
import com.coocaa.pro.manage.annotation.Auth;
import com.coocaa.pro.manage.common.Constant;
import com.coocaa.pro.manage.common.ResultMessage;
import com.coocaa.pro.manage.entity.SysLogLoginEntity;
import com.coocaa.pro.manage.entity.SysUserEntity;
import com.coocaa.pro.manage.service.sys.SysAdminMgrService;
import com.coocaa.pro.manage.service.sys.SysLogLoginService;
import com.coocaa.pro.manage.service.sys.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginAction extends BasicAction {

    @Autowired(required = false)
    private SysAdminMgrService sysAdminMgrService;
    @Autowired(required = false)
    private SysUserService sysUserService;
    @Autowired(required = false)
    private SysLogLoginService sysLogLoginService;

    /**
     * 登陆视图
     *
     * @return
     */
    @Auth(verifyLogin = false)
    @RequestMapping(value = "index")
    public String loginView(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("path", "login");
        return renderToJson(map);
    }


    /**
     * 检查验证码
     *
     * @param request
     * @param code
     * @return
     */
    @Auth(verifyLogin = false)
    @RequestMapping(value = "checkCaptcha", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String checkCaptcha(HttpServletRequest request, @RequestParam("code") String code) {
        CaptchaBean bean = (CaptchaBean) HttpSessionUtils.getSessionValue(request, Constant.SESSION_CAPTCHA);
        String result;
        if (bean != null && code.equals(bean.getCaptcha())) {
            result = ResultMessage.success(null);
        } else {
            result = ResultMessage.fail(null);
            HttpSessionUtils.setSessionValue(request, Constant.SESSION_CAPTCHA, null);
        }

        return result;
    }

    /**
     * 会员登录
     *
     * @param request
     * @return
     */
    @Auth(verifyLogin = false)
    @RequestMapping(value = "userlogin", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String login(HttpServletRequest request) throws NoSuchAlgorithmException {

        Map<String, Object> map = new HashMap<String, Object>();
        String userName = request.getParameter("username");
        String pwd = request.getParameter("password");
        String verifyCode = request.getParameter("valicode");


//		CaptchaBean bean = (CaptchaBean)HttpSessionUtils.getSessionValue(request, Constant.SESSION_CAPTCHA);
//		if(bean == null || !verifyCode.equals(bean.getCaptcha())){
//			map.put("success", false);
//			map.put("msg", "验证码错误！");
//		}else{
        Boolean isOk = sysAdminMgrService.userLogin(request, userName, pwd);
        if (isOk ) {
            map.put("success", true);
            map.put("msg", "登录成功！");
        } else {
            map.put("success", false);
            map.put("msg", "用户名或密码错误！");
        }
        //}
        return renderToJson(map);
    }

    /**
     * 修改用户密码
     *
     * @param request
     * @param oldpwd
     * @param newpwd
     * @param newpwd2
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "update_pwd", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updatePassword(HttpServletRequest request, @RequestParam String oldpwd, @RequestParam String newpwd, @RequestParam String newpwd2) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(oldpwd)) {
            if (StringUtils.isNotBlank(newpwd) && newpwd.equals(newpwd2)) {
                SysUserEntity user = (SysUserEntity) HttpSessionUtils.getSessionValue(request, Constant.SESSION_LOGIN_USER);
                if (user.getLoginPwd().equals(MD5Encode.getMD5Str(MD5Encode.getMD5Str(oldpwd + user.getSalt())))) {
                    SysUserEntity entity = new SysUserEntity();
                    entity.setUserId(user.getUserId());
                    entity.setLoginPwd(MD5Encode.getMD5Str(MD5Encode.getMD5Str(newpwd + user.getSalt())));
                    entity.setDateModify(DateUtils.getCurDate());
                    sysUserService.updateBySelective(entity);
                    map.put("success", true);
                } else {
                    map.put("success", false);
                    map.put("msg", "旧密码输入不正确！");
                }
            } else {
                map.put("success", false);
                map.put("msg", "新密码输入错误！");
            }
        } else {
            map.put("success", false);
            map.put("msg", "旧密码不能为空！");
        }

        return renderToJson(map);
    }

    /**
     * 退出登陆
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SysUserEntity user = (SysUserEntity) HttpSessionUtils.getSessionValue(request, Constant.SESSION_LOGIN_USER);
        Date loginTime = DateUtils.getCurDate();
        String loginIp = Utils.getIpAddr(request);
        SysLogLoginEntity login = new SysLogLoginEntity();
        login.setUserName(user.getLoginName());
        login.setUserId(user.getUserId());
        login.setLoginTime(loginTime);
        login.setLoginIp(loginIp);
        login.setLoginType(1);
        login.setUserAgent(request.getHeader("user-agent"));
        login.setRemark("退出");
        try {
            sysLogLoginService.add(login);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpSessionUtils.getSession(request).invalidate();

        response.sendRedirect(request.getHeader("Referer"));
    }
}
