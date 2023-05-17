package com.spring.finproj.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.service.board.BoardService;


@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
 
    @RequestMapping("/write")
    public String boardWrite() {
    	System.out.println(2);
        return "board.write";
    }
 
    @RequestMapping("/writeform")
    public String write(BoardDTO dto, @RequestParam("upfile") MultipartFile[] files, 
    		Model model, String[] category, String hashtags,
    		HttpSession session, HttpServletRequest request) throws Exception {
    	System.out.println(1);
    	return boardService.writeBoard(dto, files, model, category, hashtags, session, request);
    }
    
}
