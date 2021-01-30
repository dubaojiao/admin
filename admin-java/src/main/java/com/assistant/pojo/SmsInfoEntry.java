package com.assistant.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title
 * @ClassName SmsInfo
 * @Author jsb_pbk
 * @Date 2019/2/25
 */
public class SmsInfoEntry implements Serializable {

    private Long id;


    private Integer uid;
    /**
     *  类型  1 注册/登陆验证码  2  提现验证码
     */
    private Integer type;


    private String code;

    private String phone;

    /** 创建时间. */

    private Date time;

    private Integer operatingUser;

    private String operatingName;

    /**
     * 状态 1 成功  2 失败  3 等待  4 停止
     */

    private Integer state;
    /**
     * 消息
     */
    private String msg;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getOperatingUser() {
        return operatingUser;
    }

    public void setOperatingUser(Integer operatingUser) {
        this.operatingUser = operatingUser;
    }

    public String getOperatingName() {
        return operatingName;
    }

    public void setOperatingName(String operatingName) {
        this.operatingName = operatingName;
    }
}
