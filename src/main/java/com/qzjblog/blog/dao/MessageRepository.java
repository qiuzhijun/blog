package com.qzjblog.blog.dao;

import com.qzjblog.blog.po.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by qzj on 2021/01/01 16:23
 **/
public interface MessageRepository extends JpaRepository<Message,Long> {
}
