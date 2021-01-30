package com.assistant.dao;

import com.assistant.common.CommonDao;
import com.assistant.domain.mysql.Role;
import com.assistant.domain.mysql.RoleMenu;
import com.assistant.pojo.RoleEntry;
import com.assistant.pojo.RoleMenuTree;
import com.assistant.repos.RoleMenuRepos;
import com.assistant.repos.RoleRepos;
import com.assistant.util.CheckUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Title
 * @ClassName RoleDao
 * @Author jsb_pbk
 * @Date 2018/11/5
 */
@Service
public class RoleDao {
    @Autowired
    RoleRepos roleRepos;

    @Autowired
    RoleMenuRepos roleMenuRepos;
    @Autowired
    CommonDao commonDao;
    public void deleteAll() {
        String sql = "truncate table role";
        commonDao.deleteOrUpDate(sql, Maps.newHashMap());
        sql = "truncate table user_role";
        commonDao.deleteOrUpDate(sql, Maps.newHashMap());
        sql = "truncate table role_menu";
        commonDao.deleteOrUpDate(sql, Maps.newHashMap());
    }

    public Role saveRole(Role role) {
        return roleRepos.save(role);
    }


    public void bathSaveRoleMenu(List<RoleMenu> list){
        roleMenuRepos.saveAll(list);
    }

    public List<Role> getRoleListByState(int state) {
        return roleRepos.findAllByRoleState(state);
    }

    public List<RoleMenu> getRoleMenuListByMenuId(Integer menuId) {
        return roleMenuRepos.findAllByMenuId(menuId);
    }

    public List<RoleEntry> findAll(){
        String sql = "SELECT role_id,role_state,role_code,role_name,role_remark,create_name,DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') AS create_time,update_name,DATE_FORMAT(update_time,'%Y-%m-%d %H:%i:%s') AS update_time " +
                " FROM role";
        return commonDao.queryListEntity(sql, Maps.newHashMap(),RoleEntry.class);
    }

    public Role insertRole(Role role) {
        return roleRepos.save(role);
    }

    public Role getRoleById(Integer roleId) {
        return roleRepos.findById(roleId).get();
    }

    public List<RoleMenu> getRoleMenuListByRoleId(Integer roleId) {
        return roleMenuRepos.findAllByRoleId(roleId);
    }


    public boolean deleteRoleById(Integer roleId) {
        String sql = "DELETE FROM role WHERE role_id = :roleId";
        Map map = Maps.newHashMap();
        map.put("roleId",roleId);
        return commonDao.deleteOrUpDate(sql,map) == 1;
    }

    public boolean updateRoleById(int state, Integer roleId) {
        String sql = "UPDATE role SET role_state = :state WHERE role_id = :roleId ";
        Map map = Maps.newHashMap();
        map.put("state",state);
        map.put("roleId",roleId);
        return commonDao.deleteOrUpDate(sql,map)==1;
    }

    /**
     * 获取角色下的菜单
     * @param roleId
     * @return
     */
    public List<RoleMenuTree> getRoleMenuTree(Integer roleId)throws Exception {
        String sql = "SELECT m.menu_id,m.menu_name AS title ,m.`parent_id`,IFNULL(rm.id,0) AS checked,m.`menu_state` AS menu_state FROM menu AS m \n" +
                "LEFT JOIN (SELECT id,menu_id FROM role_menu WHERE role_id = :roleId) AS rm ON m.`menu_id` = rm.menu_id";
        Map<String,Object> paramPam = Maps.newHashMap();
        paramPam.put("roleId",roleId);
        List<RoleMenuTree> roleMenuList = commonDao.queryListEntity(sql.toString(),paramPam,RoleMenuTree.class);
        return getTree(0,roleMenuList);
    }

    /**
     * 结构树结构
     */
    private List<RoleMenuTree> getTree(Integer parentId, List<RoleMenuTree> roleMenuList)throws Exception {
        List<RoleMenuTree> roleMenuTreeList = new ArrayList<>();
        for (RoleMenuTree menu : roleMenuList) {
            RoleMenuTree roleMenuTree = new RoleMenuTree();
            if(menu.getParentId().equals(parentId)) {
                roleMenuTree.setMenuId(menu.getMenuId());
                roleMenuTree.setTitle(menu.getTitle());
                roleMenuTree.setChecked(menu.getChecked());
                roleMenuTree.setMenuState(menu.getMenuState());
                roleMenuTree.setParentId(menu.getParentId());
                roleMenuTree.setChildren(getTree(menu.getMenuId(), roleMenuList));
                roleMenuTreeList.add(roleMenuTree);
            }
        }
        return roleMenuTreeList;
    }

    /**
     * 先清空当前角色下的菜单信息
     * @param roleId
     */
    public Boolean deleteRoleMenuListByRoleId(Integer roleId,Integer size)throws Exception {
        if(roleMenuRepos.deleteRoleMenuListByRoleId(roleId) == size){
            return true;
        }
        return false;
    }

    public boolean saveRoleMenu(List<RoleMenu> roleMenuList) {
        if(roleMenuRepos.saveAll(roleMenuList).size()>0){
            return true;
        }
        return false;
    }


    public Role findById(Integer roleId) {
        if(CheckUtil.integerIsNull(roleId)){
            return null;
        }
        Optional<Role> role = roleRepos.findById(roleId);
        if(role.isPresent()){
            return role.get();
        }
        return null;
    }

    public Role findRoleByUid(Integer id) {
        String sql = "SELECT * FROM role WHERE role_id = (SELECT role_id FROM user_role WHERE user_id = :id)";
        Map map = Maps.newHashMap();
        map.put("id",id);

        return commonDao.queryEntity(sql,map,Role.class);
    }
}
