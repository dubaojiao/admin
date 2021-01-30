package com.assistant.domain.mysql;

import javax.persistence.*;
import java.util.Date;

/**
 * @Title 短信验证码表
 * @ClassName SmsInfo
 * @Author jsb_pbk
 * @Date 2019/2/25
 */
@Entity
public class SmsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(columnDefinition = "int(10) COMMENT'uid'")
    private Integer uid;
    /**
     *  类型  1 注册/登陆验证码  2  提现验证码
     */
    @Column(columnDefinition = "int(2) COMMENT'类型  1 注册/登陆验证码  2  提现验证码'",nullable = false)
    private Integer type;

    @Column(columnDefinition = "varchar(32) COMMENT'验证码'",nullable = false)
    private String code;
    @Column(columnDefinition = "varchar(32) COMMENT'手机号'",nullable = false)
    private String phone;

    /** 创建时间. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date time;
    @Column(columnDefinition = "int(10) COMMENT'操作人'")
    private Integer operatingUser;
    @Column(columnDefinition = "varchar(32) COMMENT'操作人'")
    private String operatingName;

    /**
     * 状态 1 成功  2 失败  3 等待  4 停止
     */
    @Column(columnDefinition = "int(2) COMMENT'状态 1 成功  2 失败  3 等待  4 停止'",nullable = false)
    private Integer state;
    /**
     * 消息
     */
    @Column(columnDefinition = "varchar(32) COMMENT'消息'",nullable = false)
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
