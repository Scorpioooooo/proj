package com.coocaa.pro.manage.action;

import com.coocaa.fire.utils.JsonUtils;

import java.util.Map;

public class BasicAction {

    public String renderToJson(Map<String, Object> map) {

        return JsonUtils.obj2Json(map);
    }

    public String renderToJson(Object obj) {

        return JsonUtils.obj2Json(obj);
    }


}
