package com.assistant.pojo;

import com.assistant.util.CheckUtil;

import java.io.Serializable;

public class AddAdminData implements Serializable {
    private String phone;
    private String name;
    private String pwd;
    private Integer type = 1;

    public boolean isNull(){
        if(this == null) return true;
        if(CheckUtil.stringIsNull(this.phone)) return true;
        if(CheckUtil.stringIsNull(this.pwd)) return true;
        if(CheckUtil.integerIsNull(this.type)) return true;
        return false;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
