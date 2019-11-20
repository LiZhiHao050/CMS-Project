package com.lzh.jsoup;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * @author LZH
 * @Date 2019年11月13日
 * 
 */

public class JSoupTest {
	
	
	@Test
	public void testBQG() throws IOException {
		// 记录文章数
		int count = 0;
		// 获取连接对象
		Connection connect = Jsoup.connect("http://www.xbiquge.la/0/419/");
		// 获取文档对象
		Document document = connect.get();
		// 获取当前文档的所有超链接
		Elements ahrefs = document.select("a[href]");
		// 遍历元素对象
		for (Element href : ahrefs) {
			// 超链接的url地址
			String url = href.attr("href");
//			url += "https://www.biqudu.net/";
			// 定义表达式 https://news.163.com ***** html
			//https://finance.sina.com.cn/stock/observe/2019-10-29/doc-iicezzrr5838095.shtml
//			String regex = "https://news\\\\.163\\\\.com.*html$";
			
			//以https://news.163.com开头，以html结尾
			//https://news\\.163\\.com.*html$
			// 特殊要求  
			if (url != null && url.startsWith("/") && url.endsWith("html")) {
//			if (url != null && Pattern.matches(regex, url)) {
				// 连接的文本内容
				String title = href.text();
				System.out.println(url + "@@@@@@@@@" + title);
				count++;
				
				// 获取文章的文档对象(通过网址)
				Document articleDoc = Jsoup.connect("http://www.xbiquge.la" + url).get();
				// 获取文章的内容元素对象
				Element articleContentElement = articleDoc.getElementById("content");
				if (articleContentElement != null) {
					// 获取纯文本内容
					String content = articleContentElement.text();
					
					//去除标题中的特殊符号
					title = title.replace("?", "").replace("\"", "").replace(":", "").replace("/", "").replace("\\", "");
					//写入到文件中
					FileUtil.writeFile("Z:\\1706EJsoup\\" + title + ".txt", content, "utf8");
					System.out.println("写入:" + title);
				}
			}
		}
		System.out.println("首页中找到了符合条件的网址有：" + count + "篇文章");
	}
	
	

}

