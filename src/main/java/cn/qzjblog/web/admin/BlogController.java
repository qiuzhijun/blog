package cn.qzjblog.web.admin;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.Type;
import cn.qzjblog.entity.User;
import cn.qzjblog.service.impl.BlogServiceImpl;
import cn.qzjblog.service.impl.TagServiceImpl;
import cn.qzjblog.service.impl.TypeServiceImpl;
import cn.qzjblog.vo.BlogQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


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
    private TagServiceImpl tagService;
    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private TypeServiceImpl typeService;


    //博客列表
    @GetMapping("/blogs")
    public String manage(Integer current, BlogQuery blog, Model model,HttpSession httpSession) {
        Page<Blog> page = new Page<>(1, 5);
        if (current != null) {
            page.setCurrent(current);
        }
        User user = (User) httpSession.getAttribute("user");
        model.addAttribute("page", blogService.listBlog(page, blog,user));
        model.addAttribute("types", typeService.listType());
        addPageNum(page,model);
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
    private void addTypesAndTags(Model model) {
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typeService.listType());
    }

    //后台查询博客
    @PostMapping("/search")
    public String search(Integer current, BlogQuery blog, Model model,HttpSession session) {
        Page<Blog> page = new Page<>(1, 5);
        if (current != null) {
            page.setCurrent(current);
        }
        User user = (User) session.getAttribute("user");
        model.addAttribute("page", blogService.listBlog(page, blog,user));
        addPageNum(page,model);

        return "adminHtml/backPage :: blogList";
    }
    //将页码数加入
    void addPageNum(Page page,Model model){
        Double num = (double) page.getTotal() / page.getSize();
        Double pageNum = Math.ceil(num);
        model.addAttribute("lastPage", pageNum);
    }
    //写博客，初始化blog对象，添加事务
    @Transactional
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("blog", new Blog());
        addTypesAndTags(model);
        return ADD;
    }




    //添加博客
    @Transactional
    @PostMapping(value = "/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes) {
        blog = initBlog(session, blog);
        Blog b;
        if (blog.getId() == null) {
            blog.setView(0);
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
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


    //初始化博客
    public Blog initBlog(HttpSession session, Blog blog) {
        User user = (User) session.getAttribute("user");
        Type type = typeService.getType(blog.getType().getId());
        blog.setUser(user);
        blog.setUserId(user.getId());
        blog.setType(type);
        blog.setTypeId(type.getId());
        blog.setTags(tagService.listTag(blog.getTagIds()));
        return blog;
    }
}
