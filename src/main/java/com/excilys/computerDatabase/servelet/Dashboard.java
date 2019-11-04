package com.excilys.computerDatabase.servelet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerDatabase.dto.ComputerDto;
import com.excilys.computerDatabase.entity.Computer;
import com.excilys.computerDatabase.mapper.ComputerMapper;
import com.excilys.computerDatabase.service.ComputerService;

/**
 * Servlet implementation class Menu
 */
@WebServlet("/dashboard")
@Controller
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int limit = 10;
	private static int actualPage = 1;
	String search="";
	String order = "computer.name";
	Boolean asc = true;
	@Autowired
	ComputerService computerService;
	@Autowired
	ComputerMapper computerMapper;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
          config.getServletContext());
     }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Computer> computers = new ArrayList<Computer>();
		int nbComputer = computerService.count(search);
		int newPage = actualPage;
		int nbPage = (nbComputer + limit - 1) / limit;
		String newPageString = request.getParameter("page");
		String limitString = request.getParameter("limit");
		String newSearch = request.getParameter("search");
		String newOrder = request.getParameter("order");
		
		if (newSearch != null) {
			search = newSearch;
		}
		
		if (newOrder != null) {
			order = newOrder;
			asc = !asc;
		}
		
		if(newPageString != null) {
			try {
				newPage = Integer.parseInt(newPageString);
			} catch (NumberFormatException e) {
				this.getServletContext().getRequestDispatcher( "/views/500.jsp" ).forward( request, response );
				return;
			}
		}
		
		if(limitString != null ) {
			try {
				limit = Integer.parseInt(limitString);
			} catch(NumberFormatException e) {
				this.getServletContext().getRequestDispatcher( "/views/500.jsp" ).forward( request, response );
				return;
			}
		}
		

		nbComputer = computerService.count(search);
		nbPage = (nbComputer + limit - 1) / limit;
		
		if(newPage < 1 ) {
			newPage = 1;
		}
		if(newPage > nbPage) {
			newPage = nbPage;
		}
		actualPage = newPage;
		int offset = (actualPage - 1) * limit;
		
		//computers = computerService.getPage(limit, offset,search, order, asc);
		computers = computerService.getPage(limit, offset,search, order, asc);
		ArrayList<ComputerDto> computersDto = new ArrayList<ComputerDto>();
		for(Computer computer : computers){
			computersDto.add(computerMapper.computerToDto(computer));
		}
		
		request.setAttribute("computers", computersDto );
		request.setAttribute("nbPage", nbPage);
		request.setAttribute("page", actualPage);
		request.setAttribute("nbComputer", nbComputer);
		this.getServletContext().getRequestDispatcher( "/views/dashboard.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] ids = request.getParameter("selection").split(",");
		int id = 0;
		for(String idString : ids) {
			try {
				id = Integer.parseInt(idString);
				
			} catch (NumberFormatException e) {
				
			}
			computerService.deleteComputer(id);
		}
		doGet(request, response);
	}

}
