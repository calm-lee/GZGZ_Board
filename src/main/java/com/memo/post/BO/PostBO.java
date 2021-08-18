package com.memo.post.BO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.post.DAO.PostDAO;
import com.memo.post.Post.Post;

@Service
public class PostBO {

	@Autowired
	private PostDAO postDAO;
	
	public List<Post> getPostListByUserId(int userId){
		return postDAO.selectPostListByUserId(userId);
	}
		
}
