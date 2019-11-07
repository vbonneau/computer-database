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

import com.excilys.computerDatabase.dto.ComputerDto;
import com.excilys.computerDatabase.entity.Company;
import com.excilys.computerDatabase.entity.Computer;
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
		mv.getModelMap().put("companys", companyService.getAll().stream().collect(Collectors.toMap(Company::getId, Company::getName)));
		mv.setViewName("addComputer");
		mv.getModelMap().put("computerDto", new Computer.ComputerBuilder().build());
		return mv;
	}
	
	@PostMapping("/addComputer")
	public ModelAndView addComputer(@ModelAttribute("computerDto")ComputerDto computerDto) throws ServletException, IOException {
		System.out.println("post");
		System.out.println(computerDto);
		ModelAndView mv = dachboard.dashboard(null, null);
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<String> success = new ArrayList<String>();
		Computer computer;
		
		try {
			computer = computerMapper.dtoToComputer(computerDto);
		} catch (BadEntriException e) {
			errors.add(e.getMessage());
			computer = new Computer.ComputerBuilder().build();
		}
		
		if(errors.isEmpty() && computerService.addCompeuter(computer)) {
			success.add("the computer " + computer.getName() + " has been well add");
			mv.getModelMap().put("listSuccess",success);
			errors = null;
			page.updateNbComputer();
		} else {
			errors.add("add fail");
			mv.getModelMap().put("listErrors", errors);
		}
		
		return mv;
	}
}
