package com.lzh.cms.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.lzh.cms.comons.UserConst;
import com.lzh.cms.entity.Article;
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
				return "redirect:home";           // 普通用户重定向到主页
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
	
	
	/**
	 * 跳转到个人中心(普通用户)
	 * @return
	 */
	@RequestMapping("home")
	public String toHome() {
		return "my/home";
	}
	
	
	/**
	 * 跳转到我的文章
	 * @param request
	 * @param pageNum    当前页码
	 * @return
	 */
	@RequestMapping("myArticles")
	public String myArticles(HttpServletRequest request, 
			@RequestParam(defaultValue="1")Integer pageNum) {
		
		User loginUser = (User) request.getSession().getAttribute(UserConst.SESSION_USER_KEY);  // 获取登录用户的信息
		PageInfo<Article> articles = us.myArticles(pageNum, loginUser.getId());
		request.setAttribute("articles", articles);
		
		return "/my/list";
	}
	
	
	/**
	 * 去发布文章
	 * @return
	 */
	@RequestMapping("toPublish")
	public String toPublish() {
		return "article/publish";
	}
	
	/**
	 * 用户发布文章(添加)
	 * @param request
	 * @param article
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("publish")
	@ResponseBody
	public boolean publish(HttpSession session, Article article, MultipartFile file) throws IllegalStateException, IOException {
		
		prossesFile(file, article);     // 处理文件上传
		
		User loginUser = (User) session.getAttribute(UserConst.SESSION_USER_KEY);   // 获取登录的用户信息
		article.setUserId(loginUser.getId());       // 将登录用户信息存入传递类
		
		int res = us.publish(article);
		
		return res > 0;
	}
	
	
	/**
	 * 上传保存文件操作方法
	 * @param file
	 * @param article
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private void prossesFile(MultipartFile file, Article article) throws IllegalStateException, IOException {
		
		String filename = file.getOriginalFilename();                  // 获取文件名称
		if ("".equals(filename)) {                                     // 对获取的文件名进行非空判断
			return;
		}
		String suffix = filename.substring(filename.lastIndexOf(".")); // 获取文件后缀名称
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String today = sdf.format(new Date());
		
		// 生成路径
		String path = "Z:/CMS_Workspace/lizhihao-cms/src/main/webapp/resource/pic/" + today;
		
		File menu = new File(path);
		if (!menu.exists()) {        // 如果文件不存在则创建文件
			menu.mkdir();
		}
		
		String newName = UUID.randomUUID().toString() + suffix;     // 生成新文件名称
		String finalPath = path + "/" + newName;                    // 生成最终文件路径
		String dbPath = today + "/" + newName;                      // 生成数据库文件路径
		
		File saveFile = new File(finalPath);                        // 最终
		file.transferTo(saveFile);                                  // 保存文件
		article.setPicture(dbPath);                                 // 数据库保存文件路径
	}
	
}

