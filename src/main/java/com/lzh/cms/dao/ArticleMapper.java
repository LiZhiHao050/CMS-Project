package com.lzh.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lzh.cms.entity.Article;

/**
 * @author LZH
 * @Date 2019年10月17日
 * 	文章类
 */

public interface ArticleMapper {
	
	// 根据频道ID和分类ID获取文章列表
	List<Article> getArtList(@Param("chnId")Integer chnId, @Param("catId")Integer catId);

	// 获取文章内容
	Article getDetail(Integer aId);
	
	// 获取热门文章
	List<Article> getHotArticle();

	// 获取最新文章
	List<Article> getNewArticle(int num);
	
	// 根据文章ID回显
	Article findArtById(Integer id);
	
}

