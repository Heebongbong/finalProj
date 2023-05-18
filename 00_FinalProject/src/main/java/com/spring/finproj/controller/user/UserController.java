package com.spring.finproj.controller.user;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.finproj.model.user.UserDTO;
import com.spring.finproj.service.handler.SendSMSAPI;
import com.spring.finproj.service.user.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/content")
	public String userMypage(@RequestParam("user_no") int user_no, Model model) throws Exception {
		
		userService.getUserContent(model, user_no);
		
		return "user.mypage";
	}
	
	
	@RequestMapping("/join")
	public String userJoin() throws Exception {
		
		return "user.join";
	}
	
	@RequestMapping("/checkNickname")
	@ResponseBody
	public Boolean userCheckNickname(@RequestParam("nickname") String nickname) throws Exception {
		
		System.out.println("닉네임 체크 === "+nickname);
		Boolean isUnique = userService.getNickCheck(nickname);
		System.out.println("이즈유니크 ====="+isUnique);
		
		return isUnique;
	}
	
	@RequestMapping("/insert")
	public String userMypageOk(@RequestParam("pwd_update") String pwd_update, UserDTO dto, Model model, HttpServletRequest request) throws Exception {
		
		
		Properties prop = new Properties();
		@SuppressWarnings("deprecation")
		FileInputStream fis = new FileInputStream(request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
		prop.load(new InputStreamReader(fis));
		fis.close();
		
		String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3)) + "\\profile";
		
		return "user.mypageOk";
	}
	
	@RequestMapping("/send/sms")
	public String sendSMS(String phone) throws Exception {

		SendSMSAPI s = new SendSMSAPI();
		int re = s.sendSMS("01071307454");
		System.out.println(re);
		
		return "index.index";
	}
	
}