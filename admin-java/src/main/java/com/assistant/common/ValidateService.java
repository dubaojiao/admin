package com.assistant.common;

import com.assistant.annotation.DV;
import com.assistant.util.CheckUtil;

import java.lang.reflect.Field;

public class ValidateService {

    public static void valid(Object object)throws Exception{
        Class objectClass = object.getClass();
        //获取所有的属性
        Field[] fields = objectClass.getDeclaredFields();

        for(Field field:fields){
            //对于private私有化的成员变量，通过setAccessible来修改器访问权限
            field.setAccessible(true);
            validate(field,object);
            //重新设置会私有权限
            field.setAccessible(false);
        }
    }

    public static void validate(Field field,Object object) throws Exception {
        DV dv = field.getAnnotation(DV.class);
        if (dv == null) {
            return ;
        }
        String desc = dv.desc().equals("")?field.getName():dv.desc();
        Object value = field.get(object);
        if(!dv.nullable()){
            if(value == null || CheckUtil.objectIsNull(value.toString())){
                throw  new Exception(desc+"未能为null");
            }
        }

    }
}
