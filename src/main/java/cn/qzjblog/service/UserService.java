package cn.qzjblog.service;

import cn.qzjblog.entity.User;

/**
 * Create by qzj on 2020/12/13 11:40
 **/
public interface UserService {
    //登陆校验
    User checkUser(String email,String password);
    User updateNickname(User user);

    void addStar(Long blogId,Long userId);
    void updateAvatar(User user);


}
