package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	
	public static Connection getConnection()  {
		Connection con=null;
		//1. MySql database class 로드한다.
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//2. 주소, 아이디, 비밀번호를 통해서 접속요청한다. 
			con=DriverManager.getConnection("jdbc:mysql://localhost/admincostdb", "root","123456");
			AdminCostController.callAlert("연결 성공 : 데이타베이스 연결 성공했어요.");
		} catch (Exception e) {
			AdminCostController.callAlert("연결 실패 : 데이타베이스 연결 문제있어요. 점검바람.");
			return null;
		}
		return con;
	}

}

