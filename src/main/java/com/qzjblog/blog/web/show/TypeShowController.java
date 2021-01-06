package com.qzjblog.blog.web.show;

import com.qzjblog.blog.po.Type;
import com.qzjblog.blog.service.BlogService;
import com.qzjblog.blog.service.TypeService;
import com.qzjblog.blog.vo.BlogQuery;
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
 * Create by qzj on 2020/12/29 17:41
 **/
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{typeId}")
    public String types( @PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long typeId, Model model) {
        List<Type> types = typeService.listTypeTop(99999);
        if(typeId == -1){
            typeId = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(typeId);
        model.addAttribute("types",types);
        model.addAttribute("activeTypeId",typeId);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return "types";
    }
}
