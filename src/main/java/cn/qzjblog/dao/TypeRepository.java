package cn.qzjblog.dao;

import cn.qzjblog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Create by qzj on 2020/12/14 19:55
 **/
public interface TypeRepository extends JpaRepository<Type,Long>{
    Type findByName(String name);


    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

}

