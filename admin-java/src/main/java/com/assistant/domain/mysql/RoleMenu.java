package com.assistant.domain.mysql;

import javax.persistence.*;
import java.util.Date;

/**
 * @Title 角色和菜单的 关系表
 * @ClassName RoleMenu
 * @Author jsb_pbk
 * @Date 2018/11/5
 */
@Entity
public class RoleMenu {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(10) COMMENT'id'",nullable = false)
    private Integer id;

    /**
     * 角色ID
     */
    @Column(columnDefinition = "int(10) COMMENT'角色id'",nullable = false)
    private Integer roleId;

    /**
     * 菜单id
     */
    @Column(columnDefinition = "int(10) COMMENT'菜单id'",nullable = false)
    private Integer menuId;

    /**
     * 添加人
     */
    @Column(columnDefinition = "int(10) COMMENT'添加人'",nullable = false)
    private Integer createUser;
    private String createName;
    /**
     * 添加时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createTime=new Date();


    /**
     * 备注
     */
    @Column(columnDefinition = "varchar(32) COMMENT'备注'",nullable = false)
    private String remark="";

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
