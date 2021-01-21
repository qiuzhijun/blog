package cn.qzjblog.mapper;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.Tag;
import cn.qzjblog.entity.Type;
import cn.qzjblog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
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
public interface BlogMapper extends BaseMapper<Blog> {
    @Select("select DATE_FORMAT(b.create_time,'%Y') as year from t_blog b group by DATE_FORMAT(b.create_time,'%Y') order by year DESC")
    List<String> findGroupByYear();

    @Select("select * from t_blog b where DATE_FORMAT(b.create_time,'%Y') = #{year}")
    List<Blog> findByYear(String year);

    @Select("select count(1) from t_blog b join (select blog_id from t_blog_tags where tags_id = #{tagId}) t on id = t.blog_id and b.published=1")
    Long countTag(@Param("tagId") Long tagId);
    @Select("select b.* from t_blog b join (select blog_id from t_blog_tags where tags_id = #{tagId}) t on id = t.blog_id and b.published=1 limit #{start},#{size}")
    List<Blog> selectBlogByTagId(Long tagId,@Param("start")Long start,Long size);
    @Select("select * from t_blog b,t_user u where b.user_id = u.id limit #{page.current},#{page.size}")
    Page<Blog> selectAllBlog(@Param("page") Page<Blog> page);
}