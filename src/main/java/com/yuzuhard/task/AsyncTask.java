package com.yuzuhard.task;

import com.yuzuhard.pojo.Comment;
import com.yuzuhard.util.SendMailSmtp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;



@Component
public class AsyncTask {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SendMailSmtp sendMailSmtp;

    @Async
    public void doTaskSendMail(Comment comment) throws InterruptedException{
        sendMailSmtp.send(comment);
    }
}