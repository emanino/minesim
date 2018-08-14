package uk.ac.soton.em4e15.maven.web;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import uk.ac.soton.em4e15.maven.minesim.Mine;
import uk.ac.soton.em4e15.maven.minesim.MineUtil;
import uk.ac.soton.em4e15.maven.minesim.useractions.FullEvacuateUserAction;
import uk.ac.soton.em4e15.maven.minesim.useractions.PartialEvacuateUserAction;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

/**
 * Servlet implementation class GetMineUpdatedServlet
 */
@WebServlet("/GetLoadServlet")
public class GetLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLoadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String jsonData = request.getParameter("jsonData").trim();
		Properties prop = new Properties();
		prop.load(getServletContext().getResourceAsStream("/WEB-INF/minesim.properties"));
		String json = MineUtil.loadAsJson(jsonData,prop).toJsonGui();
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
