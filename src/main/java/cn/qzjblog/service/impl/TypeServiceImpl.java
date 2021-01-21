package cn.qzjblog.service.impl;

import cn.qzjblog.entity.Tag;
import cn.qzjblog.mapper.TypeMapper;
import cn.qzjblog.myException.NotFoundException;
import cn.qzjblog.entity.Type;
import cn.qzjblog.service.TypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by qzj on 2020/12/14 19:54
 **/
@Transactional
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    QueryWrapper wrapper = new QueryWrapper();
    @Transactional
    @Override
    public Type saveType(Type type) {
        typeMapper.insert(type);
        return  type;
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        //数据库里查出来的type
        Type t = typeMapper.selectById(id);
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        //返回新加的
        typeMapper.insert(type);
        return type;
    }

    @Override
    public Type getTypeByName(String name) {
        wrapper.eq("name",name);
        Type t= typeMapper.selectOne(wrapper);
        wrapper.clear();
        return t;
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeMapper.selectById(id);
    }

    //展示排名前几名的type，返回一个列表
    @Override
    public List<Type> listTypeTop(Integer size) {

        return typeMapper.findTop(size);
    }

    @Transactional
    @Override
    public Page<Type> listType(Page<Type> page) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        Page<Type> page1 = typeMapper.selectPage(page, wrapper);
        wrapper.clear();
        return page1;
    }

    @Override
    public List<Type> listType() {
        return typeMapper.selectList(null);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeMapper.deleteById(id);
    }
}
