package com.assistant.runner;

import com.assistant.component.SysCacheComponent;
import com.assistant.constant.SysConstant;
import com.assistant.dao.MenuDao;
import com.assistant.dao.RoleDao;
import com.assistant.dao.UserInfoDao;
import com.assistant.domain.mysql.*;
import com.assistant.util.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Title 项目启动-最后执行的类
 * @ClassName WebApplicationRunner
 * @Author duke
 * @Date 2018/9/13
 */

@Component
@Order(value = 999)
public class WebApplicationRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(WebApplicationRunner.class);

    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    MenuDao menuDao;
    @Autowired
    SysCacheComponent sysCacheComponent;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("项目启动检测初始化数据");
        String flag = sysCacheComponent.getFirstBoot();
        if(!CheckUtil.stringIsNull(flag) && SysConstant.FIRST_BOOT.equals(flag)){
            // 不是首次启动直接过；
            sysCacheComponent.cacheMenu();
            logger.info("无需初始化：项目启动完成");
            return;
        }
        // 是首次启动
        userInfoDao.deleteAll();
        roleDao.deleteAll();
        menuDao.deleteAll();

        //系统启动添加默认用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setCode("000001");
        userInfo.setName("PDD01");
        userInfo.setPhone("15559709268");
        userInfo.setPwd("e10adc3949ba59abbe56e057f20f883e");
        userInfo.setNickname("PDD01");
        userInfo = userInfoDao.save(userInfo);
        logger.info(userInfo.toString());

        //系统启动添加默认用户信息
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(2);
        userInfo1.setName("PDD02");
        userInfo1.setCode("000002");
        userInfo1.setNickname("PDD02");
        userInfo1.setPhone("18988407031");
        userInfo1.setPwd("e10adc3949ba59abbe56e057f20f883e");
        userInfo1 = userInfoDao.save(userInfo1);
        logger.info(userInfo1.toString());

        List<Menu> menus = initMenuData();
        Role role = initRoleData();

        List<RoleMenu> roleMenuList = new ArrayList<>();
        RoleMenu roleMenu;
        for(Menu menu:menus){
            roleMenu = new RoleMenu();
            roleMenu.setCreateName("系统");
            roleMenu.setCreateTime(new Date());
            roleMenu.setCreateUser(0);
            roleMenu.setMenuId(menu.getMenuId());
            roleMenu.setRoleId(role.getRoleId());
            roleMenu.setRemark("系统设置");
            roleMenuList.add(roleMenu);
        }

        roleDao.bathSaveRoleMenu(roleMenuList);

        UserRole userRole = new UserRole();
        userRole.setCreateName("系统");
        userRole.setCreateTime(new Date());
        userRole.setCreateUser(0);
        userRole.setRemark("系统初始化");
        userRole.setRoleId(role.getRoleId());
        userRole.setUserId(userInfo.getId());
        userInfoDao.saveUserRole(userRole);

        UserRole userRole1 = new UserRole();
        userRole1.setCreateName("系统");
        userRole1.setCreateTime(new Date());
        userRole1.setCreateUser(0);
        userRole1.setRemark("系统初始化");
        userRole1.setRoleId(role.getRoleId());
        userRole1.setUserId(userInfo1.getId());
        userInfoDao.saveUserRole(userRole1);

        sysCacheComponent.cacheFirstBoot();

        sysCacheComponent.cacheMenu();
        logger.info("初始化数据：项目启动完成");
    }


    private List<Menu> initMenuData()  {

        java.util.List<Menu> list = new ArrayList<>();
        Menu menu1 = new Menu();
        menu1.setIcon("grid");
        menu1.setCreateTime(new Date());
        menu1.setCreateUser(1);
        menu1.setMenuCode(1);
        menu1.setMenuLevel(1);
        menu1.setMenuName("系统管理");
        menu1.setMenuOrder(1);
        menu1.setMenuPath("(1)");
        menu1.setMenuRemark("sys");
        menu1.setMenuState(1);
        menu1.setMenuUrl("/sys");
        menu1.setParentId(0);
        menu1.setUpdateTime(new Date());
        menu1.setUpdateUser(1);

        Menu menu2 = new Menu();
        menu2.setIcon("navicon");
        menu2.setCreateTime(new Date());
        menu2.setCreateUser(1);
        menu2.setMenuCode(1);
        menu2.setMenuLevel(2);
        menu2.setMenuName("菜单管理");
        menu2.setMenuOrder(1);
        menu2.setMenuPath("(1)(2)");
        menu2.setMenuRemark("menulistctrl");
        menu2.setMenuState(1);
        menu2.setMenuUrl("menulistctrl");
        menu2.setParentId(1);
        menu2.setUpdateTime(new Date());
        menu2.setUpdateUser(1);

        Menu menu3 = new Menu();
        menu3.setIcon("android-person");
        menu3.setCreateTime(new Date());
        menu3.setCreateUser(1);
        menu3.setMenuCode(1);
        menu3.setMenuLevel(2);
        menu3.setMenuName("角色管理");
        menu3.setMenuOrder(1);
        menu3.setMenuPath("(1)(3)");
        menu3.setMenuRemark("rolectrl");
        menu3.setMenuState(1);
        menu3.setMenuUrl("rolectrl");
        menu3.setParentId(1);
        menu3.setUpdateTime(new Date());
        menu3.setUpdateUser(1);

        list.add(menu1);
        list.add(menu2);
        list.add(menu3);

        list =  menuDao.bathSaveMenu(list);
        return list;
    }

    private Role initRoleData(){
        Role role = new Role();
        role.setCreateTime(new Date());
        role.setCreateUser(1);
        role.setRoleCode("PT0001");
        role.setRoleId(1);
        role.setRoleName("超级管理员");
        role.setRoleRemark("root");
        role.setRoleState(1);
        role.setUpdateTime(new Date());
        role.setUpdateUser(1);

        role =  roleDao.saveRole(role);
        return role;
    }

}
