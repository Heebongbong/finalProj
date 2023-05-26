package com.spring.finproj.service.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.spring.finproj.model.board.MentionDAO;
import com.spring.finproj.model.board.MentionDTO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private MentionDAO mentionDAO;

	@Override
	public Map<String, Object> getBoardAddList(HttpServletRequest request, int cm_no,
			String keyword, String category) throws Exception {
		Map<String, Object> boardTotal = new HashMap<String, Object>();
		Map<Integer, List<MentionDTO>> mapList2 = new HashMap<Integer, List<MentionDTO>>();
		
		List<BoardDTO> list = null;
		
		String key;
		
		if(cm_no == 0) {
			if((keyword==null || keyword=="") && (category==""||category==null)) {
				list = boardDAO.getBoardList();
			}else {
				if(keyword.startsWith("%23")) {
					key = keyword+= category;
					
					StringTokenizer st = new StringTokenizer(key, "%23");
					List<String> hashList = new ArrayList<String>();
					
					while(st.hasMoreTokens()) {
						hashList.add(st.nextToken());
					}
					
					list = boardDAO.getBoardHashKeyList(hashList);
				}else {
					key = keyword+= category;
					System.out.println(key);
					list = boardDAO.getBoardList(key);
				}
			}
		}else {
			if((keyword==null || keyword=="") && (category==""||category==null)) {
				list = boardDAO.getBoardList(cm_no);
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
					list = boardDAO.getBoardHashKeyMap(map);
					
				}else {
					key = keyword+= category;
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("keyword", key);
					map.put("cm_no", cm_no);
					list = boardDAO.getBoardList(map);
				}
			}
		}

		for (BoardDTO d : list) {
			d.setPhoto_files(request);

			List<MentionDTO> list2 = mentionDAO.getMentionList(d.getCm_no());
			mapList2.put(d.getCm_no(), list2);
		}

		boardTotal.put("BoardList", list);
		boardTotal.put("MentionList", mapList2);

		return boardTotal;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int writeBoard(BoardDTO boardDTO, MultipartFile[] files,
			HttpServletRequest request) throws Exception {

		String hashtag = boardDTO.getHashtag();
		
		System.out.println(hashtag);

		boardDTO.setHashtag(hashtag);
		boardDTO.setUser_no(boardDTO.getUser_no());

		if (files.length == 0) {
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(
					request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
			prop.load(new InputStreamReader(fis));
			fis.close();

			LocalDateTime nowDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
			String today = nowDate.format(formatter);

			// type + 이메일 아이디
			String type = boardDTO.getType();

			StringTokenizer st1 = new StringTokenizer(boardDTO.getEmail(), "@", true);
			String email_id = st1.nextToken();
			String boardFolder = type + "_" + email_id;
			String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3)) + "\\board\\" + boardFolder;

			File folder = new File(saveFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			saveFolder += "\\" + today;
			File folder2 = new File(saveFolder);
			if (!folder2.exists()) {
				folder2.mkdirs();
			}

			for (MultipartFile mfile : files) {

				String originalFileName = mfile.getOriginalFilename();

				// UUID.randomName을 이용하여 랜덤한 고유 식별자 생성
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					mfile.transferTo(new File(folder, saveFileName));
				}
			}
			boardDTO.setPhoto_folder(boardFolder);
		}

		int re = boardDAO.insertBoardContent(boardDTO);
		return re;
	}

	@Override
	public String declaration(int cm_no, String reason, HttpSession session) {
		UserDTO loginUser = (UserDTO) session.getAttribute("LoginUser");
		Map<String, String> decla = new HashMap<String, String>();
		decla.put("cm_no", ((Integer)cm_no).toString());
		decla.put("user_no", ((Integer)loginUser.getUser_no()).toString());
		decla.put("reason", reason);
		
		int check = boardDAO.checkDeclaration(decla);
		if(check==0) {
			int re = boardDAO.insertDeclaration(decla);
			return ((Integer)re).toString();
		}else {
			return "-1";
		}
	}

	@Override
	public int deleteBoardCont(int cm_no, HttpServletRequest request) {
		BoardDTO dto = boardDAO.getBoardContent(cm_no);
		
		int re = boardDAO.deleteBoardContent(cm_no);
		if(re>0) {
			boardDAO.deleteAccuserContent(cm_no);
			boardDAO.deleteAlarmContent(cm_no);
			boardDAO.deleteMentionContent(cm_no);
			boardDAO.deleteRecommandContent(cm_no);
		}
		
		return re;
	}
		
		
	@SuppressWarnings("deprecation")
	@Override
	public int updateBoard(BoardDTO boardDTO, MultipartFile[] files,
			HttpSession session, HttpServletRequest request) throws Exception {
		
		boardDTO.setHashtag(boardDTO.getHashtag());
		
		System.out.println(1);
		
		if (files.length == 0) {
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(
					request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
			prop.load(new InputStreamReader(fis));
			fis.close();

			System.out.println(2);
			
			LocalDateTime nowDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
			String today = nowDate.format(formatter);

			// type + 이메일 아이디
			String type = boardDTO.getType();

			StringTokenizer st1 = new StringTokenizer(boardDTO.getEmail(), "@", true);
			String email_id = st1.nextToken();
			String boardFolder = type + "_" + email_id;
			String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3)) + "\\board\\" + boardFolder;

			File folder = new File(saveFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			saveFolder += "\\" + today;
			File folder2 = new File(saveFolder);
			if (!folder2.exists()) {
				folder2.mkdirs();
			}

			for (MultipartFile mfile : files) {

				String originalFileName = mfile.getOriginalFilename();

				// UUID.randomName을 이용하여 랜덤한 고유 식별자 생성
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					mfile.transferTo(new File(folder, saveFileName));
				}
			}
			boardDTO.setPhoto_folder(boardFolder);
		}
		System.out.println(3);
		int re = boardDAO.updateBoardContent(boardDTO);
		return re;
	}

	@Override
	public Map<String, Object> contentBoard(HttpServletRequest request, int cm_no, Model model) throws Exception {

		Map<String, Object> hash = new HashMap<String, Object>();
    	Map<String, Integer> mapp = new HashMap<String, Integer>();
    	List<String> file = new ArrayList<String>();
    	BoardDTO dto = boardDAO.getBoardContent(cm_no);
    	
		if(dto != null) {
			StringTokenizer st = new StringTokenizer(dto.getHashtag(), "#");
			while(st.hasMoreTokens()) {
				mapp.put(st.nextToken(), 1);
			}
			
			try {
				if(dto.getPhoto_folder() != null) {
					dto.setPhoto_files(request);
					file = dto.getPhoto_files();
					System.out.println(file);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(mapp);
			
			hash.put("Files", file);
			hash.put("BoardDTO", dto);
			hash.put("HashMap", mapp);
			
			return hash;
		}else {
			return null;
		}
	}
}