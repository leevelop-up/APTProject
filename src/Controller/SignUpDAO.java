package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Citizen;
import Model.Employee;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class SignUpDAO {
	
	
	
	public static ArrayList<Citizen> dbArrayList=new ArrayList<>();
	
	public static int insertAdminCostData(Citizen citizen) {
		StringBuffer insertSignUp=new StringBuffer();
		insertSignUp.append("insert into citizen ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
		insertSignUp.append("(address,address2,password,password2,name,gender,birth,email,phone) ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
		insertSignUp.append("values ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
		insertSignUp.append("(?,?,?,?,?,?,?,?,?) ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!

		
		Connection con= null;
		PreparedStatement psmt= null;
		int count=0;

		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(insertSignUp.toString());
			psmt.setString(1, citizen.getAddress());
			psmt.setString(2, citizen.getAddress2());
			psmt.setString(3, citizen.getPassword());
			psmt.setString(4, citizen.getPassword2());
			psmt.setString(5, citizen.getName());
			psmt.setString(6, citizen.getGender());
			psmt.setString(7, citizen.getBirth());
			psmt.setString(8, citizen.getEmail());
			psmt.setString(9, citizen.getPhone());

			//1.5 실제데이타를 연결한 쿼리문을 실행하라.
			count=psmt.executeUpdate();
			if(count == 0) {
				callAlert("오류 발생 : 다시다시");
				return count;
			}
		
		} catch (SQLException e) {
			callAlert("빈칸 발생 : 회원가입을 다시 진행하세요");
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
	
	public static ArrayList<Citizen> getCitizenDataTotalData(){
		
		String selectCitizen= "select * from citizen ";
		
		//1.2  데이타베이스 Connetion을 가져와야 한다. 
		Connection con= null;
		//1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
		PreparedStatement psmt= null;
		//1.4 쿼리문을 실행하고나서 가져와야할 레코드를 담고있는 보자기 객체
		ResultSet rs = null;
		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(selectCitizen);
			
			//1.5 실제데이타를 연결한 쿼리문을 실행하라.(번개를 치는것)
			//executeQuery() 쿼리문을 실행해서 결과를 가져올때 사용하는 번개문
			//executeUpdate() 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
			rs=psmt.executeQuery();
			if(rs == null) {
				callAlert("select 실패 :  select 쿼리문실패 점검바람.");
				return null;
			}
			while(rs.next()) {
				Citizen citizen=new Citizen(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10));
				dbArrayList.add(citizen);
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

	public static int updateCitizenData(Citizen citizen) {
		//1.1 데이타베이스에  학생테이블에 수정하는 쿼리문
		StringBuffer updatecitizen =new StringBuffer();
		updatecitizen.append("update citizen set ");
		updatecitizen.append("address2=?,password=?,name=?,gender=?,birth=?,email=?,phone=? where address=? ");

			Connection con= null;
			//1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
			PreparedStatement psmt= null;
			int count=0;
			try {
			
				con=DBUtility.getConnection();
				psmt = con.prepareStatement(updatecitizen.toString());
				//1.4 쿼리문에 실제데이타를 연결한다.  
				psmt.setString(1, citizen.getAddress2());
				psmt.setString(2, citizen.getPassword());
				psmt.setString(3, citizen.getName());
				psmt.setString(4, citizen.getGender());
				psmt.setString(5, citizen.getBirth());
				psmt.setString(6, citizen.getEmail());
				psmt.setString(7, citizen.getPhone());
				psmt.setString(8, citizen.getAddress());
				
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

	
	public static int deleteCitizenData(String address) {
		//3.1 데이터베이스 학생테이블에 있는 레코드를 삭제하는 쿼리문
		String deletecitizen = "delete from citizen where address = ? ";

		// 3.2 데이터베이스 Connection 을 가져와야 한다.
		Connection con = null;
		// 3.3 쿼리문을 실행해야할 Statement를 만들어야한다.
		PreparedStatement psmt = null;
		// 3.4. 쿼리문을 실행하고나서 가져와야할 레코드를 담고있는 보자기객체 ___ResultSet
		int count=0;
		
		
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(deletecitizen);
			psmt.setString(1, address);

	
			count= psmt.executeUpdate(); 
			if (count==0) {
				callAlert("delete 실패 : delete쿼리문 실패. 점검바람");
				return count;
			}

		} catch (SQLException e) {
			callAlert("delete 실패 : 데이터베이스 delete 실패했습니다. 점검바람");
		} finally {
			// 1.6 자원객체를 닫아야한다.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				callAlert("자원닫기 실패 : psmt,con 닫기실패");
			}
		}	
	
		return count;
	}
public static void callAlert(String contentText) {
	Alert alert=new Alert(AlertType.INFORMATION);
	alert.setTitle("경고!");
	alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
	alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
	
	alert.showAndWait();
}
}


