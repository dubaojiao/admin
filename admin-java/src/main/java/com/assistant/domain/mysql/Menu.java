package com.assistant.domain.mysql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 菜单
 */
@Entity
public class Menu implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(10) COMMENT'菜单id'",nullable = false)
    private Integer menuId;

    /**
     * 父级ID
     */
    @Column(columnDefinition = "int(10) COMMENT'父级id'",nullable = false)
    private  Integer parentId;

    /**
     * 层级路径
     */
    @Column(columnDefinition = "varchar(128) COMMENT'层级路径'",nullable = false)
    private  String menuPath="";

    /**
     *层级
     */
    @Column(columnDefinition = "int(2) COMMENT'层级'",nullable = false)
    private Integer menuLevel;

    /**
     * 菜单编码
     */
    @Column(columnDefinition = "int(10) COMMENT'菜单编码'",nullable = false)
    private  Integer menuCode;

    /**
     * 菜单名称
     */
    @Column(columnDefinition = "varchar(32) COMMENT'菜单名称'",nullable = false)
    private  String menuName="";
    /**
     * 菜单标识
     */
    @Column(columnDefinition = "varchar(32) COMMENT'菜单标识'",nullable = false)
    private  String menuIdea="";
    /**
     * 菜单路由
     */
    @Column(columnDefinition = "varchar(128) COMMENT'菜单路由'",nullable = false)
    private  String menuUrl="";

    /**
     * 排序
     */
    @Column(columnDefinition = "int(3) COMMENT'排序'",nullable = false)
    private Integer menuOrder;

    /**
     * 备注
     */
    @Column(columnDefinition = "varchar(128) COMMENT'备注'",nullable = false)
    private  String menuRemark="";

    /**
     * 状态
     */
    @Column(columnDefinition = "int(2) COMMENT'1正常/2停用   默认1'",nullable = false)
    private Integer menuState=1;

    /**
     * 添加人
     */
    @Column(columnDefinition="int(10) COMMENT'添加人'",nullable = false)
    private int createUser;
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
    private int updateUser;
    private String updateName;
    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime=new Date();

    /**
     * 菜单图标
     */
    @Column(columnDefinition="varchar(64) COMMENT'菜单图标'",nullable = false)
    private String icon;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public Integer getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(Integer menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getMenuRemark() {
        return menuRemark;
    }

    public void setMenuRemark(String menuRemark) {
        this.menuRemark = menuRemark;
    }

    public Integer getMenuState() {
        return menuState;
    }

    public void setMenuState(Integer menuState) {
        this.menuState = menuState;
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

    public String getMenuIdea() {
        return menuIdea;
    }

    public void setMenuIdea(String menuIdea) {
        this.menuIdea = menuIdea;
    }
}
