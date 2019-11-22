package com.lizhihao.cms.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
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
	
	// 注入Redis操作模板
	@Autowired
	private RedisTemplate<String, Category> redisTemplate;
	
	
	// 获取分类
	@Override
	public List<Category> getListByChnlId(Integer chnId) {
		List<Category> categories = null;
		
		ListOperations<String, Category> opsForList = redisTemplate.opsForList();
		
		List<Category> range = opsForList.range("Category" + chnId, 0, -1);
		if (range != null && range.size() != 0) {     // 对Redis中的数据进行非空判断
			System.out.println("From Redis");
			categories = range;                       // 不为空则从Redis中获取
		} else {
			System.out.println("From MySQL");                  // 否则从Mysql中获取
			categories = cm.getListByChnlId(chnId);
			for (Category category : categories) {    // 并将数据存入Redis
				opsForList.leftPush("Category" + chnId, category);
			}
		}
		
		return categories;
	}

}

