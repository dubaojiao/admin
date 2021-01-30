package com.assistant.pojo;

import com.assistant.util.CheckUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title
 * @ClassName
 * @Author linzhijian
 * @Date 2019/3/20 14:43
 */
public class BaseBannerEntry implements Serializable {

    /** 主键，自增Id */
    private Integer id;

    /** 标题 */
    private String title = "";

    /** 跳转类型1 APP页面 2  WEB页面*/
    private Integer  type;

    /** 链接 type =1 源生唯一标识， type = 2 的时候 表示页面url*/
    private String url = "";

    /** 图片id */
    private String img = "";

    /**
     * 缩略图url
     */
    private String thumbnailUrl;

    /**
     * 背景图url
     */
    private String backGroundUrl;

    /** 排序（正向） */
    private Integer sort = 1;

    /** 是否启用 */
    private boolean enable = false;

    /** 创建时间 */
    private Date gmtCreate;

    /** 添加人姓名 */
    private String addUser = "";

    private String basePath;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getType() {
        return this.type;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getBackGroundUrl() {
        return backGroundUrl;
    }

    public void setBackGroundUrl(String backGroundUrl) {
        this.backGroundUrl = backGroundUrl;
    }

    public boolean isNull(){
        if(CheckUtil.objectIsNull(this)){
            return true;
        }
        if(CheckUtil.stringIsNull(this.title) || CheckUtil.integerIsNull(this.type)){
            return true;
        }
        return false;
    }
}
