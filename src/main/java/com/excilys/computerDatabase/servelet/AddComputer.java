package com.excilys.computerDatabase.servelet;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
@Controller
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<String> errors;
	@Autowired
	ComputerService computerService;
	@Autowired
	CompanyService companyService;
	@Autowired
	ComputerMapper computerMapper;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
          config.getServletContext());
     }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Company> companys = new ArrayList<Company>();
		companys = companyService.getAll();
		request.setAttribute("companys",companys);
		this.getServletContext().getRequestDispatcher( "/views/addComputer.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		errors = new ArrayList<String>();
		System.out.println(request.getParameter("id"));
		int companyId =0;
		
		try {
			companyId = Integer.parseInt(request.getParameter("companyId"));
		} catch(NumberFormatException e) {
			errors.add("the company id must be a integer");
		}
		
		ComputerDto dto = new ComputerDto.ComputerDtoBuilder().withName(request.getParameter("computerName"))
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
		if(errors.size() == 0 && computerService.addCompeuter(computer)) {
			request.setAttribute("success","the computer has been well add");
			errors = null;
		} else {
			errors.add("add fail");
			request.setAttribute("computer", computer);
			request.setAttribute("errors", errors);
		}
		
		doGet(request,response);
	}
}
