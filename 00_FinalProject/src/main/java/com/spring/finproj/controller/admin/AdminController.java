package com.spring.finproj.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.finproj.model.chat.ChatDAO;
import com.spring.finproj.model.chat.ChatDTO;
import com.spring.finproj.model.user.UserDAO;
import com.spring.finproj.model.user.UserDTO;
import com.spring.finproj.service.board.BoardService;
import com.spring.finproj.service.camping.CampingService;
import com.spring.finproj.service.user.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private CampingService campingService;
	@Autowired
	private ChatDAO chatDAO;
	
	@RequestMapping("/login")
	public String adminLogin(HttpSession session, Model model) {
		UserDTO admin = userDAO.getUserContent(1);
		session.setAttribute("LoginUser", admin);
		session.setMaxInactiveInterval(60*60);
		
		List<ChatDTO> list = chatDAO.getChatRoomList(admin.getUser_no());
		
		model.addAttribute("ChatRoomList", list);
		
		return "admin.admin";
	}
	
	@RequestMapping("/logout")
	public String adminLogout(HttpSession session) {

		session.invalidate();
		
		return "admin.admin";
	}
	
	@RequestMapping(value = {"", "/chat"})
	public String adminNavi() {
		
		return "admin.admin";
	}
	@RequestMapping("/camping")
	public String adminCampingNavi() {
		return "admin.camping";
	}

	// 관리자
	@RequestMapping("/camping/update")
	public void campingUpdateList() throws IOException {
		campingService.updateCampingListSetDB();
	}
	
    //관리자
    @RequestMapping("/user")
    public String userList(Model model) {
    	userService.getUserList(model);
    	return "admin.user";
    }
    @RequestMapping("/user/delete")
    public String userDelete(int user_no) {
    	userService.userDelete(user_no);
    	System.out.println(123456);
    	return "redirect:/admin/user";
    }

    // 관리자
    @RequestMapping("/accuse")
    public String accuseList(Model model) {
    	boardService.getAccuseList(model);
    	return "admin.accuse";
    }

    @RequestMapping("/board/delete")
    public String deleteBoardCont(int cm_no, HttpServletRequest request) throws Exception {
    	boardService.deleteBoardCont(cm_no, request);
    	return "redirect:/admin/accuse";
    }

    @RequestMapping("/deleteAccuse")
    public String deleteAcuuseCont(int cm_no, int user_no) {
    	boardService.deleteAcuuseCont(cm_no, user_no);
    	return "redirect:/admin/accuse";
    }
}