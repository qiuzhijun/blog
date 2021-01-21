package cn.qzjblog.web.show;

import cn.qzjblog.entity.Comment;
import cn.qzjblog.entity.User;
import cn.qzjblog.service.impl.BlogServiceImpl;
import cn.qzjblog.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Create by qzj on 2020/12/26 16:54
 **/
@Controller
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private BlogServiceImpl blogService;
    @Value("${comment.avatar}")
    private String avatar;

    /*获取整个评论列表*/
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }


    /*添加一条评论*/
    @PostMapping("/comments")
    public String Post(Comment comment, HttpSession session) {
        comment = commentInit(comment, session);
        commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlog().getId();
    }

    Comment commentInit(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlogId(blogId);
        comment.setBlog(blogService.getBlogById(blogId));
        comment.setParentCommentId(comment.getParentComment().getId());
        User selectUser = blogService.selectUser(blogId);
        User sessionUser = (User) session.getAttribute("user");
        comment.setAdminComment(false);
        if (sessionUser != null) {
            comment.setAvatar(sessionUser.getAvatar());
            if (sessionUser.getId() == selectUser.getId()) {
                comment.setAdminComment(true);
            }
        } else {
            comment.setAvatar(avatar);
        }
        return comment;
    }

}
