package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Employee;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class EmployeeDAO {
public static ArrayList<Employee> dbArrayList=new ArrayList<>();
	
public static int insertEmployeeData(Employee employee) {
		//1.1 ����Ÿ���̽���  �л����̺� �Է��ϴ� ������
		StringBuffer insertEmployee=new StringBuffer();
		insertEmployee.append("insert into employee ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
		insertEmployee.append("(number,name,age,department,Employmentday,position,address,Phone,image) ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
		insertEmployee.append("values ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
		insertEmployee.append("(?,?,?,?,?,?,?,?,?) ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
	
		//1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
		Connection con= null;
		//1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
		PreparedStatement psmt= null;
		int count=0;

		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(insertEmployee.toString());
			//1.4 �������� ��������Ÿ�� �����Ѵ�.  
			psmt.setString(1, employee.getNumber());
			psmt.setString(2, employee.getName());
			psmt.setString(3, employee.getAge());
			psmt.setString(4, employee.getDepartment());
			psmt.setString(5, employee.getEmploymentday());
			psmt.setString(6, employee.getPosition());
			psmt.setString(7, employee.getAddress());
			psmt.setString(8, employee.getPhone());
			psmt.setString(9, employee.getImage());


			try {
			//1.5 ��������Ÿ�� ������ �������� �����϶�.
			count=psmt.executeUpdate();
			
			if(count == 0) {
				AdminCostController.callAlert("���� �������� : ���� ���������� ���˹ٶ�.");
				return count;
			}
			}catch(Exception e1) {
				AdminCostController.callAlert("�����ȣ�� �����մϴ�.: �ٽ� �����Ͻʽÿ�");
			}
		} catch (SQLException e) {
			AdminCostController.callAlert("���� ���� : ����Ÿ���̽� ���Խ����߾��.. ���˹ٶ�.");
			e.printStackTrace();
		} finally {
			//1.6 �ڿ���ü�� �ݾƾ��Ѵ�. 
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				AdminCostController.callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
			}	
		}
		return count;
		
	}
	
	public static ArrayList<Employee> getEmployeeDataTotalData(){
		
		String selectEmployee= "select * from employee ";
		
		//1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
		Connection con= null;
		//1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
		PreparedStatement psmt= null;
		//1.4 �������� �����ϰ��� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
		ResultSet rs = null;
		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(selectEmployee);
			
			//1.5 ��������Ÿ�� ������ �������� �����϶�.(������ ġ�°�)
			//executeQuery() �������� �����ؼ� ����� �����ö� ����ϴ� ������
			//executeUpdate() �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������
			rs=psmt.executeQuery();
			if(rs == null) {
				callAlert("select ���� :  select ���������� ���˹ٶ�.");
				return null;
			}
			while(rs.next()) {
				Employee employee=new Employee(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9));
				dbArrayList.add(employee);
			}
			
			
		} catch (SQLException e) {
			callAlert("���� ���� : ����Ÿ���̽� ���Խ����߾��.. ���˹ٶ�.");
		} finally {
			//1.6 �ڿ���ü�� �ݾƾ��Ѵ�. 
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
			}	
		}		
		return dbArrayList;
		
	}
	
	public static int deleteEmployeeData(String number) {
		//1.1 ����Ÿ���̽�  �л����̺� �ִ� ���ڵ带 �����ϴ� ������
				String deleteemployee = "delete from employee where number = ? ";
				
				//1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
				Connection con= null;
				//1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
				PreparedStatement psmt= null;
				//1.4 �������� �����ϰ��� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
				int count =0;

				try {
					con=DBUtility.getConnection();
					psmt = con.prepareStatement(deleteemployee);
					psmt.setString(1, number);
					
					//1.5 ��������Ÿ�� ������ �������� �����϶�.(������ ġ�°�)
					//executeQuery() �������� �����ؼ� ����� �����ö� ����ϴ� ������
					//executeUpdate() �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������
					count=psmt.executeUpdate();
					if(count == 0) {
						AdminCostController.callAlert("delete ���� :  delete ���������� ���˹ٶ�.");
						return count;
					}

				} catch (SQLException e) {
					AdminCostController.callAlert("delete ���� : ����Ÿ���̽� delete�����߾��.. ���˹ٶ�.");
				} finally {
					//1.6 �ڿ���ü�� �ݾƾ��Ѵ�. 
					 try {
						if(psmt != null) {psmt.close();}
						if(con != null) { con.close(); }
					} catch (SQLException e) {
						AdminCostController.callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
					}	
				}	
		return count;

	}
	public static int updateEmployeeData(Employee employee) {
		//1.1 ����Ÿ���̽���  �л����̺� �����ϴ� ������
		StringBuffer updateemplyee =new StringBuffer();
		updateemplyee.append("update employee set ");
		updateemplyee.append("name=?,age=?,department=?,Employmentday=?,position=?,address=?,phone=?,image=? where number=? ");
	
//			String updateStudent = "update student set "+
//			"kor=?,eng=?,mat=?,sci=?,soc=?,mus=?,total=?,avg=?,date=? where no=? ";
			
			//1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
			Connection con= null;
			//1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
			PreparedStatement psmt= null;
			int count=0;
			try {
			
				con=DBUtility.getConnection();
				psmt = con.prepareStatement(updateemplyee.toString());
				//1.4 �������� ��������Ÿ�� �����Ѵ�.  
				
				psmt.setString(1, employee.getName());
				psmt.setString(2, employee.getAge());
				psmt.setString(3, employee.getDepartment());
				psmt.setString(4, employee.getEmploymentday());
				psmt.setString(5, employee.getPosition());
				psmt.setString(6, employee.getAddress());
				psmt.setString(7, employee.getPhone());
				psmt.setString(8, employee.getImage());
				psmt.setString(9, employee.getNumber());
				
				//1.5 ��������Ÿ�� ������ �������� �����϶�.
				count=psmt.executeUpdate();
				if(count == 0) {
					
					callAlert("���� �������� : ���� ���������� ���˹ٶ�.");
					
					return count;
				}
				
			} catch (SQLException e) {
				callAlert("���� ���� : ����Ÿ���̽� ���������߾��.. ���˹ٶ�.");
				e.printStackTrace();
			} finally {
				//1.6 �ڿ���ü�� �ݾƾ��Ѵ�. 
				 try {
					if(psmt != null) {psmt.close();}
					if(con != null) { con.close(); }
				} catch (SQLException e) {
					callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
				}	
			}
	return count;
}
			
		

	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}

}
