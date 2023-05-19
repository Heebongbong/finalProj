package com.spring.finproj.service.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDAO;
import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDAO;

	@Override
	public String getBoardList(HttpServletRequest request, Model model, String keyword) throws Exception{
		List<BoardDTO> list = null;
		if(keyword!="") {
			list = boardDAO.getBoardList(keyword);
		}else {
			list = boardDAO.getBoardList();
		}
		
		for(BoardDTO d : list) {
			d.setPhoto_files(request);
		}
		model.addAttribute("BoardList", list);
		return "board.list";
	}

	@Override
	public List<BoardDTO> getBoardAddList(HttpServletRequest request, int cm_no, String keyword) throws Exception {
		List<BoardDTO> list = null;
		if(keyword!="") {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", keyword);
			map.put("cm_no", cm_no);
			list = boardDAO.getBoardList(map);
		}else {
			list = boardDAO.getBoardList(cm_no);
		}
		
		for(BoardDTO d : list) {
			d.setPhoto_files(request);
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String writeBoard(BoardDTO boardDTO, MultipartFile[] files, 
			Model model, String[] category, String hashtags, 
			HttpSession session, HttpServletRequest request) throws Exception {
		
		UserDTO user = (UserDTO)session.getAttribute("LoginUser");
		
		String hashtag = "#"+String.join("#", category);
		StringTokenizer st = new StringTokenizer(hashtags, "#");
		while(st.hasMoreTokens()) {
			hashtag += "#"+st.nextToken().trim();
		}
		
		boardDTO.setHashtag(hashtag);
		boardDTO.setUser_no(user.getUser_no());
        
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
		prop.load(new InputStreamReader(fis));
		fis.close();
		
		LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        String today = nowDate.format(formatter);
        
        String boardFolder = user.getNickname()+"\\"+today;
		String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3))+"\\board\\"+boardFolder;
		
		File folder = new File(saveFolder);
		if(!folder.exists()) {
		    folder.mkdirs();
		}
		
		for (MultipartFile mfile : files) {
		  
			String originalFileName = mfile.getOriginalFilename();
		    
		    // UUID.randomName을 이용하여 랜덤한 고유 식별자 생성
		    if (!originalFileName.isEmpty()) {
			    String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
			    mfile.transferTo(new File(folder, saveFileName));
		    }
		}
		boardDTO.setPhoto_folder(boardFolder);
		
		int re = boardDAO.insertBoardContent(boardDTO);
		if(re>0) {
			 return "board.list";
		}else {
			model.addAttribute("msg", "글작성중 문제가 발생했습니다.");
		    return "error/error";
		}
	}
}