package com.qzjblog.blog.web.admin;

import com.qzjblog.blog.po.User;
import com.qzjblog.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Create by qzj on 2020/12/12 22:17
 **/
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    //登陆方法
    @PostMapping("/login")
    @ResponseBody
    public User login(@RequestParam String email,
                      @RequestParam String password,
                      HttpSession session,
                      RedirectAttributes attributes) {
        User user = userService.checkUser(email, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
        }
        return user;
    }

    //注销方法
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/index";
    }




}
