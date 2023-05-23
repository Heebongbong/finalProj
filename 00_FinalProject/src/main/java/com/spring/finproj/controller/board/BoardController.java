package com.spring.finproj.controller.board;

import java.util.Map;

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
import com.spring.finproj.model.board.MentionDTO;
import com.spring.finproj.service.board.BoardService;
import com.spring.finproj.service.board.MentionService;


@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    
    @Autowired
    private MentionService mentionService;
    
    
    @RequestMapping("/list")
    public String boardList(@RequestParam(required = false) String keyword, HttpServletRequest request, Model model) throws Exception {
    	boardService.getBoardList(request, model, keyword);
    	return "board.list";
    }
 
    @RequestMapping("/write")
    public String boardWrite() {
        return "board.write";
    }
    
    @RequestMapping("/writeOk")
    public String write(BoardDTO dto, @RequestParam("upfile") MultipartFile[] files, 
    		Model model, String[] category,
    		HttpSession session, HttpServletRequest request) throws Exception {
    	
    	int check = boardService.writeBoard(dto, files, model, category, session, request);
    	
    	if(check > 0) {
			return "board.list";
		}else {
			model.addAttribute("msg", "글작성중 문제가 발생했습니다.");
		    return "error/error";
		}
    }
    
    @RequestMapping("/addlist")
    @ResponseBody
    public Map<String , Object> boardAddList(HttpSession session, HttpServletRequest request, 
    		@RequestParam(required = false) int cm_no, 
    		@RequestParam(required = false) String keyword) throws Exception{
    	return boardService.getBoardAddList(session, request, cm_no, keyword);
    }
    
    @RequestMapping("/addmention")
    public void mentionRequest(MentionDTO dto, HttpServletRequest request, Model model, @RequestParam("cm_no") int no) throws Exception {
    	mentionService.getMentionInsert(dto);
    	mentionService.getMentionlist(request, model, no);
    	
    }
}
