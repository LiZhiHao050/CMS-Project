package com.lzh.kafka;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author LZH
 * @Date 2019年11月21日
 * 	开启消费者
 */

public class KafkaConsumerTest {

	public static void main(String[] args) {
		
		new ClassPathXmlApplicationContext("spring-beans.xml");
		
	}
	
}

