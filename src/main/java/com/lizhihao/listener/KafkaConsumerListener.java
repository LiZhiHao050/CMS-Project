package com.lizhihao.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.lizhihao.cms.entity.Article;

/**
 * @author LZH
 * @Date 2019年11月20日
 * 	消费者
 */

@Component
public class KafkaConsumerListener implements MessageListener<String, String> {

	@Override
	public void onMessage(ConsumerRecord<String, String> record) {
		String key = record.key();
		if (key != null && "article".equals("key")) {
			String json = record.value();
			
			// JSON转换为对象
			Gson gson = new Gson();
			Article article = gson.fromJson(json, Article.class);
			
			
			System.out.println("Consumer Complate!");
		}
		
	}

}

