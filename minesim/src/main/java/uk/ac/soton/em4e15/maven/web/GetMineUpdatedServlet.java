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
import uk.ac.soton.em4e15.maven.minesim.useractions.FullEvacuateUserAction;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

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
		JsonReader jsonReader = Json.createReader(new StringReader(jsonData));
		JsonObject jobj = jsonReader.readObject();

		
		Properties prop = new Properties();
		prop.load(getServletContext().getResourceAsStream("/WEB-INF/minesim.properties"));
		//prop.load(new FileInputStream(new File(resourceUrl.toString())));
		Mine mine = new Mine(prop, mineSeed, updateSeed, 0);
		
		int i = 1;
		JsonObject jobjcurrent = jobj.getJsonObject(""+i);
		while(jobjcurrent != null) {			
			int code = Integer.parseInt(jobjcurrent.getString("code"));
			int times = Integer.parseInt(jobjcurrent.getString("times"));
			String params = jobjcurrent.getString("params");
			for(int j = 0; j < times; j++) {
				if(code == 0) {
					// code 0 = Business as Usual
					mine.update(new HashSet<UserAction>());
				} else if (code == 1) {
					HashSet<UserAction> actions = new HashSet<UserAction>();
					actions.add(new FullEvacuateUserAction());
					mine.update(actions);
				}
			}
			i++;
			jobjcurrent = jobj.getJsonObject(""+i);
		}
		
		String json = mine.toJsonGui();
		
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

}
