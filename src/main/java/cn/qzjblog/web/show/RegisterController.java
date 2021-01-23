package cn.qzjblog.web.show;

import cn.hutool.extra.mail.MailUtil;
import cn.qzjblog.entity.User;
import cn.qzjblog.service.impl.UserServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Create by qzj on 2021/01/22 20:08
 **/
@Controller
public class RegisterController {
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/receive/{code}")
    public String receive(@PathVariable String code, HttpSession session) {
        User user = userService.selectUser(code);
        User u = userService.finAddUser(user);
        session.setAttribute("user", u);
        return "redirect:/";
    }

    @RequestMapping("/register")
    @ResponseBody
    public User register(String nickname, String email, String password) {
        //如果没查到，就可以注册
        User u = userService.checkEmail(email);
        if (u == null) {
            String str = sendMail(email);
            userService.addUser(nickname, email, password, str);
            return u;
        }
        return new User();
    }


    //发送邮件
    public static String sendMail(String email) {
        StringBuilder content = new StringBuilder();
        String str = RandomStringUtils.randomAlphanumeric(15);
        content.append("<h1>尊敬的用户</h1>");
        content.append("您收到一条来自Cealum的注册消息<br>");
        content.append("<a onclick='load(this)' href='http://localhost:8080/blog/receive/" + str + "'>点击链接来完成注册</a><br><br><br>");
        //http://qzjblog.cn/blog/
        content.append("如果不是您本人发送，请忽略此邮件!!");
        String con = content.toString();
        MailUtil.send(email, "Cealum博客注册邮件", con, true);
        return str;
    }


}
