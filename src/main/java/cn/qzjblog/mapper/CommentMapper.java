package cn.qzjblog.mapper;

import cn.qzjblog.entity.Comment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface CommentMapper extends BaseMapper<Comment> {
    @Select("select b.* from t_comment as a, t_comment as b  where a.id = b.parent_comment_id and a.blog_id =#{blogId} and a.id=#{commentId}")
    List<Comment> selectReplyComments(@Param("commentId") Long commentId, @Param("blogId") Long blogId);
    @Select("delete from t_comment where blog_id = #{id}")
    Integer deleteCommentByBlogId(@Param("id") Long id);
    @Select("select a.* from t_comment a,t_comment b where a.id=b.parent_comment_id and b.id=#{id}")
    Comment selectParentComment(@Param("id") Long id);
}
