package cn.qzjblog.web.show;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.Tag;
import cn.qzjblog.service.BlogService;
import cn.qzjblog.service.TagService;
import cn.qzjblog.service.impl.BlogServiceImpl;
import cn.qzjblog.service.impl.TagServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TagServiceImpl tagService;
    @Autowired
    private BlogServiceImpl blogService;

    @GetMapping("/tags/{tagId}")
    public String tags(Integer current, @PathVariable Long tagId, Model model) {
        Page<Blog> page = new Page<>(0, 6);
        if (current != null) {
            page.setCurrent(current);
        }
        List<Tag> tags = tagService.listTagTop(16);
        if (tagId == -1) {
            if (tags.size() > 0) {
                tagId = tags.get(0).getId();
            }
        }
        model.addAttribute("tags", tags);
        model.addAttribute("activeTagId", tagId);

        model.addAttribute("page", blogService.listBlog(page, tagId));
        Double num = (double) page.getTotal() / page.getSize();
        Double pageNum = Math.ceil(num);
        model.addAttribute("lastPage", pageNum);
        return "tags";
    }
}
