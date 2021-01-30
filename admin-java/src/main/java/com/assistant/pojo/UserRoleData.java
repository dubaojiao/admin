package com.assistant.pojo;

import com.assistant.util.CheckUtil;

import java.io.Serializable;

public class UserRoleData implements Serializable {
    private Integer uid;
    private Integer roleId;

    public boolean isNull(){
        if(this == null) return true;
        if(CheckUtil.integerIsNull(this.uid)) return true;
        return false;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
