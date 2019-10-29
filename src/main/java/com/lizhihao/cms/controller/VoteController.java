package com.lizhihao.cms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lizhihao.cms.comons.ArticleType;
import com.lizhihao.cms.comons.UserConst;
import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.User;
import com.lizhihao.cms.service.VoteService;

/**
 * @author LZH
 * @Date 2019年10月29日
 * 
 */

@Controller
@RequestMapping("vote")
public class VoteController {
	
	@Autowired
	VoteService vs;
	
	/**
	 * 前往发布投票页面
	 * @return
	 */
	@GetMapping("push")
	public String push() {
		return "my/vote/add";
	}
	
	
	/**
	 * 	发布投票文章
	 * @param article
	 * @return
	 */
	@PostMapping("push")
	@ResponseBody
	public boolean pushVote(HttpSession session, Article article) {
		User loginUser = (User) session.getAttribute(UserConst.SESSION_USER_KEY);
		article.setUserId(loginUser.getId());
		article.setArticleType(ArticleType.VOTE);
		
		int res = vs.pushVote(article);
		return res > 0;
	}
	
	
	/**
	 * 	查询投票页面
	 * @return
	 */
	@RequestMapping("list")
	public String showVote(Model model) {
		List<Article> list = vs.list();
		model.addAttribute("list", list);
		return "my/vote/list";
	}
	

}

