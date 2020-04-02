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
		insertSignUp.append("insert into citizen ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
		insertSignUp.append("(address,address2,password,password2,name,gender,birth,email,phone) ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
		insertSignUp.append("values ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
		insertSignUp.append("(?,?,?,?,?,?,?,?,?) ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!

		
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

			//1.5 ��������Ÿ�� ������ �������� �����϶�.
			count=psmt.executeUpdate();
			if(count == 0) {
				callAlert("���� �߻� : �ٽôٽ�");
				return count;
			}
		
		} catch (SQLException e) {
			callAlert("��ĭ �߻� : ȸ�������� �ٽ� �����ϼ���");
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
	
	public static ArrayList<Citizen> getCitizenDataTotalData(){
		
		String selectCitizen= "select * from citizen ";
		
		//1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
		Connection con= null;
		//1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
		PreparedStatement psmt= null;
		//1.4 �������� �����ϰ��� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
		ResultSet rs = null;
		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(selectCitizen);
			
			//1.5 ��������Ÿ�� ������ �������� �����϶�.(������ ġ�°�)
			//executeQuery() �������� �����ؼ� ����� �����ö� ����ϴ� ������
			//executeUpdate() �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������
			rs=psmt.executeQuery();
			if(rs == null) {
				callAlert("select ���� :  select ���������� ���˹ٶ�.");
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

	public static int updateCitizenData(Citizen citizen) {
		//1.1 ����Ÿ���̽���  �л����̺� �����ϴ� ������
		StringBuffer updatecitizen =new StringBuffer();
		updatecitizen.append("update citizen set ");
		updatecitizen.append("address2=?,password=?,name=?,gender=?,birth=?,email=?,phone=? where address=? ");

			Connection con= null;
			//1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
			PreparedStatement psmt= null;
			int count=0;
			try {
			
				con=DBUtility.getConnection();
				psmt = con.prepareStatement(updatecitizen.toString());
				//1.4 �������� ��������Ÿ�� �����Ѵ�.  
				psmt.setString(1, citizen.getAddress2());
				psmt.setString(2, citizen.getPassword());
				psmt.setString(3, citizen.getName());
				psmt.setString(4, citizen.getGender());
				psmt.setString(5, citizen.getBirth());
				psmt.setString(6, citizen.getEmail());
				psmt.setString(7, citizen.getPhone());
				psmt.setString(8, citizen.getAddress());
				
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

	
	public static int deleteCitizenData(String address) {
		//3.1 �����ͺ��̽� �л����̺� �ִ� ���ڵ带 �����ϴ� ������
		String deletecitizen = "delete from citizen where address = ? ";

		// 3.2 �����ͺ��̽� Connection �� �����;� �Ѵ�.
		Connection con = null;
		// 3.3 �������� �����ؾ��� Statement�� �������Ѵ�.
		PreparedStatement psmt = null;
		// 3.4. �������� �����ϰ��� �����;��� ���ڵ带 ����ִ� ���ڱⰴü ___ResultSet
		int count=0;
		
		
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(deletecitizen);
			psmt.setString(1, address);

	
			count= psmt.executeUpdate(); 
			if (count==0) {
				callAlert("delete ���� : delete������ ����. ���˹ٶ�");
				return count;
			}

		} catch (SQLException e) {
			callAlert("delete ���� : �����ͺ��̽� delete �����߽��ϴ�. ���˹ٶ�");
		} finally {
			// 1.6 �ڿ���ü�� �ݾƾ��Ѵ�.
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				callAlert("�ڿ��ݱ� ���� : psmt,con �ݱ����");
			}
		}	
	
		return count;
	}
public static void callAlert(String contentText) {
	Alert alert=new Alert(AlertType.INFORMATION);
	alert.setTitle("���!");
	alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
	alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
	
	alert.showAndWait();
}
}


