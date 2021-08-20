package com.memo.post.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.post.Post.Post;

@Repository
public interface PostDAO {
	
	public List<Post> selectPostListByUserId(int userId);
	
	public int insertPost(
			@Param("userId") int userId, 
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imgPath") String imgPath);
}
