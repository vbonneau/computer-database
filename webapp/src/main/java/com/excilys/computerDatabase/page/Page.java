package com.excilys.computerDatabase.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computerDatabase.dto.ComputerDto;
import com.excilys.computerDatabase.exception.BadEntriException;
import com.excilys.computerDatabase.service.ComputerService;

@Component
public class Page {

	private int limit = 10;
	private static int actualPage = 1;
	private long nbComputer = 0;
	private String search = "";
	private String order = "name";
	private boolean asc = true;
	private ComputerService computerService;
	private long nbPage;
	private int offset;

	@Autowired
	public Page(ComputerService computerService) {
		this.computerService = computerService;
		updateNbComputer();
	}

	public int getLimit() {
		return limit;
	}

	public int getActualPage() {
		return actualPage;
	}

	public void setLimitString(String limitString) throws BadEntriException {
		if (limitString != null) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				throw new BadEntriException("the limit is not an integer");
			}
			updateNbPage();
			updateOffset();
		}
	}

	private void updateNbPage() {
		if (limit == 0) {
			nbPage = 1;
		} else {
			nbPage = (nbComputer + limit - 1) / limit;
		}
	}

	public void updateNbComputer() {
		nbComputer = computerService.count(search);
		updateNbPage();
	}

	private void updateOffset() {
		offset = (actualPage - 1) * limit;
	}

	public void setSearch(String search) {
		if (search != null) {
			this.search = search;
			updateNbComputer();
		}
	}

	public void setOrder(String order) {
		if (order != null) {
			this.order = order;
			asc = !asc;
		}
	}

	public void setAsc(Boolean asc) {
		this.asc = asc;
	}

	public List<ComputerDto> getComputersDto() {
		return computerService.getPage(limit, offset, search, order, asc);
	}

	public Object getNbPage() {
		return nbPage;
	}

	public long getNbComputer() {
		return nbComputer;
	}

	public void setActualPageString(String newPageString) throws BadEntriException {
		int newPage;
		if (newPageString != null) {
			try {
				newPage = Integer.parseInt(newPageString);
			} catch (NumberFormatException e) {
				throw new BadEntriException("the page is not an integer");
			}
			checkPage(newPage);
			actualPage = newPage;
			updateOffset();
		}
	}

	private void checkPage(int newPage) throws BadEntriException {
		if (newPage < 1 || newPage > nbPage) {
			throw new BadEntriException("page not correct");
		}
	}

	public void update(String param, String value) throws BadEntriException {
		if (param != null && value != null) {
			switch (param) {
			case "limit":
				setLimitString(value);
				break;
			case "page":
				setActualPageString(value);
				break;
			case "search":
				setSearch(value);
			case "order":
				setOrder(value);
				break;
			default:
				break;
			}
		}
	}

	public Map<String, Object> getInfo() {
		Map<String, Object> map = new HashMap<>();
		map.put("computers", getComputersDto());
		map.put("nbPage", getNbPage());
		map.put("page", getActualPage());
		map.put("nbComputer", getNbComputer());
		return map;
	}

}
