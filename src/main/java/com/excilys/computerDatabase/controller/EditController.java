package com.excilys.computerDatabase.controller;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class EditController {
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
	
	@GetMapping("/editComputer")
	public ModelAndView getEditComputer(@RequestParam(value = "id") Integer id){
		ModelAndView mv = new ModelAndView();
		
		mv.getModelMap().put("companys", companyService.getAll().stream().collect(Collectors.toMap(Company::getId, Company::getName)));
		mv.getModelMap().put("computerDto", computerMapper.computerToDto(computerService.getOne(id)));
		
		mv.setViewName("editComputer");
		return mv;
	}
	
	@PostMapping("/editComputer")
	public ModelAndView editComputer(@ModelAttribute("computerDto") ComputerDto computerDto){
		System.out.println(computerDto);
		ModelAndView mv = new ModelAndView();
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<String> success = new ArrayList<String>();
		Computer computer;
		
		try {
			computer = computerMapper.dtoToComputer(computerDto);
		} catch (BadEntriException e) {
			errors.add(e.getMessage());
			computer = new Computer.ComputerBuilder().build();
		}
		System.out.println(computer);
		
		if(errors.isEmpty() && computerService.updateComputer(computer)) {
			success.add("the computer " + computer.getName() + " has been well add");
			mv.getModelMap().put("listSuccess",success);
			errors = null;
			page.updateNbComputer();
		} else {
			errors.add("add fail");
			mv.getModelMap().put("listErrors", errors);
		}
		
		mv.getModel().put("computer", computerDto);
		mv.setViewName("editComputer");
		mv.getModelMap().put("companys", companyService.getAll().stream().collect(Collectors.toMap(Company::getId, Company::getName)));
		
		return mv;
	}
}
