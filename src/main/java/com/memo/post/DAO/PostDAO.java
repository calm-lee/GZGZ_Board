package com.memo.post.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.post.Post.Post;

@Repository
public interface PostDAO {
	
	public List<Post> selectPostListByUserId(int userId);
	
	//DB에서 postId, userId 일치하는 POST 가져옴
	public Post selectPostByPostIdAndUserId(
			@Param("postId") int postId
			, @Param("userId") int userId);
	
	public int insertPost(
			@Param("userId") int userId, 
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imgPath") String imgPath);
	
	public int updatePost(
			@Param("userId") int userId, 
			@Param("userLoginId") int userLoginId, 
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imgPath") String imgPath);
}
