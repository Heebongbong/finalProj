package com.spring.finproj.model.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class BoardDTO {
	private int cm_no;
	private int user_no;
	private String content;
	private String date;
	private String update;
	private String hashtag;
	private String photo_folder;

	private List<String> photo_files;
	
	public void setPhoto_files(HttpServletRequest request) throws Exception {
		this.photo_files = new ArrayList<String>();
		
		if(this.photo_folder!=null) {
			System.out.println(this.photo_folder);
			
			Properties prop = new Properties();
			@SuppressWarnings("deprecation")
			FileInputStream fis = new FileInputStream(request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
			prop.load(new InputStreamReader(fis));
			fis.close();
			
			String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3))+"\\board\\"+this.photo_folder;
			
			File file = new File(saveFolder);
			File[] files = file.listFiles();
			for(File f : files) {
				this.photo_files.add(f.getName());
			}
		}
	}
}