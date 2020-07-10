package com.yuzuhard.service;

import com.yuzuhard.dao.CategoryDAO;
import com.yuzuhard.pojo.Category;
import com.yuzuhard.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    public static final String deleted = "deleted";
    public static final String saved = "saved";


    @Autowired
    CategoryDAO categoryDAO;

    //navigatePages分页导航栏要显示出的页的数量
    public Page4Navigator<Category> list(int start, int size, int navigatePages){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //PageRequest存放分页信息，第一个参数page(start)是页码，就是本次查询要查询哪一页，默认是从0开始的。
        Pageable pageable = PageRequest.of(start, size, sort);
        //??需要把查询结果分页，所以使用findAll(Pageable)
        Page pageFromJPA = categoryDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    //逻辑删除
    public void delete(int id){
        Category category = get(id);
        category.setStatus(deleted);
        update(category);
    }

    public Category get(int id){
        return categoryDAO.findById(id).get();
    }

    public void update(Category bean){
        categoryDAO.save(bean);
    }
}
