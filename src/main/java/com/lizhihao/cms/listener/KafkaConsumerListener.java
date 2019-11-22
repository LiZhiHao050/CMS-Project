package com.lizhihao.cms.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.service.ArticleService;
import com.lizhihao.cms.service.UserService;

/**
 * @author LZH
 * @Date 2019年11月20日
 * 	消费者
 */

@Component
public class KafkaConsumerListener implements MessageListener<String, String> {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private ElasticsearchTemplate esTemplate;
	
	
	@Override
	public void onMessage(ConsumerRecord<String, String> record) {
		String key = record.key();
		
		if (key != null && "article".equals(key)) {
			System.out.println("Comsumer Ready");
			String json = record.value();
			
			// JSON转换为对象
			Gson gson = new Gson();
			Article article = gson.fromJson(json, Article.class);
			
			// 将文章存入数据库
			us.publishFromKafka(article);        
			
			// 将文章存入ES
			IndexQuery query = new IndexQuery();
			query.setId(article.getId().toString());
			query.setObject(article);
			esTemplate.index(query);
			
			System.out.println("Consumer:" + article.getTitle() + " Complate!");
		}
		
	}

}

