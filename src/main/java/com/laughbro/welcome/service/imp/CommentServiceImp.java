package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.CommentMapper;
import com.laughbro.welcome.dao.mapper.PostMapper;
import com.laughbro.welcome.dao.pojo.Comment;
import com.laughbro.welcome.service.CommentService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.comment_params.CommentAddParams;
import com.laughbro.welcome.vo.params.comment_params.CommentComParams;
import com.laughbro.welcome.vo.params.comment_params.CommentListParams;
import com.laughbro.welcome.vo.params.comment_params.CommentUserParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;


    @Override
    public Result comment_list(CommentListParams commentListParams) {
        List<Comment> commentList = null;
        //判断是否点赞排序
        if(commentListParams.getLike_order()==1){
            //判断时间排序方式
            if(commentListParams.getTime_order()==1){


            }else{


            }
        }else{
            //判断时间排序方式
            if(commentListParams.getTime_order()==1){
                //时间升序，无点赞
                   commentList= commentMapper.select_comments_all_by_postid_order_time_asc(commentListParams.getPostid());
            }else{
                //时间降序，无点赞
                commentList= commentMapper.select_comments_all_by_postid_order_time_desc(commentListParams.getPostid());
            }
        }

        return Result.success(commentList);
    }

    @Override
    public Result comment_add(CommentAddParams commentAddParams) {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(currentDate);
        //判断评论类型
        if(commentAddParams.getIs_facomment()==1){
            //是一级评论
            commentMapper.insert_comments_for_post(commentAddParams.getFa_post(),commentAddParams.getCreator(),formattedDate,1,commentAddParams.getReply(),commentAddParams.getContain());
        }else{
            //二级
            commentMapper.insert_comments_for_comment(commentAddParams.getFa_post(),commentAddParams.getFa_comment(),commentAddParams.getCreator(),formattedDate,0,commentAddParams.getReply(),commentAddParams.getContain());
        }
        return null;
    }

    @Override
    public Result comment_like(CommentComParams commentComParams) {
        commentMapper.update_comments_likenum_plus_1(commentComParams.getCommentid());
        return Result.success(null);
    }

    @Override
    public Result comment_list_reply(CommentUserParams commentUserParams) {
       List<Comment> commentList=commentMapper.select_comments_by_reply(commentUserParams.getId());
       return Result.success(commentList);
    }

    @Override
    public Result comment_list_user(CommentUserParams commentUserParams) {
        List<Comment> commentList=commentMapper.select_comments_by_user(commentUserParams.getId());
        return Result.success(commentList);
    }

    @Override
    public Result comment_update_unread(CommentUserParams commentUserParams) {
        commentMapper.update_all_comments_read_by_reply(commentUserParams.getId());
        return Result.success(null);
    }

    @Override
    public Result comment_delete(CommentComParams commentComParams) {
        commentMapper.delete_comments_by_commentid(commentComParams.getCommentid());
        commentMapper.delete_comments_sons_by_fa_comment(commentComParams.getCommentid());
        return Result.success(null);
    }

    @Override
    public Result comment_update_unread_one(CommentComParams commentComParams) {
        commentMapper.update_comments_read_by_Commentid(commentComParams.getCommentid());
        return Result.success(postMapper.select_post_by_post_id(String.valueOf(commentMapper.select_postid_by_commentid(commentComParams.getCommentid()))));
    }


}
