package com.assistant.dao;

import com.assistant.domain.mysql.UserRole;
import com.assistant.repos.UserInfoRepos;
import com.assistant.common.CommonDao;
import com.assistant.domain.mysql.UserInfo;
import com.assistant.pojo.UserInfoEntry;
import com.assistant.repos.UserRoleRepos;
import com.assistant.util.CheckUtil;
import com.assistant.util.RandomCodeUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Title  用户信息 数据 操作
 * @ClassName UserInfoDao
 * @Author duke
 * @Date 2018/9/13
 */
@Service
public class UserInfoDao {
    @Autowired
    UserInfoRepos userInfoRepos;
    @Autowired
    CommonDao commonDao;
    @Value("${spring.page.size.web}")
    Integer size;
    @Autowired
    UserRoleRepos userRoleRepos;

    public UserRole saveUserRole(UserRole userRole){
        return userRoleRepos.save(userRole);
    }

    public UserRole findByUserId(Integer uid){
        return userRoleRepos.findFirstByUserId(uid);
    }


    public UserInfo save(UserInfo userInfo){
        userInfo.setCode(userInfo.getCode().toUpperCase());
        return userInfoRepos.save(userInfo);
    }

    public void deleteAll(){
        String sql = "truncate table user_info";
        commonDao.deleteOrUpDate(sql, Maps.newHashMap());
    }

    public long countAll(){
        return userInfoRepos.count();
    }

    public UserInfo findUserInfoByPhone(String phone){
        return userInfoRepos.findFirstByPhone(phone);
    }

    public List<UserInfoEntry>  findAllBySize(Integer index){
        int a = (index-1)*size;
        int b = size;
        String sql = "SELECT * FROM user_info ORDER BY id LIMIT :a,:b";
        Map map  = new HashMap(2);
        map.put("a",a);
        map.put("b",b);
        return commonDao.queryListEntity(sql,map,UserInfoEntry.class);

    }

    public List<UserInfoEntry> getUserList(String term, Integer type, Integer index) {
        String sql = "SELECT * FROM user_info WHERE 1=1 ";
        Map map = Maps.newHashMap();
        map.put("a",(index-1)*size);
        map.put("b",size);
        if(CheckUtil.integerIsNull(type)){
            sql += " AND user_type IN (1,2,3) ";
        }else {
            if(type == 5){
                sql += " AND user_type IN (1,4) ";
            }else {
                sql += " AND user_type = :type ";
                map.put("type",type);
            }
        }
        if(!CheckUtil.stringIsNull(term)){
            sql += " AND (name LIKE :term OR nickname LIKE :term OR phone LIKE :term OR code LIKE :term ) ";
            map.put("term","%"+term+"%");
        }
        sql += " ORDER BY id DESC LIMIT :a,:b";
        return commonDao.queryListEntity(sql,map,UserInfoEntry.class);
    }

    public Integer getUserListCount(String term, Integer type) {
        String sql = "SELECT COUNT(*) FROM user_info WHERE 1=1 ";
        Map map = Maps.newHashMap();
        if(CheckUtil.integerIsNull(type)){
            sql += " AND user_type IN (1,2,3) ";
        }else {
            if(type == 5){
                sql += " AND user_type IN (1,4) ";
            }else {
                sql += " AND user_type = :type ";
                map.put("type",type);
            }
        }
        if(!CheckUtil.stringIsNull(term)){
            sql += " AND (name LIKE :term OR nickname LIKE :term OR phone LIKE :term OR code LIKE :term ) ";
            map.put("term","%"+term+"%");
        }
        return commonDao.getCountBy(sql,map);
    }

    public String getUserCode() {
        do {
            String code = RandomCodeUtil.randomNum(6);
            UserInfo userInfo = userInfoRepos.findFirstByCode(code);
            if(CheckUtil.objectIsNull(userInfo)){
                return code;
            }
        }while (true);
    }

    public UserInfo findById(Integer id) {
        if(CheckUtil.integerIsNull(id)){
            return null;
        }
        Optional<UserInfo> userInfo = userInfoRepos.findById(id);
        if(userInfo.isPresent()){
            return userInfo.get();
        }
        return null;
    }

