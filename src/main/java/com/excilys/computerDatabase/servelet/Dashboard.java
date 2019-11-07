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


import com.excilys.computerDatabase.exception.BadEntriException;
import com.excilys.computerDatabase.mapper.ComputerMapper;
import com.excilys.computerDatabase.page.Page;
import com.excilys.computerDatabase.service.ComputerService;

/**
 * Servlet implementation class Menu
 */
//@WebServlet("/dashboard")
//@Controller
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	ComputerService computerService;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	Page page;
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
		
		List<String> errors = new ArrayList<String>();
		
		try {
			page.setActualPageString(request.getParameter("page"));
			page.setLimitString(request.getParameter("limit"));
		} catch (BadEntriException e) {
			errors.add(e.getMessage());
			request.setAttribute("errors", errors);
		}
		page.setSearch(request.getParameter("search"));
		page.setOrder(request.getParameter("order"));
		
		request.setAttribute("computers", page.getComputers() );
		request.setAttribute("nbPage", page.getNbPage());
		request.setAttribute("page", page.getActualPage());
		request.setAttribute("nbComputer", page.getNbComputer());
		this.getServletContext().getRequestDispatcher( "/views/dashboard.jsp" ).forward( request, response );
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] ids = request.getParameter("selection").split(",");
		List<String> errors = new ArrayList<String>();
		List<String> success = new ArrayList<String>();
		int id = 0;
		for (String idString : ids) {
			try {
				id = Integer.parseInt(idString);
				
			} catch (NumberFormatException e) {
				errors.add("errror the list contain : " + idString + ". He connot be an id because it's not an integer" );
			}
			
			if (computerService.deleteComputer(id)){
				success.add("errror the computer with id \"" + idString + "\" has been deleted");
				
			} else {
				errors.add("errror the computer with id \"" + idString + "\" has not been deleted" );
			}
			
		}
		page.updateNbComputer();
		request.setAttribute("erros", errors);
		request.setAttribute("listSuccess", success);
		doGet(request, response);
	}

}
