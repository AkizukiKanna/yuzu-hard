package com.yuzuhard.dto;

import com.yuzuhard.pojo.Content;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class CommentDto {
    int id;
    int CTid;
    String CTtitle;
    String text;
    Date created;
    String url;
    String ownerName;
    String mail;
    String ip;
    String status;
    String parentOwnerName;
    int parent;
    //前台用 已格式化日期
    String createdDate;

    public CommentDto() {
    }

    public CommentDto(int id, String text, String createdDate, String ownerName, int parent) {
        this.id = id;
        this.text = text;
        this.createdDate = createdDate;
        this.ownerName = ownerName;
        this.parent = parent;
    }

    public CommentDto(int id, int CTid, String CTtitle, String text, Date created, String url, String ownerName, String mail, String ip, String status, String parentOwnerName) {
        this.id = id;
        this.CTid = CTid;
        this.CTtitle = CTtitle;
        this.text = text;
        this.created = created;
        this.url = url;
        this.ownerName = ownerName;
        this.mail = mail;
        this.ip = ip;
        this.status = status;
        this.parentOwnerName = parentOwnerName;
    }
}
