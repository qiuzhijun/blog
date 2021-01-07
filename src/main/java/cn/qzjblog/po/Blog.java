package cn.qzjblog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by qzj on 2020/12/12 20:09
 **/

@Entity
@Table(name="t_blog")
public class Blog {
    @Id
    @GeneratedValue
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//更新时间

    public String getDescription() {
        return description;
    }

    public Blog setDescription(String description) {
        this.description = description;
        return this;
    }

    //主动维护关系，多方是关系维护方
    @ManyToOne
    private Type type;

    //级联关系,新增一个博客，也新增一个标签,二者都会保存。
    @ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Blog setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

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

    public String getContent() {
        return content;
    }

    public Blog setContent(String content) {
        this.content = content;
        return this;
    }


    public Long getId() {
        return id;
    }

    public Blog setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Blog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public Blog setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
        return this;
    }

    public String getFlag() {
        return flag;
    }

    public Blog setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public Integer getView() {
        return view;
    }

    public Blog setView(Integer view) {
        this.view = view;
        return this;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public Blog setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
        return this;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public Blog setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
        return this;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public Blog setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
        return this;
    }

    public boolean isPublished() {
        return published;
    }

    public Blog setPublished(boolean published) {
        this.published = published;
        return this;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public Blog setRecommended(boolean recommended) {
        this.recommended = recommended;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Blog setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Blog setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Blog setType(Type type) {
        this.type = type;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Blog setUser(User user) {
        this.user = user;
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Blog setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }
    //不进数据库
    @Transient
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

