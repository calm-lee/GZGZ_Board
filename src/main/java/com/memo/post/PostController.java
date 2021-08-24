package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.BO.PostBO;
import com.memo.post.Post.Post;

@RequestMapping("/post")
@Controller
public class PostController {
	
	@Autowired
	private PostBO postBO;

	
	
	@RequestMapping("/post_list_view")
	public String postListView(
			@RequestParam(value = "prevId", required = false) Integer prevIdParam,
			@RequestParam(value = "nexId", required = false) Integer nextIdParam,
			HttpServletRequest request, 
			Model model) {
		
		HttpSession session = request.getSession(); //userId 등의 정보를 꺼낼 수 있는 객체
		Integer userId =  (Integer) session.getAttribute("userId");
		
		if(userId == null) {
			// 세션 정보에 로그인 아이디가 없으면 로그인 화면으로 이동
			return "redirect:/user/sign_in_view";
		}
		
		// 게시글 번호  10 9 8 | 7 6 5 | 4 3 2 | 1
		// 1) 다음 : 가장 작은 수(오른 쪽 값) => nextIdParam 쿼리: nextIdParam보다 작은 3개(Limit)을 가져온다.
		// 2) 이전 : 가장 큰 수 (왼쪽 값) => prevIdParam 쿼리: prevIdParam보다 큰 3개(Limit)을 가져온다. 순서가 뒤집히므로 코드에서 정렬을 뒤집는다.
			
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
		
		int prevId = 0;
		int nextId = 0;
		
		if(postList.isEmpty() == false) {
			prevId = postList.get(0).getId(); //postList의 0번째 리스트의 아이디
			nextId = postList.get(postList.size() - 1).getId(); //postList의 마지막 리스트의 아이디
		}
		// 마지막 페이지 => nextId를 0으로 세팅한다.
		if(postBO.isLastPage(userId, nextId)) {
			nextId = 0;
		}
		
		// 첫 페이지 => prevId를 0으로 세팅한다.
		if(postBO.isFirstPage(userId, prevId)) {
			prevId = 0;
		}
		
		
		model.addAttribute("postList", postList);
		model.addAttribute("viewName", "post/post_list");
		model.addAttribute("prevId", prevId); // 리스트 중 가장 앞 쪽(제일 큰) id
		model.addAttribute("nextId", nextId); // 리스트 중 가장 뒷 쪽(제일 작은) id
		
		return "template/layout";
	}
	
	@RequestMapping("/post_create_view")
	public String postCreateView(Model model) {
		model.addAttribute("viewName", "post/post_create");
		return "template/layout";
	}
	
	@RequestMapping("/post_detail_view")
	public String postDetailView(Model model, @RequestParam("postId") int postId, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
	
		if(userId == null) {
			// 세션에 로그인 아이디가 없다면 로그인이 안된 것이므로 로그인 페이지로 리다이렉트
			return "redirect: /user/sign_in_view";
		}
		
		Post post = postBO.getPostByPostIdAndUserId(postId, userId);
		model.addAttribute("post", post);
		model.addAttribute("viewName", "post/post_detail");
		return "template/layout";
	}
}