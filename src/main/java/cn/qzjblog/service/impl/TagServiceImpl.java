package cn.qzjblog.service.impl;

import cn.qzjblog.entity.Tag;
import cn.qzjblog.mapper.TagMapper;
import cn.qzjblog.myException.NotFoundException;
import cn.qzjblog.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TagMapper tagMapper;
    @Autowired
    private QueryWrapper wrapper;

    @Override
    public List<Tag> listTag() {
        return tagMapper.selectList(null);
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        tagMapper.insert(tag);
        return tag;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagMapper.selectById(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, t);
        tagMapper.insert(tag);
        return tag;
    }

    @Override
    public Tag getTagByName(String name) {
        wrapper.eq("name",name);
        Tag tag = tagMapper.selectOne(wrapper);
        wrapper.clear();
        return tag;
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public List<Tag> listTag(String ids) {
        //根据id集合获取对象
        return tagMapper.selectBatchIds(convertToList(ids));
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
    public Page<Tag> listTag(Page<Tag> page) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        Page<Tag> page1 = tagMapper.selectPage(page, wrapper);
        wrapper.clear();
        return page1;
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteById(id);
    }

    //展示排名前几名的tag，返回一个列表
    @Override
    public List<Tag> listTagTop(Integer size) {
        List<Tag> list = tagMapper.findTop(size);
        return list;
    }

}
