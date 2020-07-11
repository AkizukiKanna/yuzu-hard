package com.yuzuhard.service;


import com.yuzuhard.dao.TagDAO;
import com.yuzuhard.pojo.Category;
import com.yuzuhard.pojo.Tag;
import com.yuzuhard.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    public static final String deleted = "deleted";
    public static final String saved = "saved";


    @Autowired
    TagDAO tagDAO;

    //navigatePages分页导航栏要显示出的页的数量
    public Page4Navigator<Tag> list(int start, int size, int navigatePages){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //PageRequest存放分页信息，第一个参数page(start)是页码，就是本次查询要查询哪一页，默认是从0开始的。
        Pageable pageable = PageRequest.of(start, size, sort);
        //??需要把查询结果分页，所以使用findAll(Pageable)
        Page pageFromJPA = tagDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    //逻辑删除
    public void delete(int id){
        Tag tag = get(id);
        tag.setStatus(deleted);
        update(tag);
    }

    public Tag get(int id){
        return tagDAO.findById(id).get();
    }

    public void update(Tag bean){
        tagDAO.save(bean);
    }

    public void add(Tag bean){
        bean.setStatus(saved);
        tagDAO.save(bean);
    }
}
