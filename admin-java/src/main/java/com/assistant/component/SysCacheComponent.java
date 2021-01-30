package com.assistant.component;

import com.assistant.common.RedisClient;
import com.assistant.constant.RedisConstant;
import com.assistant.constant.SysConstant;
import com.assistant.dao.MenuDao;
import com.assistant.dao.RoleDao;
import com.assistant.domain.mysql.Role;
import com.assistant.pojo.MenuTree;
import com.assistant.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 系统数据的缓存组件
 */
@Component
public class SysCacheComponent {
    @Autowired
    RedisClient redisService;
    @Autowired
    RoleDao roleDao;
    @Autowired
    MenuDao menuDao;

    /**
     * 缓存菜单
     * @return
     * @throws Exception
     */
    public boolean cacheMenu()throws Exception{
        //获取所有的角色
        List<Role> roles = roleDao.getRoleListByState(1);
        roles.forEach(x->{
            try {
                this.cacheMenuByRoleId(x.getRoleId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return true;
    }

    /**
     * 缓存菜单
     * @param roleId
     * @return
     * @throws Exception
     */
    public boolean cacheMenuByRoleId(Integer roleId) throws Exception{
        //获取角色下的菜单
        List<MenuTree> menus = menuDao.getMenuTreeListByRoleId(roleId);
        String key = RedisConstant.getWebSysMenuKey(roleId.toString());
        return redisService.setList(key,menus);
    }
    /**
     * 缓存菜单
     * @param roleId
     * @param menus
     * @return
     * @throws Exception
     */
    public boolean cacheMenuByRoleId(Integer roleId,List<MenuTree> menus) throws Exception{
        //获取角色下的菜单
       String key = RedisConstant.getWebSysMenuKey(roleId.toString());
        return redisService.setList(key,menus);
    }


    /**
     * 根据roleID 获取菜单 树结构
     * @param roleId
     * @return
     * @throws Exception
     */
    public List<MenuTree> getMenuTreeByRoleId(Integer roleId)throws Exception{
        List<MenuTree> menus = redisService.getList(RedisConstant.getWebSysMenuKey(roleId+""),MenuTree.class);
        if(menus == null || menus.isEmpty()){
            menus = menuDao.getMenuTreeListByRoleId(roleId);
            this.cacheMenuByRoleId(roleId,menus);
        }
        return menus;
    }

    /**
     *   缓存号码---验证码
     * @param phone
     * @param code
     * @return
     * @throws Exception
     */
    public boolean cacheCode(String phone,String code) throws Exception{
        try {
            if(CheckUtil.stringIsNull(phone) || CheckUtil.stringIsNull(code)){
                return false;
            }
            String key = RedisConstant.getAppCodeKey(phone);
            return redisService.set(key,code);
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


    /**
     *  获取验证码
     * @param phone
     * @return
     * @throws Exception
     */
    public String getCodeByPhone(String phone) throws Exception{
        try {
            if(CheckUtil.stringIsNull(phone)){
                return "";
            }
            String key = RedisConstant.getAppCodeKey(phone);
            return redisService.get(key);
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }

    /**
     *  移除验证码
     * @param phone
     * @return
     * @throws Exception
     */
    public void delCodeByPhone(String phone) throws Exception{
        try {
            if(CheckUtil.stringIsNull(phone)){
                return ;
            }
            String key = RedisConstant.getAppCodeKey(phone);
             redisService.del(key);
        }catch (Exception ex){
            ex.printStackTrace();
            return ;
        }
    }



    /**
     * 缓存首次启动标识
     * @return
     * @throws Exception
     */
    public boolean cacheFirstBoot() throws Exception{
        try {
            String key = RedisConstant.getFirstBootKey();
            return redisService.set(key, SysConstant.FIRST_BOOT);
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


    /**
     *  获取首次启动标识
     * @return
     * @throws Exception
     */
    public String getFirstBoot () throws Exception{
        try {
            String key = RedisConstant.getFirstBootKey();
            return redisService.get(key);
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }
}
