package com.yuzuhard.service;

import com.yuzuhard.dao.CommentDAO;
import com.yuzuhard.dto.CommentDto;
import com.yuzuhard.dto.ContentDto;
import com.yuzuhard.pojo.Comment;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "comments")
public class CommentService {
    //审核中、已发布、已删除
    public static final String checking = "checking";
    public static final String published = "published";
    public static final String deleted = "deleted";

    List resultsAll = null;

    @Autowired
    CommentDAO commentDAO;

    @Cacheable(key = "'comments-page-'+#p0+ '-' + #p1+ '-' + #p2")
    public Page4Navigator<Object[]> list(int start, int size, int navigatePages){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Object[]> pageFromJPA = commentDAO.findAllComments(pageable);
        Page4Navigator<Object[]> page = new Page4Navigator<>(pageFromJPA, navigatePages);

        List list = new ArrayList();
        for (Object[] objects : pageFromJPA.getContent()){
            CommentDto commentDto = new CommentDto(
                    (int)objects[0],
                    (int)objects[1],
                    (String)objects[2],
                    (String)objects[3],
                    (Date)objects[4],
                    (String)objects[5],
                    (String)objects[6],
                    (String)objects[7],
                    (String)objects[8],
                    (String)objects[9],
                    (String)objects[10]
                    );
            commentDto.setText(HtmlUtils.htmlUnescape((String)objects[3]));
            list.add(commentDto);
        }
        page.setContent(list);
        return page;
    }


    @Transactional
    @CacheEvict(allEntries=true)
    public void delete(int id){
        commentDAO.updateStatus(id,deleted);
    }

    @Transactional
    @CacheEvict(allEntries=true)
    public void undo(int id){
        commentDAO.updateStatus(id,published);
    }

    //前台分页评论
    @Cacheable(key = "'commentsByCTid-page-'+#p0+ '-' + #p1+ '-' + #p2 + '-' + #p3")
    public Page4Navigator<Object[]> listByCTid(int ctid ,int start, int size, int navigatePages){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Object[]> pageFromJPA = commentDAO.findByCTid(ctid,pageable);
        Page4Navigator<Object[]> page = new Page4Navigator<>(pageFromJPA, navigatePages);

        List list = new ArrayList();
        for (Object[] objects : pageFromJPA.getContent()){

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format((Date)objects[2]);

            CommentDto commentDto = new CommentDto(
                    (int)objects[0],
                    (String)objects[1],
                    format,
                    (String)objects[3],
                    (int)objects[4]
            );

            list.add(commentDto);


        }
        Collections.reverse(list);
        resultsAll = new ArrayList<>();
        resultsAll.addAll(list);
        searchTree(list);
        page.setContent(resultsAll);
        return page;
    }

    public void searchTree(List<CommentDto> list){
        if (list.isEmpty())
            return ;

        List<CommentDto> results = new ArrayList<>();

        for (CommentDto c : list){
            int id = c.getId();
            List<Object[]> objectsList = commentDAO.findChildByParent(id);
            if (null == objectsList)
                continue;
            for (Object[] objects : objectsList){

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = sdf.format((Date)objects[2]);

                CommentDto commentDto = new CommentDto(
                        (int)objects[0],
                        (String)objects[1],
                        format,
                        (String)objects[3],
                        (int)objects[4]
                );
                results.add(commentDto);
            }
        }
        resultsAll.addAll(results);

        searchTree(results);

    }


    @CacheEvict(allEntries=true)
    public void add(Comment bean){
        bean.setStatus(published);
        commentDAO.save(bean);
    }

    @Cacheable(key = "'comments-one-'+ #p0")
    public Comment get(int id){
        return commentDAO.findById(id).get();
    }

    @Cacheable(key = "'commentsRecently-'+#p0")
    public List<CommentDto> findRecentlyComments(int num){
        List<Object[]> objects = commentDAO.findRecentlyComments(num);
        List list = new ArrayList();
        for (Object[] o : objects){
            CommentDto commentDto = new CommentDto(
                    (Integer)o[0],
                    (Integer)o[1],
                    (String)o[2],
                    (String)o[3],
                    (Date) o[4],
                    (String) o[5],
                    (String) o[6]);

            list.add(commentDto);
        }
        return list;
    }
}
