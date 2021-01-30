package com.assistant.service;

import com.assistant.common.SmsClient;
import com.assistant.component.SysCacheComponent;
import com.assistant.constant.SysConstant;
import com.assistant.dao.SmsInfoDao;
import com.assistant.dao.UserInfoDao;
import com.assistant.domain.mysql.SmsInfo;
import com.assistant.pojo.ApiResult;
import com.assistant.pojo.FileUploadData;
import com.assistant.util.CheckUtil;
import com.assistant.util.FileUtil;
import com.assistant.util.RandomCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommonService {
    @Value("${spring.profiles.active}")
    String active;
    @Autowired
    SmsClient smsClient;
    @Autowired
    SmsInfoDao smsInfoDao;
    @Autowired
    SysCacheComponent sysCacheComponent;

    @Value("${spring.file.img.local.path}")
    String imgLocalPath;
    @Value("${spring.file.img.http.path}")
    String httpFilePath;
    @Autowired
    UserInfoDao userInfoDao;

    public ApiResult sendCode(String phone)throws Exception{
        SmsInfo smsInfo  = new SmsInfo();
        smsInfo.setPhone(phone);
        smsInfo.setTime(new Date());
        smsInfo.setType(1);
        String code = sysCacheComponent.getCodeByPhone(phone);
        if(CheckUtil.stringIsNull(code)){
            code  = RandomCodeUtil.randomNum(4);
            sysCacheComponent.cacheCode(phone,code);
        }
        smsInfo.setCode(code);
        //TODO 发送
        ApiResult result;
        if("dev".equals(active)){
            result = ApiResult.returnSuccess(SysConstant.SUCCESS);//smsClient.sendVCode(phone,code);
        }else {
            result = smsClient.sendVCode(phone,code);
        }
        if(!result.isSuccess()){
            sysCacheComponent.delCodeByPhone(phone);
            smsInfo.setState(2);
            smsInfo.setMsg(result.getMessage());
            smsInfoDao.save(smsInfo);

            return ApiResult.returnError(result.getMessage());
        }
        smsInfo.setState(1);
        smsInfo.setMsg(result.getMessage());
        smsInfoDao.save(smsInfo);

        if("dev".equals(active)){
            return ApiResult.returnSuccess("验证码发送成功",code);
        }else {
            return ApiResult.returnSuccess("验证码发送成功");
        }
    }


    public ApiResult fileUpload(String type, MultipartFile img) throws Exception{

        String allName =img.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = allName.substring(allName.lastIndexOf("."));
        String file_a = "";
        String a;
        file_a =  "/"+type+"/";
        a = RandomCodeUtil.getTimeCode("type");
        String fileName = file_a  + a +suffixName;
        // 文件上传后的路径
        File dest = new File(imgLocalPath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        //图片保存
        img.transferTo(dest);
        Map map = new HashMap();
        map.put("basePath",httpFilePath);
        map.put("img",fileName);
        return ApiResult.returnSuccess(SysConstant.C_SUCCESS,map);
    }


    public ApiResult fileUploadBase64(FileUploadData data) throws Exception {
        String fileName = data.getType()+"/"+RandomCodeUtil.getTimeCode(data.getType())+".png";
        String path = imgLocalPath+fileName;
        if(FileUtil.str2File(data.getBase64().replace("data:image/png;base64,",""),path)){
            Map map = new HashMap();
            map.put("basePath",httpFilePath);
            map.put("img",fileName);
            return ApiResult.returnSuccess(SysConstant.C_SUCCESS,map);
        }
        return ApiResult.returnError("文件写失败");
    }
}
