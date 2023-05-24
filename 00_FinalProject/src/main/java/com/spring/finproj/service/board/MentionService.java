package com.spring.finproj.service.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spring.finproj.model.board.MentionDTO;

public interface MentionService {

	void getMentionlist(HttpServletRequest request, Model model, int cm_no) throws Exception;

	int getMentionInsert(MentionDTO dto) throws Exception;

    void update(MentionDTO dto) throws Exception;

    void delete(MentionDTO dto) throws Exception;

    List<MentionDTO> addMentionlist(HttpServletRequest request, Model model, int cm_no) throws Exception;
}
