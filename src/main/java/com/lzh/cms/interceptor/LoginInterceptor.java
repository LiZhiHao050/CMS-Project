package com.lzh.cms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.lzh.cms.comons.UserConst;
import com.lzh.cms.entity.User;

/**
 * @author LZH
 * @Date 2019年10月16日
 * 
 */

// 定义拦截器
public class LoginInterceptor implements HandlerInterceptor {
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		User login = (User) session.getAttribute(UserConst.SESSION_USER_KEY);
		
		if (login != null) {
			return true;
		}
		
		System.out.println("拦截:" + request.getRequestURI());
		request.getRequestDispatcher("/user/login").forward(request, response);
		return false;
	}
	
}

