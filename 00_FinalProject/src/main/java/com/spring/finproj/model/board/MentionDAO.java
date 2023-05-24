package com.spring.finproj.model.board;

import java.util.List;

public interface MentionDAO {

	//CRUD
	public List<MentionDTO> getMentionList(int cm_no);
	public MentionDTO getMentionOne(MentionDTO dto);
	public int insertMentionContent(MentionDTO dto);
	public int updateMentionContent(MentionDTO dto);
	public int deleteMentionContent(int mention_no);
	
}
