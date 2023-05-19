package com.spring.finproj.controller.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.service.board.BoardService;


@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    
    @RequestMapping("/list")
    public String boardList(@RequestParam(required = false) String keyword, HttpServletRequest request, Model model) throws Exception {
    	boardService.getBoardList(request, model, keyword);
    	return "board.list";
    }
 
    @RequestMapping("/write")
    public String boardWrite() {
    	System.out.println(2);
        return "board.write";
    }
    @RequestMapping("/writeform")
    public String write(BoardDTO dto, @RequestParam("upfile") MultipartFile[] files, 
    		Model model, String[] category, String hashtags,
    		HttpSession session, HttpServletRequest request) throws Exception {
    	
    	return boardService.writeBoard(dto, files, model, category, hashtags, session, request);
    }
    
    @RequestMapping("/addlist")
    @ResponseBody
    public List<BoardDTO> boardAddList(HttpServletRequest request, @RequestParam int cm_no, @RequestParam(required = false) String keyword) throws Exception{
    	
    	return boardService.getBoardAddList(request, cm_no, keyword);
    }
}
