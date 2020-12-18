

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.mysql.cj.jdbc.Blob;

/**
 * Servlet implementation class AddImage
 */
@WebServlet("/AddImage")
@MultipartConfig(maxFileSize = 16177215)
public class AddImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddImage() {
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
			
			String sql = "SELECT Photo FROM User WHERE Email = ?";
			
			Blob imageData = null;
			
			try {
				Connection con;
				con = DatabaseConnection.initializeDatabase();
				PreparedStatement statement = con .prepareStatement(sql);
				statement.setString(1, userEmail);
				ResultSet rs = statement.executeQuery();
				System.out.print(rs == null);
				while(rs.next()){
					imageData = (Blob) rs.getBlob("Photo");
				}
				statement.close();
				con.close();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				InputStream inputStream = imageData.getBinaryStream();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	            byte[] buffer = new byte[4096];
	            int bytesRead = -1;
	             
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);                  
	            }
	             
	            byte[] imageBytes = outputStream.toByteArray();
	            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
	            request.setAttribute("pic", base64Image);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			 request.getRequestDispatcher("/WEB-INF/AddProfile.jsp").forward(request, response);
			
		}else {
		response.sendRedirect("/Riminder/Login");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userEmail = null;
		
		HttpSession userSession = request.getSession(false);
		
		if(userSession != null) {
			userEmail = (String) userSession.getAttribute("UserEmail");
		}
		
		InputStream inputStream = null;
		
		Part filePart = request.getPart("Image");
        if (filePart != null) {
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
            
            if(filePart.getContentType().equals("image/jpeg") || filePart.getContentType().equals("image/png")) {
            	inputStream = filePart.getInputStream();
                FileUploadDao.uploadFile(inputStream, userEmail);
            }else {
            	request.setAttribute("errorMessage", "Only Allow PNG or JPEG!!!");
    			request.getRequestDispatcher("/WEB-INF/AddProfile.jsp").forward(
    					request, response);
    			return;
            }
        }
        response.sendRedirect("/Riminder/ListReminder");
	}

}
