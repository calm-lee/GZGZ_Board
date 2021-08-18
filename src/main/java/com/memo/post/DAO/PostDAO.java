package com.memo.post.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.memo.post.Post.Post;

@Repository
public interface PostDAO {
	
	public List<Post> selectPostListByUserId(int userId);
	
}
