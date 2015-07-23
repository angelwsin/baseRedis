package com.p2p.dao.redis;

import org.springframework.stereotype.Repository;

import com.p2p.bean.comment.Comment;

@Repository
public class CommentDao extends BaseDao<Comment> implements ICommentDao{
}
