package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminListController implements Initializable{
	@FXML private Button adBtnCost;
	@FXML private Button adBtnStaff;
	@FXML private Button adBtnFamily;
	@FXML private Button adBtnExit;

	public Stage adminliststage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//����Ʈ�� ������ Ŭ���� ������ â���� ��ȯ
		adBtnCost.setOnAction((e)-> { handlerBtnCostAction(); });
		//����Ʈ�� �λ���� Ŭ���� ��������â���� ��ȯ
		adBtnStaff.setOnAction((e)-> { handlerBtnStaffAction(); });
		//����Ʈ�� ������� Ŭ���� �������â���� ��ȯ
		adBtnFamily.setOnAction((e)-> { handlerBtnFamilyAction(); });
		//ȭ��ǥ ������ �ʱ�ȭ������ �̵�
		adBtnExit.setOnAction((e)-> { handlerBtnExitAction(); });
	
}
	
	
	

	//����Ʈ�� ������ Ŭ���� ������ â���� ��ȯ
	private void handlerBtnCostAction() {
			try {
			Stage admincoststage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/admincost.fxml"));
			Parent root = loader.load();
			
			AdminCostController adminCostController=loader.getController();
			adminCostController.admincoststage=admincoststage;
			Scene scene=new Scene(root);
			admincoststage.setScene(scene);
			adminliststage.close();//ó��â�� �ݴ´�.
			admincoststage.show();//���ο� â�� ����.
				
		} catch (IOException e) { e.printStackTrace();}	
	}

	//����Ʈ�� �λ���� Ŭ���� ��������â���� ��ȯ
	private void handlerBtnStaffAction() {
		Parent root=null;
		try {
			Stage employeestage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/employees.fxml"));
			root = loader.load();
			
			EmployeesController employeesController=loader.getController();
			employeesController.employeestage=employeestage;
			
			Scene scene=new Scene(root);
			employeestage.setScene(scene);
			adminliststage.close();//ó��â�� �ݴ´�.
			employeestage.show();//���ο� â�� ����.
		} catch (Exception e) { 
			e.printStackTrace();}	
		
		
	}
	//����Ʈ�� ������� Ŭ���� �������â���� ��ȯ
	private void handlerBtnFamilyAction() {
		Parent root=null;
		try {
			Stage adcitistage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/citizeninfo.fxml"));
			root = loader.load();
			
			AdminCitiController adminCitiController=loader.getController();
			adminCitiController.adcitistage=adcitistage;
			
			Scene scene=new Scene(root);
			adcitistage.setScene(scene);
			adminliststage.close();//ó��â�� �ݴ´�.
			adcitistage.show();//���ο� â�� ����.
		} catch (Exception e) { 
			e.printStackTrace();}	
		
			
		}
		//ȭ��ǥ ������ �ʱ�ȭ������ �̵�
		private void handlerBtnExitAction() {
			try {
				
				
				Stage primaryStage=new Stage();
				FXMLLoader loder=new FXMLLoader(getClass().getResource("../View/citizenlogin.fxml"));
				Parent root = loder.load();
				CitiLoginController citiLoginController=loder.getController();
				citiLoginController.primaryStage = primaryStage;
				Scene scene=new Scene(root);
			//	scene.getStylesheets().add(getClass().getResource("add.css").toString());
				primaryStage.setScene(scene);
				adminliststage.close();
				primaryStage.show();
				
			} catch (IOException e1) {	}
		}


	public static void callAlert(String contentText) {
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("���!");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		
		alert.showAndWait();
	}

}
