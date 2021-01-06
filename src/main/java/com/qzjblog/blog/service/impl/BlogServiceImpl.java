package com.qzjblog.blog.service.impl;

import com.qzjblog.blog.dao.BlogRepository;
import com.qzjblog.blog.myException.NotFoundException;
import com.qzjblog.blog.po.Blog;
import com.qzjblog.blog.po.Type;
import com.qzjblog.blog.service.BlogService;
import com.qzjblog.blog.util.MarkdownUtils;
import com.qzjblog.blog.util.MyBeanUtils;
import com.qzjblog.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.sql.Time;
import java.util.*;

/**
 * Create by qzj on 2020/12/17 15:47
 **/
@Transactional
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository repository;

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = repository.findById(id).get();
        if(blog == null){
            throw new  NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(b.getContent()));
        repository.updateViews(id);
        return b;
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return repository.findRecommendedTop(pageable);
    }

    @Override
    public Blog getBlogById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = repository.findGroupByYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for(String year : years){
            map.put(year,repository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return repository.count();
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        return repository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    //如果传来的标题不为空或空字符串，就添加模糊查询，从传回来的title拼接模糊查询
                    predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    //如果传来的id有值不为空，就添加一个查询条件，
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommended()) {
                    //评论是否开启，传回一个boolean值，开启就添加一个条件
                    predicates.add(cb.equal(root.<Boolean>get("recommended"), blog.isRecommended()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Long tagId) {
        return repository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setView(0);
        }
        return repository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = repository.findById(id).get();
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return repository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Blog> listBlogByQuery(String query,Pageable pageable) {
        return repository.findByQuery(query,pageable);
    }
}
