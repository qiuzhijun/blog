package cn.qzjblog.service;

import cn.qzjblog.entity.Type;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


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

    Page<Type> listType(Page<Type> page);

    List<Type> listType();

    void deleteType(Long id);

}
