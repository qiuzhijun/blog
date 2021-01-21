package cn.qzjblog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by qzj on 2020/12/12 20:09
 **/

@Data
@TableName("t_blog")
public class Blog {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String title;//标题
    private String firstPicture; //首图地址
    private String content;  //博客内容
    private String description; //博客描述
    private String flag;    //标记
    private Integer view; //浏览量
    private boolean appreciation;//赞赏开启
    private boolean shareStatement;    //版权声明开启
    private boolean commentabled; //评论开启
    private boolean published;//发布
    private boolean recommended; //推荐
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;//创建时间
    @TableField(fill= FieldFill.INSERT)
    private Date updateTime;//更新时间
    @TableField(select = false,exist = false)
    private Type type;
    @TableField(select = false,exist = false)
    private List<Tag> tags = new ArrayList<>();
    @TableField(select = false,exist = false)
    private User user;
    @TableField(select = false,exist = false)
    private List<Comment> comments = new ArrayList<>();
    @TableField("user_id")
    private Long userId;
    @TableField("type_id")
    private Long typeId;
    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", flag='" + flag + '\'' +
                ", view=" + view +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommended=" + recommended +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }


    //不进数据库
    @TableField(exist = false)
    private String tagIds;

    public String getTagIds() {
        return tagIds;
    }

    public Blog setTagIds(String tagIds) {
        this.tagIds = tagIds;
        return this;
    }

    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }

    public String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag:tags) {
                if(flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());

            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }
}

