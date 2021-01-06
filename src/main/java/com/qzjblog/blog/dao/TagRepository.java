package com.qzjblog.blog.dao;

import com.qzjblog.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Create by qzj on 2020/12/14 19:55
 **/
public interface TagRepository extends JpaRepository<Tag,Long>{
    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

}
