package cn.qzjblog.service.impl;

import cn.qzjblog.dao.MessageRepository;
import cn.qzjblog.po.Message;
import cn.qzjblog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by qzj on 2021/01/01 16:28
 **/
@Transactional
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> findMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
