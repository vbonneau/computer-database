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
import com.excilys.computerDatabase.entity.Company;
import com.excilys.computerDatabase.entity.Computer;
import com.excilys.computerDatabase.exception.BadEntriException;
import com.excilys.computerDatabase.mapper.ComputerMapper;
import com.excilys.computerDatabase.service.CompanyService;
import com.excilys.computerDatabase.service.ComputerService;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
@Controller
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
     @Autowired
     ComputerService computerService;
     @Autowired
     CompanyService companyService;
     @Autowired
     ComputerMapper computerMapper;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
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
		List<Company> companys = new ArrayList<Company>();
		ArrayList<String> errors = new ArrayList<String>();
		Computer computer;
		String idString = request.getParameter("id");
		int id = 0;
		try {
			id = Integer.parseInt(idString);
		} catch (NumberFormatException e) {
			errors.add("the computer id must be a integer");
		}
		computer = computerService.getOne(id);
		if(computer.getId() == 0) {
			errors.add("computer not find");
		}
		
		companys = companyService.getAll();
		request.setAttribute("companys",companys);
		request.setAttribute("computer",computer);
		this.getServletContext().getRequestDispatcher( "/views/editComputer.jsp" ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> errors = new ArrayList<String>();
		
		int companyId =0;
		int id=0;
		
		try {
			companyId = Integer.parseInt(request.getParameter("companyId"));
		} catch(NumberFormatException e) {
			errors.add("the company id must be a integer");
		}
		
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			errors.add("the computer id must be a integer");
		}
		
		ComputerDto dto = new ComputerDto.ComputerDtoBuilder().withName(request.getParameter("computerName"))
				.withId(id)
				.withIntroduced(request.getParameter("introduced"))
				.withDiscontinued(request.getParameter("discontinued"))
				.withCompanyId(companyId)
				.build();
		Computer computer;
		try {
			computer = computerMapper.dtoToComputer(dto);
		} catch (BadEntriException e) {
			errors.add(e.getMessage());
			computer = new Computer.ComputerBuilder().build();
		}
		if(errors.size() == 0 && computerService.updateComputer(computer)) {
			request.setAttribute("success","the computer has been well update");
			errors = null;
		} else {
			errors.add("add fail");
			request.setAttribute("errors", errors);
		}
		request.setAttribute("computer", computer);
		doGet(request, response);
	}

}
