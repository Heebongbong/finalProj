package com.spring.finproj.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDAO;
import com.spring.finproj.model.board.BoardDTO;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDAO;

	@Override
    @Transactional	// @Transactional은 클래스나 메서드에 붙여줄 경우, 해당 범위 내 메서드가 트랜잭션이 되도록 보장해준다.
    public void writeArticle(BoardDTO board) {
        if(board.getContent() != null) {

        }
    }

	@Override
	public void getCommunity(MultipartFile file) {
		// TODO Auto-generated method stub
		
	}

}
