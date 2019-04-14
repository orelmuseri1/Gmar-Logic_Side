package AskingQueriesFP;

import java.sql.SQLException;
import java.text.ParseException;

public class Main {
	
	
	public static void main(String[] args) throws ParseException, SQLException {
		int port = 3306;
		String userName = "root";
		String password = "orelmuseri1";
		Asker asker = new Asker(port,userName,password);
		int ellerts=asker.ask();
		System.out.println(ellerts);
	}
	int connection() {
		
		return 0;
	}
	
	
	
	
	
	
	
	
}

