package com.excilys.computerDatabase.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerDatabase.dto.CompanyDto;
import com.excilys.computerDatabase.dto.ComputerDto;
import com.excilys.computerDatabase.exception.BadEntriException;
import com.excilys.computerDatabase.mapper.ComputerMapper;
import com.excilys.computerDatabase.page.Page;
import com.excilys.computerDatabase.service.CompanyService;
import com.excilys.computerDatabase.service.ComputerService;

@Controller
@RequestMapping
public class AddController {
	@Autowired
	ComputerService computerService;
	@Autowired
	CompanyService companyService;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	Page page;
	@Autowired
	DachboardController dachboard;

	@GetMapping("/addComputer")
	public ModelAndView getAddComputer() throws ServletException, IOException {
		ModelAndView mv = new ModelAndView();
		mv.getModelMap().put("companys",
				companyService.getAll().stream().collect(Collectors.toMap(CompanyDto::getId, CompanyDto::getName)));
		mv.setViewName("addComputer");
		mv.getModelMap().put("computerDto", new ComputerDto());
		return mv;
	}

	@PostMapping("/addComputer")
	public ModelAndView addComputer(@ModelAttribute("computerDto") ComputerDto computerDto)
			throws ServletException, IOException {
		System.out.println("post");
		System.out.println(computerDto);
		ModelAndView mv;
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<String> success = new ArrayList<String>();
		
		try {
			if(!computerService.updateComputer(computerDto)) {
				errors.add("add fail");
			}
		} catch (BadEntriException e) {
			errors.add(e.getMessage());
			errors.add("add fail");
		}
		
		if (errors.isEmpty()) {
			success.add("the computer " + computerDto.getName() + " has been well add");
			mv = dachboard.dashboard(null, null);
			mv.getModelMap().put("listSuccess", success);
			errors = null;
			page.updateNbComputer();
		} else {
			mv = new ModelAndView("addComputer");
			mv.getModelMap().put("computerDto", computerDto);
			mv.getModelMap().put("companys",
					companyService.getAll().stream().collect(Collectors.toMap(CompanyDto::getId, CompanyDto::getName)));
			mv.getModelMap().put("listErrors", errors);
		}

		return mv;
	}
}
