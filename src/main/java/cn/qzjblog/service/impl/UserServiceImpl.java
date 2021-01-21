package cn.qzjblog.service.impl;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.User;
import cn.qzjblog.mapper.UserMapper;
import cn.qzjblog.service.UserService;
import cn.qzjblog.util.MD5Utils;
import cn.qzjblog.vo.Table;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Create by qzj on 2020/12/13 11:41
 *
 **/
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QueryWrapper wrapper;

    @Override
    public User checkUser(String email, String password) {
        wrapper.eq("email",email);
        wrapper.eq("password",MD5Utils.code(password));
        User user = userMapper.selectOne(wrapper);
        wrapper.clear();
        return user;
    }

    @Override
    public User updateNickname(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",user.getId());
        userMapper.update(user,wrapper);
        return user;
    }

    @Override
    public void addStar(Long blogId,Long userId) {
        userMapper.addStarBlogOfUser(blogId,userId);
    }


    public List<Blog> selectAllStarBlog(Long userId) {
        List<Blog> blogList = userMapper.selectAllStar(userId);
        return blogList;
    }

    public List<Table> selectTable(Long id) {
        return userMapper.selectTable(id);
    }


    public void updateAvatar(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",user.getId());
        userMapper.update(user,wrapper);
    }
}
