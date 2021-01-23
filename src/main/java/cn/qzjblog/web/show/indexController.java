package cn.qzjblog.web.show;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.User;
import cn.qzjblog.service.impl.BlogServiceImpl;
import cn.qzjblog.service.impl.TagServiceImpl;
import cn.qzjblog.service.impl.TypeServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;



@Controller
public class indexController {


    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private TypeServiceImpl typeService;
    @Autowired
    private TagServiceImpl tagService;
    @GetMapping("/")
    public String index(Integer current, Model model) {
        Page<Blog> page = new Page<>(1,5);
        if(current != null){
            page.setCurrent(current);
        }
        model.addAttribute("page", blogService.listBlog(page));
        model.addAttribute("types",typeService.listTypeTop(5));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listBlogTop(5));
        Double num = (double)page.getTotal() / page.getSize();
        Double pageNum = Math.ceil(num);
        model.addAttribute("lastPage",pageNum);
        return "index";
    }


    //博客详情
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model, HttpSession session) {
        User u = (User) session.getAttribute("user");
        model.addAttribute("blog",blogService.getAndConvert(id,u));
        return "blog";
    }

    //前端查询
    @PostMapping("/search")
    public String search(Integer current, @RequestParam String query,
            Model model){
            Page<Blog> page = new Page<>(0,6);
        if(current != null){
            page.setCurrent(current);
        }
        model.addAttribute("page", blogService.listBlogByQuery( query , page));
        model.addAttribute("query",query);
        return "search";
    }



    @GetMapping("/friendlyLink")
    public String profile() {
        return "friendlyLink";
    }



}
