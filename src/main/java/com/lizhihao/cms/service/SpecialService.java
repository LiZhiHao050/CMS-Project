package com.lizhihao.cms.service;

import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.entity.Special;

/**
 * @author LZH
 * @Date 2019年10月25日
 * 	专题文章服务层
 */

public interface SpecialService {
	
	/**
	 * 	专题查询
	 * @param pageNum
	 * @return
	 */
	public PageInfo<Special> getSpecial(Integer pageNum);
	
	
	/**
	 * 	添加专题
	 * @param special
	 * @return
	 */
	public int addSpecial(Special special);

	/**
	 * 	管理专题(获取)
	 * @param id
	 * @return
	 */
	public Special getSpeById(Integer id);

	/**
	 * 	添加专题
	 * @param specId
	 * @param articleId
	 * @return
	 */
	public int addAtrticle(Integer specId, Integer articleId);

	/**
	 * 	删除专题
	 * @param specId         专题ID
	 * @param articleId      文章ID
	 * @return
	 */
	public int delAtrticle(Integer specId, Integer articleId);
	

}

