package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.BO.PostBO;

@RestController
@RequestMapping("/post")
public class PostRestController {
	
	@Autowired
	private PostBO postBO;
	
	@PostMapping("/create")
	@ResponseBody
	public Map<String, String> postCreate(
			@RequestParam("subject") String subject
			, @RequestParam("content") String content
			, @RequestParam(value = "file", required = false) MultipartFile file// 필수값이 아니란 것을 표시 
			, HttpServletRequest request
			) {
		
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId"); // 누가 썼는지 구별하기 위해 get
		String userLoginId = (String) session.getAttribute("userLoginId"); // 파일 업로드를 하는 과정에서 이름이 겹치지 않는 폴더를 만들기 위해 get함
			
		// TODO : postBO => insert
		int row = postBO.createPost(userId, userLoginId, subject, content, file);
		
		Map<String, String> result = new HashMap<>();
		
		if(row > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
	}
	
	@PostMapping("/update")
	public Map<String, String> postUpdate(
			@RequestParam("postId") int postId
			, @RequestParam("subject") String subject
			, @RequestParam("content") String content
			, @RequestParam(value="file", required=false) MultipartFile file
			, HttpServletRequest request
			){
	
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId"); // 누가 썼는지 구별하기 위해 get
		String userLoginId = (String) session.getAttribute("userLoginId"); // 파일 업로드를 하는 과정에서 이름이 겹치지 않는 폴더를 만들기 위해 get함
		
		Map<String, String> result = new HashMap<>();
		int row = postBO.updatePost(postId, userId, userLoginId, subject, content, file);
		
		if(row > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}
		
		return result;
	}
}
