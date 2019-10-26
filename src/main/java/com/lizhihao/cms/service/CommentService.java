package com.lizhihao.cms.service;

import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.entity.Comment;

/**
 * @author LZH
 * @Date 2019年10月23日
 * 评论服务接口层
 */

public interface CommentService {
	
	// 获取评论列表(文章ID, 页码)
	PageInfo<Comment> getComList(Integer articleId, Integer pageNum);
	
	/**
	 *	发表评论
	 * @param content        评论内容
	 * @param articleId      文章ID
	 * @param userId         用户ID
	 * @return
	 */
	int addComment(String content, Integer articleId, Integer userId);
	
	/**
	 * 	删除评论
	 * @param comId          评论ID
	 * @return
	 */
	int delComment(Integer comId);

}

