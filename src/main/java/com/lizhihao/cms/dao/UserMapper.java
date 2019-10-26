package com.lizhihao.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.Tag;
import com.lizhihao.cms.entity.User;

/**
 * @author LZH
 * @Date 2019年10月16日
 * 用户登录注册
 */

public interface UserMapper {
	
	// 注册
	@Insert("insert into cms_user (username,password,gender,create_time,update_time) values "
			+ "(#{username},#{password},#{gender},now(),now())")
	int register(User user);
	
	// 通过用户名查找
	@Select("select id,username,password,role,locked from cms_user where username = #{value}")
	User findByName(String username);
	
	// 用户发布文章
	int publish(Article article);

	// 用户查询文章列表
	List<Article> myArticles(Integer userId);
	
	// 用户删除文章(逻辑删除)
	@Update("UPDATE cms_article SET deleted = 1 WHERE id = #{value}")
	int delArticle(Integer id);
	
	// 用户修改文章
	int updateArt(Article article);
	
}

