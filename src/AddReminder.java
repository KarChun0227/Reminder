

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


/**
 * Servlet implementation class AddReminder
 */
@WebServlet("/AddReminder")
public class AddReminder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ReminderService Reminders = new ReminderService();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddReminder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/AddReminder.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession userSession = request.getSession(false);

		String userEmail = (String) userSession.getAttribute("UserEmail");
		
		if (userEmail == null) {
			response.sendRedirect("/Riminder/Login");
			return;
		}
		
		if(SecureUtils.sessionValidationBySessionCookie(request)) {
			String clientHiddenToken = request.getParameter("hiddenTokenField");
			String serverSyncToken = SyncTokenStore.getRelevantToken(SecureUtils.getSessionIdFromSessionCookie(request));
	
			if (serverSyncToken.equals(clientHiddenToken)) {
				String newTodo = Jsoup.clean(request.getParameter("reminder"), Whitelist.simpleText());
				String category = Jsoup.clean(request.getParameter("category"), Whitelist.simpleText());
				Reminders.addReminder(new Reminder(newTodo, category), userEmail);
				response.sendRedirect("/Riminder/ListReminder");
			} else {
				response.sendRedirect("/Riminder/ListReminder");
			}
		} else {
			response.sendRedirect("/Riminder/ListReminder");
		}
		
		
	}
}
