package com.spring.finproj.service.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
			
			boardTotal.put("LikeList", userLikeList);
			
			List<Integer> mentionLikeList = mentionDAO.getMentionLikeList(login_user_no);
			
			boardTotal.put("MentionLikeList", mentionLikeList);
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
					mfile.transferTo(new File(folder2, saveFileName));
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
	public int deleteBoardCont(int cm_no, HttpServletRequest request) throws Exception {
		BoardDTO dto = boardDAO.getBoardContent(cm_no);
		
		//사진파일 삭제
		if(dto.getPhoto_folder()!=null) {
			dto.setPhoto_files(request);
			
			Properties prop = new Properties();
			@SuppressWarnings("deprecation")
			FileInputStream fis = new FileInputStream(
					request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
			prop.load(new InputStreamReader(fis));
			fis.close();

			String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3)) + "\\board\\";

			String realFolder = saveFolder + dto.getPhoto_folder();
			
			// type + 이메일 아이디
			String type = dto.getType();

			StringTokenizer st1 = new StringTokenizer(dto.getEmail(), "@", true);
			String email_id = st1.nextToken();
			String nameFolder = saveFolder + "\\" + type + "_" + email_id;
			
			//게시글 폴더 및 사진 삭제
			File fileFolder = new File(realFolder);
			
			File[] fileList = fileFolder.listFiles();
			
			for(File f : fileList) {
				f.delete();
			}
			fileFolder.delete();
			
			//계정 폴더 내 타 게시글 부재시 삭제
			File userFolder = new File(nameFolder);
			if(userFolder.delete()) {
				System.out.println("계정 폴더 삭제 됨.");
			}else {
				System.out.println("계정 폴더 내 타 폴더 존재");
			}
		}
		
		int re = boardDAO.deleteBoardContent(cm_no);
		if(re>0) {
			boardDAO.deleteAccuserContent(cm_no);
			boardDAO.deleteAlarmContent(cm_no);
			boardDAO.deleteRecommandContent(cm_no);
			
			List<MentionDTO> menList = mentionDAO.getMentionList(cm_no);
			
			int men_re = boardDAO.deleteMentionContent(cm_no);
			if(men_re>0) {
				mentionDAO.deleteMentionLikeList(menList);
			}
		}
		
		return re;
	}
		
		
	@SuppressWarnings("deprecation")
	@Override
	public int updateBoard(BoardDTO dto, MultipartFile[] files,
			HttpServletRequest request, String[] deletefile) throws Exception {
		
		//본 게시글 dto
		BoardDTO db_dto = boardDAO.getBoardContent(dto.getCm_no());
		db_dto.setPhoto_files(request);

		//dto photofolder setting
		dto.setPhoto_folder(db_dto.getPhoto_folder());
		
		//properties file read
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
		prop.load(new InputStreamReader(fis));
		fis.close();
		
		String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3)) + "\\board";
		
		if (files.length != 0) { //파일 저장 필요
			
			if(db_dto.getPhoto_folder()==null) { //폴더 없음, 생성 필요
				LocalDateTime nowDate = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
				String today = nowDate.format(formatter);

				// type + 이메일 아이디
				String type = db_dto.getType();

				StringTokenizer st1 = new StringTokenizer(db_dto.getEmail(), "@", true);
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
				//dto 객체에 폴더명 세팅
				dto.setPhoto_folder(boardFolder);
			
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
		} //file 저장 end
		
		//삭제요청 파일 처리
		if(deletefile.length>0) {
			String realFolder = saveFolder + "\\" + db_dto.getPhoto_folder();
			for(String f : deletefile) {
				File d_f = new File(realFolder+"\\"+f);
				d_f.delete();
			}
		}
		
		int re = boardDAO.updateBoardContent(dto);
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
	public int manageBoardLike(int check, int cm_no, HttpSession session) {
		UserDTO user = (UserDTO) session.getAttribute("LoginUser");
		Map<String, Integer> keyMap = new HashMap<String, Integer>();
		int re;
		
		keyMap.put("cm_no", cm_no);
		keyMap.put("user_no", user.getUser_no());
		
		if(check == 1) {//like -> unlike
			re = boardDAO.deleteBoardLike(keyMap);
			
		}else {// unlike -> like
			re = boardDAO.insertBoardLike(keyMap);
		}
		
		if(re>0) {
			return check;
		}else {
			return -1;
		}
	}

	@Override
	public int manageMentionLike(int check, int mention_no, HttpSession session) {
		UserDTO user = (UserDTO) session.getAttribute("LoginUser");
		Map<String, Integer> keyMap = new HashMap<String, Integer>();
		int re;
		
		keyMap.put("mention_no", mention_no);
		keyMap.put("user_no", user.getUser_no());
		
		if(check == 1) {//like -> unlike
			re = mentionDAO.deleteMentionLike(keyMap);
			
		}else {// unlike -> like
			re = mentionDAO.insertMentionLike(keyMap);
		}
		
		if(re>0) {
			return check;
		}else {
			return -1;
		}
	}
}