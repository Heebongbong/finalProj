package com.spring.finproj.service.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.alarm.AlarmDTO;
import com.spring.finproj.model.board.BoardDAO;
import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.model.board.MentionDAO;
import com.spring.finproj.model.board.MentionDTO;
import com.spring.finproj.model.chat.ChatDAO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class MentionServiceImpl implements MentionService{
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private MentionDAO mentionDAO;
	@Autowired
	private ChatDAO chatDAO;
	
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
		
		BoardDTO d =boardDAO.getBoardContent(dto.getCm_no());
		
		AlarmDTO a = new AlarmDTO();
		a.setUser_no(d.getUser_no());
		a.setField(3);
		a.setCheck(true);

		chatDAO.insertAlarm(a);
		
		return this.mentionDAO.insertMentionContent(dto);
		
	}

	@Override
	public int getMentionDelete(int mention_no) throws Exception {

		return this.mentionDAO.deleteMentionContent(mention_no);
		
	}

}
