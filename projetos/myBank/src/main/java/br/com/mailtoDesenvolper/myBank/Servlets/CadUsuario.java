package br.com.mailtoDesenvolper.myBank.Servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.mailtoDesenvolper.myBank.model.usuario.DadosCadastroUsuario;
import br.com.mailtoDesenvolper.myBank.model.usuario.UsuarioService;

@WebServlet("/cadUsuario")
public class CadUsuario extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public static UsuarioService service = new UsuarioService();
       
  
   

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
	
		try {
			DadosCadastroUsuario dados = new DadosCadastroUsuario(request.getParameter("nome"), request.getParameter("numero")); 
		    service.salvarDados(dados);
			response.sendRedirect("index.jsp");
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

	}

}