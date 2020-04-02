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
		//1.1 데이타베이스에  학생테이블에 입력하는 쿼리문
		StringBuffer insertEmployee=new StringBuffer();
		insertEmployee.append("insert into employee ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
		insertEmployee.append("(number,name,age,department,Employmentday,position,address,Phone,image) ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
		insertEmployee.append("values ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
		insertEmployee.append("(?,?,?,?,?,?,?,?,?) ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
	
		//1.2  데이타베이스 Connetion을 가져와야 한다. 
		Connection con= null;
		//1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
		PreparedStatement psmt= null;
		int count=0;

		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(insertEmployee.toString());
			//1.4 쿼리문에 실제데이타를 연결한다.  
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
			//1.5 실제데이타를 연결한 쿼리문을 실행하라.
			count=psmt.executeUpdate();
			
			if(count == 0) {
				AdminCostController.callAlert("삽입 쿼리실패 : 삽입 쿼리문실패 점검바람.");
				return count;
			}
			}catch(Exception e1) {
				AdminCostController.callAlert("사원번호가 동일합니다.: 다시 실행하십시오");
			}
		} catch (SQLException e) {
			AdminCostController.callAlert("삽입 실패 : 데이타베이스 삽입실패했어요.. 점검바람.");
			e.printStackTrace();
		} finally {
			//1.6 자원객체를 닫아야한다. 
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				AdminCostController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
			}	
		}
		return count;
		
	}
	
	public static ArrayList<Employee> getEmployeeDataTotalData(){
		
		String selectEmployee= "select * from employee ";
		
		//1.2  데이타베이스 Connetion을 가져와야 한다. 
		Connection con= null;
		//1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
		PreparedStatement psmt= null;
		//1.4 쿼리문을 실행하고나서 가져와야할 레코드를 담고있는 보자기 객체
		ResultSet rs = null;
		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(selectEmployee);
			
			//1.5 실제데이타를 연결한 쿼리문을 실행하라.(번개를 치는것)
			//executeQuery() 쿼리문을 실행해서 결과를 가져올때 사용하는 번개문
			//executeUpdate() 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
			rs=psmt.executeQuery();
			if(rs == null) {
				callAlert("select 실패 :  select 쿼리문실패 점검바람.");
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
			callAlert("삽입 실패 : 데이타베이스 삽입실패했어요.. 점검바람.");
		} finally {
			//1.6 자원객체를 닫아야한다. 
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				callAlert("자원닫기실패 : psmt, con 닫기실패.");
			}	
		}		
		return dbArrayList;
		
	}
	
	public static int deleteEmployeeData(String number) {
		//1.1 데이타베이스  학생테이블에 있는 레코드를 삭제하는 쿼리문
				String deleteemployee = "delete from employee where number = ? ";
				
				//1.2  데이타베이스 Connetion을 가져와야 한다. 
				Connection con= null;
				//1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
				PreparedStatement psmt= null;
				//1.4 쿼리문을 실행하고나서 가져와야할 레코드를 담고있는 보자기 객체
				int count =0;

				try {
					con=DBUtility.getConnection();
					psmt = con.prepareStatement(deleteemployee);
					psmt.setString(1, number);
					
					//1.5 실제데이타를 연결한 쿼리문을 실행하라.(번개를 치는것)
					//executeQuery() 쿼리문을 실행해서 결과를 가져올때 사용하는 번개문
					//executeUpdate() 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
					count=psmt.executeUpdate();
					if(count == 0) {
						AdminCostController.callAlert("delete 실패 :  delete 쿼리문실패 점검바람.");
						return count;
					}

				} catch (SQLException e) {
					AdminCostController.callAlert("delete 실패 : 데이타베이스 delete실패했어요.. 점검바람.");
				} finally {
					//1.6 자원객체를 닫아야한다. 
					 try {
						if(psmt != null) {psmt.close();}
						if(con != null) { con.close(); }
					} catch (SQLException e) {
						AdminCostController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
					}	
				}	
		return count;

	}
	public static int updateEmployeeData(Employee employee) {
		//1.1 데이타베이스에  학생테이블에 수정하는 쿼리문
		StringBuffer updateemplyee =new StringBuffer();
		updateemplyee.append("update employee set ");
		updateemplyee.append("name=?,age=?,department=?,Employmentday=?,position=?,address=?,phone=?,image=? where number=? ");
	
//			String updateStudent = "update student set "+
//			"kor=?,eng=?,mat=?,sci=?,soc=?,mus=?,total=?,avg=?,date=? where no=? ";
			
			//1.2  데이타베이스 Connetion을 가져와야 한다. 
			Connection con= null;
			//1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
			PreparedStatement psmt= null;
			int count=0;
			try {
			
				con=DBUtility.getConnection();
				psmt = con.prepareStatement(updateemplyee.toString());
				//1.4 쿼리문에 실제데이타를 연결한다.  
				
				psmt.setString(1, employee.getName());
				psmt.setString(2, employee.getAge());
				psmt.setString(3, employee.getDepartment());
				psmt.setString(4, employee.getEmploymentday());
				psmt.setString(5, employee.getPosition());
				psmt.setString(6, employee.getAddress());
				psmt.setString(7, employee.getPhone());
				psmt.setString(8, employee.getImage());
				psmt.setString(9, employee.getNumber());
				
				//1.5 실제데이타를 연결한 쿼리문을 실행하라.
				count=psmt.executeUpdate();
				if(count == 0) {
					
					callAlert("수정 쿼리실패 : 수정 쿼리문실패 점검바람.");
					
					return count;
				}
				
			} catch (SQLException e) {
				callAlert("수정 실패 : 데이타베이스 수정실패했어요.. 점검바람.");
				e.printStackTrace();
			} finally {
				//1.6 자원객체를 닫아야한다. 
				 try {
					if(psmt != null) {psmt.close();}
					if(con != null) { con.close(); }
				} catch (SQLException e) {
					callAlert("자원닫기실패 : psmt, con 닫기실패.");
				}	
			}
	return count;
}
			
		

	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}

}