    public void deleteUserRoleByUid(Integer uid) {
        String sql = "DELETE FROM user_role WHERE user_id = :uid";

        Map map = Maps.newHashMap();

        map.put("uid",uid);

        commonDao.deleteOrUpDate(sql,map);

    }

    public List<UserInfoEntry> getGuildAdmin(Integer guild, Integer index) {
        Map map = Maps.newHashMap();
        map.put("a",(index-1)*size);
        map.put("b",size);
        map.put("guild",guild);

        String sql = "SELECT * FROM user_info WHERE id IN ( SELECT member_id FROM guild_member WHERE guild_id = :guild AND admin_flag = 1 ) ORDER BY id DESC LIMIT :a,:b ";

        return commonDao.queryListEntity(sql,map,UserInfoEntry.class);

    }

    public Integer getGuildAdminCount(Integer guild) {
        Map map = Maps.newHashMap();
        map.put("guild",guild);

        String sql = "SELECT COUNT(*) FROM guild_member WHERE guild_id = :guild AND admin_flag = 1 ";

        return commonDao.getCountBy(sql,map);

    }

    public List<UserInfoEntry> getGuildMemberList(Integer guildId, Integer index, String term) {
        Map map = Maps.newHashMap();
        map.put("a",(index-1)*size);
        map.put("b",size);
        map.put("guild",guildId);

        String sql = "SELECT *,(SELECT name FROM user_info WHERE id = (SELECT admin_id FROM guild_member WHERE member_id = id  LIMIT 1)) AS admin_name FROM user_info WHERE id IN ( SELECT member_id FROM guild_member WHERE guild_id = :guild AND admin_flag = 0 ) ";
        if(!CheckUtil.stringIsNull(term)){
            sql += " AND (phone LIKE :term OR nickname LIKE :term OR name LIKE :term) ";
            map.put("term","%"+term+"%");
        }
        sql +=   " ORDER BY id DESC LIMIT :a,:b ";
        return commonDao.queryListEntity(sql,map,UserInfoEntry.class);
    }

    public Integer getGuildMemberListCount(Integer guildId, String term) {
        Map map = Maps.newHashMap();
        map.put("guild",guildId);
        String sql = "SELECT COUNT(*) FROM user_info WHERE id IN ( SELECT member_id FROM guild_member WHERE guild_id = :guild AND admin_flag = 0 ) ";
        if(!CheckUtil.stringIsNull(term)){
            sql += " AND (phone LIKE :term OR nickname LIKE :term OR name LIKE :term) ";
            map.put("term","%"+term+"%");
        }
        return commonDao.getCountBy(sql,map);
    }

    public UserInfo findUserInfoByCode(String code) {
        return userInfoRepos.findFirstByCode(code);
    }




    public List<UserInfoEntry> getGuildAdminAll(Integer guild) {
        Map map = Maps.newHashMap();
        map.put("guild",guild);

        String sql = "SELECT * FROM user_info WHERE id IN ( SELECT member_id FROM guild_member WHERE guild_id = :guild AND admin_flag = 1 ) ORDER BY id DESC ";
        return commonDao.queryListEntity(sql,map,UserInfoEntry.class);

    }


    public List<UserInfoEntry> getGuildMemberListByAdminId(Integer adminId,Integer index) {
        Map map = Maps.newHashMap();
        map.put("a",(index-1)*size);
        map.put("b",size);
        map.put("adminId",adminId);

        String sql = "SELECT * FROM user_info WHERE id IN ( SELECT member_id FROM guild_member WHERE admin_id = :adminId) ";

        sql +=   " ORDER BY id DESC LIMIT :a,:b ";
        return commonDao.queryListEntity(sql,map,UserInfoEntry.class);
    }

    public Integer getGuildMemberListByAdminIdCount(Integer adminId) {
        Map map = Maps.newHashMap();
        map.put("adminId",adminId);

        String sql = "SELECT COUNT(*) FROM user_info WHERE id IN ( SELECT member_id FROM guild_member WHERE admin_id = :adminId) ";
        return commonDao.getCountBy(sql,map);
    }
}
