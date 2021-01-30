package com.assistant.domain.mysql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Banner
 */
@Entity
public class BaseBanner implements Serializable {

    /** 主键，自增Id */
    @Id
    @Column(columnDefinition = "int(10) COMMENT '自增主键'", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 标题 */
    @Column(columnDefinition = "varchar(64) COMMENT '标题' DEFAULT ''", nullable = false)
    private String title = "";

    /** 跳转类型1 APP页面 2  WEB页面*/
    @Column(columnDefinition = "int(2) COMMENT ' 跳转类型 1 APP页面 2  WEB页面'", nullable = false)
    private Integer  type;

    /** 链接 type =1 源生唯一标识， type = 2 的时候 表示页面url*/
    @Column(columnDefinition = "varchar(255) COMMENT '链接 type =1 源生唯一标识， type = 2 的时候 表示页面url' DEFAULT ''", nullable = false)
    private String url = "";

    /** 图片id */
    @Column(columnDefinition = "varchar(255) COMMENT '图片id 如果多张图片 使用，进行拼接' DEFAULT ''", nullable = false)
    private String img = "";

    /** 排序（正向） */
    @Column(columnDefinition = "int(10) COMMENT '排序（正序）'", nullable = false)
    private Integer sort = 1;

    /** 是否启用 */
    @Column(columnDefinition = "tinyint(1) COMMENT '是否启用'", nullable = false)
    private boolean enable = true;

    /** 创建时间 */
    @Column(columnDefinition = "datetime COMMENT '创建时间'", nullable = false)
    private Date gmtCreate;

    /** 添加人姓名 */
    @Column(columnDefinition = "varchar(32) COMMENT '添加人姓名' DEFAULT ''", nullable = false)
    private String addUser = "";

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

}
