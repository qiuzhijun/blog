package cn.qzjblog.service;

import cn.qzjblog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Create by qzj on 2020/12/14 19:50
 **/
public interface TypeService {

    Type saveType(Type type);

    Type updateType(Long id, Type type);

    Type getTypeByName(String name);

    Type getType(Long id);

    List<Type> listTypeTop(Integer size);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    void deleteType(Long id);

}
