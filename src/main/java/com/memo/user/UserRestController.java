package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.BO.UserBO;
import com.memo.user.User.User;

@RequestMapping("/user")
@RestController // @Controller + @ResponseBody
public class UserRestController {

	/**
	 * 회원가입하는 화면 데이터만 처리하는 API용 Controller
	 * 
	 * @author calm_lee
	 * 
	 */

	@Autowired
	private UserBO userBO;

	@RequestMapping("/is_duplicated")
	public Map<String, Boolean> isDuplicatedId(@RequestParam("loginId") String loginId) {

		Map<String, Boolean> result = new HashMap<>();
		User user = userBO.getUser(loginId);

		if (user == null) {
			result.put("result", false);
		} else {
			result.put("result", true);
		}

		return result;
	}

	@PostMapping("/sign_up_for_ajax")
	public Map<String, String> signUpForAjax(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, @RequestParam("name") String name
			, @RequestParam("email") String email)
			{
		
		// 암호화
		String encryptedPassword = EncryptUtils.md5(password);
				
		// insert DB
		userBO.addUser(loginId, encryptedPassword, name, email);
		
		// 결과값 리턴
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		
		return result;
	}
}