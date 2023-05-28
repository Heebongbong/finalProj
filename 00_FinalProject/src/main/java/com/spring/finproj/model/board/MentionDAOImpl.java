package com.spring.finproj.model.board;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MentionDAOImpl implements MentionDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<MentionDTO> getMentionList(int cm_no) {
		return this.sqlSession.selectList("mention_list", cm_no);
	}

	@Override
	public MentionDTO getMentionOne(MentionDTO dto) {
		// TODO Auto-generated method stub
		return this.sqlSession.selectOne("mention_one", dto);
	}

	@Override
	public int insertMentionContent(MentionDTO dto) {
		return this.sqlSession.insert("mention_insert", dto);
	}

	@Override
	public int updateMentionContent(MentionDTO dto) {
		return this.sqlSession.insert("mention_insert", dto);
	}

	@Override
	public int deleteMentionContent(int mention_no) {
		return this.sqlSession.insert("mention_delete", mention_no);
	}

	@Override
	public List<Integer> getMentionLikeList(int user_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("mention_like_list", user_no);
	}

	@Override
	public int getMentionLikeCount(int mention_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mention_like_count", mention_no);
	}

	@Override
	public int deleteMentionLike(Map<String, Integer> keyMap) {
		// TODO Auto-generated method stub
		return sqlSession.delete("mention_like_delete", keyMap);
	}

	@Override
	public int insertMentionLike(Map<String, Integer> keyMap) {
		// TODO Auto-generated method stub
		return sqlSession.insert("mention_like_insert", keyMap);
	}

	@Override
	public int deleteMentionLikeList(List<MentionDTO> menList) {
		// TODO Auto-generated method stub
		return sqlSession.delete("mention_like_list_delete", menList);
	}
}