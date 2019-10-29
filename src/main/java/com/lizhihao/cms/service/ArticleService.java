package com.lizhihao.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.Links;

/**
 * @author LZH
 * @Date 2019年10月17日
 * 	文章类服务层
 */

public interface ArticleService {
	
	// 获取文章列表
	PageInfo<Article> getArtList(Integer chnId, Integer catId, Integer pageNum);

	// 获取文章内容
	Article getDetail(Integer aId);
	
	// 获取热门文章
	PageInfo<Article> getHotArticle(Integer pageNum);
	
	/**
	 * 通过文章ID查找文章
	 * @param id
	 * @return
	 */
	Article findArtById(Integer id);
	
	/**
	 *  获取最新文章
	 * @param num      显示多少条数据
	 * @return
	 */
	List<Article> getNewArticle(int num);
	
	/**
	 * 	获取友情链接
	 * @param num      显示多少条数据
	 * @return
	 */
	List<Links> getFriendLinks(int num);
	
	
	/**
	 *	管理文章(管理员)
	 * @param pageNum
	 * @param status 
	 * @return
	 */
	PageInfo<Article> managerArts(int pageNum, Integer status);
	
	
	/**
	 * 	审核文章状态(管理员)
	 * @param status        文章状态
	 * @param artId         文章ID
	 * @return
	 */
	int auditStatus(Integer status, Integer artId);
	
	
	/**
	 * 	修改文章热门状态
	 * @param status        热门状态
	 * @param artId         文章ID
	 * @return
	 */
	int setHot(Integer status, Integer artId);
	
	/**
	 * 	增加文章点击量
	 * @param aId
	 * @return
	 */
	int updateHits(Integer aId);
	
}

