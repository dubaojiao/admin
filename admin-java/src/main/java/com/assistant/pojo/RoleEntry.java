package com.assistant.pojo;

import java.io.Serializable;

public class RoleEntry implements Serializable {
    /**
     * ID
     */
    private Integer roleId;

    /**
     * 角色编码
     */
    private String roleCode="";
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 备注
     */
    private String roleRemark="";


    /**
     * 添加时间
     */
    private String createTime = "";

    private String createName;

    /**
     * 修改时间
     */
    private String updateTime = "";
    private String updateName = "";
    /**
     * 角色状态1 正常 2 停用  默认为1
     */
    private Integer roleState= 1;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
}
