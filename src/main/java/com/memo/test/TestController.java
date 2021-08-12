package com.memo.test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memo.test.BO.TestBO;

@Controller
public class TestController {
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "TEST!";
	}
	


	@Autowired
	private TestBO testBO;

	@RequestMapping("/test_db")
	@ResponseBody
	public Map<String,Object> testDb(){
		Map<String, Object> result = testBO.getUser();
		return result;  //jackson 라이브러리 때문에 json으로 내려감
	}
	
	@RequestMapping("/test_jsp")
	public String testJSP() {
		return "test/test";
	}
	
}
