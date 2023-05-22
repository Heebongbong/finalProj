package com.spring.finproj.service.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.board.BoardDAO;
import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.model.board.MentionDAO;
import com.spring.finproj.model.board.MentionDTO;

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
	public void create(MentionDTO dto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(MentionDTO dto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(MentionDTO dto) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
