package com.assistant.pojo;

import java.io.Serializable;
import java.util.List;

public class RoleMenuTree implements Serializable {
    /**
     * 菜单ID
     */
    private Integer menuId;
    /**
     * 菜单名称
     */
    private String title;
    /**
     * 是否选中 0 未选中  其他值为选中
     */
    private Integer checked;
    /**
     * 角色Id
     */
    private Integer roleId;
    /**
     * 子集合
     */
    private List<RoleMenuTree> children;
    /**
     * 菜单状态 1 正常  2 停用
     */
    private Integer menuState;

    private Integer parentId;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getMenuState() {
        return menuState;
    }

    public void setMenuState(Integer menuState) {
        this.menuState = menuState;
    }

    public List<RoleMenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<RoleMenuTree> children) {
        this.children = children;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
