package com.lizhihao.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizhihao.cms.dao.ArticleMapper;
import com.lizhihao.cms.dao.UserMapper;
import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.Tag;
import com.lizhihao.cms.entity.User;
import com.lizhihao.cms.service.UserService;
import com.lizhihao.utils.MD5Utils;

/**
 * @author LZH
 * @Date 2019年10月16日
 * 用户服务类
 */

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserMapper um;

	@Autowired
	ArticleMapper am;
	
	// 注入Redis模板对象
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	// 注册
	@Override
	public int register(User user) {
		user.setPassword(MD5Utils.md5(user.getPassword()));      // 加密密码
		return um.register(user);
	}
	
	// 验证用户唯一性
	@Override
	public boolean checkExist(String username) {
		return null != um.findByName(username);
	}
	
	// 登录
	@Override
	public User login(User user) {
		String pwd = MD5Utils.md5(user.getPassword());      // 使用MD5解析密码
		User login = um.findByName(user.getUsername());     // 通过用户名获取用户
		
		if (login != null && pwd.equals(login.getPassword())) {    // 判断用户名与密码是否为一个用户
			
			SetOperations<String, User> set = redisTemplate.opsForSet();    // 获取Redis Set操作对象
			set.add(login.getId().toString(), login);                       // 将登录用户存入Set
			
			return login;
		}
		return null;
	}
	
	/**
	 * 处理文章标签
	 * @param article      要处理的文章
	 */
	private void prossesTags(Article article) {
		String tag = "";
		if (article.getTags() == null) {                   // 判空
			return;                                        // 为空则返回
		}
		
		String[] split = article.getTags().split(",");     // 根据逗号分割
		
		for (String tags : split) {
			tag = tags.trim();                             // 去除空格
			Tag resTag = am.getTag(tag);                   // 获取标签对象
			if (resTag == null) {                          // 判断是否为空
				resTag = new Tag(tag);                     // 为空则生成新的标签
				am.addTag(resTag);                         // 创建新标签
			}
			
			try {
				am.addArtsTag(article.getId(), resTag.getId());
			} catch (Exception e) {
				System.out.println("主键重复异常(插入失败)");
			}
			
		}
		
	}
	
	
	// 发布文章
	@Override
	public int publish(Article article) {
		int res = um.publish(article);
		
		prossesTags(article);               // 调用处理标签方法
		
		return res;
	}

	
	// 查询文章列表
	@Override
	public PageInfo<Article> myArticles(Integer pageNum, Integer userId) {
		PageHelper.startPage(pageNum, 8);
		List<Article> artList = um.myArticles(userId);
		return new PageInfo<>(artList);
	}

	// 逻辑删除文章
	@Override
	public int delArticle(Integer id) {
		int res = um.delArticle(id);
		am.delArtTags(id);                         // 删除中间表的数据 
		return res;
	}
	
	// 用户修改文章
	@Override
	public int updateArt(Article article) {
		int res = um.updateArt(article);
		am.delArtTags(article.getId());            // 删除中间表中的数据
		prossesTags(article);
		return res;
	}

	// 个人设置
	@Override
	public int option(User user) {
		int res = um.option(user);
		return res;
	}
	
	
	@Override
	public User findByName(String name) {
		User user = um.findByName(name);
		return user;
	}

}

