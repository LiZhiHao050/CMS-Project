package com.lzh.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzh.cms.dao.CategoryMapper;
import com.lzh.cms.entity.Category;
import com.lzh.cms.service.CategoryService;

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

