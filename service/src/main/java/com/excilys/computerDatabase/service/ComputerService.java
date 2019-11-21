package com.excilys.computerDatabase.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.dao.ComputerDao;
import com.excilys.computerDatabase.dto.ComputerDto;
import com.excilys.computerDatabase.entity.Computer;
import com.excilys.computerDatabase.exception.BadEntriException;
import com.excilys.computerDatabase.mapper.ComputerMapper;

@Service
public class ComputerService {

	@Autowired
	private ComputerDao dao;
	@Autowired
	ComputerMapper computerMapper;

	public List<ComputerDto> getPage(int limit, int offset) {
		List<Computer> listComputer = dao.findPage(limit, offset);
		return listComputer.stream().map(computer -> computerMapper.computerToDto(computer)).collect(Collectors.toList());
		
	}

	public ComputerDto getOne(int id) {
		Computer computer = dao.findOneById(id);
		return computerMapper.computerToDto(computer);
	}

	public boolean addCompeuter(ComputerDto computerDto) throws BadEntriException {
		return dao.inserComputer(computerMapper.dtoToComputer(computerDto));
	}

	public boolean updateComputer(ComputerDto computerDto) throws BadEntriException {
		return dao.updateComputer(computerMapper.dtoToComputer(computerDto));
	}

	public boolean deleteComputer(int id) {
		return dao.deleteComputer(id);
	}

	public List<ComputerDto> getPage(int limit, int offset, String search, String order, boolean asc) {
		List<Computer> listComputer = dao.findPage(limit, offset);
		return listComputer.stream().map(computer -> computerMapper.computerToDto(computer)).collect(Collectors.toList());
	}

	public long count(String search) {
		return dao.countComputer(search);
	}

	public long count() {
		return dao.countComputer();
	}

}