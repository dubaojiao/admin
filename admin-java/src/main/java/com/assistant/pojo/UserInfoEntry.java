package com.assistant.pojo;

import java.util.Date;

/**
 * @Title
 * @ClassName UserInfoEntry
 * @Author jsb_pbk
 * @Date 2018/9/13
 */
public class UserInfoEntry {

    private Integer id;
    /**用户账号*/
    private String code;

    /** 用户名称 */
    private String nickname;
    /** 用户名称 */
    private String name;

    /** 用户手机号 */
    private String phone;

    /** 用户密码 */
    private String pwd = "e10adc3949ba59abbe56e057f20f883e";

    /** 用户状态.用户状态1正常 2停用,3-离职 */
    private Integer state = 1;
    /** 用户身份证号. */
    private String idCard = "";

    /** 用户身份证. */
    private String idCardPath = "";

    /** 头像路径. */
    private String headPath = "default.png";

    private String basePath;

    /** 性别 */
    private String gender = "";

    /** 生日. */
    private String birthday = "";

    /** 备注. */
    private String remark = "";

    /** 创建时间. */
    private Date createdTime = new Date();

    /** 最后登陆时间. */
    private Date loginTime = new Date();

    /** 登陆次数. */
    private Integer loginNum = 0;

    /**
     * 用户类型 0 全部 1 后台用户 2 公会会长 3 公会成员  4 客户
     */
    private Integer userType = 1;

    /**
     * 身高
     */
    private String  height;

    /**
     * 年龄
     */
    private String age;

    private String roleName;
    /**小姐专用字段*/
    /**
     * 0 待审核 1 审核通过 2 拒绝  99 未提交资料
     */
    private Integer memberFlag;
    /**
     * 0 不可用  1 在线  2 不在线
     */
    private Integer upFlag;

    private String memberMsg;

    /**客户专用字段*/
    /**0 未激活 1 激活  2 荣耀会员*/
    private String memberLevel;

    private String memberLevelName;

    private String expireTime;

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getMemberLevelName() {
        return memberLevelName;
    }

    public void setMemberLevelName(String memberLevelName) {
        this.memberLevelName = memberLevelName;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getMemberMsg() {
        return memberMsg;
    }

    public void setMemberMsg(String memberMsg) {
        this.memberMsg = memberMsg;
    }

    public Integer getMemberFlag() {
        return memberFlag;
    }

    public void setMemberFlag(Integer memberFlag) {
        this.memberFlag = memberFlag;
    }

    public Integer getUpFlag() {
        return upFlag;
    }

    public void setUpFlag(Integer upFlag) {
        this.upFlag = upFlag;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
