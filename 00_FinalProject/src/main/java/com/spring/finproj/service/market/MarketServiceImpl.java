package com.spring.finproj.service.market;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
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
import com.spring.finproj.model.board.MarketDAO;
import com.spring.finproj.model.board.MentionDAO;
import com.spring.finproj.model.board.MentionDTO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class MarketServiceImpl implements MarketService{
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private MarketDAO marketDAO;
	@Autowired
	private MentionDAO mentionDAO;

	@Override
	public Map<String , Object> getMarketList(String keyword, String category, HttpServletRequest request, int cm_no) throws Exception {
		Map<String, Object> marketTotal = new HashMap<String, Object>();
		Map<Integer, List<MentionDTO>> mapList2 = new HashMap<Integer, List<MentionDTO>>();
		List<BoardDTO> list = null;
		String key;

		if(cm_no == 0) {
			if((keyword==null || keyword=="") && (category==""||category==null)) {
				list = marketDAO.getMarketList();
			}else {
				if(keyword.startsWith("%23")) {
					key = keyword += category;
					
					StringTokenizer st = new StringTokenizer(key, "%23");
					List<String> hashList = new ArrayList<String>();
					
					while(st.hasMoreTokens()) {
						hashList.add(st.nextToken());
					}
					
					list = marketDAO.getMarketList(hashList);

				}else {
					StringTokenizer st = new StringTokenizer(category, "%23");
					List<String> hashList = new ArrayList<String>();
					
					while(st.hasMoreTokens()) {
						hashList.add(st.nextToken());
					}
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("keyword", keyword);
					map.put("list", hashList);
					list = marketDAO.getMarketList(map);
				}
			}
		}else {
			if((keyword==null || keyword=="") && (category==""||category==null)) {
				list = marketDAO.getMarketList(cm_no);
			}else {
				if(keyword.startsWith("%23")) {
					key = keyword+= category;
					
					StringTokenizer st = new StringTokenizer(key, "%23");
					List<String> hashList = new ArrayList<String>();
					
					while(st.hasMoreTokens()) {
						hashList.add(st.nextToken());
					}
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("hashList", hashList);
					map.put("cm_no", cm_no);
					list = marketDAO.getMarketHashKeyMap(map);
					
				}else {
					StringTokenizer st = new StringTokenizer(category, "%23");
					List<String> hashList = new ArrayList<String>();
					
					while(st.hasMoreTokens()) {
						hashList.add(st.nextToken());
					}
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("keyword", keyword);
					map.put("list", hashList);
					map.put("cm_no", cm_no);
					list = marketDAO.getMarketKeyMap(map);
				}
			}
		}
		
		for(BoardDTO d : list) {
			d.setPhoto_files(request);
			
			
			d.setLikeCount(boardDAO.getBoardLikeCount(d.getCm_no()));
			
			//<cm_no, List<mention>> 댓글 맵리스트
			List<MentionDTO> list2 = mentionDAO.getMentionList(d.getCm_no());
			
			for(MentionDTO m : list2) {
				m.setLikeCount(mentionDAO.getMentionLikeCount(m.getMention_no()));
			}
			
			mapList2.put(d.getCm_no(), list2);
		}
		
		HttpSession session = request.getSession();
		if(session.getAttribute("LoginUser")!=null) {
			int login_user_no = ((UserDTO)session.getAttribute("LoginUser")).getUser_no();
			List<Integer> userLikeList = boardDAO.getBoardLikeList(login_user_no);
			
			marketTotal.put("LikeList", userLikeList);
			
			List<Integer> mentionLikeList = mentionDAO.getMentionLikeList(login_user_no);
			
			marketTotal.put("MentionLikeList", mentionLikeList);
		}
		
		marketTotal.put("BoardList", list);
		marketTotal.put("MentionList", mapList2);
		
		return marketTotal;
	}

	@Override
	public int insertMarketCont(BoardDTO dto, String[] category, MultipartFile[] files, 
			HttpServletRequest request, HttpSession session) throws Exception {
		UserDTO user = (UserDTO)session.getAttribute("LoginUser");
		
		String hashtags = "";
		for(String s : category) {
			hashtags += s;
		}
		
		StringTokenizer st_hash = new StringTokenizer(dto.getHashtag(), "#");
		while(st_hash.hasMoreTokens()) {
			String s = st_hash.nextToken();
			if(!s.equals(""))
				hashtags += "#"+s;
		}
		
		dto.setHashtag(hashtags);
		dto.setUser_no(user.getUser_no());
        
		int file_check = 0;
		for (MultipartFile ms : files) {
			if(!ms.isEmpty()) file_check = 1;
		}
		if (file_check == 1) {
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
			dto.setPhoto_folder(boardFolder+"/"+today);
		}
		dto.setCm_no(marketDAO.getCmMax()+1);
		
		int re = marketDAO.insertBoardContent(dto);
		if(re>0) {
			re = marketDAO.insertMarketContent(dto);
		}

		return re;
	}
	
	@Override
	public Map<String, Object> contentBoard(HttpServletRequest request, int cm_no, Model model) throws Exception {

		Map<String, Object> hash = new HashMap<String, Object>();
    	Map<String, Integer> mapp = new HashMap<String, Integer>();
    	List<String> file = new ArrayList<String>();
    	BoardDTO dto = marketDAO.getMarketContent(cm_no);
    	
		if(dto != null) {
			StringTokenizer st = new StringTokenizer(dto.getHashtag(), "#");
			while(st.hasMoreTokens()) {
				mapp.put(st.nextToken(), 1);
			}
			
			try {
				if(dto.getPhoto_folder() != null) {
					dto.setPhoto_files(request);
					file = dto.getPhoto_files();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			hash.put("Files", file);
			hash.put("BoardDTO", dto);
			hash.put("HashMap", mapp);
			
			return hash;
		}else {
			return null;
		}
		
	}

	@Override
	public int updateMarketCont(BoardDTO boardDTO, MultipartFile[] files, HttpSession session,
			HttpServletRequest request, String[] deletefile, String[] category) throws Exception {
		Set<String> hashList = new HashSet<String>();
		
		StringTokenizer st_hash = new StringTokenizer(boardDTO.getHashtag(), "#");
		while(st_hash.hasMoreTokens()) {
			hashList.add(st_hash.nextToken());
		}
		String cateList = "";
		for(String s : category) {
			cateList += s;
		}
		StringTokenizer st_cate = new StringTokenizer(cateList, "#");
		while(st_cate.hasMoreTokens()) {
			hashList.add(st_cate.nextToken());
		}
		
		String hashtags = "";
		for(String s : hashList) {
			hashtags += "#" + s;
		}
		boardDTO.setHashtag(hashtags);
		
		//본 게시글 dto
		BoardDTO db_dto = boardDAO.getBoardContent(boardDTO.getCm_no());

		//dto photofolder setting
		boardDTO.setPhoto_folder(db_dto.getPhoto_folder());
		
		Properties prop = new Properties();
		@SuppressWarnings("deprecation")
		FileInputStream fis = new FileInputStream(
				request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
		prop.load(new InputStreamReader(fis));
		fis.close();
		
		String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3)) + "\\board\\";

		
		int file_check = 0;
		for (MultipartFile ms : files) {
			if(!ms.isEmpty()) file_check = 1;
		}
		if (file_check == 1) {
			
			if(db_dto.getPhoto_folder()==null) {
			
				LocalDateTime nowDate = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
				String today = nowDate.format(formatter);
	
				// type + 이메일 아이디
				String type = boardDTO.getType();
	
				StringTokenizer st1 = new StringTokenizer(boardDTO.getEmail(), "@", true);
				String email_id = st1.nextToken();
				String boardFolder = type + "_" + email_id;
				String realFolder = saveFolder + "\\" + boardFolder;
				
				File folder = new File(realFolder);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				
				realFolder += "\\" + today;
				File folder2 = new File(realFolder);
				if (!folder2.exists()) {
					folder2.mkdirs();
				}
	
				for (MultipartFile mfile : files) {
	
					String originalFileName = mfile.getOriginalFilename();
	
					// UUID.randomName을 이용하여 랜덤한 고유 식별자 생성
					if (!originalFileName.isEmpty()) {
						String saveFileName = UUID.randomUUID().toString()
								+ originalFileName.substring(originalFileName.lastIndexOf('.'));
						mfile.transferTo(new File(folder2, saveFileName));
					}
				}
				boardDTO.setPhoto_folder(boardFolder+ "/" + today);
			}else { //폴더 존재 , 파일 저장필요
				String realFolder = saveFolder + "\\" + db_dto.getPhoto_folder();
				
				for (MultipartFile mfile : files) {
	
					String originalFileName = mfile.getOriginalFilename();
	
					// UUID.randomName을 이용하여 랜덤한 고유 식별자 생성
					if (!originalFileName.isEmpty()) {
						String saveFileName = UUID.randomUUID().toString()
								+ originalFileName.substring(originalFileName.lastIndexOf('.'));
						mfile.transferTo(new File(realFolder, saveFileName));
					}
				}
			}
		}//file 저장
		
		//삭제처리 필요 파일
		if(deletefile!=null) {
			String realFolder = saveFolder + "\\" + db_dto.getPhoto_folder();
			for(String f : deletefile) {
				File d_f = new File(realFolder+"\\"+f);
				d_f.delete();
			}
		}
		
		int re = marketDAO.updateBoardContent(boardDTO);
		
		if(re>0) {
			re = marketDAO.updateMarketContent(boardDTO);
		}
		return re;
	}
}