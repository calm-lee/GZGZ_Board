package com.memo.post.BO;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.DAO.PostDAO;
import com.memo.post.Post.Post;

@Service
public class PostBO {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PostDAO postDAO;

	@Autowired
	private FileManagerService fileManagerService;

	public List<Post> getPostListByUserId(int userId) {
		return postDAO.selectPostListByUserId(userId);
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
}
