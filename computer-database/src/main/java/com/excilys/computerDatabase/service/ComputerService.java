package com.excilys.computerDatabase.service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.dao.ComputerDao;
import com.excilys.computerDatabase.entity.Computer;

@Service
public class ComputerService {

	@Autowired
	private ComputerDao dao;

	public ComputerService() {

	}

	public ArrayList<Computer> getAll() {
		ArrayList<Computer> listComputer = dao.findAll();
		return listComputer;
	}

	public ArrayList<Computer> getPage(int limit, int offset, String order) {
		ArrayList<Computer> listComputer = dao.findPage(limit, offset, order);
		return listComputer;
	}

	public int count() {
		return dao.countComputer();
	}

	public Computer getOne(int id) {
		Computer computer = dao.findOneById(id);

		return computer;
	}

	public boolean addCompeuter(Computer computer) {
		return dao.inserComputer(computer);
	}

	public boolean updateComputer(Computer computer) {
		return dao.updateComputer(computer);
	}

	public boolean deleteComputer(int id) {
		return dao.deleteComputer(id);
	}

	public ArrayList<Computer> getPage(int limit, int offset, String search, String order, boolean asc) {
		ArrayList<Computer> listComputer = dao.findPage(limit, offset,search, order, asc);
		return listComputer;
	}

	public int count(String search) {
		return dao.countComputer(search);
	}

}