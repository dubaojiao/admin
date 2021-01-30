package com.assistant.domain.mysql;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Title 用户信息表
 * @ClassName UserInfo
 * @Author jsb_pbk
 * @Date 2019/2/22
 */
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**用户编码*/
    @Column(columnDefinition = "varchar(32) COMMENT '用户编码' DEFAULT ''",nullable = false)
    private String code;

    /** 用户名称 */
    @Column(columnDefinition = "varchar(32) COMMENT '用户昵称' DEFAULT ''",nullable = false)
    private String nickname;
    /** 用户名称 */
    @Column(columnDefinition = "varchar(32) COMMENT '用户名称' DEFAULT ''",nullable = false)
    private String name;

    /** 用户手机号 */
    @Column(columnDefinition = "varchar(16) COMMENT '用户手机号' DEFAULT ''",nullable = false)
    private String phone;

    /** 用户密码 */
    @Column(columnDefinition = "varchar(32) COMMENT '用户密码' DEFAULT ''",nullable = false)
    private String pwd = "e10adc3949ba59abbe56e057f20f883e";

    /** 用户状态.用户状态1正常 2停用,3-离职 */
    @Column(columnDefinition = "int(2) COMMENT '用户状态:1-正常,2-停用,3-离职' DEFAULT 1",nullable = false)
    private Integer state = 1;
    /** 用户身份证号. */
    @Column(columnDefinition = "varchar(32) COMMENT '用户身份证号' DEFAULT ''",nullable = false)
    private String idCard = "";

    /** 用户身份证. */
    @Column(columnDefinition = "varchar(1000) COMMENT '用户身份证' DEFAULT ''",nullable = false)
    private String idCardPath = "";

    /** 头像路径. */
    @Column(columnDefinition = "varchar(500) COMMENT '头像路径' DEFAULT 'default.png'",nullable = false)
    private String headPath = "default.png";

    /** 性别 */
    @Column(columnDefinition = "varchar(32) COMMENT '性别' DEFAULT ''",nullable = false)
    private String gender = "";

    /** 生日. */
    @Column(columnDefinition = "varchar(32) COMMENT '生日' DEFAULT ''",nullable = false)
    private String birthday = "";

    /** 备注. */
    @Column(columnDefinition = "varchar(128) COMMENT '备注'")
    private String remark = "";

    /** 创建时间. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdTime = new Date();

    /** 最后登陆时间. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime = new Date();

    /** 登陆次数. */
    @Column(columnDefinition = "int(10) COMMENT '登陆次数'",nullable = false)
    private Integer loginNum = 0;

    @Column(columnDefinition = "int(2) COMMENT '用户类型 0 全部 1 后台用户 2 公会会长 3 公会成员  4 客户'",nullable = false)
    private Integer userType = 1;

    @Column(columnDefinition = "varchar(18) COMMENT '身高'")
    private String  height;

    @Column(columnDefinition = "varchar(18) COMMENT '身高'")
    private String age;

    private Integer parentId;
    /**0 未激活 1 激活  2 荣耀会员*/
    private Integer memberLevel;

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardPath() {
        return idCardPath;
    }

    public void setIdCardPath(String idCardPath) {
        this.idCardPath = idCardPath;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
