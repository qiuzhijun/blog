package cn.qzjblog.service;

import cn.qzjblog.entity.Comment;

import java.util.List;

/**
 * Create by qzj on 2020/12/26 16:58
 **/
public interface CommentService {
    //查询
    List<Comment> listCommentByBlogId(Long blogId);
    //保存
    Comment saveComment(Comment comment);
}
