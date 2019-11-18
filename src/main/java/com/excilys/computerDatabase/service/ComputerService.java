package com.excilys.computerDatabase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.dao.ComputerDao;
import com.excilys.computerDatabase.entity.Computer;

@Service
public class ComputerService {

	@Autowired
	private ComputerDao dao;

	public List<Computer> getPage(int limit, int offset) {
		List<Computer> listComputer = dao.findPage(limit, offset);
		return listComputer;
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

	public List<Computer> getPage(int limit, int offset, String search, String order, boolean asc) {
		List<Computer> listComputer = dao.findPage(limit, offset, search, order, asc);
		return listComputer;
	}

	public long count(String search) {
		return dao.countComputer(search);
	}

	public long count() {
		return dao.countComputer();
	}

}