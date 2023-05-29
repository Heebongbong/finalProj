package com.spring.finproj.service.board;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spring.finproj.model.board.MentionDTO;

public interface MentionService {

	public void getMentionlist(HttpServletRequest request, Model model, int cm_no) throws Exception;
	public int getMentionInsert(MentionDTO dto) throws Exception;
	public void update(MentionDTO dto) throws Exception;
	public int getMentionDelete(int mention_no) throws Exception;
	public Map<String, Object> addMentionlist(HttpServletRequest request, Model model, int cm_no) throws Exception;
}
