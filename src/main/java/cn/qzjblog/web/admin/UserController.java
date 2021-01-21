package cn.qzjblog.web.admin;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.User;
import cn.qzjblog.service.impl.UserServiceImpl;
import cn.qzjblog.util.MD5Utils;
import cn.qzjblog.vo.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create by qzj on 2021/01/18 20:19
 **/
@Controller
@RequestMapping("/admin")
public class UserController {
    private final String RETURN = "adminHtml/profile";
    private final String REDIRECT_RETURN = "redirect:adminHtml/profile";

    @Autowired
    private UserServiceImpl userService;

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

    //注册用户
    @PostMapping("/addUser")
    @ResponseBody
    public User addUser() {
        return null;
    }

    //添加收藏文章
    @PostMapping("/addStar")
    public void addStar(@RequestParam("id") Long blogId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        userService.addStar(blogId, user.getId());
    }


    @GetMapping(value = "/file")
    public String file() {
        return "test/test";
    }


    @PostMapping(value = "/updateAvatar")
    public String updateAvatar(@RequestParam(value = "file") MultipartFile file, HttpSession session, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            model.addAttribute("msg", "文件不存在");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        if (!(fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".bmp"))) {
            model.addAttribute("msg", "文件类型错误");
        }
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
                        //http                  ://        localhost          :            8080          /blog        /images/

        String filePath = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/static/images/user-avatar/"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        User u = (User) session.getAttribute("user");
        try {
            file.transferTo(dest);
            String filename = "/images/user-avatar/" + fileName;
            u.setAvatar(fileName);
            userService.updateAvatar(u);
            model.addAttribute("filename", filename);
            model.addAttribute("msg", "传输成功");
        } catch (IOException e) {
            model.addAttribute("msg", e.getMessage());
        }
        model.addAttribute("user", u);
        return RETURN;
    }
}
