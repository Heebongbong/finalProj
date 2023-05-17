package com.spring.finproj.service.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDAO;
import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.model.board.FileInfoDTO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDAO;

	@Override
    @Transactional	// @Transactional은 클래스나 메서드에 붙여줄 경우, 해당 범위 내 메서드가 트랜잭션이 되도록 보장해준다.
    public void writeArticle(BoardDTO board) {
        if(board.getContent() != null) {

        }
    }

	@Override
	public void getCommunity(MultipartFile file) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public String writeBoard(BoardDTO boardDTO, MultipartFile[] files, Model model, HttpSession session, HttpServletRequest request) throws Exception {
		UserDTO user_no = (UserDTO) session.getAttribute("userinfo");
        
        if (user_no != null) {
        	Properties prop = new Properties();
            FileInputStream fis = new FileInputStream(request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
            prop.load(new InputStreamReader(fis));
            fis.close();
            
            String realPath = prop.getProperty(System.getenv("USERPROFILE").substring(3))+"\\board";
        	
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = File.separator + today; // + realPath
            System.out.println(saveFolder);
            File folder = new File(saveFolder);
            if(!folder.exists())
                folder.mkdirs();
            List<FileInfoDTO> fileInfos = new ArrayList<FileInfoDTO>();
            for (MultipartFile mfile : files) {
                FileInfoDTO fileInfoDto = new FileInfoDTO();
                String originalFileName = mfile.getOriginalFilename();
                if (!originalFileName.isEmpty()) {
                    String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
                    fileInfoDto.setSaveFolder(today);
                    fileInfoDto.setOriginFile(originalFileName);
                    fileInfoDto.setSaveFile(saveFileName);
                    System.out.println(mfile.getOriginalFilename() + "   " + saveFileName);
                    mfile.transferTo(new File(folder, saveFileName));
                }
                fileInfos.add(fileInfoDto);
            }
            // BoardDTO.setFileInfos(fileInfos);
            // BoardDTO.setUserid(memberDto.getUserid());
            try {
                //writeArticle(BoardDTO);
                return "guestbook/writesuccess";
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("msg", "글작성중 문제가 발생했습니다.");
                return "error/error";
            }
        } else {
            model.addAttribute("msg", "로그인 후 사용 가능한 페이지입니다.");
            return "error/error";
        }
	}
}