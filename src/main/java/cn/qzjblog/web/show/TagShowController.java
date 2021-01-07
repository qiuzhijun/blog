package cn.qzjblog.web.show;

import cn.qzjblog.po.Tag;
import cn.qzjblog.service.BlogService;
import cn.qzjblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Create by qzj on 2020/12/29 17:42
 **/
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{tagId}")
    public String tags(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long tagId, Model model) {
        List<Tag> tags = tagService.listTagTop(99999);
        if(tagId == -1){
            tagId = tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("activeTagId",tagId);
        model.addAttribute("page",blogService.listBlog(pageable,tagId));
        return "tags";
    }
}
