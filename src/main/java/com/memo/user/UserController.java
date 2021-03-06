package com.memo.user;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.common.EncryptUtils;
import com.memo.user.BO.UserBO;

/*화면만 구성하는 컨트롤러
 * @author 
 * */


@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserBO userBO;
	
	@RequestMapping("/sign_up_view")
	public String signUpView(Model model) {
		model.addAttribute("viewName", "user/sign_up");
		return "template/layout";
	}
	
	
	/**
	 * 회원가입 submit - Non AJAX
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return 로그인 화면으로 redirect
	 */
	
	@RequestMapping("/sign_up_for_submit")
	public String signUpForSubmit(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, @RequestParam("name") String name
			, @RequestParam("email") String email) {
		
		//암호화
		String encryptedPassword = EncryptUtils.md5(password);
		
		//DB insert
		userBO.addUser(loginId, encryptedPassword, name, email);
		
		return "redirect:/user/sign_in_view"; // redirect는 @ResponseBody가 아닌 일반 Controller에서 작동, 다른 url로 이동
	}
	
	/**
	 * 로그인 화면
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/sign_in_view")
	public String signInView(Model model) {
		model.addAttribute("viewName", "user/sign_in");
		return "template/layout";
	}
	
	@RequestMapping("/sign_out")
	public String signOut(HttpServletRequest request) {
		//로그아웃
		HttpSession session = request.getSession();
		
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		return "redirect:/user/sign_in_view";
	}
}