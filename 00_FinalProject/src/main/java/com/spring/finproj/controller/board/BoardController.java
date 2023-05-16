package com.spring.finproj.controller.board;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.model.board.FileInfoDTO;
import com.spring.finproj.model.user.UserDTO;
import com.spring.finproj.service.board.BoardService;


@Controller
@RequestMapping("/board")
public class BoardController {
 
    @Autowired
    private BoardService boardService;
 
    @RequestMapping(value = "/board_list", method = RequestMethod.GET)
    public String write() {
        return "guestbook/write";
    }
 
    @RequestMapping(value = "/board_list", method = RequestMethod.POST)
    public String write(BoardDTO BoardDTO, @RequestParam("upfile") MultipartFile[] files, Model model, HttpSession session) throws IllegalStateException, IOException {
        UserDTO user_no = (UserDTO) session.getAttribute("userinfo");
        if (user_no != null) {
            // String realPath = ServletContext.getRealPath("/resources/upload");
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
                boardService.writeArticle(BoardDTO);
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
