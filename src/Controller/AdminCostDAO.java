package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.AdminCost;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class AdminCostDAO {
	public static ArrayList<AdminCost> dbArrayList=new ArrayList<>();
	public static ArrayList<AdminCost> dbArrayList1=new ArrayList<>();
	
	
	public static int insertAdminCostData(AdminCost admincost) {
		//1.1 데이타베이스에  학생테이블에 입력하는 쿼리문
		StringBuffer insertAdminCost=new StringBuffer();
		insertAdminCost.append("insert into admincost ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
		insertAdminCost.append("(info,dong,ho,electric,water,gas,heat,joint,receive,tatalCost,reciveDay) ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
		insertAdminCost.append("values ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
		insertAdminCost.append("(?,?,?,?,?,?,?,?,?,?,?) ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!

		
		//1.2  데이타베이스 Connetion을 가져와야 한다. 
		Connection con= null;
		//1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
		PreparedStatement psmt= null;
		int count=0;

		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(insertAdminCost.toString());
			//1.4 쿼리문에 실제데이타를 연결한다.  
			psmt.setString(1, admincost.getDong()+admincost.getReciveDay());
			psmt.setString(2, admincost.getDong());
			psmt.setString(3, admincost.getHo());
			psmt.setString(4, admincost.getElectric());
			psmt.setString(5, admincost.getWater());
			psmt.setString(6, admincost.getGas());
			psmt.setString(7, admincost.getHeat());
			psmt.setString(8, admincost.getJoint());
			psmt.setString(9, admincost.getReceive());
			psmt.setString(10, admincost.getTatalCost());
			psmt.setString(11, admincost.getReciveDay());

			
			//1.5 실제데이타를 연결한 쿼리문을 실행하라.
			count=psmt.executeUpdate();
			if(count == 0) {
				AdminCostController.callAlert("삽입 쿼리실패 : 삽입 쿼리문실패 점검바람.");
				return count;
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
	
	public static ArrayList<AdminCost> getAdminCostTotalData(){
		
		String selectAdminCost= "select dong,ho,electric,water,gas,heat,joint,receive,tatalCost,reciveDay from admincost ";
		
		//1.2  데이타베이스 Connetion을 가져와야 한다. 
		Connection con= null;
		//1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
		PreparedStatement psmt= null;
		//1.4 쿼리문을 실행하고나서 가져와야할 레코드를 담고있는 보자기 객체
		ResultSet rs = null;
		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(selectAdminCost);
			
			//1.5 실제데이타를 연결한 쿼리문을 실행하라.(번개를 치는것)
			//executeQuery() 쿼리문을 실행해서 결과를 가져올때 사용하는 번개문
			//executeUpdate() 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
			rs=psmt.executeQuery();
			if(rs == null) {
				AdminCostController.callAlert("select 실패 :  select 쿼리문실패 점검바람.");
				return null;
			}
			while(rs.next()) {
				AdminCost admincost=new AdminCost(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getString(10));
				dbArrayList.add(admincost);
			}
			
			
		} catch (SQLException e) {
			AdminCostController.callAlert("삽입 실패 : 데이타베이스 삽입실패했어요.. 점검바람.");
		} finally {
			//1.6 자원객체를 닫아야한다. 
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				AdminCostController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
			}	
		}		
		return dbArrayList;
		
	}
	
	public static int deleteAdminCostData(String info) {
		//1.1 데이타베이스  학생테이블에 있는 레코드를 삭제하는 쿼리문
				String deleteAdminCost = "delete from admincost where dong = ? ";
				
				//1.2  데이타베이스 Connetion을 가져와야 한다. 
				Connection con= null;
				//1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
				PreparedStatement psmt= null;
				//1.4 쿼리문을 실행하고나서 가져와야할 레코드를 담고있는 보자기 객체
				int count =0;

				try {
					con=DBUtility.getConnection();
					psmt = con.prepareStatement(deleteAdminCost);
					psmt.setString(1, info);
					
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
	public static int updateAdminCostData(AdminCost admincost) {
		//1.1 데이타베이스에  학생테이블에 수정하는 쿼리문
		String updateadmincost = "update admincost set "+"dong=?,ho=?,electric=?,water=?,gas=?,heat=?,joint=?,receive=?,tatalCost=?,reciveDay=? where info=?";

			
			//1.2  데이타베이스 Connetion을 가져와야 한다. 
			Connection con= null;
			//1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
			PreparedStatement psmt= null;
			int count=0;
			try {
				con=DBUtility.getConnection();
				psmt = con.prepareStatement(updateadmincost.toString());
				//1.4 쿼리문에 실제데이타를 연결한다.  
				
				//String total=admincost.getDong()+admincost.getReciveDay();
				psmt.setString(1, admincost.getDong());
				psmt.setString(2, admincost.getHo());
				psmt.setString(3, admincost.getElectric());
				psmt.setString(4, admincost.getWater());
				psmt.setString(5, admincost.getGas());
				psmt.setString(6, admincost.getHeat());
				psmt.setString(7, admincost.getJoint());
				psmt.setString(8, admincost.getReceive());
				psmt.setString(9, admincost.getTatalCost());
				psmt.setString(10, admincost.getReciveDay());
				psmt.setString(11, admincost.getDong()+admincost.getReciveDay());
				//1.5 실제데이타를 연결한 쿼리문을 실행하라.
				count=psmt.executeUpdate();
				if(count == 0) {
					AdminCostController.callAlert("수정 쿼리실패 : 수정 쿼리문실패 점검바람.");
					return count;
				}
				
			} catch (SQLException e) {
				AdminCostController.callAlert("수정 실패 : 데이타베이스 수정실패했어요.. 점검바람.");
			} finally {
				//1.6 자원객체를 닫아야한다. 
				 try {
					if(psmt != null) {psmt.close();}
					if(con != null) { con.close(); }
				} catch (SQLException e) {
					AdminCostController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
					e.printStackTrace();
				}	
			}
	return count;
}
		
//public static ArrayList<AdminCost> getcostData(){
//		
//		String selectAdminCost1= "SELECT electric, water, gas, heat, joint, tatalcost , reciveday from admincost where dongho=? ";
//		
//		//1.2  데이타베이스 Connetion을 가져와야 한다. 
//		Connection con= null;
//		//1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
//		PreparedStatement psmt= null;
//		//1.4 쿼리문을 실행하고나서 가져와야할 레코드를 담고있는 보자기 객체
//		ResultSet rs = null;
//		try {
//			con=DBUtility.getConnection();
//			psmt = con.prepareStatement(selectAdminCost1);
//			
//			//1.5 실제데이타를 연결한 쿼리문을 실행하라.(번개를 치는것)
//			//executeQuery() 쿼리문을 실행해서 결과를 가져올때 사용하는 번개문
//			//executeUpdate() 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
//			rs=psmt.executeQuery();
//			if(rs == null) {
//				AdminCostController.callAlert("select 실패 :  select 쿼리문실패 점검바람.");
//				return null;
//			}
//			while(rs.next()) {
//				AdminCost admincost=new AdminCost(
//						rs.getString(1),
//						rs.getString(2),
//						rs.getString(3),
//						rs.getString(4),
//						rs.getString(5),
//						rs.getString(6),
//						rs.getString(7),
//						rs.getString(8),
//						rs.getString(9),
//						rs.getString(10),
//						rs.getString(11));
//				dbArrayList.add(admincost);
//			}
//			
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			AdminCostController.callAlert("삽입 실패 : 데이타베이스 삽입실패했어요.. 점검바람.");
//		} finally {
//			//1.6 자원객체를 닫아야한다. 
//			 try {
//				if(psmt != null) {psmt.close();}
//				if(con != null) { con.close(); }
//			} catch (SQLException e) {
//				AdminCostController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
//			}	
//		}		
//		return dbArrayList;
//		
//	}
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
}
