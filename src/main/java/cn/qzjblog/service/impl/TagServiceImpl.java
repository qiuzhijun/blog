package cn.qzjblog.service.impl;

import cn.qzjblog.dao.TagRepository;
import cn.qzjblog.myException.NotFoundException;
import cn.qzjblog.po.Tag;
import cn.qzjblog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by qzj on 2020/12/14 19:54
 **/
@Transactional
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository repository;

    @Override
    public List<Tag> listTag() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return repository.save(tag);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = repository.findById(id).get();
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, t);
        return repository.save(tag);
    }

    @Override
    public Tag getTagByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Tag> listTag(String ids) {
        //根据id集合获取对象
        return repository.findAllById(convertToList(ids));
    }

    //字符串转换为数组
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idArray = ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                list.add(Long.valueOf(idArray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blog.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return repository.findTop(pageable);
    }
}
