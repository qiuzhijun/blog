package cn.qzjblog.service;

import cn.qzjblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Create by qzj on 2020/12/14 19:50
 **/
public interface TagService {

    List<Tag> listTag();

    Tag saveTag(Tag tag);

    Tag updateTag(Long id, Tag tag);

    Tag getTagByName(String name);
    Tag getTag(Long id);

    List<Tag> listTag(String ids);
    Page<Tag> listTag(Pageable pageable);

    void deleteTag(Long id);

    List<Tag> listTagTop(Integer size);
}
