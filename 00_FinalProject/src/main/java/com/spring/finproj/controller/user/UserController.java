package com.spring.finproj.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.user.UserDTO;
import com.spring.finproj.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/snsProfile")
	@ResponseBody
	public String snsProfile(HttpSession session, HttpServletResponse response) throws Exception {
		return userService.getSnsProfile(session, response);
	}

	@RequestMapping("/mypage")
	public String userMypage() throws Exception {
		return "user.mypage";
	}

	@RequestMapping("/mypageOk")
	public String userMypageOk(UserDTO dto, HttpSession session, @RequestParam("upfile") MultipartFile multipartFile,
			HttpServletRequest request, Model model) throws Exception {

		int check = userService.updateUserContent(dto, session, multipartFile, request);

		if (check > 0) {
			return "redirect:/index";
		} else {// 불일치
			model.addAttribute("msg", "수정 중 문제가 발생했습니다.");
			return "error/error";
		}
	}

	@RequestMapping("/join")
	public String userJoin() throws Exception {

		return "user.join";
	}

	@RequestMapping("/joinOk")
	public String userJoinOk(UserDTO dto, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "upfile", required = false) MultipartFile multipartFile, Model model) throws Exception {

		int check = userService.insertUserContent(dto, request, response, session, multipartFile);
		
		System.out.println("체크"+check);
		
		if (check > 0) {
			return "redirect:/index";
		} else {// 불일치
			model.addAttribute("msg", "수정 중 문제가 발생했습니다.");
			return "error/error";
		}

	}
	
	@RequestMapping("/delete")
	public String userDrop() throws Exception {
		
		return "user.delete";
	}
	
	@RequestMapping("/deleteOk")
	public String userDropOk(@RequestParam(defaultValue = "") String check_pwd, HttpSession session, HttpServletResponse response) throws Exception {
		int check = userService.deleteUser(check_pwd, session, response);
		if (check > 0) {
			return "redirect:/index";
		} else {// 불일치
			response.getWriter().println("<script>alert('탈퇴 중 문제가 발생했습니다.');history.back();</script>");
			return null;
		}
	}

	@RequestMapping("/check/nickname")
	@ResponseBody
	public String userCheckNickname(String nickname, HttpSession session) throws Exception {
		System.out.println(nickname);
		return userService.getNickCheck(nickname, session);
	}
	
	@RequestMapping("/check/email")
	@ResponseBody
	public String userCheckemail(String email) throws Exception {
		System.out.println(email);
		return userService.getEmailCheck(email);
	}

	@RequestMapping("/check/phone")
	@ResponseBody
	public String userCheckPhone(String phone, String type) throws Exception {
		System.out.println(phone);
		System.out.println(type);
		return userService.getPhoneCheck(phone, type);
	}

	@RequestMapping("/sms/send")
	@ResponseBody
	public String sendSMS(String phone, HttpSession session) throws Exception {

		return userService.sendSMS(phone, session);
	}
	
	@RequestMapping("/sms/siteuser")
	@ResponseBody
	public String sendSMSSite(String phone, HttpSession session) throws Exception {
		
		return userService.sendSMSSite(phone, session);
	}

	@RequestMapping("/sms/check")
	@ResponseBody
	public String checkSMS(String input_code, HttpSession session) throws Exception {

		return userService.checkSMS(input_code, session);
	}

	@RequestMapping("/check/pwd")
	@ResponseBody
	public String checkPwd(String check_pwd, HttpSession session) throws Exception {

		System.out.println(check_pwd);
		return userService.checkPwd(check_pwd, session);
	}

	@RequestMapping("/forget")
	public String userForget() throws Exception {

		return "user.forget";
	}
	
	@RequestMapping("/forgetOk")
	public String userForgetOk(UserDTO dto, Model model) {
		int check = userService.updatePwd(dto);
		
		if (check > 0) {
			return "redirect:/index";
		} else {// 불일치
			model.addAttribute("msg", "수정 중 문제가 발생했습니다.");
			return "error/error";
		}
	}

    @RequestMapping("/userboard")
    public String boardUserPage(Model model, int user_no) {
    	userService.getBoardUserCont(user_no, model);
    	return "user.userboard";
    }
    
    @RequestMapping("/likeboard")
    public String boardLikePage() {
    	return "user.likeboard";
    }
    
    //관리자
    @RequestMapping("/admin")
    public String userList(Model model) {
    	userService.getUserList(model);
    	return "admin.user";
    }
    @RequestMapping("/admin/delete")
    public String userDelete(int user_no) {
    	userService.userDelete(user_no);
    	return "redirect:/user/admin";
    }

}