package com.lzh.cms.service;

import com.lzh.cms.entity.User;

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

}

