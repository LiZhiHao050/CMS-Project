package com.lizhihao.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.comons.PageUtils;
import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.Category;
import com.lizhihao.cms.entity.Channel;
import com.lizhihao.cms.entity.Links;
import com.lizhihao.cms.service.ArticleService;
import com.lizhihao.cms.service.CategoryService;
import com.lizhihao.cms.service.ChannelService;

/**
 * @author LZH
 * @Date 2019年10月17日
 *  主页控制层
 */

@Controller
public class IndexController {
	
	@Autowired
	ChannelService chns;
	
	@Autowired
	CategoryService cats;
	
	@Autowired
	ArticleService arts;
	
	
	/**
	 * 前往主页
	 * @param requset
	 * @param chnId       频道id
	 * @param catId       分类id
	 * @param pageNum     页码
	 * @return
	 */
	@RequestMapping({"index","/"})
	public String index(HttpServletRequest requset,
			@RequestParam(defaultValue="0") Integer chnId,
			@RequestParam(defaultValue="0") Integer catId,
			@RequestParam(defaultValue="1") Integer pageNum) {
		
		// 获取所有的频道
		List<Channel> channels = chns.getAllChannel();
		
		if (chnId != 0) {                                              // 如果频道的id不为0,即不为首页
			List<Category> categories = cats.getListByChnlId(chnId);   // 获取该频道下的分类列表
			requset.setAttribute("categories", categories);
			PageInfo<Article> articles = arts.getArtList(chnId, catId, pageNum);    // 获取分类下的文章列表
			requset.setAttribute("articles", articles);
			/*PageUtils.page(requset, "/index?chnId="+chnId+"&catId=" + catId, 1, articles.getList(),
					(long)articles.getSize(), articles.getPageNum());*/
		} else {                                                       // 否则即为首页
			PageInfo<Article> hotArticle = arts.getHotArticle(pageNum);// 获取热门文章放置到首页
			requset.setAttribute("articles", hotArticle);
		}
		
		List<Article> newArts = arts.getNewArticle(5);                 // 获取最新文章
		List<Links> friendLinks = arts.getFriendLinks(5);       // 获取友情链接
		
		requset.setAttribute("lasts", newArts);
		requset.setAttribute("links", friendLinks);
		
		requset.setAttribute("chnId", chnId);                          // 频道id
		requset.setAttribute("catId", catId);                          // 分类id
		
		requset.setAttribute("channels", channels);
		return "index";
	}
	
	
	
}

