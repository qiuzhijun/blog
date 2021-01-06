package com.qzjblog.blog.web.admin;

import com.qzjblog.blog.po.Blog;
import com.qzjblog.blog.po.User;
import com.qzjblog.blog.service.BlogService;
import com.qzjblog.blog.service.TagService;
import com.qzjblog.blog.service.TypeService;
import com.qzjblog.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 * Create by qzj on 2020/12/17 16:59
 **/
@Controller
@RequestMapping(value = "/admin")
public class BlogController {
    private static final String ADD = "adminHtml/addBlog";
    private static final String LIST = "adminHtml/backPage";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;


    //博客列表
    @GetMapping("/blogs")
    public String manage(
            @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
            BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        model.addAttribute("types", typeService.listType());
        return LIST;
    }


    //修改博客，添加事务
    @Transactional
    @GetMapping("/{id}/add")
    public String update(@PathVariable Long id, Model model) {
        addTypesAndTags(model);
        Blog blog = blogService.getBlogById(id);
        blog.init();
        model.addAttribute("blog", blog);

        return ADD;
    }

    //查询博客
    @PostMapping("/search")
    public String search(
            @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
            BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "adminHtml/backPage :: blogList";
    }

    //写博客，初始化blog对象，添加事务
    @Transactional
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("blog", new Blog());
        addTypesAndTags(model);
        return ADD;
    }

    private void addTypesAndTags(Model model) {
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typeService.listType());
    }


    //添加博客
    @Transactional
    @PostMapping(value = "/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes) {
        System.out.println(blog);
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b;
        if(blog.getId()==null){
         b = blogService.saveBlog(blog);
        }else {
            b = blogService.updateBlog(blog.getId(),blog);
        }

        if (b != null) {
            attributes.addFlashAttribute("message", "操作成功");
        } else {
            attributes.addFlashAttribute("message", "操作失败");
        }
        return REDIRECT_LIST;
    }

    //删除博客
    @Transactional
    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;


    }


}
