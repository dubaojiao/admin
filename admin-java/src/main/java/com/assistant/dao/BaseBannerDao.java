package com.assistant.dao;

import com.assistant.common.CommonDao;
import com.assistant.domain.mysql.BaseBanner;
import com.assistant.repos.BaseBannerRepos;
import com.assistant.util.CheckUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Title
 * @ClassName
 * @Author linzhijian
 * @Date 2019/3/20 14:45
 */
@Service
public class BaseBannerDao {

    @Autowired
    BaseBannerRepos baseBannerRepos;
    @Autowired
    CommonDao commonDao;

    public BaseBanner saveOrUpdate(BaseBanner data){
        return baseBannerRepos.save(data);
    }

    public BaseBanner findById(Integer id){
        if(CheckUtil.integerIsNull(id)){
            return null;
        }
        Optional<BaseBanner> baseBanner = baseBannerRepos.findById(id);
        if(baseBanner.isPresent()){
            return baseBanner.get();
        }
        return null;
    }

    public List<BaseBanner> findAll(){
        return baseBannerRepos.findAll();
    }

    public boolean updateState(Integer id,Boolean enable){
        String sql = "UPDATE base_banner SET enable = :enable WHERE id = :id";
        Map map = Maps.newHashMap();
        map.put("enable",enable);
        map.put("id",id);
        return commonDao.deleteOrUpDate(sql,map) == 1;

    }

    public List<BaseBanner> findByWord(String keyWord){
        String sql = "SELECT id,add_user,`enable`,gmt_create,img,sort,title,type,url FROM base_banner WHERE title LIKE :keyWord";
        Map map = Maps.newHashMap();
        String word = "%"+keyWord+"%";
        map.put("keyWord",word);
        return commonDao.queryListEntity(sql,map,BaseBanner.class);
    }

    public void removeById(Integer id){
        baseBannerRepos.deleteById(id);
    }

    public List<BaseBanner> findImgByState() {
        String sql = "SELECT id,add_user,`enable`,gmt_create,img,sort,title,type,url FROM base_banner WHERE enable = 1";
        return commonDao.queryListEntity(sql,new HashMap<>(),BaseBanner.class);
    }
}
