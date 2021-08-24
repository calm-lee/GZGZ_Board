package com.memo.post.BO;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.DAO.PostDAO;
import com.memo.post.Post.Post;

@Service
public class PostBO {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final int POST_MAX_SIZE = 3; // 상수값
	
	@Autowired
	private PostDAO postDAO;

	@Autowired
	private FileManagerService fileManagerService;

	public List<Post> getPostListByUserId(int userId, Integer prevId, Integer nextId) {
		
		// 게시글 번호  10 9 8 | 7 6 5 | 4 3 2 | 1
		// 1) 다음 : 가장 작은 수(오른 쪽 값) => nextIdParam 쿼리: nextIdParam보다 작은 3개(Limit)을 가져온다.
		// 2) 이전 : 가장 큰 수 (왼쪽 값) => prevIdParam 쿼리: prevIdParam보다 큰 3개(Limit)을 가져온다. 순서가 뒤집히므로 코드에서 정렬을 뒤집는다.
		
		String direction = null;
		Integer standardId = null;
		
		if(prevId != null) {// 이전 버튼 클릭
			direction = "prev";
			standardId = prevId;
			// TODO: 정렬을 뒤집어야 한다
			
			List<Post> postList = postDAO.selectPostListByUserId(userId, direction, standardId, userId);
			Collections.reverse(postList);
			return postList;
			
		} else if(nextId != null) {
			// 다음 버튼 클릭
			direction = "next";
			standardId = nextId;
		}
		return postDAO.selectPostListByUserId(userId, direction, standardId, POST_MAX_SIZE);
	}

	// 가장 오른쪽 페이지인가?
	public boolean isLastPage(int userId, int nextId) {
		// 게시글 번호  10 9 8 | 7 6 5 | 4 3 2 | 1
		// 1
		return nextId == postDAO.selectPostIdByUserIdAndSort(userId, "ASC");
	
	}
	
	// 가장 왼쪽 페이지
	public boolean isFirstPage(int userId, int prevId) {
		// 10
		return prevId == postDAO.selectPostIdByUserIdAndSort(userId, "DESC");
	}
	
	
	// 내가 쓴 포스트만 가져오기 위해서 postId=userId
	public Post getPostByPostIdAndUserId(int postId, int userId) {
		return postDAO.selectPostByPostIdAndUserId(postId, userId);
	}
	
	// 몇 행 넣을 건지 return할 거라 int
	public int createPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {

		// file을 가지고 image URL로 구성하고 DB에 넣는다.
		String imgPath = null;

		if (file != null) {
			try {
				// 컴퓨터(서버)에 파일 업로드 후 웹으로 접근할 수 있는 img URL을 얻어낸다.
				imgPath = fileManagerService.saveFile(userLoginId, file);
			} catch (IOException e) {
				log.error("[파일업로드]" + e.getMessage());
			}
		}
		log.info("#### 이미지 주소: " + imgPath);
		return postDAO.insertPost(userId, subject, content, imgPath);
	}
	
	public int updatePost(int postId, int userId, String userLoginId, String subject, String content, MultipartFile file) {
		
		String imgPath = null;
		
		if (file != null) {
			try {
				// 컴퓨터(서버)에 파일 업로드 후 웹으로 접근할 수 있는 img URL을 얻어낸다.
				imgPath = fileManagerService.saveFile(userLoginId, file);
			} catch (IOException e) {
				log.error("[파일업로드]" + e.getMessage());
			}
		}
		
		if(imgPath != null) {
			Post post = postDAO.selectPostByPostIdAndUserId(postId, userId);
			String oldImgUrl = post.getImgPath();
			try {
				fileManagerService.deleteFile(oldImgUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("[파일삭제] 삭제 중 에러 : " + postId + " " + oldImgUrl);
			}
		}
		
		return postDAO.updatePost(postId, userId, subject, content, imgPath);
	}
}
