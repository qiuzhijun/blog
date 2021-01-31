package cn.qzjblog.web.show;

import cn.hutool.extra.mail.MailUtil;
import cn.qzjblog.entity.User;
import cn.qzjblog.service.impl.UserServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/register")
    @ResponseBody
    public User register(String nickname, String email, String password) {
        //如果没查到，就可以注册
        User u = userService.checkEmail(email);
        if (u == null) {
            String str = sendMail(email);
            userService.addUser(nickname, email, password, str);
            if (str != "") {
                return u;
            }
        }
        return new User();
    }


    //发送邮件
    public static String sendMail(String email) {
        StringBuilder content = new StringBuilder();
        String str = RandomStringUtils.randomAlphanumeric(15);
        content.append("<h1>尊敬的用户</h1>");
        content.append("您收到一条来自Cealum的注册消息<br>");
        content.append("<a onclick='load(this)' href='http://qzjblog.cn/blog/receive/" + str + "'>点击链接来完成注册</a><br><br><br>");
        content.append("如果不是您本人发送，请忽略此邮件!!");
        String con = content.toString();
        MailUtil.send(email, "Cealum博客注册", con, true);
        return str;
    }
    //发送邮件
    public static void sendCode(String email,int code) {
        String msg = "验证码：" + code;
        MailUtil.send(email, "Cealum博客修改密码", msg, true);
    }

    //找回密码
    @GetMapping("/forget")
    @ResponseBody
    public User forget(String email,HttpSession session) {
        User user = userService.selectUserByEmail(email);
        if (user != null) {
            int random6 = (int) ((Math.random() * 9 + 1) * 100000);
            session.setAttribute("code",random6);
            session.setAttribute("email",email);
            sendCode(email, random6);
            return user;
        }
        return new User();
    }
    //验证码校验
    @PostMapping("/checkCode")
    @ResponseBody
    public Object checkCode(int code, HttpSession session) {
        Integer c= (Integer) session.getAttribute("code");
        if(c != code){
            return "error";
        }
        return "success";
    }
    //修改密码
    @PostMapping("/updatePsd")
    public Object updatePsd(@RequestParam String password, HttpSession session) {
        String email = (String) session.getAttribute("email");
        User user = userService.updatePsd(email,password);
        session.setAttribute("user",user);
        return "redirect:/";
    }

}
