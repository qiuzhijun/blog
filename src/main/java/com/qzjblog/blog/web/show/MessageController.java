package com.qzjblog.blog.web.show;

import com.qzjblog.blog.po.Message;
import com.qzjblog.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by qzj on 2021/01/01 16:18
 **/
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/message")
    public String findMessage(Model model) {
        model.addAttribute("messages", messageService.findMessages());
        return "message";
    }

    @PostMapping("/addMessage")
    @ResponseBody
    public Message addMessage(@RequestParam String message) {
        Message m = new Message();
        if (message != "" && message != null) {
            Message msg = new Message();
            msg.setMessage(message);
            m = messageService.saveMessage(msg);
        }
        return m;
    }
}
