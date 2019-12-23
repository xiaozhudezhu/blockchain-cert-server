package com.swinginwind.core.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.swinginwind.core.session.SessionService;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	SessionService sessionService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//浏览器访问时会事先调用OPTIONS方法，所以要过滤掉
		if(request.getMethod().equalsIgnoreCase("OPTIONS"))
			return true;
		String loginToken = request.getHeader("Login-Token");
		if(loginToken == null || sessionService.getSession(loginToken) == null) {
			System.out.println("用户未登录");
			response.setStatus(401);
			return false;
		}
		else {
			Object session = sessionService.getSession(loginToken);
			if(session == null) {
				System.out.println("loginToken: " + loginToken + "已失效");
				response.setStatus(401);
				return false;
			}
			sessionService.getSessionThreadLocal().set(session);
			sessionService.getSessionIdThreadLocal().set(loginToken);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
