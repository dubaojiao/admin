package com.assistant.domain.mysql;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(10) COMMENT'role_id'",nullable = false)
    private Integer roleId;

    /**
     * 角色编码
     */
    @Column(columnDefinition="varchar(32) COMMENT'角色编码'",nullable = false)
    private String roleCode="";

    /**
     * 角色名称
     */
    @Column(columnDefinition="varchar(32) COMMENT'角色名称'",nullable = false)
    private String roleName="";

    /**
     * 备注
     */
    @Column(columnDefinition="varchar(32) COMMENT'备注'")
    private String roleRemark="";

    /**
     * 添加人
     */
    @Column(columnDefinition="int(10) COMMENT'添加人'",nullable = false)
    private Integer createUser;

    private String createName;
    /**
     * 添加时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createTime=new Date();

    /**
     * 修改人
     */
    @Column(columnDefinition="int(10) COMMENT'修改人'")
    private Integer updateUser;
    private String updateName;
    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime=new Date();

    /**
     * 角色状态1 正常 2 停用  默认为1
     */
    @Column(columnDefinition="int(2) COMMENT'角色状态1 正常 2 停用  默认为1'",nullable = false)
    private Integer roleState= 1;

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

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

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(int updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }

}
