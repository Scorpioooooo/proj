package com.coocaa.pro.manage.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class MainAction extends BasicAction {

    private final static Logger log = Logger.getLogger(MainAction.class);

    @Autowired(required = false)
    private SysAdminMgrService sysAdminMgrService;
    @Autowired(required = false)
    private AppStoreDictService appStoreDictService;
    @Autowired(required = false)
    private AppManagementService appManagementService;
    @Autowired(required = false)
    private AppStoreSortService appStoreSortService;
    @Autowired(required = false)
    private ShelvesRecordService shelvesRecordService;
    @Autowired(required = false)
    private AppManagerMongoService appManagerMongoService;
    @Autowired(required = false)
    private AppPlatformManagementService appPlatformManagementService;

    /**
     * 主介面
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "main")
    public ModelAndView viewMain(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        SysUserEntity userInfo = (SysUserEntity) HttpSessionUtils.getSessionValue(request, Constant.SESSION_LOGIN_USER);
        map.put("userInfo", userInfo);

        return RenderView(request, "main", map);
    }

    /**
     * <b>deskTopView</b><br/>
     * <p>
     * 桌面内容
     * </p>
     * <b>date：</b>2014-5-22 下午1:42:49<br/>
     * 
     * @param request
     * @return
     * 
     * @exception
     * @since 1.0
     */
    @RequestMapping(value = "desktop")
    public ModelAndView deskTopView(HttpServletRequest request) {
        String ret_str = null;
        String key = CacheKeyUtils.getCacheKey("MainAction", "deskTopView");
        ret_str =(String) MemcachedCMgr.getInstance().get(key);


        if(ret_str==null){
            Map<String, Object> map = new HashMap<String, Object>();

            // 应用数量统计
            QueryOperator typeQuery = new QueryOperator();
            WhereOperator typeWhere = typeQuery.getWhereOperator();
            typeWhere.addWhere(new QueryWhereBean(Operator.AND, "sortParentId", Compare.EQ, 0, AppStoreSortEntity.class));
            try {
                List<AppStoreSortEntity> storeSorts = appStoreSortService.queryByAll(typeQuery);
                List<MessageBean> typeMsgs = new ArrayList<MessageBean>();
                for (AppStoreSortEntity appStoreSortEntity : storeSorts) {
                    QueryOperator appMgrQuery = new QueryOperator();
                    WhereOperator appMgrWhere = new WhereOperator();
                    appMgrWhere.addWhere(new QueryWhereBean(Operator.AND, "sortParentId", Compare.LIKE, appStoreSortEntity.getSortId(), AppManagementEntity.class));
                    appMgrQuery.setWhereOperator(appMgrWhere);
                    Integer rowNum = appManagementService.queryByCount(appMgrQuery);

                    typeMsgs.add(new MessageBean(appStoreSortEntity.getSortName(), rowNum,"sortParentId", appStoreSortEntity.getSortId()));
                }
                QueryOperator appMgrQuery = new QueryOperator();
                WhereOperator appMgrWhere = new WhereOperator();
                appMgrWhere.addWhere(new QueryWhereBean(Operator.AND, "sortParentId", Compare.EQ, "", AppManagementEntity.class));
                appMgrWhere.addWhere(new QueryWhereBean(Operator.OR, "sortParentId", Compare.IS_NULL, null, AppManagementEntity.class));
                appMgrQuery.setWhereOperator(appMgrWhere);
                Integer rowNum = appManagementService.queryByCount(appMgrQuery);
                typeMsgs.add(new MessageBean("未分类数", rowNum,"sortParentId", ""));

                Integer totalNum = appManagementService.queryByCount(null);
                typeMsgs.add(new MessageBean("应用总计", totalNum,"sortParentId", ""));

                map.put("appTypes", typeMsgs);
            }
            catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


            //渠道应用数量统计
            try {
                List<String> channels =  new ArrayList<String>();
                channels = appPlatformManagementService.getPlatChannel();

                List<MessageBean> channelTypeMsgs = new ArrayList<MessageBean>();
                for (String channel : channels) {
                    Integer rowNum = null;
                    if(StringUtils.isNotBlank(channel)){
                        rowNum = appManagementService.queryAppByChannelCount(channel);
                        channelTypeMsgs.add(new MessageBean(channel, rowNum,"channel", channel));
                    }else{
                        rowNum = appManagementService.queryAppByNotChannelCount();
                        channelTypeMsgs.add(new MessageBean("未关联数", rowNum,"channel", channel));
                    }
                }

                map.put("channelTypeMsgs", channelTypeMsgs);

            } catch (Exception e1) {
                e1.printStackTrace();
            }



            // 统计应用设备支持情况
            QueryOperator query = new QueryOperator();
            WhereOperator where = query.getWhereOperator();
            where.addWhere(new QueryWhereBean(Operator.AND, "dictId", Compare.LIKE, "%" + AppStoreDictEnum.DictType.DD006 + "%", AppStoreDictEntity.class));
            where.addWhere(new QueryWhereBean(Operator.AND, "codeStatus", Compare.EQ, 1, AppStoreDictEntity.class));
            query.setWhereOperator(where);
            List<AppStoreDictEntity> dicts = new ArrayList<AppStoreDictEntity>();
            try {
                dicts = appStoreDictService.queryByAll(query);
                List<MessageBean> deviceMsgs = new ArrayList<MessageBean>();
                for (AppStoreDictEntity appStoreDictEntity : dicts) {
                    QueryOperator appMgrQuery = new QueryOperator();
                    WhereOperator appMgrWhere = new WhereOperator();
                    appMgrWhere.addWhere(new QueryWhereBean(Operator.AND, "deviceName", Compare.LIKE, "%" + appStoreDictEntity.getDictId() + "%", AppManagementEntity.class));
                    appMgrQuery.setWhereOperator(appMgrWhere);
                    Integer rowNum = appManagementService.queryByCount(appMgrQuery);

                    MessageBean messageBean = new MessageBean();
                    messageBean.setKey(appStoreDictEntity.getDictValue());
                    messageBean.setValue(rowNum);
                    messageBean.setField("deviceName");
                    messageBean.setFieldValue("*" + appStoreDictEntity.getDictId() + "*");
                    deviceMsgs.add(messageBean);
                }
                QueryOperator appMgrQuery = new QueryOperator();
                WhereOperator appMgrWhere = new WhereOperator();
                appMgrWhere.addWhere(new QueryWhereBean(Operator.AND, "deviceName", Compare.EQ, "", AppManagementEntity.class));
                appMgrWhere.addWhere(new QueryWhereBean(Operator.OR, "deviceName", Compare.IS_NULL, null, AppManagementEntity.class));
                appMgrQuery.setWhereOperator(appMgrWhere);
                Integer rowNum = appManagementService.queryByCount(appMgrQuery);
                MessageBean messageBean = new MessageBean();
                messageBean.setKey("未支持数");
                messageBean.setValue(rowNum);
                messageBean.setField("deviceName");
                messageBean.setFieldValue("");
                deviceMsgs.add(messageBean);

                map.put("deviceInfos", deviceMsgs);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                // 周应用更新统计
                List<MessageBean> weeksAppUpdateStatis = new ArrayList<MessageBean>();
                Date currentDate = new Date();
                Date startDate = DateUtils.getFirstDayOfWeek(currentDate);
                Date endDate = DateUtils.getLastDayOfWeek(currentDate);

                // 统计新接入应用
                int newAccessAppCount = appManagerMongoService.findNewAccessApp(startDate, endDate);
                MessageBean newAccessAppMessageBean = new MessageBean();
                newAccessAppMessageBean.setKey("新接入应用");
                newAccessAppMessageBean.setValue(newAccessAppCount);
                weeksAppUpdateStatis.add(newAccessAppMessageBean);
                // 统计接入应用审核
                int accessAppCheckCount = appManagerMongoService.findAccessAppCheck(startDate, endDate);
                MessageBean accessAppCheckMessageBean = new MessageBean();
                accessAppCheckMessageBean.setKey("接入应用审核");
                accessAppCheckMessageBean.setValue(accessAppCheckCount);
                weeksAppUpdateStatis.add(accessAppCheckMessageBean);
                // 统计上架数据
                QueryOperator shelvesRecordQuery = new QueryOperator();
                WhereOperator shelvesRecordWhere = new WhereOperator();

                shelvesRecordWhere.addWhere(new QueryWhereBean(Operator.AND, "createrTime", Compare.GET, startDate, ShelvesRecordEntity.class));
                shelvesRecordWhere.addWhere(new QueryWhereBean(Operator.AND, "createrTime", Compare.LT, endDate, ShelvesRecordEntity.class));
                shelvesRecordWhere.addWhere(new QueryWhereBean(Operator.AND, "shelvesType", Compare.EQ, 1, ShelvesRecordEntity.class));
                shelvesRecordQuery.setWhereOperator(shelvesRecordWhere);
                int shelvesCount = shelvesRecordService.queryByCount(shelvesRecordQuery);
                MessageBean shelvesMessageBean = new MessageBean();
                shelvesMessageBean.setKey("本周上架");
                shelvesMessageBean.setValue(shelvesCount);
                weeksAppUpdateStatis.add(shelvesMessageBean);

                // 统计下架数据
                QueryOperator offShelvesRecordQuery = new QueryOperator();
                WhereOperator offShelvesRecordWhere = new WhereOperator();
                offShelvesRecordWhere.addWhere(new QueryWhereBean(Operator.AND, "createrTime", Compare.GET, startDate, ShelvesRecordEntity.class));
                offShelvesRecordWhere.addWhere(new QueryWhereBean(Operator.AND, "createrTime", Compare.LT, endDate, ShelvesRecordEntity.class));
                offShelvesRecordWhere.addWhere(new QueryWhereBean(Operator.AND, "shelvesType", Compare.EQ, 0, ShelvesRecordEntity.class));
                offShelvesRecordQuery.setWhereOperator(offShelvesRecordWhere);
                int offShelvesCount = shelvesRecordService.queryByCount(offShelvesRecordQuery);
                MessageBean offShelvesMessageBean = new MessageBean();
                offShelvesMessageBean.setKey("本周下架");
                offShelvesMessageBean.setValue(offShelvesCount);
                weeksAppUpdateStatis.add(offShelvesMessageBean);

                map.put("weeksAppUpdateStatis", weeksAppUpdateStatis);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            ret_str = renderToJson(map);
            // 缓存1天
            MemcachedCMgr.getInstance().set(key, CacheEnum.CacheTimeEnum.CACHE_EXP_D.key, ret_str);

        }



        return RenderView(request, "desktop", JsonUtils.json2MapObject(ret_str));
    }

    /**
     * 获取系统菜单
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMenus", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMenus(HttpServletRequest request) {
        String menus = "[]";
        try {
            SysUserEntity userInfo = (SysUserEntity) HttpSessionUtils.getSessionValue(request, Constant.SESSION_LOGIN_USER);
            menus = sysAdminMgrService.getUserMenusByUser(userInfo);
        }
        catch (Exception e) {
            log.error("获取系统菜单失败");
        }
        return menus;
    }

    /**
     * 获取中文拼音首字母
     * 
     * @param request
     * @param appName
     * @return
     */
    @RequestMapping(value = "/getStrPinYin", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getStrPinYin(HttpServletRequest request, String appName) {
        Map<String, Object> map = new HashMap<String, Object>();
        String result = PinyinUtil.getPinYinHeadChar(appName);
        map.put("msg", StringUtils.swapCase(result));
        return renderToJson(map);
    }
}
