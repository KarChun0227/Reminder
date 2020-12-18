

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession userSession = request.getSession(true);
		String userSessionEmail = (String) userSession.getAttribute("UserEmail");
		if(userSessionEmail != null){
			response.sendRedirect("/Riminder/ListReminder");
			return;
		}
		request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = getServletContext( );
		
		HttpSession userSession = request.getSession(false);
		if(userSession.getAttribute("LoginTime") == null){
			userSession.setAttribute("LoginTime", 0);
		}
		userSession.setAttribute("LoginTime", (int) userSession.getAttribute("LoginTime"));
		
		String email = request.getParameter("name");
		String password = request.getParameter("password");
		String hash = null;
		String salt = null;
		
		if(email == null || password == null ) {
			request.setAttribute("errorMessage", "Null field!");
			request.getRequestDispatcher("/WEB-INF/Login.js").forward(
					request, response);
			return;
		}else {
			email = Jsoup.clean(email, Whitelist.basic());
			password = Jsoup.clean(password, Whitelist.basic());
		}
		
		
		try {
			String sql = "SELECT UserHash, UserSalt FROM User WHERE Email = ?";
			Connection con = DatabaseConnection.initializeDatabase();
			PreparedStatement statement = con .prepareStatement(sql);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
	            hash = rs.getString("UserHash");
	            salt = rs.getString("UserSalt");
	         }
			statement.close();
			con.close();
		
		if(salt == null || hash == null) {
			request.setAttribute("errorMessage", "Invalid Credentials!");
			request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(
					request, response);
			return;
		}
		
		if (SecureUtils.UserValidation(password, salt, hash)) {
			
			userSession.setAttribute("UserEmail", email);	
			userSession.setMaxInactiveInterval(1800);
			
			String userSessionID = userSession.getId();
			
			String xsrfSyncToken = SecureUtils.generateSyncToken(userSessionID);
			SyncTokenStore.addToTokenStore(userSessionID, xsrfSyncToken);
			
			response.addCookie(SecureUtils.SESSION_ID_COOKIE.apply(userSession));
			
			response.sendRedirect("/Riminder/ListReminder");
			
		} else {
			if((int) userSession.getAttribute("LoginTime") > 3) {
				request.setAttribute("errorMessage", "You login Too Many Times!");
				context.log("This user fail to login over three times: " + email);
				request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(
						request, response);
				return;
			}
			userSession.setAttribute("LoginTime", (int) userSession.getAttribute("LoginTime") + 1);
			request.setAttribute("errorMessage", "Invalid Credentials!");
			request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(
					request, response);
		}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.log("Exception: " + e);
		}
	}

}
