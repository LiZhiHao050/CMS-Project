package com.lizhihao.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lizhihao.cms.dao.CategoryMapper;
import com.lizhihao.cms.entity.Category;
import com.lizhihao.cms.service.CategoryService;

/**
 * @author LZH
 * @Date 2019年10月17日
 * 	分类服务实现层
 */

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryMapper cm;
	
	// 获取分类
	@Override
	public List<Category> getListByChnlId(Integer chnId) {
		List<Category> categories = cm.getListByChnlId(chnId);
		return categories;
	}

}

