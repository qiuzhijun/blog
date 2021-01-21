package cn.qzjblog.mapper;

import cn.qzjblog.entity.Type;
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
public interface TypeMapper extends BaseMapper<Type> {
    @Select("select t.id,name,count from t_type t join ( select type_id,COUNT(*) as count from t_blog where published=1 group by type_id order by count desc limit 0,#{size}) b on t.id=b.type_id;")
    List<Type> findTop(Integer size);
    //修改博客时获取的type名称
    @Select("select * from t_type t join (select type_id from t_blog where id = #{blogId}) b on t.id=b.type_id")
    Type selectTypeByBlogId(Long blogId);
    @Select("select t.* from t_type t where t.id = #{blogId}")
    Type selectType(Long blogId);

}
