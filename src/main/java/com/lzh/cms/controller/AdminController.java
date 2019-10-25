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
import com.lzh.cms.entity.Article;
import com.lzh.cms.entity.User;
import com.lzh.cms.service.AdminService;
import com.lzh.cms.service.ArticleService;

/**
 * @author LZH
 * @Date 2019年10月21日
 * 
 */

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	ArticleService as;
	
	@Autowired
	AdminService adminService;
	
	
	/**
	 * 跳转到管理员的主页
	 * @return
	 */
	@RequestMapping("index")
	public String index() {
		return "admin/index";
	}
	
	
	/**
	 * 	管理员管理文章页
	 * @param model
	 * @param pageNum       分页之页码
	 * @param status       	根据文章状态查找
	 * @return
	 */
	@RequestMapping("managerArts")
	public String managerArts(Model model, @RequestParam(defaultValue="1") Integer pageNum,
			@RequestParam(defaultValue="3") Integer status) {
		PageInfo<Article> managerArts = as.managerArts(pageNum, status);
		
		model.addAttribute("managerArts", managerArts);
		model.addAttribute("status", status);
		
		String pageStr = PageUtils.pageLoad(managerArts.getPageNum(),managerArts.getPages() , "/admin/managerArts?status="+status, 10);
		model.addAttribute("pageUtil", pageStr);
		
		return "admin/article/managerArts";
	}
	
	/**
	 * 	管理员获取文章
	 * @param model        文章ID
	 * @param id
	 * @return
	 */
	@RequestMapping("getArticle")
	public String getArticle(Model model, Integer id) {
		Article article = as.getDetail(id);
		model.addAttribute("article", article);
		return "admin/article/detail";
	}
	
	/**
	 * 	管理员审核文章状态
	 * @param request
	 * @param status         文章状态
	 * @param artId          文章ID
	 * @return
	 */
	@RequestMapping("passArticle")
	@ResponseBody
	public ResultMsg passArticle(HttpServletRequest request, Integer status, Integer artId) {
		
		User loginUser = (User) request.getSession().getAttribute(UserConst.SESSION_USER_KEY);
		
		if (loginUser == null) {                                   // 判断是否登录
			return new ResultMsg(2, "您尚未登录,不能审核文章", null);
		}
		
		if (loginUser.getRole() != UserConst.USER_ROLE_ADMIN) {    // 判断是否为管理员
			return new ResultMsg(3, "您没有权限审核文章", null);
		}
		
		Article article = as.getDetail(artId);
		if (article == null) {                                     // 判断文章是否存在
			return new ResultMsg(4, "该文章不存在", null);
		}
		
		if (article.getStatus() == status) {                       // 判断文章的状态
			return new ResultMsg(5, "此文章就是您要修改的状态", null);
		}
		
		int res = as.auditStatus(status, artId);         // 调用审核状态
		if (res > 0) {                                   // 根据审核结果返回信息
			return new ResultMsg(1, "审核成功!", null);
		} else {
			return new ResultMsg(6, "审核失败", null);
		}
		
	}
	
	
	/**
	 * 	修改热门文章状态
	 * @param request
	 * @param status          热门状态
	 * @param artId           文章ID
	 * @return
	 */
	@RequestMapping("sethot")
	@ResponseBody
	public ResultMsg setHot(HttpServletRequest request, Integer status, Integer artId) {
		
		User loginUser = (User) request.getSession().getAttribute(UserConst.SESSION_USER_KEY);   // 获取登录人
		if (loginUser == null) {                                   // 判断是否登录
			return new ResultMsg(2, "您尚未登录,不能修改热门文章状态", null);
		}
		if (loginUser.getRole() != UserConst.USER_ROLE_ADMIN) {    // 判断是否为管理员
			return new ResultMsg(3, "您没有权限修改热门文章状态", null);
		}
		
		Article article = as.getDetail(artId);
		if (article == null) {
			return new ResultMsg(4, "该文章不存在", null);
		}
		if (article.getHot() == status) {
			return new ResultMsg(5, "此文章就是您要修改的状态", null);
		}
		
		int res = as.setHot(status, artId);
		if (res > 0) {
			return new ResultMsg(1, "已修改为热门文章!", null);
		} else {
			return new ResultMsg(6, "修改失败", null);
		}
	}
	
	/**
	 * 	管理员管理用户
	 * @param model
	 * @param pageNum     分页页码
	 * @param name        模糊查询名称
	 * @return
	 */
	@RequestMapping("managerUser")
	public String managerUser(Model model, @RequestParam(defaultValue="1") Integer pageNum,
			@RequestParam(defaultValue="") String name) {
		
		PageInfo<User> userList = adminService.userList(pageNum, name);
		
		String pageStr = PageUtils.pageLoad(userList.getPageNum(),userList.getPages() , "/admin/managerUser?name="+name, 10);
		
		model.addAttribute("name", name);
		model.addAttribute("userList", userList);
		model.addAttribute("pageUtil", pageStr);
		
		return "admin/user/list";
	}
	
	/**
	 * 	修改用户状态
	 * @param id
	 * @param locked
	 * @return
	 */
	@RequestMapping("modifyUserStatus")
	@ResponseBody
	public boolean modifyUserStatus(Integer id, Integer locked) {
		System.out.println("修改状态为:" + locked);
		int res = adminService.modifyUserStatus(id, locked);
		return res > 0;
	}
	
	
	/**
	 * 	管理友情链接
	 * @return
	 */
	@RequestMapping("managerLinks")
	public String managerLinks(Model model, @RequestParam(defaultValue="1") Integer pageNum,
			@RequestParam(defaultValue="") String name) {
		
		
		return "admin/links/list";
	}
}

