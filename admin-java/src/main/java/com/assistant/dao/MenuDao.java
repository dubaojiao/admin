package com.assistant.dao;

import com.assistant.common.CommonDao;
import com.assistant.domain.mysql.Menu;
import com.assistant.pojo.MenuTree;
import com.assistant.repos.MenuRepos;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title
 * @ClassName MenuDao
 * @Author jsb_pbk
 * @Date 2018/11/5
 */
@Service
public class MenuDao {
    @Autowired
    MenuRepos menuRepos;
    @Autowired
    CommonDao commonDao;

    public void  deleteAll(){
        String sql = "truncate table menu";
        commonDao.deleteOrUpDate(sql, Maps.newHashMap());
    }

    public List<Menu> bathSaveMenu(List<Menu> list) {
        return menuRepos.saveAll(list);
    }

    public List<MenuTree> getMenuTreeListByRoleId(Integer roleId) throws Exception{
        List<Menu> menuList = menuRepos.findAllRole(roleId);
        return getTree(0,menuList);
    }
    /**
     * 菜单结构树结构
     */
    private List<MenuTree> getTree(Integer parentId, List<Menu> rootMenu)throws Exception {
        List<MenuTree> tree = new ArrayList<>();
        for (Menu menu : rootMenu) {
            MenuTree menuTree = new MenuTree();
            if(menu.getParentId().equals(parentId)) {
                menuTree.setMenuId(menu.getMenuId());
                menuTree.setTitle(menu.getMenuName());
                menuTree.setIcon(menu.getIcon());
                menuTree.setPath(menu.getMenuUrl());
                menuTree.setAtt(menu);
                menuTree.setName(menu.getMenuRemark());
                menuTree.setChildren(getTree(menu.getMenuId(), rootMenu));
                tree.add(menuTree);
            }
        }
        return tree;
    }

    public List<MenuTree> getMenuTreeList() throws Exception{
        List<Menu> menuList = menuRepos.findAll();
        return getTree(0,menuList);
    }

    public List<Menu> getMenuListByParentId(Integer pId) {
        return menuRepos.findAllByParentId(pId);
    }

    public Menu getMenuById(Integer id) {
        return menuRepos.findById(id).get();
    }

    public Menu insertMenu(Menu menu) {
        return menuRepos.save(menu);
    }

    public boolean deleteMenuById(Integer menuId) {
        String sql ="DELETE FROM menu WHERE menu_id = :menuId";
        Map map = Maps.newHashMap();
        map.put("menuId",menuId);
        return commonDao.deleteOrUpDate(sql,map) == 1;
    }

    public boolean updateMenuState(int state, Integer menuId) {
        String sql ="UPDATE menu SET menu_state =:state  WHERE menu_id = :menuId";
        Map map = Maps.newHashMap();
        map.put("menuId",menuId);
        map.put("state",state);
        return commonDao.deleteOrUpDate(sql,map) == 1;
    }
}
