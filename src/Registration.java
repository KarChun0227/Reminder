import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist; 

/**
 * Servlet implementation class Registration
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/Registration.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = getServletContext( );
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String salt = null;
		String hash = null;
		
		if(name == null || email == null || password == null ) {
			request.setAttribute("errorMessage", "Null field!");
			request.getRequestDispatcher("/WEB-INF/Registration.jsp").forward(
					request, response);
			return;
		}else {
			name = Jsoup.clean(name, Whitelist.basic());
			email = Jsoup.clean(email, Whitelist.basic());
			password = Jsoup.clean(password, Whitelist.basic());
		}
		
		try {
			salt = SecureUtils.getSalt();
			hash = SecureUtils.HashProcess(password, salt);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			context.log("Hash Error: " + e1);
			e1.printStackTrace();
		}
		try {
			Connection con = DatabaseConnection.initializeDatabase();
			String sql = "select Email from User where Email = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			
			if(!rs.next()) {
				sql = "insert into User (UserName, Email, UserHash, UserSalt) values(?, ?, ?, ?)";
				statement.clearParameters();
				statement = con.prepareStatement(sql);
				statement.setString(1, name);
				statement.setString(2, email);
				statement.setString(3, hash);
				statement.setString(4, salt);
				statement.executeUpdate();
				statement.close();
				con.close();
				
				request.setAttribute("errorMessage", "Registered!!");
				request.getRequestDispatcher("/WEB-INF/Registration.jsp").forward(
						request, response);
			}else {
				request.setAttribute("errorMessage", "Email been Used!!");
				request.getRequestDispatcher("/WEB-INF/Registration.jsp").forward(
						request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.log("Exceprion Error: " + e);
		} 	
	}


}
