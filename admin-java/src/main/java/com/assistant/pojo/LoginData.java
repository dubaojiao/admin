package com.assistant.pojo;

import com.assistant.util.CheckUtil;

/**
 * @Title 登陆实体
 * @ClassName LoginData
 * @Author duke
 * @Date 2018/9/13
 */
public class LoginData {
    /**用户名*/
    private String phone;
    /**验证码*/
    private String code;

    private String appType;

    private String openId;
    // 1 客户 2  会员 3  会长
    private Integer userType;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public boolean  isNull(){
        if(this == null) return true;
        if(CheckUtil.stringIsNull(this.phone)) return true;
        if(CheckUtil.stringIsNull(this.code)) return true;
        return false;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
