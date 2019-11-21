package com.excilys.computerDatabase.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.dao.CompanyDao;
import com.excilys.computerDatabase.dto.CompanyDto;
import com.excilys.computerDatabase.mapper.CompanyMapper;

@Service
public class CompanyService {

	private CompanyDao dao;
	private CompanyMapper mapper;

	private CompanyService(CompanyDao dao, CompanyMapper mapper) {
		this.dao = dao;
		this.mapper = mapper;

	}

	public List<CompanyDto> getAll() {
		return dao.findAll().stream().map(company -> mapper.companyToDto(company)).collect(Collectors.toList());
	}

	public List<CompanyDto> getPage(int limit, int offcet) {
		return dao.findPage(limit, offcet).stream().map(company -> mapper.companyToDto(company))
				.collect(Collectors.toList());
	}

	public long count() {
		return dao.countCompany();
	}

	public CompanyDto getCompany(String companyName) {
		return mapper.companyToDto(dao.findOneByName(companyName));
	}

	public void deleteCompany(int id) {
		dao.deleteCompany(id);
	}
}
