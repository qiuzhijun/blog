package com.qzjblog.blog.dao;

import com.qzjblog.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by qzj on 2020/12/26 17:21
 **/
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long BlogId, Sort sort);


}
