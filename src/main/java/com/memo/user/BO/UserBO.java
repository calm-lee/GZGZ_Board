package com.memo.user.BO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.user.DAO.UserDAO;
import com.memo.user.User.User;

@Service
public class UserBO {
	
	@Autowired
	private UserDAO userDAO;
	
	public User getUser(String loginId) {
		return userDAO.selectUserByLoginId(loginId);
	}
	
	public User getUesrByLoginIdAndPassword(String loginId, String password) {
		return userDAO.selectByLoginIdAndPassword(loginId, password);
	}
	
	public void addUser(String loginId, String password, String name, String email) {
		userDAO.insertUser(loginId, password, name, email);
	}
}
