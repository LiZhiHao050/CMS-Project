package com.lzh.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzh.cms.comons.UserConst;
import com.lzh.cms.entity.User;
import com.lzh.cms.service.UserService;

/**
 * @author LZH
 * @Date 2019年10月16日
 * 
 */

@RequestMapping("user")
@Controller
public class UserController {
	
	@Autowired
	UserService us;

	// 跳转到注册页面
	@GetMapping("register")
	public String register() {
		return "user/register";
	}
	
	/**
	 * 跳转到登录
	 * @return
	 */
	@GetMapping("login")
	public String toLogin() {
		return "user/login";
	}
	
	/**
	 * 校验用户名唯一性
	 * @param username
	 * @return
	 */
	@RequestMapping("checkExist")
	@ResponseBody
	public boolean checkExist(String username) { 
		boolean res = us.checkExist(username);
		return !res;
	}
	
	/**
	 * 注册及验证
	 * @param user
	 * @param errorResult
	 * @param model
	 * @return
	 */
	@PostMapping("register")
	public String register(@Validated User user, BindingResult errorResult, Model model) {
		
		if (errorResult.hasErrors()) {  // validate验证
			return "user/register";
		}
		
		int res = us.register(user);     // 获取注册结果
		if (res > 0) {
			return "redirect:login";
		} else {
			model.addAttribute("msg", "注册失败,请稍后再试");
			return "user/register";
		}
	}
	
	/**
	 * 登录
	 * @param user
	 * @param request
	 * @param errorRes
	 * @return
	 */
	@PostMapping("login")
	public String login(@Validated User user, HttpServletRequest request, BindingResult errorRes) {
		
		if (errorRes.hasErrors()) {
			return "login";
		}
		
		User login = us.login(user);         // 调用登录判断
		if (login == null) {
			request.setAttribute("error", "用户名或密码错误!");
			return "user/login";
		} else {
			request.getSession().setAttribute(UserConst.SESSION_USER_KEY, login);
			// 判断
			if (login.getRole() == UserConst.USER_ROLE_GENERAL) {
				return "redirect:../index";           // 普通用户重定向到主页
			} else if (login.getRole() == UserConst.USER_ROLE_ADMIN) {
				return "redirect:../admin/index"; // 管理员重定向到管理页
			} else {
				return "user/login";              // 其他情况
			}
		}
		
	}
	
	
	/**
	 * 登出
	 * @param session
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute(UserConst.SESSION_USER_KEY);
		return "user/login";
	}
	
	
}

