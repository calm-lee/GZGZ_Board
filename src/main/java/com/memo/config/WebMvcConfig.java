package com.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Autowired
	private PermissionInterceptor permissionInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(permissionInterceptor)
		.addPathPatterns("/**") //어떤 url일 때 어떤 interceptor를 타게 할 것인지, 지금 /**는 어떤 url이던 타게 하겠다는 것
		.excludePathPatterns("/user/sign_out", "/static/**", "/error"); //여기에 해당하는 url은 interceptor을 타지 않는다.
		;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) { //내 서버에 있는 이미지파일을 가져와서 mapping을 할 수 있도록 해줌
		registry.addResourceHandler("/images/**")
		        .addResourceLocations("file:///D:\\이의연\\Spring Project\\ex\\memo_workspace\\Memo\\images/"); // 실제 파일 저장 위치
	
	}	
}
