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
	public void getBoardList(HttpServletRequest request, Model model, String keyword) throws Exception {
		List<BoardDTO> list = null;
		if (keyword == null || keyword == "") {
			list = boardDAO.getBoardList();
		} else {
			list = boardDAO.getBoardList(keyword);
		}

		for (BoardDTO d : list) {
			d.setPhoto_files(request);
		}
		model.addAttribute("BoardList", list);
	}

	@Override
	public Map<String, Object> getBoardAddList(HttpSession session, HttpServletRequest request, int cm_no,
			String keyword) throws Exception {
		Map<String, Object> boardTotal = new HashMap<String, Object>();
		Map<Integer, List<MentionDTO>> mapList2 = new HashMap<Integer, List<MentionDTO>>();

		UserDTO dto = (UserDTO) session.getAttribute("LoginUser");

		System.out.println("dto >>> " + dto);

		List<BoardDTO> list = null;
		if (keyword == null || keyword == "") {
			list = boardDAO.getBoardList(cm_no);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", keyword);
			map.put("cm_no", cm_no);
			list = boardDAO.getBoardList(map);
		}

		for (BoardDTO d : list) {
			d.setPhoto_files(request);

			List<MentionDTO> list2 = mentionDAO.getMentionList(d.getCm_no());
			mapList2.put(d.getCm_no(), list2);
		}

		boardTotal.put("BoardList", list);
		boardTotal.put("MentionList", mapList2);
		boardTotal.put("LoginUser", (Object) dto);

		return boardTotal;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int writeBoard(BoardDTO boardDTO, MultipartFile[] files, Model model, String[] category, HttpSession session,
			HttpServletRequest request) throws Exception {

		UserDTO user = (UserDTO) session.getAttribute("LoginUser");

		String hashtag = boardDTO.getHashtag() + "#" + String.join("#", category);

		boardDTO.setHashtag(hashtag);
		boardDTO.setUser_no(user.getUser_no());

		if (files != null) {
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
}