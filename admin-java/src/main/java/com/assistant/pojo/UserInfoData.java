package com.assistant.pojo;

import java.io.Serializable;

public class UserInfoData implements Serializable {
    private Integer id;

    /** 用户名称 */
    private String nickname;
    /** 用户名称 */
    private String name;

    /** 用户密码 */
    private String pwd ;

    /** 用户身份证号. */
    private String idCard = "";

    /** 性别 */
    private String gender = "";

    /** 生日. */
    private String birthday = "";

    /** 备注. */
    private String remark = "";

    /**
     * 身高
     */
    private String  height;

    /**
     * 年龄
     */
    private String age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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
