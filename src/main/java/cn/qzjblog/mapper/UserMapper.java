package cn.qzjblog.mapper;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.User;
import cn.qzjblog.vo.Comments;
import cn.qzjblog.vo.Table;
import cn.qzjblog.vo.Title;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author qiuzhijun
 * @since 2021-01-10
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("select u.* from t_user u where u.id = #{userId}")
    User selectUser(Long userId);

    @Insert("insert into t_user_blog (blog_id,user_id) values(#{blogId},#{userId})")
    void addStarBlogOfUser(@Param("blogId") Long blogId, @Param("userId") Long userId);

    @Insert("delete from t_user_blog where blog_id=#{blogId} and user_id=#{userId}")
    void removeStarBlogOfUser(@Param("blogId") Long blogId, @Param("userId") Long userId);

    @Select("select b.id,b.title from t_blog b join(select blog_id from t_user_blog where user_id=#{userId}) u on u.blog_id=b.id;")
    List<Blog> selectAllStar(@Param("userId") Long userId);

    @Select("select id,title from t_blog b join (select blog_id from t_comment group by blog_id) c on b.id=c.blog_id where b.user_id=#{id};")
    List<Title> selectTitles(@Param("id") Long id);

    @Select("select nickname,content from t_comment where blog_id = #{id};")
    List<Comments> selectComments(@Param("id") Integer id);
}
