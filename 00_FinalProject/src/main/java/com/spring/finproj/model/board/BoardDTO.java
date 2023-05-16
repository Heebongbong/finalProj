package com.spring.finproj.model.board;

import java.io.File;
import java.util.ArrayList;

import lombok.Data;
@Data
public class BoardDTO {
	private String photo_folder;
	private ArrayList<String> realFiles;

	public void setPhoto_folder(String photo_folder) {
		this.photo_folder = photo_folder;
		File[] fl = new File(photo_folder).listFiles();
		this.realFiles = new ArrayList<String>();
		for(File f : fl) {
			this.realFiles.add(f.getName());
		}
	}
}