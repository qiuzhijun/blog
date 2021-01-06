package com.qzjblog.blog.web.admin;

import com.qzjblog.blog.po.Type;
import com.qzjblog.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.jar.Attributes;

/**
 * Create by qzj on 2020/12/14 20:31
 **/

@Controller
@RequestMapping(value = "/admin")
public class TypeController {

    @Autowired
    private TypeService service;

    @GetMapping("types")
    //springboot根据前段页面构造好的参数，自动封装好的自动分页方法
    public String types
            (@PageableDefault(size = 8,//指定一页放多少数据
                    sort = {"id"}, //根据什么来排序
                    direction = Sort.Direction.DESC)//倒序
                     Pageable pageable, Model model) {
        model.addAttribute("page", service.listType(pageable));
        return "adminHtml/types";
    }

    @PostMapping("/types")
    //springboot根据前段页面构造好的参数，自动封装好的自动分页方法
    public String types(Type type, RedirectAttributes attributes) {
        Type t1 = service.getTypeByName(type.getName());
        if ("".equals(type.getName())) {
            attributes.addFlashAttribute("message", "添加失败，请填写分类名称");
        } else if (t1 == null) {
            Type t = service.saveType(type);
            attributes.addFlashAttribute("message", "添加成功");
        } else {
            attributes.addFlashAttribute("message", "添加失败，分类已存在");
        }
        return "redirect:/admin/types";
    }
    @PostMapping("/delete")
    public String deleteType(Long id){
        service.deleteType(id);
        return "redirect:/admin/types";
    }

    @PostMapping("/update")
    @ResponseBody
    public Type updateType(Long id, String name, RedirectAttributes attributes){
        Type type = new Type();
        type.setId(id);
        type.setName(name);
        Type t1 = service.getTypeByName(type.getName());
        if ("".equals(type.getName())) {
            attributes.addFlashAttribute("msg", "修改失败，请填写分类名称");
        } else if (t1 != null) {
            attributes.addFlashAttribute("msg", "修改失败，分类重复");
        } else {
            Type t = service.updateType(id, type);
            attributes.addFlashAttribute("msg", "修改成功");
            return t;
        }
        return new Type();
    }
}
