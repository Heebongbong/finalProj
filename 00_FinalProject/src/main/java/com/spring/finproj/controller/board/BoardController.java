package com.spring.finproj.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.service.board.BoardService;


@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
 
    @RequestMapping(value = "/board_list", method = RequestMethod.GET)
    public String write() {
        return "guestbook/write";
    }
 
    @RequestMapping(value = "/board_list", method = RequestMethod.POST)
    public String write(BoardDTO BoardDTO, @RequestParam("upfile") MultipartFile[] files, 
    		Model model, HttpSession session, HttpServletRequest request) throws Exception {
    	return boardService.writeBoard(BoardDTO, files, model, session, request);
    }
}
