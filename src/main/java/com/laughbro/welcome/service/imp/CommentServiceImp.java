package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.CommentMapper;
import com.laughbro.welcome.dao.mapper.PostMapper;
import com.laughbro.welcome.dao.pojo.Comment;
import com.laughbro.welcome.service.CommentService;
import com.laughbro.welcome.utils.TimeUtils;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.comment_params.CommentAddParams;
import com.laughbro.welcome.vo.params.comment_params.CommentComParams;
import com.laughbro.welcome.vo.params.comment_params.CommentListParams;
import com.laughbro.welcome.vo.params.comment_params.CommentUserParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private TimeUtils timeUtils;


    @Override
    public Result comment_list(BigInteger postid, int time_order, int like_order) {
        List<Comment> commentList = null;
        //判断是否点赞排序
        if(like_order==1){
            //判断时间排序方式
            if(time_order==1){


            }else{


            }
        }else{
            //判断时间排序方式
            if(time_order==1){
                //时间升序，无点赞
                   commentList= commentMapper.select_comments_all_by_postid_order_time_asc(postid);
            }else{
                //时间降序，无点赞
                commentList= commentMapper.select_comments_all_by_postid_order_time_desc(postid);
            }
        }

        return Result.success(commentList);
    }

    @Override
    public Result comment_add(CommentAddParams commentAddParams) {
        //获得当前时间
        String formattedDate = timeUtils.timeGetNow();
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
        //判断输入是否合法
        if(commentComParams.getCommentid()==null){return Result.fail(444,"键入值为空",null);}
        //更新数据库
        int r=commentMapper.update_comments_likenum_plus_1(commentComParams.getCommentid());
        //判断是否更新成功
        if(r==0) {
            //无记录更新
            return Result.fail(201,"没有数据更新",null);
        }else{
            return Result.success(null);
        }
    }

    @Override
    public Result comment_list_reply(String id) {
       List<Comment> commentList=commentMapper.select_comments_by_reply(id);
       return Result.success(commentList);
    }

    @Override
    public Result comment_list_user(String id) {
        List<Comment> commentList=commentMapper.select_comments_by_user(id);
        return Result.success(commentList);
    }

    @Override
    public Result comment_update_unread(CommentUserParams commentUserParams) {
        int r=commentMapper.update_all_comments_read_by_reply(commentUserParams.getId());
        return Result.success("更新了数据 "+r);
    }

    @Override
    public Result comment_delete(CommentComParams commentComParams) {
        //删除选中评论
        int r=commentMapper.delete_comments_by_commentid(commentComParams.getCommentid());
        if(r==0){
            return Result.fail(1111,"无删除操作",null);
        }else{
            //删除其子评论
            int r2=commentMapper.delete_comments_sons_by_fa_comment(commentComParams.getCommentid());
            return Result.success("删除父评论 "+r+" 删除子评论 "+r2);
        }

    }

    @Override
    public Result comment_update_unread_one(CommentComParams commentComParams) {
        commentMapper.update_comments_read_by_Commentid(commentComParams.getCommentid());
        return Result.success(postMapper.select_post_by_post_id(String.valueOf(commentMapper.select_postid_by_commentid(commentComParams.getCommentid()))));
    }


}
