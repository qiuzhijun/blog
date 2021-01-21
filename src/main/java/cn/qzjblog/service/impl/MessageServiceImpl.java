package cn.qzjblog.service.impl;

import cn.qzjblog.mapper.MessageMapper;
import cn.qzjblog.entity.Message;
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
    private MessageMapper mapper;

    @Override
    public List<Message> findMessages() {
        return mapper.selectList(null);
    }

    @Override
    public Message saveMessage(Message message) {
        mapper.insert(message);
        return message;
    }
}
