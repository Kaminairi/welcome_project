package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CommentMapper {


    /**
     * 【调用接口】
     * 【作用】 按照时间升序调取某个文章的评论列表
     * @param
     * @return
     */
    @Select("select " +
            "   c.id as id, " +
            "   c.fa_post, " +
            "   c.fa_comment, " +
            "   c.creator, " +
            "   u1.name as creatorname, " +
            "   u1.img as creatorimg, " +
            "   c.ctime, " +
            "   c.likenum, " +
            "   c.is_facomment, " +
            "   c.reply, " +
            "   u2.name as replyname, " +
            "   c.contain, " +
            "   c.replyread " +
            "from " +
            "   comments c " +
            "join " +
            "   users u1 on c.creator=u1.id " +
            "left join " +
            "   users u2 on c.reply=u2.id " +
            "where " +
            "   c.fa_post =#{id} " +
            "order by " +
            "   coalesce(c.fa_comment,c.id), " +
            "   c.ctime; ")
    public List<Comment> select_comments_all_by_postid_order_time_asc(BigInteger id);


    /**
     * 【调用接口】 login_idpwd
     * 【作用】 按照时间降序调取某个文章的评论列表
     * @param
     * @return
     */
    @Select("select " +
            "   c.id as id, " +
            "   c.fa_post, " +
            "   c.fa_comment, " +
            "   c.creator, " +
            "   u1.name as creatorname, " +
            "   u1.img as creatorimg, " +
            "   c.ctime, " +
            "   c.likenum, " +
            "   c.is_facomment, " +
            "   c.reply, " +
            "   u2.name as replyname, " +
            "   c.contain, " +
            "   c.replyread " +
            "from " +
            "   comments c " +
            "join " +
            "   users u1 on c.creator=u1.id " +
            "left join " +
            "   users u2 on c.reply=u2.id " +
            "where " +
            "   c.fa_post =#{id} " +
            "order by " +
            "   coalesce(c.fa_comment,c.id) desc, " +
            "   c.ctime; ")
    public List<Comment> select_comments_all_by_postid_order_time_desc(BigInteger id);

    /**
     * 【调用接口】 login_idpwd
     * 【作用】 给评论点赞
     * @param
     * @return
     */
    @Update("update " +
            "comments " +
            "set likenum =likenum+1 " +
            "where id=#{id} ")
    public void update_comments_likenum_plus_1(BigInteger id);

    /**
     * 【调用接口】 login_idpwd
     * 【作用】 通过id拉取用户背包信息
     * @param
     * @return
     */
    @Insert("insert " +
            "into comments " +
            "(fa_post,       creator,ctime,is_facomment,reply,contain) " +
            "value " +
            "(#{fa_post},       #{creator},#{ctime},#{is_facomment},#{reply},#{contain}) ")
public void insert_comments_for_post(BigInteger fa_post,String creator,String ctime,int is_facomment,String reply,String contain);

    /**
     * 【调用接口】 login_idpwd
     * 【作用】 通过id拉取用户背包信息
     * @param
     * @return
     */
    @Insert("insert " +
            "into comments " +
            "(fa_post,fa_comment,creator,ctime,is_facomment,reply,contain) " +
            "value " +
            "(#{fa_post},#{fa_comment},#{creator},#{ctime},#{is_facomment},#{reply},#{contain}) ")
    public void insert_comments_for_comment(BigInteger fa_post,BigInteger fa_comment,String creator,String ctime,int is_facomment,String reply,String contain);

    /**
     * 【调用接口】 login_idpwd
     * 【作用】 通过id拉取用户背包信息
     * @param
     * @return
     */
    @Select("select " +
            "   c.id as id, " +
            "   c.fa_post, " +
            "   c.fa_comment, " +
            "   c.creator, " +
            "   u1.name as creatorname, " +
            "   c.ctime, " +
            "   c.likenum, " +
            "   c.is_facomment, " +
            "   c.reply, " +
            "   u2.name as replyname, " +
            "   c.contain, " +
            "   c.replyread " +
            "from " +
            "   comments c " +
            "join " +
            "   users u1 on c.creator=u1.id " +
            "left join " +
            "   users u2 on c.reply=u2.id " +
            "where " +
            "   c.creator =#{user_id} ")
    public List<Comment> select_comments_by_user(String user_id);

    /**
     * 【调用接口】 login_idpwd
     * 【作用】 获得该用户作为被回复者的所有未读回复
     * ‘
     * @param
     * @return
     */
    @Select("select " +
            "   c.id as id, " +
            "   c.fa_post, " +
            "   c.fa_comment, " +
            "   c.creator, " +
            "   u1.name as creatorname, " +
            "   c.ctime, " +
            "   c.likenum, " +
            "   c.is_facomment, " +
            "   c.reply, " +
            "   u2.name as replyname, " +
            "   c.contain, " +
            "   c.replyread " +
            "from " +
            "   comments c " +
            "join " +
            "   users u1 on c.creator=u1.id " +
            "left join " +
            "   users u2 on c.reply=u2.id " +
            "where " +
            "   c.reply =#{user_id} " +
            "and " +
            "   replyread=0 ")
    public List<Comment> select_unread_comments_by_reply(String user_id);

    /**
     * 【调用接口】 login_idpwd
     * 【作用】 获得该用户作为被回复者的所有评论
     * @param
     * @return
     */
    @Select("select " +
            "   c.id as id, " +
            "   c.fa_post, " +
            "   c.fa_comment, " +
            "   c.creator, " +
            "   u1.name as creatorname, " +
            "   c.ctime, " +
            "   c.likenum, " +
            "   c.is_facomment, " +
            "   c.reply, " +
            "   u2.name as replyname, " +
            "   c.contain, " +
            "   c.replyread " +
            "from " +
            "   comments c " +
            "join " +
            "   users u1 on c.creator=u1.id " +
            "left join " +
            "   users u2 on c.reply=u2.id " +
            "where " +
            "   c.reply =#{user_id} " )
    public List<Comment> select_comments_by_reply(String user_id);


    @Select("select " +
            "fa_post " +
            "from comments " +
            "where id=#{commentid} ")
    public BigInteger select_postid_by_commentid(BigInteger commentid);

    /**
     * 【调用接口】 login_idpwd
     * 【作用】 更新改用户  所有  的被回复为已读
     * @param
     * @return
     */
    @Update("update " +
            "comments " +
            "set replyread=1 " +
            "where reply=#{user_id} ")
    public void update_all_comments_read_by_reply(String user_id);


    /**
     * 【调用接口】 login_idpwd
     * 【作用】 更新改用户  某个  的被回复为已读
     * @param
     * @return
     */
    @Update("update " +
            "comments " +
            "set replyread=1 " +
            "where id=#{comment_id} ")
    public void update_comments_read_by_Commentid(BigInteger comment_id);

    /**
     * 【调用接口】 login_idpwd
     * 【作用】 删除选中的评论
     * @param
     * @return
     */
    @Delete("delete " +
            "from comments " +
            "where id=#{comment_id}")
    public void delete_comments_by_commentid(BigInteger comment_id);

    @Delete("delete " +
            "from comments "  +
            "where " +
            "fa_comment =#{commentid}")
    public void delete_comments_sons_by_fa_comment(BigInteger commentid);

    @Delete("delete " +
            "from comments " +
            "where " +
            "fa_post = #{postid}")
    public void delete_comments_sons_by_fa_post(BigInteger postid);
}
