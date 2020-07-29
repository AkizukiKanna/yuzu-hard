package com.yuzuhard.service;

import com.yuzuhard.dao.ContentDAO;
import com.yuzuhard.dto.ContentDto;
import com.yuzuhard.dto.Ct_t_relationshipDto;
import com.yuzuhard.pojo.Content;
import com.yuzuhard.util.Page4Navigator;
import com.yuzuhard.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

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
    public Page4Navigator<Object[]> list(int start, int size, int navigatePages){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //PageRequest存放分页信息，第一个参数page(start)是页码，就是本次查询要查询哪一页，默认是从0开始的。
        Pageable pageable = PageRequest.of(start, size, sort);
        //不需要查出摘要和文章，太大了
//        Page pageFromJPA = contentDAO.findNotAbstractAndText(pageable);
        Page<Object[]> pageFromJPA = contentDAO.findNotAbstractAndText(pageable);
        Page4Navigator<Object[]> page = new Page4Navigator<>(pageFromJPA, navigatePages);

        List list = new ArrayList();
        for (Object[] objects : pageFromJPA.getContent()){
            ContentDto contentDto = new ContentDto(
                    (int)objects[0],
                    (String)objects[1],
                    (Date)objects[2],
                    (Date)objects[3],
                    (String)objects[4],
                    (String)objects[5],
                    (String)objects[6],
                    (String)objects[7],
                    (int)objects[8]);
            list.add(contentDto);
        }
//        Page page = new PageImpl(list);

        page.setContent(list);
//        return new Page4Navigator<>(page,navigatePages);
        return page;
    }

    //根据id返回创建日期
    @Cacheable(key = "'getCreated-'+ #p0")
    public Date getCreated(int id){
        Date created = contentDAO.findCreatedById(id);
        ContentDto dto = new ContentDto(created);
        return dto.getCreated();
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
    public List<ContentDto> getByStatus(String status){
        List<Object[]> objectList = contentDAO.findByStatus(status);

        List list = new ArrayList();
        for (Object[] objects : objectList){
            ContentDto contentDto = new ContentDto(
                    (int)objects[0],
                    (String)objects[1],
                    (String)objects[2],
                    (Date)objects[3],
                    (Date)objects[4],
                    (String)objects[5],
                    (int)objects[6],
                    (String)objects[7]);
            list.add(contentDto);
        }
        return list;
    }


    @Cacheable(key = "'contentsByCategory-page-'+#p0+ '-' + #p1+ '-' + #p2+ '-' + #p3")
    public Page4Navigator<Object[]> listByCategory(int start, int size, int cgid ,int navigatePages){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        //不需要查出摘要和文章，太大了
        Page<Object[]> pageFromJPA = contentDAO.findByCategoryOrderById(cgid,pageable);
        Page4Navigator<Object[]> page = new Page4Navigator<>(pageFromJPA, navigatePages);

        List list = new ArrayList();

        for (Object[] objects : pageFromJPA.getContent()){
            ContentDto contentDto = new ContentDto(
                    (int)objects[0],
                    (String)objects[1],
                    (String)objects[2],
                    (Date)objects[3],
                    (Date)objects[4],
                    (String)objects[5],
                    (int)objects[6],
                    (String)objects[7]);
            Map<String, Object> map = new HashMap<>();

            Ct_t_relationshipService ct_t_relationshipService = SpringContextUtil.getBean(Ct_t_relationshipService.class);
            List<Ct_t_relationshipDto> tids = ct_t_relationshipService.findTidTnameByCTid((int) objects[0]);
            map.put("content",contentDto);
            map.put("tags",tids);
            list.add(map);
        }
//        Page page = new PageImpl(list);
        page.setContent(list);

        return page;
    }



    @Cacheable(key = "'contentsByTag-page-'+#p0+ '-' + #p1+ '-' + #p2+ '-' + #p3")
    public Page4Navigator<Object[]> listByTag(int start, int size, int tid ,int navigatePages){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        //不需要查出摘要和文章，太大了
        Page<Object[]> pageFromJPA = contentDAO.findByTagOrderById(tid,pageable);
        Page4Navigator<Object[]> page = new Page4Navigator<>(pageFromJPA, navigatePages);

        List list = new ArrayList();

        for (Object[] objects : pageFromJPA.getContent()){
            ContentDto contentDto = new ContentDto(
                    (int)objects[0],
                    (String)objects[1],
                    (String)objects[2],
                    (Date)objects[3],
                    (Date)objects[4],
                    (String)objects[5],
                    (int)objects[6]);
            Map<String, Object> map = new HashMap<>();

            Ct_t_relationshipService ct_t_relationshipService = SpringContextUtil.getBean(Ct_t_relationshipService.class);
            List<Ct_t_relationshipDto> tids = ct_t_relationshipService.findTidTnameByCTid((int) objects[0]);
            map.put("content",contentDto);
            map.put("tags",tids);
            list.add(map);
        }

        page.setContent(list);

        return page;
    }

}
