package br.com.mailtoDesenvolper.myBank.Servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
   
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
	
		try {
		    response.sendRedirect("index.jsp");
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

	}

}
