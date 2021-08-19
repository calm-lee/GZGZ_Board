package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
public class PostRestController {
	
	@PostMapping("/create")
	@ResponseBody
	public Map<String, String> postCreate(
			@RequestParam("subject") String subject
			, @RequestParam("content") String content
			, @RequestParam(value = "file", required = false) MultipartFile file// 필수값이 아니란 것을 표시 
			, HttpServletRequest request
			) {
		
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		String userLoginId = (String) session.getAttribute("userLoginId");
			
		// TODO : postBO => insert
		
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");

		return result;
	}
	
}
