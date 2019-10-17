package com.lzh.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzh.cms.dao.UserMapper;
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
	

}

