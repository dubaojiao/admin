package com.assistant.pojo;


import java.io.Serializable;
import java.util.Date;

public class MenuEntry implements Serializable {
    /**
     * ID
     */
    private Integer menuId;

    /**
     * 父级ID
     */
    private  Integer parentId;

    /**
     * 层级路径
     */
    private  String menuPath;

    /**
     *层级
     */
    private Integer menuLevel;

    /**
     * 菜单编码
     */
    private  Integer menuCode;

    /**
     * 菜单名称
     */
    private  String menuName;

    /**
     * 菜单路由
     */
    private  String menuUrl;

    /**
     * 排序
     */
    private Integer menuOrder;

    /**
     * 备注
     */
    private  String menuRemark;

    /**
     * 状态
     */
    private Integer menuState;

    /**
     * 添加人
     */
    private int createUser;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private int updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 菜单图标
     */
    private String icon;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
