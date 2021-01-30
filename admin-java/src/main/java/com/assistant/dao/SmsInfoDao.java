package com.assistant.dao;

import com.assistant.common.CommonDao;
import com.assistant.domain.mysql.SmsInfo;
import com.assistant.pojo.SmsInfoEntry;
import com.assistant.repos.SmsInfoRepos;
import com.assistant.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title
 * @ClassName SmsInfoDao
 * @Author jsb_pbk
 * @Date 2019/2/25
 */
@Service
public class SmsInfoDao {

    @Autowired
    SmsInfoRepos smsInfoRepos;
    @Autowired
    CommonDao commonDao;
    @Value("${spring.page.size.web}")
    Integer webSize;

    public SmsInfo save(SmsInfo data){
        return smsInfoRepos.save(data);
    }


    public List<SmsInfoEntry> getSmsList(String phone, Integer index) {
        int a = (index - 1)*webSize;
        int b = webSize;
        Map<String,Object> map = new HashMap<>();
        map.put("a",a);
        map.put("b",b);
        String sql = "SELECT * FROM sms_info WHERE 1=1";
        if(!CheckUtil.stringIsNull(phone)){
            sql +=" AND phone LIKE :phone";
            map.put("phone","%"+phone+"%");
        }
        sql += " ORDER BY id DESC LIMIT :a,:b";
        return commonDao.queryListEntity(sql,map,SmsInfoEntry.class);
    }

    public Integer  getSmsListCount(String phone) {
        Map<String,Object> map  = new HashMap<>();
        String sql = "SELECT COUNT(*) FROM sms_info WHERE 1=1";
        if(!CheckUtil.stringIsNull(phone)){
            sql +=" AND phone LIKE :phone";
            map.put("phone","%"+phone+"%");
        }
        return commonDao.getCountBy(sql,map);
    }
}
