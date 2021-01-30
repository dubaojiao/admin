package com.assistant.pojo;

import java.io.Serializable;
import java.util.List;

public class MenuTree implements Serializable{
    /**
     * 名称
     */
    private String title;
    /**
     * ID
     */
    private Integer menuId;
    /**
     * 图标
     */
    private String icon;
    /**
     * 路径
     */
    private String path;
    /**
     * 子
     */
    private List<MenuTree> children;
    /**
     * 属性
     */
    private Object att;

    private String name;

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<MenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTree> children) {
        this.children = children;
    }

    public Object getAtt() {
        return att;
    }

    public void setAtt(Object att) {
        this.att = att;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
