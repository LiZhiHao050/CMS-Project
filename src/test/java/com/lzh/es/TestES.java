package com.lzh.es;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.User;
import com.lizhihao.cms.service.ArticleService;
import com.lizhihao.cms.service.UserService;

/**
 * @author LZH
 * @Date 2019年11月21日
 * 
 */

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class TestES {
	
	// 注入用户服务层
	@Autowired
	UserService us;
	
	// 注入文章服务层
	@Autowired
	ArticleService arts;
	
	@Autowired
	ElasticsearchTemplate esTemplate;
	
	// 通过测试类插入10条数据
	@Test
	public void addArticle() {
		for (int j = 1; j <= 10; j++) {
			Article article = new Article();
			article.setTitle("测试文章" + j);
			article.setChannelId(1);
			article.setCategoryId(1);
			article.setContent("aaaaa");
			article.setUserId(44);
			us.publish(article);
			
			IndexQuery query = new IndexQuery();
			query.setId(article.getId().toString());
			query.setObject(article);
			
			esTemplate.index(query);
			
		}
	}
	
	
	// 删除数据
	@Test
	public void delete() {
		for (int i = 127; i < 141; i++) {
			 esTemplate.delete(Article.class, i+"");
			 System.out.println("Delete ID = " + i + " Complate!");
		}
		
	}
	
	// 测试用户
	@Test
	public void testUser() {
		Article detail = arts.getDetail(150);
		
		User user = detail.getUser();
		
		System.out.println(user.getUpdate_time());
	}
	
	
	// 添加所有已审核且未删除的文章到ES
	@Test
	public void addAll() {
		List<Article> alist = arts.getAll();
		
		for (Article article : alist) {
			IndexQuery query = new IndexQuery();
			query.setId(article.getId().toString());
			query.setObject(article);
			esTemplate.index(query);
			System.out.println("Complate:" + article.getId());
		}
		
	}
	
}

