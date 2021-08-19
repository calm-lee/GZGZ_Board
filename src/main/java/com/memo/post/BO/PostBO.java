package com.memo.post.BO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.DAO.PostDAO;
import com.memo.post.Post.Post;

@Service
public class PostBO {

	@Autowired
	private PostDAO postDAO;
	
	public List<Post> getPostListByUserId(int userId){
		return postDAO.selectPostListByUserId(userId);
	}
	
	// 몇 행 넣을 건지 return할 거라 int
	public int createPost(int userId, String subject, String content, MultipartFile file) {
		
		// file을 가지고 image URL로 구성하고 DB에 넣는다.
		String imgUrl = FileManagerService.
		
		return postDAO.insertPost(userId, subject, content, imgUrl);
		
	}
}
