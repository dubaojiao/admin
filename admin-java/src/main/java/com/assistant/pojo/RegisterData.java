package com.assistant.pojo;

import com.assistant.util.CheckUtil;

import java.io.Serializable;

public class RegisterData implements Serializable {
    private String name;
    private String phone;
    private String code;
    private String invitationCode;
    private String pwd;
    private String appType;
    private String openId;
    // 1 客户  2  小姐
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

    public boolean isNull(){
        if(this== null) return true;
        if(CheckUtil.stringIsNull(this.name)) return true;
        if(CheckUtil.stringIsNull(this.phone)) return true;
        if(CheckUtil.stringIsNull(this.code)) return true;
        //if(CheckUtil.stringIsNull(this.invitationCode)) return true;
        if(CheckUtil.stringIsNull(this.pwd)) return true;
        if(CheckUtil.stringIsNull(this.openId)) return true;
        return false;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
