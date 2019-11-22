package com.lizhihao.cms.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.google.gson.Gson;
import com.lizhihao.cms.comons.ArticleType;
import com.lizhihao.cms.comons.ResultMsg;
import com.lizhihao.cms.comons.UserConst;
import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.ImageBean;
import com.lizhihao.cms.entity.User;
import com.lizhihao.cms.service.ArticleService;
import com.lizhihao.cms.service.UserService;

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

	@Autowired
	ArticleService as;
	
	
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
	public String toLogin(HttpSession session) {
		Object login = session.getAttribute(UserConst.SESSION_USER_KEY);
		if (login != null) {                // 如果用户已经登录
			return "redirect:../index";     // 跳转到首页
		}
		
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
			if (login.getLocked() == 0) {             // 判断用户是否被冻结
				request.getSession().setAttribute(UserConst.SESSION_USER_KEY, login);
				// 判断
				if (login.getRole() == UserConst.USER_ROLE_GENERAL) {
					return "redirect:home";           // 普通用户重定向到主页
				} else if (login.getRole() == UserConst.USER_ROLE_ADMIN) {
					return "redirect:../admin/index"; // 管理员重定向到管理页
				} else {
					return "user/login";              // 其他情况
				}
			} else {
				request.setAttribute("error", "该用户已被冻结!");
				return "user/login";
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
		session.removeAttribute(UserConst.SESSION_USER_KEY);          // 清除该域
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
			@RequestParam(defaultValue="1")Integer pageNum, String search) {
		
		User loginUser = (User) request.getSession().getAttribute(UserConst.SESSION_USER_KEY);  // 获取登录用户的信息
		PageInfo<Article> articles = us.myArticles(pageNum, loginUser.getId(), search);
		
		request.setAttribute("articles", articles);
		request.setAttribute("search", search);
		
		return "/my/list";
	}
	
	
	/**
	 * 	去发布文章
	 * @return
	 */
	@RequestMapping("toPublish")
	public String toPublish() {
		return "article/publish";
	}
	
	/**
	 * 	去发布多图片文章
	 * @return
	 */
	@RequestMapping("toPublistImgs")
	public String toPublistImgs() {
		return "article/publishimg";
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
		
		article.setArticleType(ArticleType.HTML);          // 类型为HTML
		prossesFile(file, article);     // 处理文件上传
		
		User loginUser = (User) session.getAttribute(UserConst.SESSION_USER_KEY);   // 获取登录的用户信息
		article.setUserId(loginUser.getId());       // 将登录用户信息存入传递类
		
		int res = us.publish(article);
		
		return res > 0;
	}
	
	/**
	 * 	发布图片文章
	 * @param request
	 * @param article
	 * @param file
	 * @param imgs
	 * @param imgsdesc
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("publishimg")
	@ResponseBody
	public boolean publishimg(HttpServletRequest request, Article article, 
			@RequestParam("file") MultipartFile file,   // 标题图片
			@RequestParam("imgs") MultipartFile[] imgs, // 文章中图片
			@RequestParam("imgsdesc") String[] imgsdesc // 文章中图片的描述
				) throws IllegalStateException, IOException {
		
		article.setArticleType(ArticleType.IMAGE);      // 文章类型为Image
		
		prossesFile(file,article);                      // 将标题图片保存
		List<ImageBean> imgBeans =  new ArrayList<ImageBean>();      // 创建一个空的图片信息集合
		
		for (int i = 0; i < imgs.length; i++) {
			String url = prossesFile(imgs[i]);
			if(!"".equals(url)) {
				ImageBean imageBean = new ImageBean(url, imgsdesc[i]);
				imgBeans.add(imageBean);
			}
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(imgBeans);// 文章的内容
		article.setContent(json);//
		
		User loginUser = (User) request.getSession().getAttribute(UserConst.SESSION_USER_KEY);   // 获取登录的用户信息
		article.setUserId(loginUser.getId());       // 将登录用户信息存入传递类
		
		int res = us.publish(article);              // 进行发布
		
		return res > 0;
	}
	
	
	/**
	 * 	用户删除文章(逻辑删除)
	 * @param id         文章ID
	 * @return
	 */
	@RequestMapping("delArticle")
	@ResponseBody
	public boolean delArticle(Integer id) {
		int res = us.delArticle(id);
		return res > 0;
	}
	
	
	/**
	 * 	用户提交修改文章
	 * @param session
	 * @param article        文章
	 * @param file           上传的文件
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("update")
	@ResponseBody
	public boolean update(HttpSession session, Article article, MultipartFile file) throws IllegalStateException, IOException {
		
		prossesFile(file, article);      // 处理上传文件
		
		User loginUser = (User) session.getAttribute(UserConst.SESSION_USER_KEY);   // 获取登录的用户信息
		article.setUserId(loginUser.getId());       // 将登录用户信息存入传递类
		
		int res = us.updateArt(article);
		
		return res > 0;
	}
	
	
	/**
	 * 	上传保存文件操作方法
	 * @param file
	 * @param article
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private void prossesFile(MultipartFile file, Article article) throws IllegalStateException, IOException {
		
		String filename = file.getOriginalFilename();                  // 获取文件名称
		if ("".equals(filename) || file.isEmpty()) {                   // 对获取的文件名进行非空判断
			article.setPicture("");
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
	
	
	
	/**
	 * 	重载上传保存文件操作方法
	 * @param file
	 * @param article
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private String prossesFile(MultipartFile file) throws IllegalStateException, IOException {
		
		String filename = file.getOriginalFilename();                  // 获取文件名称
		if ("".equals(filename) || file.isEmpty()) {                   // 对获取的文件名进行非空判断
			return "";
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
		return dbPath;                                 // 返回数据库保存文件路径
	}
	
	
	/**
	 * 	跳转到个人设置页面
	 * @return
	 */
	@RequestMapping("optionUser")
	public String optionUser(HttpSession session, Model model) {
		User loginUser = (User) session.getAttribute(UserConst.SESSION_USER_KEY);
		model.addAttribute("user", loginUser);
		return "my/option";
	}
	
	
	@PostMapping("options")
	@ResponseBody
	public ResultMsg option(User user) {
		System.out.println("Option");
		int res = us.option(user);
		
		if (res > 0) {
			return new ResultMsg(1, "处理成功", null);
		} else {
			return new ResultMsg(0, "处理失败", null);
		}
	}
	
	
	@RequestMapping("updateSession")
	public String updateSession(HttpSession session, String name) {
		User user = us.findByName(name);
		session.setAttribute(UserConst.SESSION_USER_KEY, user);
		return "redirect:home";
	}
	
	
}

