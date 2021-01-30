package com.assistant.controller.web;

import com.assistant.pojo.ApiResult;
import com.assistant.pojo.BaseBannerEntry;
import com.assistant.service.web.ApplicationService;
import com.assistant.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.assistant.constant.SysConstant.ERROR_500;
import static com.assistant.constant.SysConstant.ERROR_PARAM;

@RestController
@RequestMapping(value = "/web/application/")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    /**
     * 新增/修改轮播信息
     * @param data
     * @return
     */
    @PostMapping(value = "/save/banner")
    public ApiResult saveOrUpdate(@RequestBody BaseBannerEntry data){
        try {
            if(data.isNull()){
                return ApiResult.returnError(ERROR_PARAM);
            }
            return applicationService.saveOrUpdate(data);
        }catch (Exception ex){
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    /**
     * 根据id删除轮播信息
     * @param bannerId
     * @return
     */
    @GetMapping(value = "/remove/banner/{bannerId}")
    public ApiResult removeById(@PathVariable Integer bannerId){
        try {
            if(CheckUtil.integerIsNull(bannerId)){
                return ApiResult.returnError(ERROR_PARAM);
            }
            return applicationService.removeById(bannerId);
        }catch (Exception ex){
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    /**
     * 根据id查询轮播信息
     * @param bannerId
     * @return
     */
    @GetMapping(value = "/get/banner/{bannerId}")
    public ApiResult findById(@PathVariable Integer bannerId){
        try{
            if(CheckUtil.integerIsNull(bannerId)){
                return ApiResult.returnError(ERROR_PARAM);
            }
            return applicationService.findById(bannerId);
        }catch (Exception ex){
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    /**
     * 查询全部轮播信息
     * @return
     */
    @GetMapping(value = "/get/banner/list")
    public ApiResult findAll(){
        try{
            return applicationService.findAll();
        }catch (Exception ex){
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    /**
     * 更改状态
     * @param bannerId
     * @return
     */
    @GetMapping(value = "/update/banner/state/{bannerId}")
    public ApiResult updateState(@PathVariable Integer bannerId){
        try{
            if(CheckUtil.integerIsNull(bannerId)){
                return ApiResult.returnError(ERROR_PARAM);
            }
            return applicationService.updateState(bannerId);
        }catch (Exception ex){
            return ApiResult.returnError(ERROR_500,ex);
        }
    }

    /**
     * 根据关键字模糊查询
     * @param keyWord
     * @return
     */
    @GetMapping(value = "/get/banner/word")
    public ApiResult findByWord(String keyWord){
        try{
            if (CheckUtil.stringIsNull(keyWord)){
                return applicationService.findAll();
            }
            return applicationService.findByWord(keyWord);
        }catch (Exception ex){
            return ApiResult.returnError(ERROR_500,ex);
        }
    }


}
