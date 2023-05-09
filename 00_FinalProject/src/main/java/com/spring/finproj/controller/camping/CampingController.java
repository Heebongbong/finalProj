package com.spring.finproj.controller.camping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.finproj.service.camping.CampingService;

import lombok.Data;

@Controller
@Data
@RequestMapping("/camping")
public class CampingController {
	@Autowired
	private CampingService campingService;
}
