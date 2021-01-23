package cn.qzjblog.service.impl;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.Comment;
import cn.qzjblog.entity.Tag;
import cn.qzjblog.entity.User;
import cn.qzjblog.mapper.UserMapper;
import cn.qzjblog.service.UserService;
import cn.qzjblog.util.MD5Utils;
import cn.qzjblog.vo.Comments;
import cn.qzjblog.vo.Table;
import cn.qzjblog.vo.Title;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Value("${user.manAvatar}")
    private String avatar;

    @Override
    public User checkUser(String email, String password) {
        wrapper.eq("email",email);
        wrapper.eq("password",MD5Utils.code(password));
        wrapper.eq("status",0);
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

    @Override
    public void removeStar(Long blogId,Long userId) {
        userMapper.removeStarBlogOfUser(blogId,userId);
    }


    public List<Blog> selectAllStarBlog(Long userId) {
        List<Blog> blogList = userMapper.selectAllStar(userId);
        return blogList;
    }

    public List<Table> selectTable(Long id) {
        List<Table> tables = new ArrayList<>();
        List<Title> titles = userMapper.selectTitles(id);
        for(Title title:titles){
            List<Comments> comments = userMapper.selectComments(title.getId());
            Table t = new Table();
            t.setTitle(title);
            t.setComments(comments);
            tables.add(t);
    }
        return tables;
    }


    public void updateAvatar(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",user.getId());
        userMapper.update(user,wrapper);
    }

    public User checkEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        wrapper.eq("status",0);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User selectUser(String code) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("code",code);
        User u = userMapper.selectOne(wrapper);
        return u;
    }

    public void addUser(String nickname, String email, String password, String str) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("email",email);
        User user = userMapper.selectOne(wrapper);
        if(user == null){
            User u = new User();
            u.setNickname(nickname);
            u.setPassword(MD5Utils.code(password));
            u.setEmail(email);
            //将状态置1，表示正在进行注册
            u.setStatus(1);
            u.setCode(str);
            userMapper.insert(u);
        }
    }

    public User finAddUser(User user) {
        //完成最终注册，用email确定，用status判断，如果为1，则可以完成，如果为0，则不修改；
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("email",user.getEmail());
        wrapper.eq("status",1);
        User user1 = userMapper.selectOne(wrapper);
        if(user1!=null){
            user.setStatus(0);
            user.setType("普通用户");
            user.setSex("男");
            user.setAvatar(avatar);
            userMapper.updateById(user);
        }
        return user;
    }
}
