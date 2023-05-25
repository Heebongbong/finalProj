package com.spring.finproj.model.board;

import java.util.List;

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

	

}
