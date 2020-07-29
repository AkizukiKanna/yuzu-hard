package com.yuzuhard.service;

import com.yuzuhard.dao.CategoryDAO;
import com.yuzuhard.dto.CategoryDto;
import com.yuzuhard.dto.ContentDto;
import com.yuzuhard.pojo.Category;
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
import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "categories")
public class CategoryService {
    public static final String deleted = "deleted";
    public static final String saved = "saved";


    @Autowired
    CategoryDAO categoryDAO;

    //navigatePages分页导航栏要显示出的页的数量
    @Cacheable(key = "'categories-page-'+#p0+ '-' + #p1+ '-' + #p2")
    public Page4Navigator<Category> list(int start, int size, int navigatePages) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //PageRequest存放分页信息，第一个参数page(start)是页码，就是本次查询要查询哪一页，默认是从0开始的。
        Pageable pageable = PageRequest.of(start, size, sort);
        //??需要把查询结果分页，所以使用findAll(Pageable)
        Page pageFromJPA = categoryDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    //逻辑删除
    @CacheEvict(allEntries=true)
    public void delete(int id) {
        Category category = get(id);
        category.setStatus(deleted);
        update(category);
    }

    @Cacheable(key = "'categories-one-'+ #p0")
    public Category get(int id) {
        return categoryDAO.findById(id).get();
    }

    @CacheEvict(value = {"contents","categories","ct_t_relation","tags"},allEntries=true)
    public void update(Category bean) {
        categoryDAO.save(bean);
    }

    @CacheEvict(value = {"contents","categories","ct_t_relation","tags"},allEntries=true)
    public void add(Category bean) {
        bean.setStatus(saved);
        categoryDAO.save(bean);
    }

    @Cacheable(key = "'findIdNameUseStatus-'+ #p0")
    public List<CategoryDto> findIdNameUseStatus(String status) {
        List<Object[]> objectList = categoryDAO.findIdNameUseStatus(status);

        List list = new ArrayList();
        for (Object[] objects : objectList){
            CategoryDto categoryDto = new CategoryDto(
                    (int)objects[0],
                    (String)objects[1]);
            list.add(categoryDto);
        }
        return list;
    }
}
