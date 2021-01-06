package com.qzjblog.blog.web.show;

import com.qzjblog.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Create by qzj on 2020/12/29 21:08
 **/
@Controller
public class ArchivesShowController {

    @Autowired
    private BlogService blogService;


    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }


}
