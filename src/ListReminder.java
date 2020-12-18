

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ListReminder
 */
@WebServlet("/ListReminder")
public class ListReminder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ReminderService Reminders = new ReminderService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListReminder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession userSession = request.getSession(false);
		
		if(userSession != null) {
			String userEmail = (String) userSession.getAttribute("UserEmail");
			
			request.setAttribute("Reminders", Reminders.retrieveReminders(userEmail));
			
			request.getRequestDispatcher("/WEB-INF/ListReminder.jsp").forward(
					request, response);
			
			return;
		}
		response.sendRedirect("/Riminder/Login");
	}
}
