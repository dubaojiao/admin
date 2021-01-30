package com.assistant.service.web;

import com.assistant.component.UserCacheComponent;
import com.assistant.constant.SysConstant;
import com.assistant.dao.BaseBannerDao;
import com.assistant.domain.mysql.BaseBanner;
import com.assistant.domain.redis.LoginUser;
import com.assistant.pojo.ApiResult;
import com.assistant.pojo.BaseBannerEntry;
import com.assistant.util.CheckUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationService {

    @Autowired
    BaseBannerDao baseBannerDao;

    @Autowired
    UserCacheComponent userCacheComponent;

    public ApiResult saveOrUpdate(BaseBannerEntry data){
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        BaseBanner baseBanner = baseBannerDao.findById(data.getId());
        if(CheckUtil.objectIsNull(baseBanner)){
            baseBanner = new BaseBanner();
            baseBanner.setAddUser(loginUser.getUserName());
            baseBanner.setGmtCreate(new Date());
        }
        baseBanner.setEnable(data.getEnable());
        baseBanner.setImg(data.getImg());
        baseBanner.setTitle(data.getTitle());
        baseBanner.setUrl(data.getUrl());
        baseBanner.setSort(data.getSort());
        baseBanner.setType(data.getType());
        return ApiResult.returnSuccess(SysConstant.SUCCESS,baseBannerDao.saveOrUpdate(baseBanner));
    }

    public ApiResult findById(Integer id) throws Exception{
        LoginUser loginUser = userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        BaseBanner baseBanner = baseBannerDao.findById(id);
        if(CheckUtil.objectIsNull(baseBanner)){
            return ApiResult.returnError("无法获取到轮播图信息");
        }
        return ApiResult.returnSuccess(SysConstant.SUCCESS,getImgUrl(baseBanner));
    }

    public ApiResult findAll() throws Exception{
        LoginUser loginUser =  userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        List<BaseBanner> bannerList = baseBannerDao.findAll();
        List<BaseBannerEntry> entryList = Lists.newArrayList();
        for (BaseBanner baseBanner : bannerList){
            entryList.add(getImgUrl(baseBanner));
        }
        return ApiResult.returnSuccess(SysConstant.SUCCESS,separateList(entryList));
    }


    public ApiResult updateState(Integer id) throws Exception{
        LoginUser loginUser =   userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)){
            return ApiResult.returnAuthorization();
        }
        BaseBanner baseBanner = baseBannerDao.findById(id);
        if(CheckUtil.objectIsNull(baseBanner)){
            return ApiResult.returnError("失败：ID有误");
        }
        if(baseBannerDao.updateState(id,baseBanner.getEnable() == true ? false : true)){
            return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
        }
        return ApiResult.returnError("失败：状态变更失败");
    }
    @Value("${spring.file.img.http.path}")
    String basePath;
    private BaseBannerEntry getImgUrl(BaseBanner baseBanner) throws Exception{
        BaseBannerEntry entry = new BaseBannerEntry();
        BeanUtils.copyProperties(baseBanner,entry);
        entry.setBasePath(basePath);
        return entry;
    }

    private List<List<BaseBannerEntry>> separateList(List<BaseBannerEntry> entryList) throws Exception{
        //启用状态
        List<BaseBannerEntry> availableList = Lists.newArrayList();
        //禁用状态
        List<BaseBannerEntry> disableList = Lists.newArrayList();
        //保存两个list
        List<List<BaseBannerEntry>> resultList = Lists.newArrayList();
        resultList.add(availableList);
        for(BaseBannerEntry item : entryList){
            if(item.getEnable() == true){
                availableList.add(item);
            }else {
                disableList.add(item);
            }
        }
        resultList.add(disableList);
        return resultList;
    }

    public ApiResult findByWord(String keyWord) throws Exception{
        LoginUser loginUser =   userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)) {
            return ApiResult.returnAuthorization();
        }
        List<BaseBanner> baseBannerList = baseBannerDao.findByWord(keyWord);
        List<BaseBannerEntry> rows = Lists.newArrayList();
        for(BaseBanner banner :  baseBannerList){
            BaseBannerEntry entry = new BaseBannerEntry();
            BeanUtils.copyProperties(getImgUrl(banner),entry);
            rows.add(entry);
        }
        return ApiResult.returnSuccess(SysConstant.C_SUCCESS,separateList(rows));
    }

    public ApiResult removeById(Integer id) throws Exception{
        LoginUser loginUser =   userCacheComponent.getUserCache();
        if(CheckUtil.objectIsNull(loginUser)) {
            return ApiResult.returnAuthorization();
        }
        baseBannerDao.removeById(id);
        return ApiResult.returnSuccess(SysConstant.C_SUCCESS);
    }

}
