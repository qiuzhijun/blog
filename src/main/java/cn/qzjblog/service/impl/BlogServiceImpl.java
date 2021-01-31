package cn.qzjblog.service.impl;


import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.Tag;
import cn.qzjblog.entity.Type;
import cn.qzjblog.entity.User;
import cn.qzjblog.mapper.*;
import cn.qzjblog.myException.NotFoundException;
import cn.qzjblog.service.BlogService;
import cn.qzjblog.util.MarkdownUtils;
import cn.qzjblog.util.MyBeanUtils;
import cn.qzjblog.util.PictureUtils;
import cn.qzjblog.vo.BlogQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by qzj on 2020/12/17 15:47
 **/
@Transactional
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QueryWrapper wrapper;
    @Value("${blog.defaultPicture}")
    private String defaultPicture;


    //后台查询
    @Override
    public Page<Blog> listBlog(Page page, BlogQuery blog, User user) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
            //如果传来的标题不为空或空字符串，就添加模糊查询，从传回来的title拼接模糊查询
            wrapper.like("title", blog.getTitle());
        }
        if (blog.getTypeId() != null) {
            //如果传来的id有值不为空，就添加一个查询条件，
            wrapper.eq("type_id", blog.getTypeId());
        }
        if (blog.isRecommended()) {
            //评论是否开启，传回一个boolean值，开启就添加一个条件
            wrapper.eq("recommended", blog.isRecommended());
        }
        wrapper.orderByDesc("update_time");
        Page<Blog> blogPage = null;
        if ("普通用户".equals(user.getType())) {
            wrapper.eq("user_id", user.getId());
            blogPage = blogMapper.selectPage(page, wrapper);
        } else {
            blogPage = blogMapper.selectPage(page, wrapper);
        }
        blogPage = addTypeAndUser(blogPage);
        return blogPage;
    }

    //查询所有blog，以分页方式返回，用于首页的展示
    @Override
    public Page<Blog> listBlog(Page<Blog> page) {
        wrapper.eq("published", 1);
        wrapper.orderByDesc("view");
        Page<Blog> blogPage = blogMapper.selectPage(page, wrapper);
        wrapper.clear();
        blogPage = addTypeAndUser(blogPage);
        return blogPage;

    }

    //通过博客id，浏览博客详情，转换markdown并增加浏览量
    @Override
    public Blog getAndConvert(Long id, User user) {
        Blog blog = blogMapper.selectById(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        //blog是原博客，b是要转换的博客
        BeanUtils.copyProperties(blog, b);
        b = blogInit(b, id);
        if (user != null) {
            Integer count = blogMapper.selectIsStar(user.getId(), id);
            b.setIsStar("收藏");
            blog.setIsStar("收藏");
            if (count == 1) {
                b.setIsStar("取消收藏");
                blog.setIsStar("取消收藏");
            }
        }
        return b;
    }


    //给blog赋值
    Blog blogInit(Blog b, Long id) {
        List<Tag> list = tagMapper.selectTagsByBlogId(id);
        b.setTags(list);
        Long userId = b.getUserId();
        User user = userMapper.selectUser(userId);
        b.setUser(user);
        b.setView(b.getView() + 1);
        blogMapper.updateById(b);
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(b.getContent()));
        return b;
    }

    ////查询浏览量最高的5篇博客
    @Override
    public List<Blog> listBlogTop(Integer size) {
        wrapper.clear();
        wrapper.eq("published", 1);
        wrapper.orderByDesc("view");
        Page<Blog> page = new Page<>(0, size);
        Page<Blog> result = blogMapper.selectPage(page, wrapper);
        wrapper.clear();
        return result.getRecords();
    }

    //修改博客的时候，取得type和tag列表
    @Override
    public Blog getBlogById(Long id) {

        Blog b = blogMapper.selectById(id);

        b.setType(typeMapper.selectTypeByBlogId(id));
        b.setTags(tagMapper.selectTagsByBlogId(id));
        return b;
    }

    //将user和type赋值到分页得到的blog中
    Page<Blog> addTypeAndUser(Page<Blog> blogPage) {
        List<Blog> blogList = blogPage.getRecords();
        for (Blog b : blogList) {
            Long userId = b.getUserId();
            User user = userMapper.selectUser(userId);
            Long typeId = b.getTypeId();
            Type type = typeMapper.selectType(typeId);
            b.setUser(user);
            b.setType(type);
        }
        wrapper.clear();
        return blogPage.setRecords(blogList);
    }


    //归档通过年份查询
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.findGroupByYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogMapper.findByYear(year));
        }
        return map;
    }

    //归档 ，查询博客总数
    @Override
    public Integer countBlog() {
        return blogMapper.selectCount(null);
    }


    List<Blog> addTagsAndUserAndType(List<Blog> blogList) {
        for (Blog blog : blogList) {
            Long userId = blog.getUserId();
            User user = userMapper.selectUser(userId);
            Long typeId = blog.getTypeId();
            Type type = typeMapper.selectType(typeId);
            Long blogId = blog.getId();
            List<Tag> tags = tagMapper.selectTagsInBlog(blogId);
            blog.setTags(tags);
            blog.setUser(user);
            blog.setType(type);
        }
        return blogList;
    }

    //按照tagid将博客分类
    @Override
    public Page<Blog> listBlog(Page<Blog> page, Long tagId) {
        Long current = page.getCurrent();
        Long size = page.getSize();
        long start = (current - 1) * 6;
        List<Blog> blogList = blogMapper.selectBlogByTagId(tagId, start, size);
        Long total = blogMapper.countTag(tagId);
        blogList = addTagsAndUserAndType(blogList);
        page.setTotal(total);
        page.setRecords(blogList);
        return page;
    }

    //添加博客
    @Override
    public Blog saveBlog(Blog blog) {
        String url = blog.getFirstPicture();
        int s = PictureUtils.testWsdlConnection(url);
        if (s != 200) {
            blog.setFirstPicture(defaultPicture);
        }
        blogMapper.insert(blog);
        List<Tag> tags = blog.getTags();
        for (Tag t : tags) {
            Long blogId = blog.getId();
            Long tagId = t.getId();
            tagMapper.insertTagsInBlog(blogId, tagId);
        }
        return blog;
    }


    //修改博客
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogMapper.selectById(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        String url = b.getFirstPicture();
        int s = PictureUtils.testWsdlConnection(url);
        if (s != 200) {
            b.setFirstPicture(defaultPicture);
        }
        wrapper.eq("id", id);
        blogMapper.update(b, wrapper);
        wrapper.clear();

        return b;
    }

    //通过id删除博客
    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteById(id);
        tagMapper.deleteTagsInBlog(id);
        commentMapper.deleteCommentByBlogId(id);
    }


    //首页查询博客方法，通过标题或描述模糊查询
    @Override
    public Page<Blog> listBlogByQuery(String query, Page<Blog> page) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("published", 1);
        wrapper.and(w -> w.like("title", query).or().like("description", query));
        Page page1 = blogMapper.selectPage(page, wrapper);
        List<Blog> blogList = page1.getRecords();
        blogList = addTagsAndUserAndType(blogList);
        page1.setRecords(blogList);
        wrapper.clear();
        return page1;

    }

    public Page<Blog> listBlogByType(Page<Blog> page, BlogQuery blog) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        if (blog.getTypeId() != null) {
            //如果传来的id有值不为空，就添加一个查询条件，
            wrapper.eq("type_id", blog.getTypeId());
        }
        wrapper.eq("published", 1);
        wrapper.orderByDesc("update_time");
        Page<Blog> blogPage = blogMapper.selectPage(page, wrapper);
        blogPage = addTypeAndUser(blogPage);
        wrapper.clear();
        return blogPage;
    }

    public User selectUser(Long blogId) {
        Blog b = blogMapper.selectById(blogId);
        return userMapper.selectUser(b.getUserId());
    }
}
