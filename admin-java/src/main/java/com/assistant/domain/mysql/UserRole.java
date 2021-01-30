package com.assistant.domain.mysql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关系表
 */
@Entity
public class UserRole implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(10) COMMENT'id'",nullable = false)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(columnDefinition = "int(10) COMMENT'用户id'",nullable = false)
    private Integer userId;

    /**
     * 角色ID
     */
    @Column(columnDefinition = "int(10) COMMENT'角色id'",nullable = false)
    private Integer roleId;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
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
