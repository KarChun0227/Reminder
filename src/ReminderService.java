import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReminderService {
	
	public List<Reminder> retrieveReminders(String email) {
		List<Reminder> Reminders = new ArrayList<Reminder>();
		
		try {
			String reminder = null;
			String catogory = null;
			
			String sql = "SELECT Reminder, Catagory FROM ReminderData WHERE Email = ?";
			Connection con = DatabaseConnection.initializeDatabase();
			PreparedStatement statement = con .prepareStatement(sql);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				reminder = rs.getString("Reminder");
				catogory = rs.getString("Catagory");
				Reminders.add(new Reminder(reminder,catogory));
			}
			statement.close();
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Reminders;
	}

	public void addReminder(Reminder Reminder, String email) {
		String reminder = Reminder.getName();
		String catogory = Reminder.getCategory();
		
		try {
			String sql = "insert into ReminderData (Email, Reminder, Catagory) values(?, ?, ?)";
			Connection con;
			con = DatabaseConnection.initializeDatabase();
			PreparedStatement statement = con .prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, reminder);
			statement.setString(3, catogory);
			System.out.print(statement);
			statement.executeUpdate();
			statement.close();
			con.close();
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteTodo(Reminder Reminder, String email) {
		String reminder = Reminder.getName();
		String catogory = Reminder.getCategory();
		
		try {
			String sql = "delete FROM ReminderData WHERE ID = (SELECT * FROM (select ID FROM ReminderData WHERE Reminder=? AND Catagory=? AND Email=?)AS x)";
			Connection con;
			con = DatabaseConnection.initializeDatabase();
			PreparedStatement statement = con .prepareStatement(sql);			
			statement.setString(1, reminder);
			statement.setString(2, catogory);
			statement.setString(3, email);
			statement.executeUpdate();
			statement.close();
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
