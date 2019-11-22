package com.lzh.kafka;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.Category;
import com.lizhihao.cms.entity.Channel;
import com.lizhihao.cms.service.CategoryService;
import com.lizhihao.cms.service.ChannelService;
import com.lizhihao.utils.FileUtils;
import com.lizhihao.utils.RandomUtils;

/**
 * @author LZH
 * @Date 2019年11月20日
 * 
 */

//@RunWith(SpringRunner.class)
//@ContextConfiguration("classpath:spring-beans.xml")
public class KafkaProducerTest {
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private CategoryService catService;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	
	@Test
	public void readFileAddArticle() throws IOException {
		int num = 0;
//		(1)使用工具包中流工具方法读取文件，不得乱码
		List<String> fileList = FileUtils.getFileList("Z:\\1706EJsoup");
		
		for (String str : fileList) {
			if (num > 10) {
				break;
			}
			// 获取文件内容
			String content = FileUtils.readFileByLine(str);
			
			Article article = new Article();
//			(2)将文件名作为Article对象的title属性值
			article.setTitle(str.substring(str.lastIndexOf("\\") + 1, str.lastIndexOf(".")));
			
//			(3)文本内容作为Article对象的content属性值
			article.setContent(content);
			
//			(4)在文本内容中截取前140个字作为摘要(无此字段)
//			(5)“点击量”和“是否热门”、“频道”字段要使用随机值
			article.setHits(RandomUtils.random(0, 1000000));
			article.setHot(RandomUtils.random(0, 1));
			
			// 获取所有栏目
			List<Channel> allChannel = channelService.getAllChannel();
			// 生成栏目随机下标
			int chnIndex = RandomUtils.random(0, allChannel.size() - 1);
			Channel channel = allChannel.get(chnIndex);
			// 设置栏目
			article.setChannelId(channel.getId());
			
			// 根据栏目获取频道
			List<Category> catList = catService.getListByChnlId(channel.getId());
			
			if (catList != null && catList.size() > 0) {
				// 获取频道随机下标
				int random = RandomUtils.random(0, catList.size() - 1);
				
				// 设置频道ID值
				Category category = catList.get(random);
				article.setCategoryId(category.getId());
			}
			
//			(6)文章发布日期从2019年1月1日模拟到今天
			Date randomDate = RandomUtils.randomDate("2019-01-01", "2019-11-21");
			article.setCreated(randomDate);
			
//			(7)其它的字段随便模拟
			article.setUserId(44);
			
//			(8)编写Kafka生产者，然后将生成Article对象通过Kafka发送到消费端
			Gson gson = new Gson();
			String json = gson.toJson(article);
			
			kafkaTemplate.sendDefault("article", json);
			num++;
		}
		System.out.println("Read Complate");
	}
	
}

