package com.coocaa.pro.manage.action;

import com.coocaa.fire.utils.JsonUtils;
import com.coocaa.fire.utils.UrlsUtil;
import com.coocaa.fire.utils.Utils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BasicAction {

    public ModelAndView RenderView(HttpServletRequest request, String viewName, Map<String, Object> map) {
        map.put("urls", UrlsUtil.getUrls());
        map.put("basePath", Utils.getBasePath(request));
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        mav.addAllObjects(map);
        return mav;
    }

    public ModelAndView RenderView(HttpServletRequest request, String viewName) {
        Map<String, Object> map = new HashMap<String, Object>();
        return RenderView(request, viewName, map);
    }

    public ModelAndView RenderRedirect(HttpServletRequest request, String viewName) {

        return new ModelAndView("redirect:" + viewName);
    }

    public String renderToJson(Map<String, Object> map) {

        return JsonUtils.obj2Json(map);
    }

    public String renderToJson(Object obj) {

        return JsonUtils.obj2Json(obj);
    }

}
