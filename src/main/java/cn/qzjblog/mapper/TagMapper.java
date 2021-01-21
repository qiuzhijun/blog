package cn.qzjblog.mapper;

import cn.qzjblog.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiuzhijun
 * @since 2021-01-10
 */
public interface TagMapper extends BaseMapper<Tag> {
    @Select("select t.id,t.name,count(1)as count from (SELECT t2.blog_id,t.id,t.name from t_tag t join(select blog_id,tags_id from t_blog_tags) t2 On t.id = t2.tags_id JOIN(select id from t_blog where published=1) b on b.id = t2.blog_id) t GROUP BY t.id order by count desc LIMIT 0,#{size}")
    List<Tag> findTop(Integer size);
    @Select("select * from t_tag t join (select tags_id from t_blog_tags where blog_id=#{blogId}) b on t.id=b.tags_id")
    List<Tag> selectTagsInBlog(Long blogId);

    //删除标签
    @Select("delete from t_blog_tags where blog_id = #{id}")
    void deleteTagsInBlog(Long id);
    //新增标签
    @Select("insert into t_blog_tags (blog_id,tags_id) values(#{blogId},#{tagId})")
    void insertTagsInBlog(Long blogId,Long tagId);

    @Select("select * from t_tag t join (select blog_id,tags_id from t_blog_tags where blog_id = #{id}) b on t.id=b.tags_id")
    List<Tag> selectTagsByBlogId(Long id);

}
