package cn.qzjblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by qzj on 2020/12/12 20:23
 **/

@Data
@TableName("t_tag")
public class Tag {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String name;
    @TableField(select = false,exist = false)
    private List<Blog> blog = new ArrayList<>();

    @TableField(select = false,exist = false)
    private Long count;
    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blog=" + blog +
                '}';
    }

}
