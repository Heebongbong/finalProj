package com.spring.finproj.service.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.board.MentionDAO;
import com.spring.finproj.model.board.MentionDTO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class MentionServiceImpl implements MentionService{
	@Autowired
	private MentionDAO mentionDAO;
	
	@Override
	public void getMentionlist(HttpServletRequest request, Model model, int cm_no) throws Exception {
		List<MentionDTO> list = null;
		
		list = mentionDAO.getMentionList(cm_no);
		
		model.addAttribute("MentionList", list);
	}
	
	@Override
	public Map<String, Object> addMentionlist(HttpServletRequest request, Model model, int cm_no) throws Exception {
		Map<String, Object> mapList = new HashMap<String, Object>();
		
		List<MentionDTO> list = mentionDAO.getMentionList(cm_no);
		
		for(MentionDTO m : list) {
			m.setLikeCount(mentionDAO.getMentionLikeCount(m.getMention_no()));
		}
		
		int login_user_no = ((UserDTO)request.getSession().getAttribute("LoginUser")).getUser_no();
		
		List<Integer> mentionLikeList = mentionDAO.getMentionLikeList(login_user_no);
		
		mapList.put("MentionList", list);
		mapList.put("MentionLikeList", mentionLikeList);
		
		return mapList;
	}

	@Override
	public int getMentionInsert(MentionDTO dto) throws Exception {
		
		return this.mentionDAO.insertMentionContent(dto);
		
	}

	@Override
	public int getMentionDelete(int mention_no) throws Exception {

		return this.mentionDAO.deleteMentionContent(mention_no);
		
	}

	@Override
	public void update(MentionDTO dto) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
