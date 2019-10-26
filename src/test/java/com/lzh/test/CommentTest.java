package com.lzh.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lizhihao.cms.dao.CommentMapper;
import com.lizhihao.cms.entity.Comment;

/**
 * @author LZH
 * @Date 2019年10月23日
 * 
 */

public class CommentTest extends BaseTest {
	
	@Autowired
	CommentMapper cm;

	@Test
	public void testCom() {
		List<Comment> comList = cm.getComList(27);
		
		comList.forEach(res->{
			System.out.println(res);
		});
		
	}
	
}

