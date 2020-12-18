import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GeneratePDF
 */
@WebServlet("/GeneratePDF")
public class GeneratePDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeneratePDF() {
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
		
		 if(userEmail == null) {
			 response.sendRedirect("/Riminder/Login");
			 return;
		 }
		
		ReminderService Reminders = new ReminderService();
		
		 final ServletContext servletContext = request.getSession().getServletContext();
		 final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		 final String temperotyFilePath = tempDirectory.getAbsolutePath();

		 String fileName = "Generate_Report_"+System.currentTimeMillis()+".pdf";
		 response.setContentType("application/pdf");
		 response.setHeader("Cache-Control", "no-cache");
		 response.setHeader("Cache-Control", "max-age=0");
		 response.setHeader("Content-disposition", "attachment; " + "filename="+ fileName);

		 try {

		     CreatePDF.createPDF(temperotyFilePath+"\\"+fileName, userEmail, Reminders.retrieveReminders(userEmail));
		     ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName);
		     OutputStream os = response.getOutputStream();
		     baos.writeTo(os);
		     os.flush();
		 } catch (Exception e1) {
			 e1.printStackTrace();
		 }
	}
	
	private static ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {
    InputStream inputStream = null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {

        inputStream = new FileInputStream(fileName);

        byte[] buffer = new byte[1024];
        baos = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return baos;
}

}
