package com.lzh.es;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.lizhihao.cms.comons.ESUtils;
import com.lizhihao.cms.entity.Article;


/**
 * @author LZH
 * @Date 2019年11月22日
 * 	ES操作
 */

public class OperaES {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	// 添加数据
	@Test
	public void testAdd() {
		Article p = new Article();
		IndexQuery query = new IndexQuery();
		
		new IndexQueryBuilder().build();
		
		
		// 封装数据
		query.setId(p.getId().toString());
		query.setObject(p);
		
		elasticsearchTemplate.index(query);
		System.out.println("运行完毕");
	}
	
	// 删除数据
	@Test
	public void testDel() {
		elasticsearchTemplate.delete(Article.class, "2");
		System.out.println("Complate");
	}
	
	// 批量添加
	@Test
	public void testAddMore() {
		IndexQuery query = new IndexQuery();
		
		new IndexQueryBuilder().build();
		
		for (int i = 1; i <= 5; i++) {
			Article p = new Article();
			
			query.setId(p.getId().toString());
			query.setObject(p);
			elasticsearchTemplate.index(query);
		}
		System.out.println("Complate!");
	}
	
	// 查询所有
	@Test
	public void select() {
		SearchQuery query = new NativeSearchQueryBuilder().build();
		List<Article> list = elasticsearchTemplate.queryForList(query, Article.class);
		
		for (Article Article : list) {
			System.out.println(Article);
		}
	}
	
	
	// 根据ID获取数据
	@Test
	public void getById() {
		GetQuery query = new GetQuery();
		query.setId("5");
		
		Article Article = elasticsearchTemplate.queryForObject(query , Article.class);
		
		System.out.println(Article);
	}
	
	
	// 分页
	@Test
	public void testPage() {
		// 从第0页开始,一页3条数据,倒序排列
		Pageable pageable = PageRequest.of(0, 3, Sort.by(Direction.DESC, "id"));
		SearchQuery query = new NativeSearchQueryBuilder().withPageable(pageable).build();
		AggregatedPage<Article> page = elasticsearchTemplate.queryForPage(query , Article.class);
		
		List<Article> content = page.getContent();
		
		for (Article Article : content) {
			System.out.println(Article);
		}
		
	}
	
	
	// 模糊查询
	@Test
	public void testMH() {
		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("me", "name");
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
		List<Article> list = elasticsearchTemplate.queryForList(query , Article.class);
		
		for (Article Article : list) {
			System.out.println(Article);
		}
		
	}
	
	
	// 高亮显示
	@Test
	public void testHightLight() {
		
		AggregatedPage<?> page = ESUtils.selectObjects(elasticsearchTemplate, Article.class, 0, 10, "id", new String[] {"name"}, "na");
		
		List<?> list = page.getContent();
		
		for (Object object : list) {
			System.out.println(object);
		}
		
	}

}

