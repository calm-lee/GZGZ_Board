package com.memo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

	//private Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 세션 확인 => 있으면 로그인 상태
		HttpSession session = request.getSession();
		String userLoginId = (String) session.getAttribute("userLoginId");

		// URI - url path 확인
		String uri = request.getRequestURI();

		// 비로그인 && /post => 로그인(sign_in_view) 페이지로 redirect
		if (userLoginId == null && uri.startsWith("/post")) {
			response.sendRedirect("/user/sign_in_view");
			return false; // 결과값이 boolean이기 때문에 반환값을 false로 해줌 (그래야 다음 controller로 넘어가지 않음)
		}

		// 로그인 && /user => post list 페이지로 redirect
		if (userLoginId != null && uri.startsWith("/user")) {
			response.sendRedirect("/post/post_list_view");
			return false;
		}
		return true;
	}

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		
		String uri = request.getRequestURI();
		logger.info("#### postHandle():" + uri);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		
		String uri = request.getRequestURI();
		logger.info("#### afterCompletion():" + uri);
		
	}
}
