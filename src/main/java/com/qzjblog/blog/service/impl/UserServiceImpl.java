package com.qzjblog.blog.service.impl;

import com.qzjblog.blog.dao.UserRepository;
import com.qzjblog.blog.po.User;
import com.qzjblog.blog.service.UserService;
import com.qzjblog.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Create by qzj on 2020/12/13 11:41
 *
 **/
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, MD5Utils.code(password));
        return user;
    }



}
