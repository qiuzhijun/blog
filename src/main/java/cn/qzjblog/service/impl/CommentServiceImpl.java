package cn.qzjblog.service.impl;

import cn.qzjblog.entity.Comment;
import cn.qzjblog.mapper.CommentMapper;
import cn.qzjblog.service.CommentService;
import cn.qzjblog.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by qzj on 2020/12/26 17:00
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;



    //展示评论，返回一个评论集合
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.isNull("parent_comment_id");
        wrapper.eq("blog_id", blogId);
        wrapper.orderByAsc("create_time");
        //查出所有顶级评论
        List<Comment> commentList = commentMapper.selectList(wrapper);
        wrapper.clear();
        for (Comment comment : commentList) {
            List<Comment> replyComments = commentMapper.selectReplyComments(comment.getId(),blogId);
            comment.setReplyComments(replyComments);
        }
        //应该返回带有子评论的  所有顶级评论
        List<Comment> comments = eachComment(commentList);
        for(Comment comment :comments){
            List<Comment> replyComments = ListUtils.removeDuplicate(comment.getReplyComments());
            comment.setReplyComments(replyComments);
            for(Comment reply:replyComments){

                Comment parentComment = commentMapper.selectParentComment(reply.getId());
                reply.setParentComment(parentComment);
            }
        }
        wrapper.clear();
        return comments;
    }

    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            comment.setParentComment(commentMapper.selectById(parentCommentId));
        }else {
            comment.setParentCommentId(null);
        }
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
        return comment;
    }



    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        //创建一个新的集合存放，不改变原数据
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            //循环传来的集合，将原数组的元素拷贝到新集合中
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并  评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {
        //循环这个集合
        for (Comment comment : comments) {
            //2和3
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        List<Comment> comments = commentMapper.selectReplyComments(comment.getId(),comment.getBlogId());
        comment.setReplyComments(comments);
        if (comment.getReplyComments().size()>0) {
            //如果有子代，就获取到，继续循环
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                List<Comment> comment2 = commentMapper.selectReplyComments(reply.getId(),comment.getBlogId());
                reply.setReplyComments(comment2);
                //将该子代加入暂存区
                tempReplys.add(reply);
                //如果还有，继续递归迭代
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }


}
