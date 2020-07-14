package com.yuzuhard.service;

import com.yuzuhard.dao.ContentDAO;
import com.yuzuhard.pojo.Content;
import com.yuzuhard.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

@Service
public class ContentService {
    //草稿、已发布、已删除
    public static final String draft = "draft";
    public static final String published = "published";
    public static final String deleted = "deleted";

    //允许评论、禁止评论
    public static final String allow = "allow";
    public static final String forbid = "forbid";

    @Autowired
    ContentDAO contentDAO;

    //navigatePages分页导航栏要显示出的页的数量
    public Page4Navigator<Content> list(int start, int size, int navigatePages){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //PageRequest存放分页信息，第一个参数page(start)是页码，就是本次查询要查询哪一页，默认是从0开始的。
        Pageable pageable = PageRequest.of(start, size, sort);
        //不需要查出摘要和文章，太大了
        Page pageFromJPA = contentDAO.findNotAbstractAndText(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }




    //发布
    public int addPulish(Content content){
        content.setText(HtmlUtils.htmlEscape(content.getText()));
        content.setStatus(published);
        return contentDAO.save(content).getId();
    }
    //草稿
    public int addDraft(Content content){
        content.setText(HtmlUtils.htmlEscape(content.getText()));
        content.setStatus(draft);
        return contentDAO.save(content).getId();
    }
    public Content get(int id){
        Content content = contentDAO.findById(id).get();
        content.setText(HtmlUtils.htmlUnescape(content.getText()));
        return content;
    }
}
