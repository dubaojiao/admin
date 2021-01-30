package com.assistant.controller.web;


import com.assistant.constant.SysConstant;
import com.assistant.pojo.ApiResult;
import com.assistant.service.CommonService;
import com.assistant.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件的控制器
 */
@RestController
@RequestMapping(value = "/web/")
public class FileController {


    @Autowired
    CommonService commonService;

    /**
     *  会员的图片上传
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value = "member/img/upload")
    public ApiResult memberImgUpload(@RequestParam("file") MultipartFile file, String type) {
        try {
            return CheckUtil.objectIsNull(file) || CheckUtil.stringIsNull(type)
                    ?ApiResult.returnError("不能未空")
                    :commonService.fileUpload(type,file);
        }catch (Exception ex){
            ex.printStackTrace();
            return  ApiResult.returnError(SysConstant.ERROR_500,ex);
        }

    }

}
