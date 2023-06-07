package com.spring.finproj.service.camping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.board.BoardDAO;
import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.model.board.MentionDAO;
import com.spring.finproj.model.board.MentionDTO;
import com.spring.finproj.model.camping.CampingDAO;
import com.spring.finproj.model.camping.CampingDTO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class CampingServiceImpl implements CampingService {
	@Autowired
	private CampingDAO campingDAO;
	@Autowired
	private MentionDAO mentionDAO;
	@Autowired
	private BoardDAO boardDAO;

	@Override
	public void updateCampingListSetDB() throws IOException {

		// db삭제
		campingDAO.deleteCampingList();

		// db추가
		for (int j = 1; j <= 350; j++) {
			String curl = "https://apis.data.go.kr/B551011/GoCamping/basedList?numOfRows=10&pageNo=" + j
					+ "&MobileOS=WIN&MobileApp=Camping&serviceKey=phamApqtKDIobE2PYsYGQbaOjZ1ubeYuzGHHRypOTUlsk%2FvIKv7BlDfoboSoBl%2BSgdrQXDuV13Xr3a4InxJjdA%3D%3D&_type=json";

			// 1. 장치에 요청할 URI를 입력한다.
			URL url = new URL(curl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestProperty("Content-Type", "application/json");

			// 2. Method 타입을 정의하고 API를 전송한다.
			con.setRequestMethod("GET");

			con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			ArrayList<CampingDTO> list = new ArrayList<CampingDTO>();

			JSONObject jo = new JSONObject(response.toString());
			JSONArray jo2 = jo.getJSONObject("response").getJSONObject("body").getJSONObject("items")
					.getJSONArray("item");

			for (int i = 0; i < jo2.length(); i++) {
				CampingDTO dto = new CampingDTO();
				JSONObject cont = jo2.getJSONObject(i);

				dto.setContent_id(cont.getInt("content_id"));
				dto.setFacltNm(cont.getString("facltNm"));
				dto.setLineIntro(cont.getString("lineIntro"));
				dto.setIntro(cont.getString("intro"));
				dto.setAllar(cont.getInt("allar"));
				dto.setFeatureNm(cont.getString("featureNm"));
				dto.setInduty(cont.getString("induty"));
				dto.setLctCl(cont.getString("lctCl"));
				dto.setAddr1(cont.getString("addr1"));
				dto.setAddr2(cont.getString("addr2"));
				dto.setMapX(cont.getString("mapX"));
				dto.setMapY(cont.getString("mapY"));
				dto.setTooltip(cont.getString("tooltip"));
				dto.setTel(cont.getString("tel"));
				dto.setHomepage(cont.getString("homepage"));
				dto.setOperPdCl(cont.getString("operPdCl"));
				dto.setOperDeCl(cont.getString("operDeCl"));
				dto.setPosblFcltyCl(cont.getString("posblFcltyCl"));
				dto.setExprnProgrm(cont.getString("exprnProgrm"));
				dto.setThemaEnvrnCl(cont.getString("themaEnvrnCl"));
				if (cont.getString("animalCmgCl").equals("불가능")) {
					dto.setAnimalCmgCl(0);
				} else {
					dto.setAnimalCmgCl(1);
				}
				dto.setFirstImageUrl(cont.getString("firstImageUrl"));

				list.add(dto);
			}

			int re = campingDAO.insertCampingList(list);

			System.out.println(j + " 번째 리스트 작업 [" + re + "] 행 추가");

		}
	}

	@Override
	public void getCampingList(Model model, String keyword) throws IOException {
		if (keyword == null || keyword.equals("")) {
			keyword = "서울";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("loc1", keyword);
		if (keyword.equals("경상") || keyword.equals("충청") || keyword.equals("전라")) {
			map.put("loc2", keyword + "남도");
			map.put("loc3", keyword + "북도");
			map.put("loc4", keyword.charAt(0)+"북");
			map.put("loc5", keyword.charAt(0)+"남");
		}
		System.out.println(map);

		
		List<CampingDTO> list = campingDAO.getCampingLocList(map);
		model.addAttribute("CampingList", list); 
		model.addAttribute("keyword",keyword);
		 
	}

	@Override
	public void getCampingRandomList(Model model) {
		// TODO Auto-generated method stub
		List<CampingDTO> list = campingDAO.getCampingRandomList();
		model.addAttribute("CampingList", list);
	}

	@Override
	public void getCampingContent(Model model, int content_id) throws IOException {
		CampingDTO dto = campingDAO.getCampingContent(content_id);
		model.addAttribute("Content", dto);
		System.out.println(dto);
	}

	@Override
	public List<CampingDTO> getCampingAddList(int content_id, String keyword, String category) {
		List<CampingDTO> list;
		List<String> cateList = null;
<<<<<<< HEAD
		if(category!=""||category!=null) {
			cateList = new ArrayList<String>();
			if (category.equals("경상") || category.equals("충청") || category.equals("전라")) {
				cateList.add(category);
				cateList.add(category + "남도");
				cateList.add(category + "북도");
				cateList.add(category.charAt(0)+"북");
				cateList.add(category.charAt(0)+"남");
			}else {
				cateList.add(category);
			}
=======
		

		System.out.println(category);
		System.out.println(keyword);
		System.out.println(content_id);
		
		if((keyword==null || keyword=="") && (category==""||category==null)) {
			if(content_id==0) {
				System.out.println(1);
				list = campingDAO.getCampingAddList();
			}else {
				System.out.println(2);
				list = campingDAO.getCampingAddList(content_id);
			}
		}else {
			System.out.println(3);
			
			if(category!="" || category!=null) {
				cateList = new ArrayList<String>();
				
				if (category.equals("경상") || category.equals("충청") || category.equals("전라")) {
					cateList.add(category);
					cateList.add(category + "남도");
					cateList.add(category + "북도");
					cateList.add(category.charAt(0)+"북");
					cateList.add(category.charAt(0)+"남");
				}else {
					cateList.add(category);
				}
			}
			
			Map<String, Object> keyList = new HashMap<String, Object>();
			keyList.put("keyword", keyword);
			keyList.put("category", cateList);
			keyList.put("content_id", content_id);
			
			list = campingDAO.getCampingAddList(keyList);
>>>>>>> refs/remotes/origin/master
		}
		
<<<<<<< HEAD
		
		
		if(content_id==0) {
			if((keyword==null || keyword=="") && (cateList==null||cateList.size()==0)) {
				
				 list = campingDAO.getCampingAddList();
				 
			}else {
				System.out.println(cateList);
				System.out.println(keyword);
				System.out.println(1);
				Map<String, Object> keyList = new HashMap<String, Object>();
				keyList.put("keyword", keyword);
				keyList.put("category", cateList);
				
				 list = campingDAO.getCampingAddList(keyList);
				 
			}
			
		}else { // content_id 보다 큰 리스트
			if((keyword==null || keyword=="") && (cateList==null)) {
				 
				list = campingDAO.getCampingAddList(content_id);
				
			}else {
				
				Map<String, Object> keyList = new HashMap<String, Object>();
				keyList.put("keyword", keyword);
				keyList.put("category", cateList);
				keyList.put("content_id", content_id);
				
				list = campingDAO.getCampingAddList(keyList);
				 
			}
		}
		
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("content_id", ((Integer)content_id).toString());
//		map.put("loc1", keyword);
//		if (keyword.equals("경상") || keyword.equals("충청") || keyword.equals("전라")) {
//			map.put("loc2", keyword + "남도");
//			map.put("loc3", keyword + "북도");
//			map.put("loc4", keyword.charAt(0)+"북");
//			map.put("loc5", keyword.charAt(0)+"남");
//		}
//		System.out.println(map);
//		
//		List<CampingDTO> list = campingDAO.getCampingAddList(map);

=======
		System.out.println(list);
>>>>>>> refs/remotes/origin/master
		return list;
	}

	@Override
	public Map<String, Object> getCampingReviewList(int content_id, HttpServletRequest request, int cm_no)
			throws Exception {

		Map<Integer, List<MentionDTO>> m_map = new HashMap<Integer, List<MentionDTO>>();
		Map<String, Object> fin_List = new HashMap<String, Object>();
		List<BoardDTO> reviewList = null;

		if (cm_no == 0) {
			reviewList = campingDAO.getCampingReviewList(content_id);
		} else {
			Map<String, Integer> keyList = new HashMap<String, Integer>();
			keyList.put("cm_no", cm_no);
			keyList.put("content_id", content_id);
			reviewList = campingDAO.getCampingReviewList(keyList);
		}

		for (BoardDTO b : reviewList) {
			b.setPhoto_files(request);

			b.setLikeCount(boardDAO.getBoardLikeCount(b.getCm_no()));

			List<MentionDTO> m_list = mentionDAO.getMentionList(b.getCm_no());

			for (MentionDTO m : m_list) {
				m.setLikeCount(mentionDAO.getMentionLikeCount(m.getMention_no()));
			}

			m_map.put(b.getCm_no(), m_list);
		}

		HttpSession session = request.getSession();
		if (session.getAttribute("LoginUser") != null) {
			int login_user_no = ((UserDTO) session.getAttribute("LoginUser")).getUser_no();
			List<Integer> userLikeList = boardDAO.getBoardLikeList(login_user_no);

			fin_List.put("LikeList", userLikeList);

			List<Integer> mentionLikeList = mentionDAO.getMentionLikeList(login_user_no);

			fin_List.put("MentionLikeList", mentionLikeList);
		}

		fin_List.put("BoardList", reviewList);
		fin_List.put("MentionList", m_map);

		return fin_List;
	}

}