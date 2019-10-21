package com.lzh.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzh.cms.dao.UserMapper;
import com.lzh.cms.entity.Article;
import com.lzh.cms.entity.User;
import com.lzh.cms.service.UserService;
import com.lzh.utils.MD5Utils;

/**
 * @author LZH
 * @Date 2019年10月16日
 * 用户服务类
 */

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserMapper um;

	// 注册
	@Override
	public int register(User user) {
		user.setPassword(MD5Utils.md5(user.getPassword()));      // 加密密码
		return um.register(user);
	}
	
	// 验证用户唯一性
	@Override
	public boolean checkExist(String username) {
		return null != um.findByName(username);
	}
	
	// 登录
	@Override
	public User login(User user) {
		String pwd = MD5Utils.md5(user.getPassword());      // 解析密码
		User login = um.findByName(user.getUsername());     // 通过用户名获取用户
		
		if (login != null && pwd.equals(login.getPassword())) {    // 判断用户名与密码是否为一个用户
			return login;
		}
		return null;
	}
	
	
	// 发布文章
	@Override
	public int publish(Article article) {
		return um.publish(article);
	}

	
	// 查询文章列表
	@Override
	public PageInfo<Article> myArticles(Integer pageNum, Integer userId) {
		PageHelper.startPage(pageNum, 10);
		List<Article> artList = um.myArticles(userId);
		return new PageInfo<>(artList);
	}

	// 逻辑删除文章
	@Override
	public int delArticle(Integer id) {
		int res = um.delArticle(id);
		return res;
	}
	
	// 用户修改文章
	@Override
	public int updateArt(Article article) {
		int res = um.updateArt(article);
		return res;
	}

}

