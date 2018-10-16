package uk.ac.soton.em4e15.maven.web;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.soton.em4e15.maven.minesim.Mine;
import uk.ac.soton.em4e15.maven.minesim.MineUtil;

@WebServlet("/GetMineSensorDataRDF")
public class GetMineSensorDataRDF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String jsonData = request.getParameter("jsonData").trim();
		Properties prop = new Properties();
		prop.load(getServletContext().getResourceAsStream("/WEB-INF/minesim.properties"));
		String rdf = MineUtil.loadAsJson(jsonData,prop).getSensorRDF();
		response.setContentType("text/turtle");
		response.getWriter().write(rdf);
	
	}

}