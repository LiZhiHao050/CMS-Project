package com.lizhihao.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.dao.ArticleMapper;
import com.lizhihao.cms.dao.SpecialMapper;
import com.lizhihao.cms.entity.Special;
import com.lizhihao.cms.service.SpecialService;

/**
 * @author LZH
 * @Date 2019年10月25日
 * 	专题文章服务实现层
 */

@Service
public class SpecialServiceImpl implements SpecialService {
	
	@Autowired
	SpecialMapper specialMapper;
	
	@Autowired
	ArticleMapper articleMapper;

	
	// 获取专题列表
	public PageInfo<Special> getSpecial(Integer pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<Special> speList = specialMapper.getSpeList();
		
		for (Special special : speList) {                          // 遍历集合
			int num = specialMapper.countArtNum(special.getId());  // 获取每个专题文章数
			special.setArticleNum(num);                            // 存入集合
		}
		
		return new PageInfo<>(speList);
	}
	
	
	// 添加专题
	@Override
	public int addSpecial(Special special) {
		return specialMapper.addSpecial(special);
	}
	
	
	// 管理/获取专题
	@Override
	public Special getSpeById(Integer id) {
		Special speById = specialMapper.getSpeById(id);
		speById.setArticleList(articleMapper.getArtBySpeId(id));
		return speById;
	}
	
	
	// 添加专题
	@Override
	public int addAtrticle(Integer specId, Integer articleId) {
		return specialMapper.addSpeArt(specId, articleId);
	}

	
	// 删除专题
	@Override
	public int delAtrticle(Integer specId, Integer articleId) {
		return specialMapper.delSpeArt(specId, articleId);
	}
	

}

