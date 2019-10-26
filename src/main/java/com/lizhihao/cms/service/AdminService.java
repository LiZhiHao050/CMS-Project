package com.lizhihao.cms.service;

import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.entity.User;

/**
 * @author LZH
 * @Date 2019年10月22日
 * 	管理员服务层
 */

public interface AdminService {
	
	/**
	 * 	获取所有用户的列表
	 * @param pageNum        分页页码
	 * @param name           用户名模糊查询
	 * @return
	 */
	PageInfo<User> userList(Integer pageNum, String name);
	
	
	/**
	 * 	修改用户状态
	 * @param id
	 * @param locked         要修改的状态
	 * @return
	 */
	int modifyUserStatus(Integer id, Integer locked);
	

}

