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
		//1.1 ����Ÿ���̽���  �л����̺� �Է��ϴ� ������
		StringBuffer insertAdminCost=new StringBuffer();
		insertAdminCost.append("insert into admincost ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
		insertAdminCost.append("(info,dong,ho,electric,water,gas,heat,joint,receive,tatalCost,reciveDay) ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
		insertAdminCost.append("values ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
		insertAdminCost.append("(?,?,?,?,?,?,?,?,?,?,?) ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!

		
		//1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
		Connection con= null;
		//1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
		PreparedStatement psmt= null;
		int count=0;

		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(insertAdminCost.toString());
			//1.4 �������� ��������Ÿ�� �����Ѵ�.  
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

			
			//1.5 ��������Ÿ�� ������ �������� �����϶�.
			count=psmt.executeUpdate();
			if(count == 0) {
				AdminCostController.callAlert("���� �������� : ���� ���������� ���˹ٶ�.");
				return count;
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
	
	public static ArrayList<AdminCost> getAdminCostTotalData(){
		
		String selectAdminCost= "select dong,ho,electric,water,gas,heat,joint,receive,tatalCost,reciveDay from admincost ";
		
		//1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
		Connection con= null;
		//1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
		PreparedStatement psmt= null;
		//1.4 �������� �����ϰ��� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
		ResultSet rs = null;
		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(selectAdminCost);
			
			//1.5 ��������Ÿ�� ������ �������� �����϶�.(������ ġ�°�)
			//executeQuery() �������� �����ؼ� ����� �����ö� ����ϴ� ������
			//executeUpdate() �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������
			rs=psmt.executeQuery();
			if(rs == null) {
				AdminCostController.callAlert("select ���� :  select ���������� ���˹ٶ�.");
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
			AdminCostController.callAlert("���� ���� : ����Ÿ���̽� ���Խ����߾��.. ���˹ٶ�.");
		} finally {
			//1.6 �ڿ���ü�� �ݾƾ��Ѵ�. 
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				AdminCostController.callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
			}	
		}		
		return dbArrayList;
		
	}
	
	public static int deleteAdminCostData(String info) {
		//1.1 ����Ÿ���̽�  �л����̺� �ִ� ���ڵ带 �����ϴ� ������
				String deleteAdminCost = "delete from admincost where dong = ? ";
				
				//1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
				Connection con= null;
				//1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
				PreparedStatement psmt= null;
				//1.4 �������� �����ϰ��� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
				int count =0;

				try {
					con=DBUtility.getConnection();
					psmt = con.prepareStatement(deleteAdminCost);
					psmt.setString(1, info);
					
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
	public static int updateAdminCostData(AdminCost admincost) {
		//1.1 ����Ÿ���̽���  �л����̺� �����ϴ� ������
		String updateadmincost = "update admincost set "+"dong=?,ho=?,electric=?,water=?,gas=?,heat=?,joint=?,receive=?,tatalCost=?,reciveDay=? where info=?";

			
			//1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
			Connection con= null;
			//1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
			PreparedStatement psmt= null;
			int count=0;
			try {
				con=DBUtility.getConnection();
				psmt = con.prepareStatement(updateadmincost.toString());
				//1.4 �������� ��������Ÿ�� �����Ѵ�.  
				
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
				//1.5 ��������Ÿ�� ������ �������� �����϶�.
				count=psmt.executeUpdate();
				if(count == 0) {
					AdminCostController.callAlert("���� �������� : ���� ���������� ���˹ٶ�.");
					return count;
				}
				
			} catch (SQLException e) {
				AdminCostController.callAlert("���� ���� : ����Ÿ���̽� ���������߾��.. ���˹ٶ�.");
			} finally {
				//1.6 �ڿ���ü�� �ݾƾ��Ѵ�. 
				 try {
					if(psmt != null) {psmt.close();}
					if(con != null) { con.close(); }
				} catch (SQLException e) {
					AdminCostController.callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
					e.printStackTrace();
				}	
			}
	return count;
}
		
//public static ArrayList<AdminCost> getcostData(){
//		
//		String selectAdminCost1= "SELECT electric, water, gas, heat, joint, tatalcost , reciveday from admincost where dongho=? ";
//		
//		//1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
//		Connection con= null;
//		//1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
//		PreparedStatement psmt= null;
//		//1.4 �������� �����ϰ��� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
//		ResultSet rs = null;
//		try {
//			con=DBUtility.getConnection();
//			psmt = con.prepareStatement(selectAdminCost1);
//			
//			//1.5 ��������Ÿ�� ������ �������� �����϶�.(������ ġ�°�)
//			//executeQuery() �������� �����ؼ� ����� �����ö� ����ϴ� ������
//			//executeUpdate() �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������
//			rs=psmt.executeQuery();
//			if(rs == null) {
//				AdminCostController.callAlert("select ���� :  select ���������� ���˹ٶ�.");
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
//			AdminCostController.callAlert("���� ���� : ����Ÿ���̽� ���Խ����߾��.. ���˹ٶ�.");
//		} finally {
//			//1.6 �ڿ���ü�� �ݾƾ��Ѵ�. 
//			 try {
//				if(psmt != null) {psmt.close();}
//				if(con != null) { con.close(); }
//			} catch (SQLException e) {
//				AdminCostController.callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
//			}	
//		}		
//		return dbArrayList;
//		
//	}
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
}
