package com.lizhihao.cms.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.lizhihao.cms.comons.ArticleType;
import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.Category;
import com.lizhihao.cms.entity.Channel;
import com.lizhihao.cms.entity.Votes;
import com.lizhihao.cms.service.ArticleService;
import com.lizhihao.cms.service.CategoryService;
import com.lizhihao.cms.service.ChannelService;
import com.lizhihao.cms.service.VoteService;

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
	
	@Autowired
	VoteService voteService;
	
	/**
	 * 获取文章内容
	 * @param request
	 * @param aId
	 * @return
	 */
	@RequestMapping("getDetail")
	public String getDetail(HttpServletRequest request, Integer aId) {
		Article article = arts.getDetail(aId);        // 根据文章ID获取文章
		int res = arts.updateHits(aId);
		System.out.println("增加点击量:" + res);
		
		if (article.getArticleType() == ArticleType.HTML) {
			System.out.println("HTML");
			request.setAttribute("article", article);
			return "article/detail";
		} else if (article.getArticleType() == ArticleType.IMAGE){
			System.out.println("Image");
			Gson gson = new Gson();
			article.setImgList(gson.fromJson(article.getContent(), List.class));
			request.setAttribute("article", article);
			return "article/imgdetail";
		} else {
			System.out.println("Vote");
//			Article voteArticle = arts.findArtById(aId);
//			request.setAttribute("voteArticle", voteArticle);
			
			Gson gson = new Gson();

	        LinkedHashMap<String,String> map = gson.fromJson(article.getContent(), LinkedHashMap.class);

	        LinkedHashMap<String,Votes> lmap = new LinkedHashMap<String,Votes>();
	        Set<Map.Entry<String, String>> entrySet = map.entrySet();

	        List<Votes> voteStatics = voteService.getVoteStatics(aId);
	        // 计算多少人投票
	        int totalNum = 0;
	        for (Votes voteStatic : voteStatics) {
	            totalNum += voteStatic.getVoteNum();
	        }

	        // 生成新的map集合
	        for (Map.Entry<String, String> entry : entrySet) {
	            Votes voteStatic = new Votes();
	            voteStatic.setOptionKey(entry.getKey());
	            voteStatic.setVoteNumTotal(totalNum);
	            voteStatic.setOptionTitle(entry.getValue());
	            lmap.put(entry.getKey(), voteStatic);
	        }



	        //每一项的结果
	        for (Votes voteStatic : voteStatics) {
	            Votes showStatic = lmap.get(voteStatic.getOptionKey());
	            showStatic.setVoteNum(voteStatic.getVoteNum());
	        }

	        request.setAttribute("lmap", lmap);

	        return "my/vote/detail";
			
		}
		
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
		Article article = arts.getDetail(id);                 // 根据ID获取文章
		
		request.setAttribute("article", article);
		request.setAttribute("content1", article.getContent());
		return "my/update";
	}
	
}

