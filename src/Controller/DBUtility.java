package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	
	public static Connection getConnection()  {
		Connection con=null;
		//1. MySql database class �ε��Ѵ�.
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//2. �ּ�, ���̵�, ��й�ȣ�� ���ؼ� ���ӿ�û�Ѵ�. 
			con=DriverManager.getConnection("jdbc:mysql://localhost/admincostdb", "root","123456");
			AdminCostController.callAlert("���� ���� : ����Ÿ���̽� ���� �����߾��.");
		} catch (Exception e) {
			AdminCostController.callAlert("���� ���� : ����Ÿ���̽� ���� �����־��. ���˹ٶ�.");
			return null;
		}
		return con;
	}

}

