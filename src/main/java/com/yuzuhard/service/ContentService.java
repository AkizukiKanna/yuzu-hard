package com.yuzuhard.service;

import com.yuzuhard.dao.ContentDAO;
import com.yuzuhard.pojo.Content;
import com.yuzuhard.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "contents")
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
    @Cacheable(key = "'contents-page-'+#p0+ '-' + #p1+ '-' + #p2")
    public Page4Navigator<Content> list(int start, int size, int navigatePages){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //PageRequest存放分页信息，第一个参数page(start)是页码，就是本次查询要查询哪一页，默认是从0开始的。
        Pageable pageable = PageRequest.of(start, size, sort);
        //不需要查出摘要和文章，太大了
        Page pageFromJPA = contentDAO.findNotAbstractAndText(pageable);
//        Page pageFromJPA = contentDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    //根据id返回创建日期
    @Cacheable(key = "'getCreated-'+ #p0")
    public Date getCreated(int id){
        return contentDAO.findCreatedById(id);
    }


    //新增发布
    @CacheEvict(allEntries=true)
    public int addPulish(Content content){
        content.setText(HtmlUtils.htmlEscape(content.getText()));
        content.setStatus(published);
        return contentDAO.save(content).getId();
    }
    //新增草稿
    @CacheEvict(allEntries=true)
    public int addDraft(Content content){
        content.setText(HtmlUtils.htmlEscape(content.getText()));
        content.setStatus(draft);
        return contentDAO.save(content).getId();
    }

    @Cacheable(key = "'contents-one-'+ #p0")
    public Content get(int id){
        Content content = contentDAO.findById(id).get();
        content.setText(HtmlUtils.htmlUnescape(content.getText()));
        return content;
    }

    //更新发布
    @CacheEvict(allEntries=true)
    public int updatePulish(Content content){
        content.setText(HtmlUtils.htmlEscape(content.getText()));
        content.setStatus(published);
        return contentDAO.save(content).getId();
    }
    //更新草稿
    @CacheEvict(allEntries=true)
    public int updateDraft(Content content){
        content.setText(HtmlUtils.htmlEscape(content.getText()));
        content.setStatus(draft);
        return contentDAO.save(content).getId();
    }

    //更新状态
    @CacheEvict(allEntries=true)
    public void updateStatus(int id , String status){
        contentDAO.updateStatus(id,status);
    }

    @Transactional
    @CacheEvict(allEntries=true)
    public void delete(int id){
        updateStatus(id,deleted);
    }

    //根据status查询
    @Cacheable(key = "'getByStatus-'+ #p0")
    public List<Map<String, Object>> getByStatus(String status){
        return contentDAO.findByStatus(status);
    }

}
