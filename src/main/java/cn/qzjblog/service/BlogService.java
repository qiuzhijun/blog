package cn.qzjblog.service;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.User;
import cn.qzjblog.vo.BlogQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * Create by qzj on 2020/12/17 15:44
 **/
public interface BlogService {
    Page<Blog> listBlog(Page<Blog> page);

    Blog getAndConvert(Long id);

    List<Blog> listBlogTop(Integer size);

    Blog getBlogById(Long id);

    Map<String, List<Blog>> archiveBlog();

    Integer countBlog();

    Page<Blog> listBlog(Page<Blog> page, BlogQuery blog, User user);

    User selectUser(Long blogId);

    Page<Blog> listBlog(Page<Blog> page, Long tagId);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    Page<Blog> listBlogByQuery(String query, Page<Blog> page);
}
