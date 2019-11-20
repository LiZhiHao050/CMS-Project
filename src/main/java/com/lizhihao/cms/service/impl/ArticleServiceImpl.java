package com.lizhihao.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
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
	private ArticleMapper am;
	
	// 注入Redis模板
	@Autowired
	private RedisTemplate<String, Article> redisTemplate;

	
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
		ListOperations<String, Article> opsForList = redisTemplate.opsForList();
		
		PageInfo<Article> pageInfo = null;
		
		if (redisTemplate.hasKey("HotArticle")) {
			// 存在热门文章
			List<Article> list = opsForList.range("HotArticle", (pageNum - 1) * 5, pageNum * 10 - 1);
			pageInfo = new PageInfo<>(list);
		} else {
			// 不存在热门文章,从数据库查询所有的热门文章
			List<Article> hotArticle = am.getHotArticle();
			// 存入Redis中
			opsForList.rightPushAll("HotArticle", hotArticle);
			
			// 分页
			PageHelper.startPage(pageNum, 5);
			// 获取分页的数据(管理员审核文章将清除数据重新加载)
			List<Article> hotArticleOfPage = am.getHotArticle();
			pageInfo = new PageInfo<>(hotArticleOfPage);
		}
		
		return pageInfo;
	}

	// 获取最新文章
	@Override
	public List<Article> getNewArticle(int num) {
		ListOperations<String, Article> opsForList = redisTemplate.opsForList();
		
		List<Article> newArts = null;
		
		if (redisTemplate.hasKey("NewArticle")) {
			// 存在数据,直接从Redis中获取数据
			newArts = opsForList.range("NewArticle", 0, -1);
		} else {
			// 不存在数据,从数据库中获取数据
			newArts = am.getNewArticle(num);
			// 将数据存入Redis中(管理员审核文章将清除数据重新加载)
			opsForList.rightPushAll("NewArticle", newArts);
		}
		
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
		// 判断修改的状态
		if (res > 0) {
			redisTemplate.delete("NewArticle");      // 清除Redis中的最新/热门文章数据
		}
		
		return res;
	}
	
	// 修改文章热门状态(管理员)
	@Override
	public int setHot(Integer status, Integer artId) {
		int res = am.setHot(status, artId);
		// 判断修改的状态
		if (res > 0) {
			redisTemplate.delete("HotArticle");      // 清除Redis中的热门文章数据
		}
		
		return res;
	}
	
	// 增加文章点击量
	@Override
	public int updateHits(Integer aId) {
		return am.updateHits(aId);
	}
	
	// 通过文章ID查找文章
	@Override
	public Article findArtById(Integer id) {
		Article findArtById = am.findArtById(id);
		return findArtById;
	}

}

