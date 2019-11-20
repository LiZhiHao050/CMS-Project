package com.lzh.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lizhihao.cms.entity.User;
import com.lizhihao.cms.service.UserService;

/**
 * @author LZH
 * @Date 2019年10月30日
 * 	用户测试类
 */

public class UserTest extends BaseTest {
	
	@Autowired
	UserService us;
	
	// 测试用户个人设置
	@Test
	public void testOption() {
		User user = new User();
		user.setId(45);
		user.setNickname("google");
		user.setBirthday(new Date(9, 3, 1));
		user.setGender(1);
		user.setMobile("13333300999");
		user.setMail("zs1@qq.com");
		user.setAddress("清华路33号");
		user.setConstellation("天蝎座");
		user.setMotto("我相信,我能行");
		int res = us.option(user);
		
		System.out.println("测试结果:" + res);
	}
	
	

}

