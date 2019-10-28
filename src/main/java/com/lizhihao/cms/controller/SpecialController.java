package com.lizhihao.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.comons.PageUtils;
import com.lizhihao.cms.comons.ResultMsg;
import com.lizhihao.cms.entity.Special;
import com.lizhihao.cms.service.SpecialService;

/**
 * @author LZH
 * @Date 2019年10月25日
 * 	专题文章控制层
 */

@Controller
@RequestMapping("special")
public class SpecialController {

	@Autowired
	SpecialService ss;
	
	
	/**
	 * 	管理专题
	 * @return
	 */
	@RequestMapping("managerSpecial")
	public String managerSpecial(Model model, @RequestParam(defaultValue="1") Integer pageNum) {
		PageInfo<Special> special = ss.getSpecial(pageNum);
		
		String pageStr = PageUtils.pageLoad(special.getPageNum(),special.getPages() , "/special/managerSpecial", 10);
		
		model.addAttribute("specialList", special);
		model.addAttribute("pageUtil", pageStr);
		
		return "admin/special/list";
	}
	
	/**
	 * 	跳转到添加页面
	 * @return
	 */
	@GetMapping("add")
	public String toAdd() {
		return "admin/special/add";
	}
	
	/**
	 * 	添加专题
	 * @param special
	 * @return
	 */
	@PostMapping("add")
	@ResponseBody
	public ResultMsg add(Special special) {
		int res = ss.addSpecial(special);           // 添加
		System.out.println(special.getDigest());
		if (res > 0) {
			return new ResultMsg(1, "处理成功", null);
		} else {
			return new ResultMsg(0, "处理失败", null);
		}
	}
	
	
	/**
	 * 	管理专题文章
	 * @param id
	 * @return
	 */
	@RequestMapping("getDetail")
	public String getDetail(Model model, Integer id) {
		Special special = ss.getSpeById(id);
		model.addAttribute("special", special);
		return "admin/special/detail";
	}
	
	
	/**
	 * 	为专题添加文章
	 * @param specId        专题ID
	 * @param articleId     文章ID
	 * @return
	 */
	@RequestMapping("addArticle")
	@ResponseBody
	public ResultMsg addArticle(Integer specId, Integer articleId) {
		int res = ss.addAtrticle(specId, articleId);
		if (res > 0) {
			return new ResultMsg(1, "处理成功", null);
		} else {
			return new ResultMsg(0, "处理失败", null);
		}
	}
	
	
	@RequestMapping("delArticle")
	@ResponseBody
	public ResultMsg delArticle(Integer specId, Integer articleId) {
		int res = ss.delAtrticle(specId, articleId);
		if (res > 0) {
			return new ResultMsg(1, "处理成功", null);
		} else {
			return new ResultMsg(0, "处理失败", null);
		}
	}
	
}

