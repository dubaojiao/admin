package com.assistant.pojo;

import java.io.Serializable;
import java.util.List;

public class MenuToRoleData implements Serializable {
    private Integer roleId;
    private List<RoleMenuTree> list;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<RoleMenuTree> getList() {
        return list;
    }

    public void setList(List<RoleMenuTree> list) {
        this.list = list;
    }
}
