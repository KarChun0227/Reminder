

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class SyncTokenProvider
 */
@WebServlet("/SyncTokenProvider")
public class SyncTokenProvider extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SyncTokenProvider() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession userSession = request.getSession(false);
		String sessionUserEmail = (String) userSession.getAttribute("UserEmail");
		
		if (null == sessionUserEmail || userSession == null) {
			response.getWriter().append("Session Validation using Session Cookie Failed!");
			return;
		}
		
		if (userSession != null) {
			if(SecureUtils.sessionValidationBySessionCookie(request)) {
				response.setStatus(200);
	
				Map<String, String> returnMap = new HashMap<String, String>();
				returnMap.put("syncToken", SyncTokenStore.getRelevantToken(userSession.getId()));
				String json = new Gson().toJson(returnMap);
				response.setContentType("application/json");
				response.getWriter().write(json);
			} else {
				response.getWriter().append("Session Validation using Session Cookie Failed!");
			}
	    }
	}

}
