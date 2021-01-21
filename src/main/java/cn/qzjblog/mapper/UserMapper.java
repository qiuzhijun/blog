package cn.qzjblog.mapper;

import cn.qzjblog.entity.Blog;
import cn.qzjblog.entity.User;
import cn.qzjblog.vo.Table;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
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
public interface UserMapper extends BaseMapper<User> {
    @Select("select u.* from t_user u where u.id = #{userId}")
    User selectUser(Long userId);
    @Insert("insert into t_user_blog (blog_id,user_id) values(#{blogId},#{userId})")
    void addStarBlogOfUser(Long blogId,Long userId);
    @Select("select b.id,b.title from t_blog b join(select blog_id from t_user_blog where user_id=#{userId}) u on u.blog_id=b.id;")
    List<Blog> selectAllStar(Long userId);

    @Select("select c.content,t.* from t_comment c join (select id,title from t_blog where user_id=#{id}) t on c.blog_id = t.id;")
    List<Table> selectTable(Long id);
}
