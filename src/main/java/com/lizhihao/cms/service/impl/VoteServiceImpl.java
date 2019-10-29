package com.lizhihao.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lizhihao.cms.dao.VoteMapper;
import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.Votes;
import com.lizhihao.cms.service.VoteService;

/**
 * @author LZH
 * @Date 2019年10月29日
 * 	投票服务实现类
 */

@Service
public class VoteServiceImpl implements VoteService {
	
	@Autowired
	VoteMapper vm;

	// 发布投票文章
	@Override
	public int pushVote(Article article) {
		int res = vm.pushVote(article);
		return res;
	}

	// 查询投票列表
	@Override
	public List<Article> list() {
		List<Article> list = vm.list();
		return list;
	}

	@Override
	public List<Votes> getVoteStatics(Integer aId) {
		List<Votes> list = vm.getVoteStatics(aId);
		return list;
	}
	

}

