package com.lzh.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzh.cms.dao.ArticleMapper;
import com.lzh.cms.entity.Article;
import com.lzh.cms.service.ArticleService;

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

	// 根据文章ID回显
	@Override
	public Article findArtById(Integer id) {
		Article art = am.findArtById(id);
		return art;
	}

}

