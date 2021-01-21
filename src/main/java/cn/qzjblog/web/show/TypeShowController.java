package cn.qzjblog.web.show;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.Type;
import cn.qzjblog.service.impl.BlogServiceImpl;
import cn.qzjblog.service.impl.TypeServiceImpl;
import cn.qzjblog.vo.BlogQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

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
    private TypeServiceImpl typeService;
    @Autowired
    private BlogServiceImpl blogService;

    @GetMapping("/types/{typeId}")
    public String types(Integer current,
                        @PathVariable Long typeId, Model model) {
        Page<Blog> page = new Page<>(1, 6);
        if (current != null) {
            page.setCurrent(current);
        }
        List<Type> types = typeService.listTypeTop(99999);
        if (typeId == -1) {
            if (types.size() > 0) {
                typeId = types.get(0).getId();
            }
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(typeId);
        model.addAttribute("types", types);
        model.addAttribute("activeTypeId", typeId);
        model.addAttribute("page", blogService.listBlogByType(page, blogQuery));
        Double num = (double) page.getTotal() / page.getSize();
        Double pageNum = Math.ceil(num);
        model.addAttribute("lastPage", pageNum);
        return "types";
    }
}
