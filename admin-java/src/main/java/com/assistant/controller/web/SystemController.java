package com.assistant.controller.web;

import com.assistant.constant.SysConstant;
import com.assistant.pojo.ApiResult;
import com.assistant.pojo.MenuEntry;
import com.assistant.pojo.MenuToRoleData;
import com.assistant.pojo.RoleEntry;
import com.assistant.service.web.WebSystemService;
import com.assistant.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统管理
 * Created with: IntelliJ IDEA
 * User:
 * DATE:
 * TIME:
 */
@RestController
@RequestMapping(value = "/web/sys/")
public class SystemController {

    @Autowired
    WebSystemService webSystemService;

    /*菜单开始*/
    /**
     * 获取菜单list 树结构
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "getMenuTreeList")
    public ApiResult getMenuTreeList() throws Exception {
        try {
            return webSystemService.getMenuTreeList();
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }
    }

    /**
     * 根据parentId 获取菜单列表
     *
     * @param pId 父级ID 如果是获取父级菜单传入0
     * @return
     * @throws Exception
     */
    @GetMapping(value = "getMenuListByParentId")
    public ApiResult getMenuListByParentId(Integer pId) throws Exception {
        try {
            if (CheckUtil.integerIsNull(pId)) {
                pId = 0;
            }
            return webSystemService.getMenuListByParentId(pId);
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }
    }

    /**
     * 保存菜单
     *
     * @param data 菜单对象
     * @return
     * @throws Exception
     */
    @PostMapping(value = "saveMenu")
    public ApiResult saveMenu(@RequestBody MenuEntry data) throws Exception {
        try {
            if (CheckUtil.objectIsNull(data)) {
                return  ApiResult.returnError(SysConstant.ERROR_PARAM);
            }
            return webSystemService.saveMenu(data);
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }
    }

    /**
     * 菜单删除
     *
     * @param menuId 菜单ID
     * @return
     * @throws Exception
     */
    @GetMapping(value = "removeMenu")
    public ApiResult removeMenu(Integer menuId)  {
        try {
            if (CheckUtil.integerIsNull(menuId)) {
                return  ApiResult.returnError(SysConstant.ERROR_PARAM);
            }
            return webSystemService.removeMenu(menuId);
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }
    }

    /**
     * 菜单停用/启用
     *
     * @param menuId 菜单ID
     * @return
     * @throws Exception
     */
    @GetMapping(value = "updateMenuState")
    public ApiResult updateMenuState(Integer menuId) throws Exception {
        try {
            if (CheckUtil.integerIsNull(menuId)) {
                return  ApiResult.returnError(SysConstant.ERROR_PARAM);
            }
            return webSystemService.updateMenuState(menuId);
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }
    }

    /*菜单结束*/


    /*角色开始*/

    /**
     * 获取角色列表
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "getRoleList")
    public ApiResult getRoleList() throws Exception {
        try {
            return webSystemService.getRoleList();
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }
    }

    /**
     * 保存角色
     * @param data 角色对象
     * @return
     * @throws Exception
     */
    @PostMapping(value = "saveRole")
    public ApiResult saveRole(@RequestBody RoleEntry data) throws Exception {
        try {
            if(CheckUtil.objectIsNull(data)){
                return  ApiResult.returnError(SysConstant.ERROR_PARAM);
            }
            return webSystemService.saveRole(data);
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "removeRole")
    public ApiResult removeRole(Integer roleId) throws Exception {
        try {
            if(CheckUtil.integerIsNull(roleId)){
                return  ApiResult.returnError(SysConstant.ERROR_PARAM);
            }
            return webSystemService.removeRole(roleId);
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }
    }

    /**
     * 角色停用/启用
     *
     * @param roleId 角色ID
     * @return
     * @throws Exception
     */
    @GetMapping(value = "updateRoleState")
    public ApiResult updateRoleState(Integer roleId) throws Exception {
        try {
            if(CheckUtil.integerIsNull(roleId)){
                return  ApiResult.returnError(SysConstant.ERROR_PARAM);
            }
            return webSystemService.updateRoleState(roleId);
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }
    }

    /*角色结束*/


    /*赋值开始*/
    /**
     * 获取角色菜单
     * @param roleId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "getRoleMenu")
    public ApiResult getRoleMenu(Integer roleId) throws Exception {
        try {
            if(CheckUtil.integerIsNull(roleId)){
                return  ApiResult.returnError(SysConstant.ERROR_PARAM);
            }
            return webSystemService.getRoleMenu(roleId);
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }
    }

    /**
     * 角色赋值菜单
     *
     * @param data
     * @return
     */
    @PostMapping(value = "menuToRole")
    public ApiResult menuToRole(@RequestBody MenuToRoleData data) throws Exception {
        try {
            if(CheckUtil.objectIsNull(data)){
                return  ApiResult.returnError(SysConstant.ERROR_PARAM);
            }
            return webSystemService.menuToRole(data);
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }
    }

    /*赋值结束*/

}

