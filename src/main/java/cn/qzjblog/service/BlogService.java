package cn.qzjblog.service;

import cn.qzjblog.po.Blog;
import cn.qzjblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Create by qzj on 2020/12/17 15:44
 **/
public interface BlogService {
    Page<Blog> listBlog(Pageable pageable);

    Blog getAndConvert(Long id);

    List<Blog> listBlogTop(Integer size);

    Blog getBlogById(Long id);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable, Long tagId);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    Page<Blog> listBlogByQuery(String query, Pageable pageable);
}
