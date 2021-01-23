package cn.qzjblog.web.admin;

import cn.hutool.extra.mail.MailUtil;
import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.User;
import cn.qzjblog.service.impl.UserServiceImpl;
import cn.qzjblog.util.MD5Utils;
import cn.qzjblog.util.PictureUtils;
import cn.qzjblog.vo.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Create by qzj on 2021/01/18 20:19
 **/
@Controller
@RequestMapping("/admin")
public class UserController {
    private final String RETURN = "adminHtml/profile";

    @Autowired
    private UserServiceImpl userService;
    @Value("${user.manAvatar}")
    private String manAvatar;
    @Value("${user.womanAvatar}")
    private String womanAvatar;

    @GetMapping("/profile")
    public String user(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        List<Blog> blogList = userService.selectAllStarBlog(user.getId());
        model.addAttribute("blogs", blogList);
        List<Table> tables = userService.selectTable(user.getId());
        model.addAttribute("tables", tables);
        return RETURN;
    }

    //修改个人信息
    @PostMapping("/updateNickname")
    @ResponseBody
    public User updateNickname(@RequestParam String name, HttpSession session) {
        User user = (User) session.getAttribute("user");
        user.setNickname(name);
        return userService.updateNickname(user);
    }

    //修改个人信息
    @PostMapping("updatePassword")
    @ResponseBody
    public User updatePassword(@RequestParam String password, HttpSession session) {
        User user = (User) session.getAttribute("user");
        user.setPassword(MD5Utils.code(password));
        return userService.updateNickname(user);
    }

    //修改个人信息
    @PostMapping("/updateSex")
    @ResponseBody
    public User updateSex(@RequestParam String sex, HttpSession session) {
        User user = (User) session.getAttribute("user");
        user.setSex(sex);
        return userService.updateNickname(user);
    }



    //添加收藏文章
    @PostMapping("/addStar")
    @ResponseBody
    public void addStar(@RequestParam("id") Long blogId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        userService.addStar(blogId, user.getId());
    }

    //添加收藏文章
    @PostMapping("/removeStar")
    @ResponseBody
    public void removeStar(@RequestParam("id") Long blogId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        userService.removeStar(blogId, user.getId());
    }


    @PostMapping(value = "/updateAvatar")
    public String updateAvatar(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "url") String url, HttpSession session, Model model, HttpServletRequest request) {
        User u = (User) session.getAttribute("user");
        if (!("".equals(url))) {
            int s = PictureUtils.testWsdlConnection(url);
            if (s == 200) {
                u.setAvatar(url);
            } else {
                if ("男".equals(u.getSex())) {
                    u.setAvatar(manAvatar);
                } else {
                    u.setAvatar(womanAvatar);
                }
            }
            userService.updateAvatar(u);

        }
        if (!("".equals(file.getOriginalFilename()))) {
            if (file.isEmpty()) {
                model.addAttribute("msg", "文件不存在");
                model.addAttribute("user", u);
                session.setAttribute("user", u);
                return RETURN;
            }
            String fileName = file.getOriginalFilename();  // 文件名
            if (!(fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".bmp"))) {
                model.addAttribute("msg", "文件类型错误");
                session.setAttribute("user", u);
                model.addAttribute("user", u);
                return RETURN;
            }
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String path = this.getClass().getResource("/").getPath() + "/static/images/user-avatar/";
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            File dest = new File(path + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                String filename = "/images/user-avatar/" + fileName;
                u.setAvatar(filename);
                userService.updateAvatar(u);
            } catch (IOException e) {
                model.addAttribute("msg", e.getMessage());
            }
        }
        model.addAttribute("user", u);
        session.setAttribute("user", u);
        return RETURN;
    }

}
