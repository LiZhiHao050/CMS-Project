package com.lizhihao.cms.service;

import java.util.List;

import com.lizhihao.cms.entity.Category;

/**
 * @author LZH
 * @Date 2019年10月17日
 *  分类类服务层
 */

public interface CategoryService {

	// 根据频道ID获取分类
	List<Category> getListByChnlId(Integer chnId);

}

