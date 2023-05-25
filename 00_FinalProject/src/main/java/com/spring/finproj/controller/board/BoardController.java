package com.spring.finproj.controller.board;

import java.util.List;
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
    public String boardList(@RequestParam(required = false, value = "keyword") String keyword, Model model) throws Exception {
    	model.addAttribute("Keyword", keyword);
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
    
    @RequestMapping("/update")
    public String boardUpdate(int cm_no, Model model) {
    	Map<String, Object> map = boardService.contentBoard(cm_no);
    	model.addAttribute("Map", map);
    	return "board.update";
  
    }
    
    @RequestMapping("/updateOk")
    public String update(BoardDTO dto, @RequestParam("upfile") MultipartFile[] files, 
    		Model model, String[] category,
    		HttpSession session, HttpServletRequest request) throws Exception {
    	
    	int check = boardService.updateBoard(dto, files, model, category, session, request);
    	
    	if(check > 0) {
			return "board.list";
		}else {
			model.addAttribute("msg", "글작성중 문제가 발생했습니다.");
		    return "error/error";
		}
    }
    
    @RequestMapping("/addlist")
    @ResponseBody
    public Map<String , Object> boardAddList(HttpServletRequest request, 
    		@RequestParam(required = false) int cm_no, 
    		@RequestParam(required = false) String keyword) throws Exception{
    	return boardService.getBoardAddList(request, cm_no, keyword);
    }
    
    @RequestMapping("/addmention")
    @ResponseBody
    public List<MentionDTO> addmentionRequest(MentionDTO dto, HttpServletRequest request, Model model) throws Exception {
    	int check = mentionService.getMentionInsert(dto);
    	System.out.println("check +"+check);
    	
    	List<MentionDTO> list = mentionService.addMentionlist(request, model, dto.getCm_no());
    	return list;
    }
    
    @RequestMapping("/deletemention")
    @ResponseBody
    public int delmentionRequest(int mention_no, HttpServletRequest request) throws Exception {
    	int check = mentionService.getMentionDelete(mention_no);
    	return check;
    }
    @RequestMapping("/delete")
    public String boardDelete(int cm_no, HttpServletRequest request) {
    	boardService.deleteBoardCont(cm_no, request);
    	return "redirect:/finproj/index";
    }
}
