package com.exilys.computerDatabase.service;


import java.util.ArrayList;
import com.exilys.computerDatabase.dao.ComputerDao;
import com.exilys.computerDatabase.entity.Computer;

public class ComputerService {
	
	private ComputerDao dao = ComputerDao.getComputerDao();
	private static ComputerService instence;
	
	private ComputerService() {
		
	}
	
	public static ComputerService getInstence() {
		if(instence == null) { instence = new ComputerService(); }
		return instence;
	}

	public ArrayList<Computer> getAll(){
		ArrayList<Computer> listComputer = dao.findAll();
		return listComputer;
	}
	
	public ArrayList<Computer> getPage(int limit,int offset) {
		ArrayList<Computer> listComputer = dao.findPage(limit,offset);
		return listComputer;
	}
	
	public int count() {
		return dao.countComputer();
	}
	
	public Computer getOne(int id) {
		Computer computer = dao.findOneById(id);
		
		return computer;
	}
	
	public void addCompeuter(Computer computer) {
		
		dao.inserComputer(computer);
	}
	
	public Computer updateComputer(Computer computer) {
		dao.updateComputer(computer);
		return computer;
	}
	
	public void deleteComputer(int id) {
		dao.delete(id);
	}
	
}
