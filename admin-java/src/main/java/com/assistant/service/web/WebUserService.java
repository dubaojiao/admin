package com.assistant.service.web;

import com.assistant.component.SysCacheComponent;
import com.assistant.component.UserCacheComponent;
import com.assistant.dao.RoleDao;
import com.assistant.dao.UserInfoDao;
import com.assistant.domain.mysql.Role;
import com.assistant.domain.mysql.UserInfo;
import com.assistant.domain.mysql.UserRole;
import com.assistant.domain.redis.LoginUser;
import com.assistant.pojo.*;
import com.assistant.util.CheckUtil;
import com.assistant.util.RandomCodeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WebUserService {

    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    UserCacheComponent userCacheComponent;
    @Autowired
    SysCacheComponent sysCacheComponent;

    @Value("${spring.file.img.http.path}")
    String basePath;

    public ApiResult login(LoginData data) {
       // UserInfo userInfo = userInfoDao.findUserInfoByPhone(data.getPhone());
        UserInfo userInfo = userInfoDao.findUserInfoByCode(data.getPhone());
        if(CheckUtil.objectIsNull(userInfo)){
            return ApiResult.returnError("用户名或者密码错误");
        }
        if(!userInfo.getPwd().equals(data.getCode())){
            return ApiResult.returnError("用户名或者密码错误");
        }
        UserRole userRole = userInfoDao.findByUserId(userInfo.getId());
        if(CheckUtil.objectIsNull(userRole)){
            return ApiResult.returnError("登录失败：当前用户没有角色无法登录系统");
        }
        Role role = roleDao.findById(userRole.getRoleId());
        if(CheckUtil.objectIsNull(role)){
            return ApiResult.returnError("登录失败：所属角色不存在");
        }
        if(role.getRoleState() != 1){
            return ApiResult.returnError("登录失败：所属角色已被停用");
        }

        userInfo.setLoginNum(userInfo.getLoginNum()+1);
        userInfo.setLoginTime(new Date());
        userInfoDao.save(userInfo);

        LoginUser loginUser = new LoginUser();
        loginUser.setAppType("web");
        loginUser.setLoginTime(new Date());
        loginUser.setUid(userInfo.getId());
        loginUser.setUserName(userInfo.getCode());
        loginUser.setUserPhone(userInfo.getPhone());
        loginUser.setUserType(userInfo.getUserType());
        loginUser.setNickname(userInfo.getNickname());
        loginUser.setRoleId(userRole.getRoleId());
        loginUser.setRoleName(role.getRoleName());

        String token = RandomCodeUtil.getUUID();

        if (userCacheComponent.setUserCache(loginUser,token)) {
            return ApiResult.returnSuccess("登录成功",token);
        }
        return ApiResult.returnError("登录失败：缓存信息写入失败");
    }

    public ApiResult getHomeData() throws Exception{
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        Map<String,Object> map = Maps.newHashMap();
        map.put("loginUser",loginUser);
        map.put("menu",sysCacheComponent.getMenuTreeByRoleId(loginUser.getRoleId()));
        return ApiResult.returnSuccess("成功",map);
    }

    public ApiResult getUserList(String term, Integer type, Integer index) {
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        Map<String,Object> map = Maps.newHashMap();

        List<UserInfoEntry> rows = userInfoDao.getUserList(term,type,index);
        for(UserInfoEntry entry:rows){
            Role role = roleDao.findRoleByUid(entry.getId());
            if(!CheckUtil.objectIsNull(role)){
                entry.setRoleName(role.getRoleName());
            }
        }
        Integer total = userInfoDao.getUserListCount(term,type);
        map.put("rows",rows);
        map.put("total",total);
        return ApiResult.returnSuccess("成功",map);
    }

    public ApiResult addAdmin(AddAdminData data) {
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        UserInfo userInfo = userInfoDao.findUserInfoByCode(data.getName());
        if(!CheckUtil.objectIsNull(userInfo)){
            return ApiResult.returnError("添加失败：用户名已经存在");
        }
        userInfo = new UserInfo();
        userInfo.setPwd(data.getPwd());
        userInfo.setCreatedTime(new Date());
        userInfo.setLoginNum(0);
        userInfo.setPhone(data.getPhone());
        userInfo.setUserType(1);
        //String code = userInfoDao.getUserCode();
        userInfo.setCode(data.getName());
        userInfo.setState(1);
        userInfo.setRemark("后台添加"+loginUser.getUserName());
        userInfo.setHeadPath("default.png");
        userInfo.setName(data.getName());
        userInfo.setNickname(data.getName());
        userInfo = userInfoDao.save(userInfo);

        return ApiResult.returnSuccess("添加成功");
    }

    public ApiResult updateUserState(Integer id) {
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        UserInfo userInfo = userInfoDao.findById(id);
        if(CheckUtil.objectIsNull(userInfo)){
            return ApiResult.returnError("操作失败：用户Id错误");
        }
        userInfo.setState(userInfo.getState() == 1?2:1);
        userInfoDao.save(userInfo);

        return ApiResult.returnSuccess("操作成功");
    }

    public ApiResult getUserRoleInfo(Integer id) {
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        UserInfo userInfo = userInfoDao.findById(id);
        if(CheckUtil.objectIsNull(userInfo)){
            return ApiResult.returnError("失败：用户Id错误");
        }
        List<Role> roles  = roleDao.getRoleListByState(1);
        UserRole userRole = userInfoDao.findByUserId(id);

        List<UserRoleEntry> list = Lists.newArrayList();
        UserRoleEntry entry;
        for(Role r:roles){
            entry = new UserRoleEntry();

            entry.setRoleId(r.getRoleId());
            entry.setRoleName(r.getRoleName());
            if(!CheckUtil.objectIsNull(userRole) && r.getRoleId().equals(userRole.getRoleId())){
                entry.setCheck(1);
            }else{
                entry.setCheck(0);
            }

            list.add(entry);
        }
        return ApiResult.returnSuccess("成功",list);
    }

    public ApiResult setUserRole(UserRoleData data) {
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        UserInfo userInfo = userInfoDao.findById(data.getUid());
        if(CheckUtil.objectIsNull(userInfo)){
            return ApiResult.returnError("操作失败：用户Id错误");
        }
        userInfoDao.deleteUserRoleByUid(data.getUid());
        if(!CheckUtil.integerIsNull(data.getRoleId())){
            UserRole userRole  = new UserRole();
            userRole.setUserId(data.getUid());
            userRole.setRoleId(data.getRoleId());
            userRole.setRemark("后台设置");
            userRole.setCreateUser(loginUser.getUid());
            userRole.setCreateTime(new Date());
            userRole.setCreateName(loginUser.getUserName());
            userInfoDao.saveUserRole(userRole);

        }
        return ApiResult.returnSuccess("操作成功");
    }

    public ApiResult getUserInfoById(Integer id) {
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        UserInfo userInfo = userInfoDao.findById(id);
        if(CheckUtil.objectIsNull(userInfo)){
            return ApiResult.returnError("失败：用户Id错误");
        }

        UserInfoEntry entry = new UserInfoEntry();

        BeanUtils.copyProperties(userInfo,entry);

        entry.setBasePath(basePath);

        return ApiResult.returnSuccess("成功",entry);
    }

    public ApiResult updateUserInfo(UserInfoData data) {
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        UserInfo userInfo = userInfoDao.findById(data.getId());
        if(CheckUtil.objectIsNull(userInfo)){
            return ApiResult.returnError("失败：用户Id错误");
        }

        BeanUtils.copyProperties(data,userInfo);
        userInfoDao.save(userInfo);

        return ApiResult.returnSuccess("操作成功");
    }
}
