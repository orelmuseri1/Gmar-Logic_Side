package AskingQueriesFP;

import java.awt.geom.Arc2D.Float;
import java.sql.*;

public class Main {
	
	
	public static void main(String[] args) {
		int port = 3306;
		String userName = "root";
		String password = "orelmuseri1";
		Asker asker = new Asker(port,userName,password);
		int ellerts=asker.ask();
		/*try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/FinelProjectDB", userName, password);
			Statement mystmt = myConn.createStatement();
			String query = "CREATE TABLE IF NOT EXISTS kids("
			+ "childID int NOT NULL AUTO_INCREMENT,"
			+ "firstName TEXT,"
			+ "lastName TEXT,"
			+ "isAttend TEXT,"
			+ "isPremture TEXT,"
			+ "birthDate TEXT,"
			+ "gender TEXT,"
			+ " PRIMARY KEY (childID));";
			String query = "CREATE TABLE IF NOT EXISTS events("
					+ "eventDate TEXT,"
					+ "eventType TEXT,"
					+ "kidID int,"
					+ "staff TEXT);";
			mystmt.execute(query);
			
		}catch (Exception e) {
			e.printStackTrace();
		}*/
		System.out.println(ellerts);
	}
	int connection() {
		
		return 0;
	}
	
	
	
	
	
	
	
	
}

