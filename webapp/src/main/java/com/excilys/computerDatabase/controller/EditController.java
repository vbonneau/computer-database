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

import com.excilys.computerDatabase.dto.CompanyDto;
import com.excilys.computerDatabase.dto.ComputerDto;
import com.excilys.computerDatabase.exception.BadEntriException;
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
	Page page;
	@Autowired
	DachboardController dachboard;

	@GetMapping("/editComputer")
	public ModelAndView getEditComputer(@RequestParam(value = "id") Integer id) {
		ModelAndView mv = new ModelAndView();

		mv.getModelMap().put("companys",
				companyService.getAll().stream().collect(Collectors.toMap(CompanyDto::getId, CompanyDto::getName)));
		mv.getModelMap().put("computerDto", computerService.getOne(id));

		mv.setViewName("editComputer");
		return mv;
	}

	@PostMapping("/editComputer")
	public ModelAndView editComputer(@ModelAttribute("computerDto") ComputerDto computerDto) {
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
			mv = getEditComputer(computerDto.getId());
			mv.getModelMap().put("listSuccess", success);
			errors = null;
		} else {
			mv = new ModelAndView("editComputer");
			mv.getModel().put("computer", computerDto);
			mv.getModelMap().put("companys",
					companyService.getAll().stream().collect(Collectors.toMap(CompanyDto::getId,
					CompanyDto::getName)));
			mv.getModelMap().put("listErrors", errors);
		}

		return mv;
	}
}
