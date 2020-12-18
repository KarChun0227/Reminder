import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileUploadDao {
	
	public static void uploadFile(InputStream image, String email) {
        String sql = "UPDATE User Set Photo = ? WHERE Email = ?";

        try {
        	Connection con = DatabaseConnection.initializeDatabase();
        	PreparedStatement statement = con.prepareStatement(sql);
        	statement.setString(2, email);
        	if (image != null) {
        		statement.setBlob(1, image);
        		statement.executeUpdate();
        		System.out.print("yes");
        	}
        	
			statement.close();
			con.close();

        } catch (SQLException | ClassNotFoundException e) {
        	e.printStackTrace();
        }
	}
 }
