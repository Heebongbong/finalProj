package com.spring.finproj.service.market;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.model.board.MarketDAO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class MarketServiceImpl implements MarketService{
	@Autowired
	private MarketDAO marketDAO;

	@Override
	public void getMarketList(Model model, String keyword, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		List<BoardDTO> list = null;
		if(keyword==null || keyword=="") {
			list = marketDAO.getMarketList();
		}else {
			if(keyword.charAt(0)=='#') {
				StringTokenizer st = new StringTokenizer(keyword, "#");
				List<String> hashList = new ArrayList<String>();
				while(st.hasMoreTokens()) {
					hashList.add(st.nextToken());
				}
				list = marketDAO.getMarketList(hashList);
				
			}else {
				list = marketDAO.getMarketList(keyword);
			}
		}
		
		for(BoardDTO d : list) {
			d.setPhoto_files(request);
		}
		
		
		model.addAttribute("MarketList", list);
	}

	@Override
	public int insertMarketCont(BoardDTO dto, String[] category, MultipartFile[] files, 
			HttpServletRequest request, HttpSession session) throws Exception {
		UserDTO user = (UserDTO)session.getAttribute("LoginUser");
		
		String hashtags = "";
		for(String s : category) {
			hashtags += s;
		}
		
		dto.setHashtag(dto.getHashtag()+hashtags);
		dto.setUser_no(user.getUser_no());
        
		Properties prop = new Properties();
		@SuppressWarnings("deprecation")
		FileInputStream fis = new FileInputStream(request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
		prop.load(new InputStreamReader(fis));
		fis.close();
		
		LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        String today = nowDate.format(formatter);
        
        // type + 이메일 아이디
        String type = user.getType();
        
        StringTokenizer st1 = new StringTokenizer(user.getEmail(), "@", true);
        String email_id = st1.nextToken();
        String boardFolder = type +"_"+ email_id;
		String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3))+"\\board\\"+boardFolder;
		
		File folder = new File(saveFolder);
		if(!folder.exists()) {
		    folder.mkdirs();
		}
		saveFolder += "//"+today;
		File folder2 = new File(saveFolder);
		if(!folder2.exists()) {
		    folder2.mkdirs();
		}
		for (MultipartFile mfile : files) {
		  
			String originalFileName = mfile.getOriginalFilename();
		    
		    // UUID.randomName을 이용하여 랜덤한 고유 식별자 생성
		    if (!originalFileName.isEmpty()) {
			    String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
			    mfile.transferTo(new File(saveFolder, saveFileName));
		    }
		}
		dto.setPhoto_folder(boardFolder+"//"+today);
		dto.setCm_no(marketDAO.getCmMax()+1);
		
		int re = marketDAO.insertBoardContent(dto);
		if(re>0) {
			re = marketDAO.insertMarketContent(dto);
		}

		return re;
	}
}