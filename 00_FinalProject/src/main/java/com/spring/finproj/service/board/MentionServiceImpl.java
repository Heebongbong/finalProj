package com.spring.finproj.service.board;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<Integer, List<MentionDTO>> addMentionlist(HttpServletRequest request, Model model, int cm_no) throws Exception {
		Map<Integer, List<MentionDTO>> mapList = new HashMap<Integer, List<MentionDTO>>();
		
		List<MentionDTO> list2 = mentionDAO.getMentionList(cm_no);
		mapList.put(cm_no, list2);
		
		return mapList;
	}

	@Override
	public int getMentionInsert(MentionDTO dto) throws Exception {
		
		return this.mentionDAO.insertMentionContent(dto);
		
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
