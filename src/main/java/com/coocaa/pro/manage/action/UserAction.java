package com.coocaa.pro.manage.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coocaa.pro.manage.annotation.Auth;
import com.coocaa.pro.manage.common.AuthEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sang on 2017/12/29.
 */
@Controller
@RequestMapping("/users")
public class UserAction extends BasicAction {

    /**
     * 用户列表
     * @param curPg
     * @param pgSize
     * @param queryData
     * @param request
     * @return
     */
    @Auth(verifyAuthority = true, authorityType = AuthEnum.AuthorityEnum.BROWSER)
    @RequestMapping(value = "/onePg", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String onePg(HttpServletRequest request, @RequestParam String curPg,
                       @RequestParam String pgSize,
                       @RequestParam String queryData
    ) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", 11);

        JSONArray jsonArray = new JSONArray();
        JSONObject data = new JSONObject();
        data.put("ID", "1");
        data.put("name", "宁秋梅");
        data.put("userAccount", "ningqiumei");
        data.put("department", "端产品部");
        data.put("email", "ningqiumei@coocaa.com");
        data.put("IP", "192.168.1.2");
        data.put("forbidden", "否");
        data.put("loginCnt", "13");
        data.put("jobAbout", "软件开发");
        data.put("creatTime", "2018-09-08 20:12:00");
        data.put("lastUpdateTime", "2018-09-08 20:12:00");
        data.put("psInfo", "2018-09-08 20:12:00");
        jsonArray.add(data);

        data = new JSONObject();
        data.put("ID", "2");
        data.put("name", "谢仁斌");
        data.put("userAccount", "xierenbin");
        data.put("department", "端产品部");
        data.put("email", "xierenbin@coocaa.com");
        data.put("IP", "192.168.1.2");
        data.put("forbidden", "否");
        data.put("loginCnt", "13");
        data.put("jobAbout", "软件开发");
        data.put("creatTime", "2018-09-08 20:12:00");
        data.put("lastUpdateTime", "2018-09-08 20:12:00");
        data.put("psInfo", "2018-09-08 20:12:00");
        jsonArray.add(data);
        map.put("data", jsonArray);
        return renderToJson(map);
    }

}
