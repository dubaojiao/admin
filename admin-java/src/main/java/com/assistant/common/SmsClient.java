package com.assistant.common;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.assistant.component.SmsComponent;
import com.assistant.pojo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SmsClient {

    @Autowired
    SmsComponent smsComponent;


    public ApiResult sendVCode(String phone, String code) {
        System.out.println(phone);
        System.out.println(code);/*
        return ApiResult.returnSuccess("OK");*/

        try {
            SendSmsResponse sendSmsResponse = smsComponent.sendSms(phone,code);
            System.out.println(sendSmsResponse.getCode());
            System.out.println(sendSmsResponse.getMessage());
            System.out.println(sendSmsResponse.getBizId());
            System.out.println(sendSmsResponse.getRequestId());
            if("OK".equals(sendSmsResponse.getCode())){
               return ApiResult.returnSuccess("OK");
            }else {
                return ApiResult.returnError(sendSmsResponse.getMessage());
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return ApiResult.returnError("发送接口调用异常");
        }
    }
}
