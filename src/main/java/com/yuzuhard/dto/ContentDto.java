package com.yuzuhard.dto;

import com.yuzuhard.pojo.Category;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContentDto {
    int id;
    String title;
    Date created;
    Date modified;
    String firstImg;
    String status;
    String password;
    String allowComment;
    int pvNum;
}
