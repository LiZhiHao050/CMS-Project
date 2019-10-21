package com.lzh.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzh.cms.entity.Article;
import com.lzh.cms.entity.Category;
import com.lzh.cms.entity.Channel;
import com.lzh.cms.service.ArticleService;
import com.lzh.cms.service.CategoryService;
import com.lzh.cms.service.ChannelService;

/**
 * @author LZH
 * @Date 2019年10月18日
 * 
 */

@RequestMapping("article")
@Controller
public class ArticleController {
	
	@Autowired
	ArticleService arts;
	
	@Autowired
	CategoryService cats;
	
	@Autowired
	ChannelService chns;
	
	
	/**
	 * 获取文章内容
	 * @param request
	 * @param aId
	 * @return
	 */
	@RequestMapping("getDetail")
	public String getDetail(HttpServletRequest request, Integer aId) {
		Article article = arts.getDetail(aId);
		request.setAttribute("article", article);
		return "article/detail";
	}
	
	
	
	/**
	 * Ajax获取频道列表
	 * @return
	 */
	@GetMapping("getAllChnl")
	@ResponseBody
	public List<Channel> getAllChnl() {
		List<Channel> channels = chns.getAllChannel();
		return channels;
	}
	
	
	/**
	 * 二级联动获取分类列表
	 * @param chnlId
	 * @return
	 */
	@GetMapping("getCatByChnl")
	@ResponseBody
	public List<Category> getCatByChnl(Integer chnlId) {
		List<Category> categories = cats.getListByChnlId(chnlId);
		return categories;
	}
	
	
	/**
	 * 	修改回显
	 * @param request
	 * @param id       文章ID
	 * @return
	 */
	@GetMapping("update")
	public String toUpdate(HttpServletRequest request, Integer id) {
		Article article = arts.findArtById(id);                 // 根据ID获取文章
		
		request.setAttribute("article", article);
		request.setAttribute("content1", article.getContent());
		return "my/update";
	}
	
}

