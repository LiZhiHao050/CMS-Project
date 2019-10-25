package com.lzh.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.lzh.cms.comons.PageUtils;
import com.lzh.cms.comons.ResultMsg;
import com.lzh.cms.comons.UserConst;
import com.lzh.cms.entity.Comment;
import com.lzh.cms.entity.User;
import com.lzh.cms.service.CommentService;

/**
 * @author LZH
 * @Date 2019年10月23日
 * 评论控制层
 */

@Controller
@RequestMapping("comment")
public class CommentController {
	
	@Autowired
	CommentService cs;

	
	/**
	 * 获取评论
	 * @param articleId
	 * @return
	 */
	@RequestMapping("getComList")
	public String getComList(Model model, Integer articleId,
			@RequestParam(defaultValue="1") Integer pageNum) {
		
		PageInfo<Comment> commenPage = cs.getComList(articleId, pageNum);
		String pageStr = PageUtils.pageLoad(commenPage.getPageNum(), commenPage.getPages(), "/comment/getComList?articleId="+articleId, 10);
		
		model.addAttribute("commenPage", commenPage);
		model.addAttribute("pageUtil", pageStr);
		
		return "my/comment/list";
	}
	
	/**
	 * 用户评论
	 * @param request
	 * @param content       评论内容
	 * @param articleId     文章ID
	 * @return
	 */
	@RequestMapping("addComment")
	@ResponseBody
	public ResultMsg addComment(HttpServletRequest request, String content, Integer articleId) {
		User loginUser = (User) request.getSession().getAttribute(UserConst.SESSION_USER_KEY);
		
		if (loginUser == null) {
			return new ResultMsg(-1, "您尚未登录,不能评论", null);
		}
		
		int res = cs.addComment(content, articleId, loginUser.getId());
		
		if (res > 0) {
			return new ResultMsg(1, "发表成功!", null);
		} else {
			return new ResultMsg(0, "发表失败!", null);
		}
	}
	
	
	/**
	 * 	删除评论
	 * @param request
	 * @param comId          评论ID
	 * @return
	 */
	@RequestMapping("delComment")
	@ResponseBody
	public ResultMsg delComment(HttpServletRequest request, Integer comId) {
		User loginUser = (User) request.getSession().getAttribute(UserConst.SESSION_USER_KEY);
		
		if (loginUser == null) {
			return new ResultMsg(0, "您尚未登录,不能删除评论", null);
		}
		
		int res = cs.delComment(comId);
		if (res > 0) {
			return new ResultMsg(1, "删除成功!", null);
		} else {
			return new ResultMsg(9, "删除失败!", null);
		}
	}
	
	
}

