package cn.qzjblog.service;

import cn.qzjblog.po.User;

/**
 * Create by qzj on 2020/12/13 11:40
 **/
public interface UserService {
    //登陆校验
    User checkUser(String email,String password);

}
