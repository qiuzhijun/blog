package com.qzjblog.blog.dao;

import com.qzjblog.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by qzj on 2020/12/13 11:43
 **
 */
//实体类，主键类型
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmailAndPassword(String email,String password);


}
