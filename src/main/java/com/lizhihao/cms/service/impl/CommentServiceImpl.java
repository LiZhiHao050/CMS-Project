package com.lizhihao.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.dao.CommentMapper;
import com.lizhihao.cms.entity.Comment;
import com.lizhihao.cms.service.CommentService;

/**
 * @author LZH
 * @Date 2019年10月23日
 * 评论服务实现层
 */

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentMapper cm;
	
	// 获取评论列表(文章ID)
	@Override
	public PageInfo<Comment> getComList(Integer articleId, Integer pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<Comment> comments = cm.getComList(articleId);
		return new PageInfo<Comment>(comments);
	}
	
	// 发表评论
	@Override
	public int addComment(String content, Integer articleId, Integer userId) {
		int res = cm.addComment(content, articleId, userId);
		return res;
	}
	
	// 删除评论
	@Override
	public int delComment(Integer comId) {
		int res = cm.delComment(comId);
		return res;
	}
	
	
	

}

