package com.assistant.service.web;

import com.assistant.component.SysCacheComponent;
import com.assistant.component.UserCacheComponent;
import com.assistant.constant.SysConstant;
import com.assistant.dao.MenuDao;
import com.assistant.dao.RoleDao;
import com.assistant.domain.mysql.Menu;
import com.assistant.domain.mysql.Role;
import com.assistant.domain.mysql.RoleMenu;
import com.assistant.domain.redis.LoginUser;
import com.assistant.pojo.*;
import com.assistant.util.CheckUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统管理
 * 1.菜单管理
 * 2.角色管理
 */
@Service
public class WebSystemService {

    @Autowired
    MenuDao menuDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    SysCacheComponent sysCacheComponent;
    @Autowired
    UserCacheComponent userCacheComponent;



    /**
     * 获取菜单list 树结构
     *
     * @return
     */
    public ApiResult getMenuTreeList() throws Exception {
        return ApiResult.returnSuccess(SysConstant.SUCCESS,menuDao.getMenuTreeList());
    }

    /**
     * 根据parentId 获取菜单列表
     *
     * @param pId 父级ID 如果是获取父级菜单传入0
     * @return
     * @throws Exception
     */

    public ApiResult getMenuListByParentId(Integer pId) throws Exception {
        List<Menu> menuList = menuDao.getMenuListByParentId(pId);
        List<MenuEntry> menuEntryList = new ArrayList<>();
        for (Menu item : menuList) {
            MenuEntry menuEntry = new MenuEntry();
            BeanUtils.copyProperties(item, menuEntry);
            menuEntryList.add(menuEntry);
        }
        return ApiResult.returnSuccess(SysConstant.SUCCESS,menuEntryList);
    }

    /**
     * 保存菜单
     *
     * @param data 菜单对象
     * @return
     * @throws Exception
     */

    public ApiResult saveMenu(MenuEntry data) throws Exception {
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }

