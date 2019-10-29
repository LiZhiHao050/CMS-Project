package com.lizhihao.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.Votes;

/**
 * @author LZH
 * @Date 2019年10月29日
 * 	投票Mapper层
 */

public interface VoteMapper {

	// 发布投票文章
	@Insert("INSERT INTO cms_article (title,content,user_id,created,updated,articleType) VALUES (#{title},#{content},#{userId},now(),now(),#{articleType, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler,\r\n" + 
			"jdbcType=INTEGER, javaType=com.lizhihao.cms.comons.ArticleType})")
	int pushVote(Article article);
	
	// 查询投票列表前十
	@Select("select id,title from cms_article where articleType = 2 order by id desc limit 10")
	List<Article> showVote();
	
	// 查询投票列表所有
	@Select("select id,title from cms_article where articleType = 2")
	List<Article> list();
	
	@Select("select  count(1) as voteNum , `option` as optionKey "
            + " FROM cms_vote  "
            + " where article_id = #{value} GROUP BY `option`")
	List<Votes> getVoteStatics(Integer aId);
	

}

