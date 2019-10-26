package com.lizhihao.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.dao.ArticleMapper;
import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.Links;
import com.lizhihao.cms.service.ArticleService;

/**
 * @author LZH
 * @Date 2019年10月17日
 *  文章类服务实现层
 */

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	ArticleMapper am;

	// 获取文章列表
	@Override
	public PageInfo<Article> getArtList(Integer chnId, Integer catId, Integer pageNum) {
		PageHelper.startPage(pageNum, 5);
		List<Article> list = am.getArtList(chnId, catId);
		return new PageInfo<>(list);
	}
	
	// 获取文章内容
	@Override
	public Article getDetail(Integer aId) {
		Article art = am.getDetail(aId);
		return art;
	}

	// 获取热门文章
	@Override
	public PageInfo<Article> getHotArticle(Integer pageNum) {
		PageHelper.startPage(pageNum, 5);
		List<Article> hotArticle = am.getHotArticle();
		return new PageInfo<>(hotArticle);
	}

	// 获取最新文章
	@Override
	public List<Article> getNewArticle(int num) {
		List<Article> newArts = am.getNewArticle(num);
		return newArts;
	}
	
	@Override
	public List<Links> getFriendLinks(int num) {
		List<Links> friendLinks = am.getFriendLinks(num);
		return friendLinks;
	}

	
	// 管理文章(管理员)
	@Override
	public PageInfo<Article> managerArts(int pageNum, Integer status) {
		PageHelper.startPage(pageNum, 10);
		List<Article> artList = am.managerArts(status);
		return new PageInfo<>(artList);
	}
	
	// 审核文章状态(管理员)
	@Override
	public int auditStatus(Integer status, Integer artId) {
		int res = am.auditStatus(status, artId);
		return res;
	}
	
	// 修改文章热门状态
	@Override
	public int setHot(Integer status, Integer artId) {
		int res = am.setHot(status, artId);
		return res;
	}

}

