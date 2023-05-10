package com.spring.finproj.service.camping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.finproj.model.camping.CampingDAO;

import lombok.Data;

@Data
@Service
public class CampingServiceImpl implements CampingService{
	@Autowired
	private CampingDAO campingDAO;
}
