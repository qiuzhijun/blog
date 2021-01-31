package cn.qzjblog.service;

import cn.qzjblog.entity.User;

/**
 * Create by qzj on 2020/12/13 11:40
 **/
public interface UserService {
    User selectUser(String str);

    void addUser(String nickname, String email, String password, String str);

    //登陆校验
    User checkUser(String email, String password);

    User updateNickname(User user);

    void addStar(Long blogId, Long userId);

    void removeStar(Long blogId, Long userId);

    void updateAvatar(User user);

    User checkEmail(String email);

    User finAddUser(User user);

    User selectUserByEmail(String email);

    User updatePsd(String email, String password);
}
