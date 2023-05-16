package com.spring.finproj.model.board;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BoardDTO {

	private int cm_no;
	private int user_no;
	private String content;
	private String date;
	private String update;
	private String hashtag;
	private int photo_length;
	
	private String photo_folder;
	private List<FileInfoDTO> realFiles;

}
