package com.yuzuhard.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "content")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name="cgid")
    Category category;
    String title;
    String articleAbstract;
    Date created;
    Date modified;
    String text;
    String firstImg;
    String status;
    String password;
    String allowComment;
    int pvNum;


    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", articleAbstract='" + articleAbstract + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", text='" + text + '\'' +
                ", firstImg='" + firstImg + '\'' +
                ", status='" + status + '\'' +
                ", password='" + password + '\'' +
                ", allowComment='" + allowComment + '\'' +
                ", pvNum=" + pvNum +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(String allowComment) {
        this.allowComment = allowComment;
    }

    public int getPvNum() {
        return pvNum;
    }

    public void setPvNum(int pvNum) {
        this.pvNum = pvNum;
    }
}
