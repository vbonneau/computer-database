package com.excilys.computerDatabase.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerDatabase.exception.BadEntriException;
import com.excilys.computerDatabase.page.Page;
import com.excilys.computerDatabase.service.ComputerService;

@Controller
@RequestMapping
public class DachboardController {
	@Autowired
	Page page;
	@Autowired
	private ComputerService computerService;

	@GetMapping("/dashboard")
	public ModelAndView dashboard(@RequestParam(value = "param", required = false) String param,
			@RequestParam(value = "value", required = false) String value) {

		ModelAndView mv = new ModelAndView();
		System.out.println("dachboard");

		try {
			page.update(param, value);
		} catch (BadEntriException e) {
			mv.getModel().put("error", new ArrayList<String>().add(e.getMessage()));
		}

		mv.getModel().putAll(page.getInfo());
		mv.setViewName("dashboard");
		return mv;
	}

	@PostMapping("/delete")
	public ModelAndView deleteListId(@RequestParam(value = "selection", required = false) String selection) {
		String[] ids = selection.split(",");
		List<String> errors = new ArrayList<String>();
		List<String> success = new ArrayList<String>();
		for (String idString : ids) {
			delete(idString, errors, success);
		}
		page.updateNbComputer();
		ModelAndView mv = dashboard(null, null);
		mv.getModelMap().put("listError", errors);
		mv.getModelMap().put("listSuccess", success);
		return mv;
	}

	private void delete(String id, List<String> errors, List<String> success) {
		try {
			if (computerService.deleteComputer(Integer.parseInt(id))) {
				success.add("error : the computer with id \"" + id + "\" has been deleted");
			} else {
				errors.add("the computer with id \"" + id + "\" has not been deleted");
			}
		} catch (NumberFormatException e) {
			errors.add("errror the list contain : " + id + ". He connot be an id because it's not an integer");
		}
	}

	@GetMapping("/")
	public ModelAndView error() {

		ModelAndView mv = new ModelAndView("404");
		return mv;
	}
}
