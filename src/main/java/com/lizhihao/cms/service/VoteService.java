package com.lizhihao.cms.service;

import java.util.List;

import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.Votes;

/**
 * @author LZH
 * @Date 2019年10月29日
 * 
 */

public interface VoteService {

	/**
	 * 	发布投票文章
	 * @param article
	 * @return
	 */
	int pushVote(Article article);
	
	/**
	 * 	查询投票列表
	 * @return
	 */
	List<Article> list();
	
	
	List<Votes> getVoteStatics(Integer aId);

}

