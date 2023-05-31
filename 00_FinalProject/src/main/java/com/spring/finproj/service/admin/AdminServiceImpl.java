package com.spring.finproj.service.admin;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.finproj.model.admin.AdminDAO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDAO AdminDAO;
	
	

	
}