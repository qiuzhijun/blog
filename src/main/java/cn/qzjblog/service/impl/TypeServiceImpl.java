package cn.qzjblog.service.impl;

import cn.qzjblog.dao.TypeRepository;
import cn.qzjblog.myException.NotFoundException;
import cn.qzjblog.po.Type;
import cn.qzjblog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private TypeRepository repository;


    @Transactional
    @Override
    public Type saveType(Type type) {
        return repository.save(type);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        //数据库里查出来的type
        Type t = repository.findById(id).get();
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        //返回新加的
        return repository.save(type);
    }

    @Override
    public Type getTypeByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blog.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return repository.findTop(pageable);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        repository.deleteById(id);
    }
}
