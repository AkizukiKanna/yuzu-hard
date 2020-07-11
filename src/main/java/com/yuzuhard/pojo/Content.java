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
}
