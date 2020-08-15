package com.yuzuhard.web;

import com.yuzuhard.dto.ContentDto;
import com.yuzuhard.dto.Ct_t_relationshipDto;
import com.yuzuhard.pojo.Comment;
import com.yuzuhard.pojo.Content;
import com.yuzuhard.service.CommentService;
import com.yuzuhard.service.ContentService;
import com.yuzuhard.service.Ct_t_relationshipService;
import com.yuzuhard.task.AsyncTask;
import com.yuzuhard.util.IPUtill;
import com.yuzuhard.util.Page4Navigator;
import com.yuzuhard.util.Result;
import com.yuzuhard.util.SendMailSmtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/fore")
public class ForeController {
    @Autowired
    ContentService contentService;
    @Autowired
    Ct_t_relationshipService ct_t_relationshipService;
    @Autowired
    CommentService commentService;
    @Autowired
    AsyncTask asyncTask;

//    @GetMapping("/fore/contents")
//    public Object getContents(){
//        return contentService.getByStatus(contentService.published);
//    }

    @GetMapping("/contents")
    public Object getContents(){
        List list =new ArrayList();

        List<ContentDto> list_content = contentService.getByStatus(contentService.published);

        for (ContentDto contentDto: list_content){
            int id = contentDto.getId();
            List<Ct_t_relationshipDto> ctTRelationshipDtos  = ct_t_relationshipService.findTidTnameByCTid(id);
            Map<String,Object> map = new HashMap<>();
            map.put("content",contentDto);
            map.put("tag",ctTRelationshipDtos);
            list.add(map);
        }

        return list;
    }


    //根据ctid查对应tid和tname
    @GetMapping("/contents/{id}/tag")
    public List<Ct_t_relationshipDto> getTagByCTid(@PathVariable("id") int ctid){
        return ct_t_relationshipService.findTidTnameByCTid(ctid);
    }


    @GetMapping("/contents/{id}")
    public Object get(@PathVariable("id") int id) throws Exception {
        Content content = null;
        try {
            content = contentService.get(id);
        }catch (Exception e){
            Result.fail(e.toString());
        }
        if (content==null){
            return Result.fail("出错了");
        }else
        return Result.success(content);
    }

    @GetMapping("/index/category/{cgid}")
    public Object getCategoryIndex(@PathVariable("cgid") int cgid,
                                   @RequestParam(value = "start", defaultValue = "0") int start,
                                   @RequestParam(value = "size", defaultValue = "5") int size) throws Exception{
        start = 0 < start ? start : 0;
        Page4Navigator<Object[]> page = contentService.listByCategory(start, size, cgid,5);

        Map<String, Object> map = new HashMap<>();
        map.put("page",page);

        return Result.success(map);
    }

    @GetMapping("/index/tag/{tid}")
    public Object getTagIndex(@PathVariable("tid") int tid,
                                   @RequestParam(value = "start", defaultValue = "0") int start,
                                   @RequestParam(value = "size", defaultValue = "5") int size) throws Exception{
        start = 0 < start ? start : 0;
        Page4Navigator<Object[]> page = contentService.listByTag(start, size, tid,5);

        Map<String, Object> map = new HashMap<>();
        map.put("page",page);

        return Result.success(map);
    }

    //评论
    @GetMapping("/{ctid}/comments")
    public Page4Navigator<Object[]> list(@PathVariable("ctid") int ctid,
                                         @RequestParam(value = "start", defaultValue = "0") int start,
                                         @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = 0 < start ? start : 0;
        Page4Navigator<Object[]> page = commentService.listByCTid(ctid,start, size, 5); //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }

    @PostMapping("/comments/{ctid}")
    public Object addComment(@PathVariable("ctid")int ctid ,@RequestBody Comment bean, HttpServletRequest request, HttpSession session) throws Exception{
        Content content = contentService.get(ctid);
        bean.setContent(content);
        bean.setText(HtmlUtils.htmlEscape(bean.getText()));
        String ipAddr = IPUtill.getIpAddr(request);
        bean.setIp(ipAddr);
        bean.setCreated(new Date());
        Object user = session.getAttribute("user");
        if (null != user){
            bean.setUrl("www.yuzu-hard.xyz");
        }
        commentService.add(bean);

        asyncTask.doTaskSendMail(bean);
        return Result.success();
    }


}
