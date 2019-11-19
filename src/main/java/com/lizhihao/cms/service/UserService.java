package com.lizhihao.cms.service;

import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.User;

/**
 * @author LZH
 * @Date 2019年10月16日
 * 用户服务接口
 */

public interface UserService {
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	public int register(User user);
	
	
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	public User login(User user);

	/**
	 * 验证用户唯一性
	 * @return
	 */
	public boolean checkExist(String username);
	
	
	/**
	 * 用户发布文章
	 * @param article
	 * @return
	 */
	public int publish(Article article);

	/**
	 * 用户查询文章列表
	 * @param pageNum   页码
	 * @param userId    用户ID
	 * @return
	 */
	public PageInfo<Article> myArticles(Integer pageNum, Integer userId);

	/**
	 * 	用户删除文章(逻辑删除)
	 * @param id    文章ID
	 * @return
	 */
	public int delArticle(Integer id);

	/**
	 * 	用户提交修改文章
	 * @param article
	 * @return
	 */
	public int updateArt(Article article);

	/**
	 * 	个人设置
	 * @param user
	 * @return
	 */
	public int option(User user);
	
	
	public User findByName(String name);
}

