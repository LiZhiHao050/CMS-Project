package com.lizhihao.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.dao.AdminMapper;
import com.lizhihao.cms.entity.User;
import com.lizhihao.cms.service.AdminService;

/**
 * @author LZH
 * @Date 2019年10月22日
 * 	管理员服务实现层
 */

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	AdminMapper am;
	
	// 获取用户列表
	@Override
	public PageInfo<User> userList(Integer pageNum, String name) {
		PageHelper.startPage(pageNum, 10);
		List<User> userList = am.userList(name);
		return new PageInfo<>(userList);
	}

	// 修改用户的状态
	@Override
	public int modifyUserStatus(Integer id, Integer locked) {
		int res = am.modifyUserStatus(id, locked);
		return res;
	}
	
	
	
	

}

