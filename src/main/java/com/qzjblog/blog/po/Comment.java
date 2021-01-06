package com.qzjblog.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by qzj on 2020/12/12 20:26
 **/
@Entity
@Table(name="t_comment")
public class Comment{
    @Id
    @GeneratedValue
    private long id;
    private String nickname;//昵称
    private String email; //邮箱
    private String avatar; //头像
    private String content;//评论内容
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//创建时间

    private Boolean adminComment;

    @ManyToOne
    private Blog blog;

    @ManyToOne
    private Comment parentComment;
    @OneToMany(mappedBy = "parentComment",fetch=FetchType.EAGER)
    private List<Comment> replyComments = new ArrayList<>();

    public Blog getBlog() {
        return blog;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public Comment setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
        return this;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public Comment setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
        return this;
    }

    public Comment setBlog(Blog blog) {
        this.blog = blog;
        return this;
    }

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

    public long getId() {
        return id;
    }

    public Comment setId(long id) {
        this.id = id;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Comment setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Comment setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Comment setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Comment setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Comment() {
    }

    public Boolean getAdminComment() {
        return adminComment;
    }

    public Comment setAdminComment(Boolean adminComment) {
        this.adminComment = adminComment;
        return this;
    }
}
