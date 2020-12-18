

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeleteReminder
 */
@WebServlet("/DeleteReminder")
public class DeleteReminder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ReminderService ReminderService = new ReminderService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReminder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession userSession = request.getSession(false);

		String userEmail = (String) userSession.getAttribute("UserEmail");
		
		ReminderService.deleteTodo(new Reminder(request.getParameter("todo"), request.getParameter("category")),userEmail);
		response.sendRedirect("/Riminder/ListReminder");
	}
}