        if(!CheckUtil.objectIsNull(data)){
            Menu menu = new Menu();
            BeanUtils.copyProperties(data, menu);
            if (data.getMenuId() == null || 0 == data.getMenuId()) {
                menu = getMenuData(menu,loginUser);
                //添加
                if (menuDao.insertMenu(menu).getMenuId() > 0) {
                    return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
                }
            } else {
                //修改 暂时没有修改功能
                Menu getMenu = menuDao.getMenuById(data.getMenuId());
                if (getMenu != null) {
                    getMenu.setMenuUrl(menu.getMenuUrl());
                    getMenu.setMenuName(menu.getMenuName());
                    getMenu.setMenuRemark(menu.getMenuRemark());
                    getMenu.setIcon(menu.getIcon());
                    getMenu.setUpdateTime(new Date());
                    getMenu.setUpdateUser(loginUser.getUid());
                    getMenu.setUpdateName(loginUser.getUserName());
                    if (menuDao.insertMenu(getMenu).getMenuId() > 0) {
                        return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
                    }
                }
            }
        }
        return ApiResult.returnError(SysConstant.C_FAILURE);
    }

    private Menu getMenuData(Menu menu, LoginUser loginUser) throws Exception {

        menu.setCreateUser(loginUser.getUid());
        menu.setCreateName(loginUser.getUserName());
        menu.setCreateTime(new Date());
        menu.setMenuOrder(99);
        menu.setParentId(menu.getParentId()==null?0:menu.getParentId());
        menu.setMenuState(1);
        menu.setMenuCode((menu.getMenuCode() == null)?0:menu.getMenuCode());
        menu.setIcon(CheckUtil.stringIsNull(menu.getIcon())?"default":menu.getIcon());
        if(menu.getParentId() == null || menu.getParentId() == 0){
            //父级菜单
            menu.setMenuLevel(1);
            menu.setMenuPath(CheckUtil.stringIsNull(menu.getMenuPath())?"("+menu.getMenuCode()+")":menu.getMenuPath());
        }else{
            //子菜单
            Menu parentMenu = menuDao.getMenuById(menu.getParentId());
            menu.setMenuLevel(parentMenu.getMenuLevel()+1);
            menu.setMenuPath(CheckUtil.stringIsNull(menu.getMenuPath())?parentMenu.getMenuPath()+"("+menu.getMenuCode()+")":parentMenu.getMenuPath()+menu.getMenuPath());
        }
        return menu;
    }

    /**
     * 菜单删除
     *
     * @param menuId
     * @return
     * @throws Exception
     */

    public ApiResult removeMenu(Integer menuId) throws Exception {
        if (menuDao.getMenuById(menuId) != null) {
            if (menuDao.getMenuListByParentId(menuId).size() > 0 || roleDao.getRoleMenuListByMenuId(menuId).size() > 0) {
                //System.out.println("此菜单下有子菜单或有授权角色，无法删除");
                return ApiResult.returnError("此菜单下有子菜单或有授权角色，无法删除");
            }
            if(menuDao.deleteMenuById(menuId)){
                return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
            }
        }
        return ApiResult.returnError(SysConstant.C_FAILURE);
    }

    /**
     * 菜单停用/启用
     *
     * @param menuId
     * @return
     */

    public ApiResult updateMenuState(Integer menuId) throws Exception {
        Menu menu = menuDao.getMenuById(menuId);
        if (menu != null) {
             if(menuDao.updateMenuState(menu.getMenuState() == 1 ? 2 : 1, menuId)){
                 sysCacheComponent.cacheMenu();
                 return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
            }
        }
        return ApiResult.returnError(SysConstant.C_FAILURE);
    }

    /**
     * 获取角色列表
     *
     * @return
     * @throws Exception
     */

    public ApiResult getRoleList() throws Exception {
        List<RoleEntry> roleEntries = roleDao.findAll();
        return ApiResult.returnSuccess(SysConstant.SUCCESS,roleEntries);
    }




    /**
     * 保存角色
     *
     * @param data
     * @return
     * @throws Exception
     */

    public ApiResult saveRole(RoleEntry data) throws Exception {
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        if(!CheckUtil.objectIsNull(data)){
            Role role = new Role();
            BeanUtils.copyProperties(data,role);
            if (data.getRoleId() == null || 0 == data.getRoleId()) {
                //添加
                role.setCreateTime(new Date());
                role.setCreateUser(loginUser.getUid());
                role.setCreateName(loginUser.getUserName());
                role.setRoleState(1);
                if (roleDao.insertRole(role).getRoleId() > 0) {
                    return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
                }
            } else {
                //修改
                Role getRole = roleDao.getRoleById(data.getRoleId());
                if (getRole.getRoleId() > 0) {
                    getRole.setRoleCode(data.getRoleCode());
                    getRole.setRoleName(data.getRoleName());
                    getRole.setRoleRemark(data.getRoleRemark());
                    getRole.setUpdateTime(new Date());
                    getRole.setUpdateUser(loginUser.getUid());
                    getRole.setCreateName(loginUser.getUserName());
                    if (roleDao.insertRole(getRole).getRoleId() > 0) {
                        return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
                    }
                }
            }
        }
        return ApiResult.returnError(SysConstant.C_FAILURE);
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     * @throws Exception
     */

    public ApiResult removeRole(Integer roleId) throws Exception {
        if (roleDao.getRoleById(roleId) != null) {
            if (roleDao.getRoleMenuListByRoleId(roleId).size() > 0) {
                System.out.println("此角色下有授权，无法删除");
                return ApiResult.returnError("此角色下有授权，无法删除");
            }
           if(roleDao.deleteRoleById(roleId)){
                return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
           }
        }
        return ApiResult.returnError(SysConstant.C_FAILURE);
    }

    /**
     * 角色停用/启用
     *
     * @param roleId 角色ID
     * @return
     * @throws Exception
     */

    public ApiResult updateRoleState(Integer roleId) throws Exception {
        Role role = roleDao.getRoleById(roleId);
        if (role != null) {
            /*if (roleDao.getUserRoleTrByRoleId(roleId).size() > 0) {
                System.out.println("此角色下有授权，无法修改");
                return ApiResult.returnError("此角色下有授权，无法删除");
            }*/
            if(roleDao.updateRoleById(role.getRoleState() == 1 ? 2 : 1, roleId)){
                return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
            }
        }
        return ApiResult.returnError(SysConstant.C_FAILURE);
    }

    /**
     * 获取角色下的菜单
     *
     * @param roleId
     * @return
     */

    public ApiResult getRoleMenu(Integer roleId) throws Exception {
        return ApiResult.returnSuccess(SysConstant.SUCCESS,roleDao.getRoleMenuTree(roleId));
    }

    /**
     * 角色赋值菜单
     *
     * @param data
     * @return
     * @throws Exception
     */

    @Transactional    //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    public ApiResult menuToRole(MenuToRoleData data) throws Exception {
        /*
         * 先清空当前角色下的所以菜单信息，然后全部插入进去
         * dao层进行数据库查询 操作成功后更新redis缓存信息
         * */
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }

        Integer roleId = data.getRoleId();
        List<RoleMenuTree> list = data.getList();
        List<RoleMenu> roleMenuTreeList = roleDao.getRoleMenuListByRoleId(roleId);
        if (roleMenuTreeList.size() > 0) {
            if (!roleDao.deleteRoleMenuListByRoleId(roleId, roleMenuTreeList.size())) {
                return ApiResult.returnError(SysConstant.C_FAILURE);
            }
        }
        if (list== null|| list.isEmpty() || list.size()==0) {
            return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
        }
        List<RoleMenu> roleMenuList = new ArrayList<>();
        RoleMenu roleMenu;
        for (RoleMenuTree item : list) {
            roleMenu= new RoleMenu();
            roleMenu.setRoleId(item.getRoleId());
            roleMenu.setMenuId(item.getMenuId());
            roleMenu.setCreateUser(loginUser.getUid());
            roleMenu.setCreateName(loginUser.getUserName());
            roleMenu.setCreateTime(new Date());
            roleMenuList.add(roleMenu);
        }
        if (!roleDao.saveRoleMenu(roleMenuList)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResult.returnError("保存角色菜单时出错");
        }
        if (sysCacheComponent.cacheMenuByRoleId(roleId)) {
            return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResult.returnError("Redis保存角色菜单时出错");
        }
    }

}
