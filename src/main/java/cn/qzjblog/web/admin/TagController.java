package cn.qzjblog.web.admin;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.Tag;
import cn.qzjblog.entity.Type;
import cn.qzjblog.service.TagService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Create by qzj on 2020/12/14 20:31
 **/
@Controller
@RequestMapping(value = "/admin")
public class TagController {

    @Autowired
    private TagService service;

    @GetMapping("tags")
    //springboot根据前段页面构造好的参数，自动封装好的自动分页方法
    public String tags(Integer current,Integer size, Model model) {
        Page<Tag> page = new Page<>(1,6);
        if(current != null){
            page.setCurrent(current);
        }
        model.addAttribute("page", service.listTag(page));
        Double num = (double)page.getTotal() / page.getSize();
        Double pageNum = Math.ceil(num);
        model.addAttribute("lastPage",pageNum);
        return "adminHtml/tags";
    }

    @PostMapping("/tags")
    //springboot根据前段页面构造好的参数，自动封装好的自动分页方法
    public String tags(Tag tags, RedirectAttributes attributes) {
        Tag t1 = service.getTagByName(tags.getName());
        if ("".equals(tags.getName())) {
            attributes.addFlashAttribute("message", "添加失败，请填写分类名称");
        } else if (t1 == null) {
            Tag t = service.saveTag(tags);
            attributes.addFlashAttribute("message", "添加成功");
        } else {
            attributes.addFlashAttribute("message", "添加失败，分类已存在");
        }
        return "redirect:/admin/tags";
    }
    @PostMapping("/deleteTag")
    public String deleteTag(Long id){
        service.deleteTag(id);
        return "redirect:/admin/tags";
    }

    @PostMapping("/updateTag")
    @ResponseBody
    public Tag updateTag(Long id, String name, RedirectAttributes attributes){
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);

        Tag t1 = service.getTagByName(tag.getName());
        if ("".equals(tag.getName())) {
            attributes.addFlashAttribute("msg", "修改失败，请填写分类名称");
        } else if (t1 != null) {
            attributes.addFlashAttribute("msg", "修改失败，分类重复");
        } else {
            Tag t = service.updateTag(id, tag);
            attributes.addFlashAttribute("msg", "修改成功");
            return t;
        }
        return new Tag();
    }
}
