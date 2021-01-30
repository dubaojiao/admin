package com.assistant.pojo;

import com.assistant.util.CheckUtil;

import java.io.Serializable;

public class BindPhoneData implements Serializable {
    private String phone;
    private String code;
    private String openId;

    public boolean isNull(){
        if(this == null) return true;
        if(CheckUtil.stringIsNull(this.phone)
                || CheckUtil.stringIsNull(this.code)
                || CheckUtil.stringIsNull(this.openId)) return true;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
