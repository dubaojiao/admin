package com.assistant.controller.web;

import com.assistant.pojo.*;
import com.assistant.service.web.WebUserService;
import com.assistant.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import static com.assistant.constant.SysConstant.ERROR_500;
import static com.assistant.constant.SysConstant.ERROR_PARAM;

@RestController
@RequestMapping(value = "/web/user/")
public class UserController {

    @Autowired
    WebUserService webUserService;

    /**
     * 后台登录
     * @param data
     * @return
     */
    @PostMapping(value = "login")
    public ApiResult login(@RequestBody LoginData data){
        try {
            if (data.isNull()) {
                return ApiResult.returnError(ERROR_PARAM);
            }
            return webUserService.login(data);
        } catch (Exception ex) {
            return ApiResult.returnError(ERROR_500, ex);
        }
    }


    /**
     * 登陆成功获取主页数据
     * @return
     */
    @GetMapping(value = "get/home/date")
    public ApiResult getHomeData(){
        try {
            return webUserService.getHomeData();
        }catch (Exception ex){
            ex.printStackTrace();
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    /**
     * 获取用户列表
     * @param term 模糊查询
     * @param type   用户类型 0 全部 1 后台用户 2 公会会长 3 公会成员  4 客户
     * @param index
     * @return
     */
    @GetMapping(value = "list")
    public ApiResult getUserList(String term,Integer type,Integer index){
        try {
            return webUserService.getUserList(term,type,index);
        }catch (Exception ex){
            ex.printStackTrace();
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    /**
     * 添加后台人员
     * @param data
     * @return
     */
    @PostMapping(value = "add/admin")
    public ApiResult addAdmin(@RequestBody AddAdminData data){
        try {
            if(data.isNull()){
                return ApiResult.returnError(ERROR_PARAM);
            }
            return webUserService.addAdmin(data);
        }catch (Exception ex){
            ex.printStackTrace();
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    /**
     * 修改用户状态
     * @param id
     * @return
     */
    @GetMapping(value = "update/state")
    public ApiResult updateUserState(Integer id){
        try {
            if(CheckUtil.integerIsNull(id)){
                return ApiResult.returnError(ERROR_PARAM);
            }
            return webUserService.updateUserState(id);
        }catch (Exception ex){
            ex.printStackTrace();
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    @GetMapping(value = "get/role/{id}")
    public ApiResult getUserRoleInfo(@PathVariable("id") Integer id){
        try {
            return webUserService.getUserRoleInfo(id);
        }catch (Exception ex){
            ex.printStackTrace();
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    /**
     * 用户赋值角色
     * @param data
     * @return
     */
    @PostMapping(value = "set/role")
    @Transactional
    public ApiResult setUserRole(@RequestBody UserRoleData data){
        try {
            if(data.isNull()){
                return ApiResult.returnError(ERROR_PARAM);
            }
            ApiResult result = webUserService.setUserRole(data);
            if(!result.isSuccess()){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return result;
        }catch (Exception ex){
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    @GetMapping(value = "get/{id}")
    public ApiResult getUserInfoById(@PathVariable("id") Integer id){
        try {
            return webUserService.getUserInfoById(id);
        }catch (Exception ex){
            ex.printStackTrace();
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    /**
     * 修改用户信息
     * @param data
     * @return
     */
    @PostMapping(value = "update/info")
    public ApiResult updateUserInfo(@RequestBody UserInfoData data){
        try {
            if(data == null || CheckUtil.integerIsNull(data.getId())){
                return ApiResult.returnError(ERROR_PARAM);
            }
            return webUserService.updateUserInfo(data);
        }catch (Exception ex){
            ex.printStackTrace();
            return ApiResult.returnError(ERROR_500,ex);
        }
    }



}
