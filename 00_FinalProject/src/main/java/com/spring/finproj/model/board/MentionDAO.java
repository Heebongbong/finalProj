package com.spring.finproj.model.board;

import java.util.List;
import java.util.Map;

public interface MentionDAO {

	//CRUD
	public List<MentionDTO> getMentionList(int cm_no);
	public int insertMentionContent(MentionDTO dto);
	public int updateMentionContent(MentionDTO dto);
	public int deleteMentionContent(int mention_no);
	public List<Integer> getMentionLikeList(int user_no);
	public int getMentionLikeCount(int mention_no);
	public int deleteMentionLike(Map<String, Integer> keyMap);
	public int insertMentionLike(Map<String, Integer> keyMap);
	public int deleteMentionLikeList(List<MentionDTO> menList);
	public int getMentionCommNo(int mention_no);
}
