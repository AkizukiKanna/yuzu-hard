package com.yuzuhard.dto;

import com.yuzuhard.pojo.Category;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class ContentDto {
    int id;
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
    String categoryName;

    public ContentDto() {
    }

    public ContentDto(Date created) {
        this.created = created;
    }

    public ContentDto(int id, String title, Date created, Date modified, String firstImg, String status, String password, String allowComment, int pvNum) {
        this.id = id;
        this.title = title;
        this.created = created;
        this.modified = modified;
        this.firstImg = firstImg;
        this.status = status;
        this.password = password;
        this.allowComment = allowComment;
        this.pvNum = pvNum;
    }

    public ContentDto(int id, String title, String articleAbstract, Date created, Date modified, String firstImg, int pvNum) {
        this.id = id;
        this.title = title;
        this.articleAbstract = articleAbstract;
        this.created = created;
        this.modified = modified;
        this.firstImg = firstImg;
        this.pvNum = pvNum;
    }

    public ContentDto(int id, String title, String articleAbstract, Date created, Date modified, String firstImg, int pvNum, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
        this.title = title;
        this.articleAbstract = articleAbstract;
        this.created = created;
        this.modified = modified;
        this.firstImg = firstImg;
        this.pvNum = pvNum;
    }
}
