package com.qzjblog.blog.service;

import com.qzjblog.blog.po.Message;

import java.util.List;

/**
 * Create by qzj on 2021/01/01 16:27
 **/
public interface MessageService {
    List<Message> findMessages();
    Message saveMessage(Message message);
}
