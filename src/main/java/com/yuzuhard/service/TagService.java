package com.yuzuhard.service;


import com.yuzuhard.dao.TagDAO;
import com.yuzuhard.dto.CategoryDto;
import com.yuzuhard.dto.TagDto;
import com.yuzuhard.pojo.Category;
import com.yuzuhard.pojo.Tag;
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

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "tags")
public class TagService {
    public static final String deleted = "deleted";
    public static final String saved = "saved";


    @Autowired
    TagDAO tagDAO;

    //navigatePages分页导航栏要显示出的页的数量
    @Cacheable(key = "'tags-page-'+#p0+ '-' + #p1+ '-' + #p2")
    public Page4Navigator<Tag> list(int start, int size, int navigatePages){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //PageRequest存放分页信息，第一个参数page(start)是页码，就是本次查询要查询哪一页，默认是从0开始的。
        Pageable pageable = PageRequest.of(start, size, sort);
        //??需要把查询结果分页，所以使用findAll(Pageable)
        Page pageFromJPA = tagDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    //逻辑删除
    @CacheEvict(allEntries=true)
    public void delete(int id){
        Tag tag = get(id);
        tag.setStatus(deleted);
        update(tag);
    }

    @Cacheable(key = "'tags-one-'+ #p0")
    public Tag get(int id){
        return tagDAO.findById(id).get();
    }

    @CacheEvict(value = {"contents","categories","ct_t_relation","tags"},allEntries=true)
    public void update(Tag bean){
        tagDAO.save(bean);
    }

    @CacheEvict(value = {"contents","categories","ct_t_relation","tags"},allEntries=true)
    public void add(Tag bean){
        bean.setStatus(saved);
        tagDAO.save(bean);
    }

    @Cacheable(key = "'findIdNameUseStatus-'+ #p0")
    public List<TagDto> findIdNameUseStatus(String status){
        List<Object[]> objectList = tagDAO.findIdNameUseStatus(status);

        List list = new ArrayList();
        for (Object[] objects : objectList){
            TagDto tagDto = new TagDto(
                    (int)objects[0],
                    (String)objects[1]);
            list.add(tagDto);
        }
        return list;
    }
}
