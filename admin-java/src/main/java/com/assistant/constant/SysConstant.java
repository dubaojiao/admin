package com.assistant.constant;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title 系统 常量
 * @ClassName SysConstant
 * @Author duke
 * @Date 2018/9/13
 */
public class SysConstant {
    /**
     * web
     */
    public final static  String WEB = "/web/";
    /**
     * web
     */
    public final static  String WX = "/wx/";
    /**
     * 回调
     */
    public final static  String CALLBACK = "/callback/";
    /**
     * app
     */
    public final static  String APP = "/app/";

    public final static  String ERROR_500 = "服务器内部错误";

    public final static  String ERROR_PARAM = "请求参数有误";

    public final static String SUCCESS = "成功";

    public final static String C_SUCCESS = "操作成功";

    public final static String C_FAILURE = "操作失败";

    public final static String PHONE_NO_CODE = "请点击获取验证码";

    public final static String CODE_ERROR = "验证码输入错误";

    public final static String GO_TO_REGISTER = "请前往注册";
    //首次启动
    public final static String FIRST_BOOT = "NO";


    public  static List<Map<String,Object>> citys = new ArrayList<>();

    public  static List<String> timeInterval = new ArrayList<>();

    public  static List<Map<String,Object>> level = new ArrayList<>();

    static {
        if(citys == null){
            citys = new ArrayList<>();
        }
        Map<String,Object> cityMap1 = new HashMap<>();
        List<String> areas = new ArrayList<>();
        areas.add("盘龙区");
        areas.add("五华区");
        areas.add("西山区");
        areas.add("官渡区");
        areas.add("东川区");
        areas.add("普宁区");
        areas.add("呈贡区");
        areas.add("富民县");
        areas.add("嵩明县");
        areas.add("宜良县");
        areas.add("石林彝族自治县");
        areas.add("寻甸回族彝族自治县");
        areas.add("禄劝彝族苗族自治县");
        areas.add("安宁市");

        cityMap1.put("昆明",areas);

        citys.add(cityMap1);

        if(timeInterval == null){
            timeInterval = new ArrayList<>();
        }
        timeInterval.add("11:00-12:59");
        timeInterval.add("13:00-14:59");
        timeInterval.add("15:00-16:59");
        timeInterval.add("17:00-18:59");
        timeInterval.add("19:00-20:59");


        if(level == null){
            level = new ArrayList<>();
        }
        Map<String,Object> levelMap1 = new HashMap<>();
        levelMap1.put("id","1");
        levelMap1.put("name","普通会员");
        levelMap1.put("amount","9999");
        levelMap1.put("price","暂不开放请勿购买");
        level.add(levelMap1);

        Map<String,Object> levelMap2 = new HashMap<>();
        levelMap2.put("id","2");
        levelMap2.put("name","VIP会员");
        levelMap2.put("amount","298");
        levelMap2.put("price","688");
        level.add(levelMap2);
    }



}
