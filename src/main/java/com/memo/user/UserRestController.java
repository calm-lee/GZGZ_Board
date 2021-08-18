package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	// 로그인
	@RequestMapping("/sign_in")
	public Map<String, String> signIn(
			@RequestParam("loginId") String loginId 
			,@RequestParam("password") String password
			,HttpServletRequest request
			){
		
		
		// password를 md5로 해싱(암호화)한다.
		// loginId, password로 user가 가져와서 있으면 로그인 성공
		// 성공 : session에 저장(로그인 상태를 유지, 로그아웃 하기 전까지는 모든 request-response에 로그인 상태)
		// 실패 : session에 저장 X, 에러 리턴
		
		Map<String, String> result = new HashMap<>();
		
		String encryptedPassword = EncryptUtils.md5(password);
		
		User user = userBO.getUesrByLoginIdAndPassword(loginId, encryptedPassword);
		
		if(user != null) {
			// 성공: 세션에 저장 (로그인 상태를 유지)
			HttpSession session = request.getSession();
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
			
			result.put("result", "success");
			
		} else {
			//  실패: 에러 리턴
			result.put("result", "fail");
			result.put("message", "존재하지 않는 사용자입니다.");
		};
		
		return result;
	}
}