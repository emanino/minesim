package uk.ac.soton.em4e15.maven.web;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import uk.ac.soton.em4e15.maven.minesim.Mine;

/**
 * Servlet implementation class GetMineUpdatedServlet
 */
@WebServlet("/GetMineAfterFirstEvent")
public class GetMineAfterFirstEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMineAfterFirstEvent() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int mineSeed = Integer.valueOf(request.getParameter("mineSeed").trim());
		int updateSeed = Integer.valueOf(request.getParameter("updateSeed").trim());
		//ServletContext context = getServletContext();
		//URL resourceUrl = context.getResource("/WEB-INF/minesim.properties");
		
		Properties prop = new Properties();
		prop.load(getServletContext().getResourceAsStream("/WEB-INF/minesim.properties"));
		//prop.load(new FileInputStream(new File(resourceUrl.toString())));
		Mine mine = new Mine(prop, mineSeed, updateSeed, 0);
		mine.updateUntilFirstEvent(4);
		String json = "{\"minedraw\":"+mine.toJsonGui()+",\"minesave\":"+mine.saveAsJson()+"}";
		
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
