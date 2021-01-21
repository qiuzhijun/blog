package cn.qzjblog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by qzj on 2020/12/12 20:26
 **/
@Data
@TableName("t_comment")
public class Comment{
    @TableId(type = IdType.AUTO)
    private long id;
    private String nickname;//昵称
    private String email; //邮箱
    private String avatar; //头像
    private String content;//评论内容
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;//创建时间
    private Long blogId;
    private Long parentCommentId;
    private Boolean adminComment;
    @TableField(select = false,exist = false)
    private Blog blog;

    @TableField(select = false,exist = false)
    private Comment parentComment;
    @TableField(select = false,exist = false)
    private List<Comment> replyComments = new ArrayList<>();


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", adminComment=" + adminComment +
                ", blog=" + blog +
                ", parentComment=" + parentComment +
                ", replyComments=" + replyComments +
                '}';
    }

}
