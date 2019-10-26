package com.lizhihao.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.lizhihao.cms.entity.Comment;

/**
 * @author LZH
 * @Date 2019年10月23日
 * 评论
 */

public interface CommentMapper {
	
	// 获取文章列表
	List<Comment> getComList(Integer articleId);

	// 发表评论(评论数量使用触发器增加)
	@Insert("INSERT INTO cms_comment (articleId,userId,content,created) VALUES (#{articleId},#{userId},#{content},now())")
	int addComment(@Param("content")String content, @Param("articleId")Integer articleId, @Param("userId")Integer userId);
	
	// 删除评论(评论数量使用触发器删除)
	@Delete("DELETE FROM cms_comment WHERE id = #{value}")
	int delComment(Integer comId);
	
}

