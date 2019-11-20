package com.lzh.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lizhihao.cms.entity.Special;
import com.lizhihao.cms.service.SpecialService;

/**
 * @author LZH
 * @Date 2019年10月30日
 * 	专题测试类
 */

public class SpecialTest extends BaseTest {
	
	@Autowired
	SpecialService specialService;

	// 测试添加专题
	@Test
	public void testAdd() {
		Special spe = new Special(null, "中美贸易战", "也叫中美贸易争端,是中美经济关系的主要问题", null, null);
		int res = specialService.addSpecial(spe);
		System.out.println("添加:" + res);
	}
	
	// 测试添加专题文章
	@Test
	public void testAddArt() {
		int res = specialService.addAtrticle(3, 81);
		System.out.println("添加专题文章:" + res);
	}
	
	// 测试移除专题文章
	@Test
	public void testDelArt() {
		int res = specialService.delAtrticle(2, 77);
		System.out.println("移除专题文章:" + res);
	}
	
	// 测试修改专题
	@Test
	public void testModify() {
		Special spe = new Special(2, "大事件", "包揽全球大事", null, null);
		int res = specialService.modify(spe);
		System.out.println("修改:" + res);
	}
	
	
}

