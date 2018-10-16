package uk.ac.soton.em4e15.maven.web;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import uk.ac.soton.em4e15.maven.minesim.MineUtil;

/**
 * Servlet implementation class GetMineUpdatedServlet
 */
@WebServlet("/GetMineUpdatedServlet")
public class GetMineUpdatedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMineUpdatedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int mineSeed = Integer.valueOf(request.getParameter("mineSeed").trim());
		int updateSeed = Integer.valueOf(request.getParameter("updateSeed").trim());
		String jsonData = request.getParameter("jsonData").trim();
		Properties prop = new Properties();
		prop.load(getServletContext().getResourceAsStream("/WEB-INF/minesim.properties"));
		//prop.load(new FileInputStream(new File(resourceUrl.toString())));
		
		String json =  MineUtil.generateUpdatedMine(mineSeed,updateSeed,jsonData,prop).toJsonGui();
		
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
