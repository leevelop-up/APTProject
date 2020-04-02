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
		
		//리스트중 관리비 클릭시 관리비 창으로 전환
		adBtnCost.setOnAction((e)-> { handlerBtnCostAction(); });
		//리스트중 인사관리 클릭시 직원관리창으로 전환
		adBtnStaff.setOnAction((e)-> { handlerBtnStaffAction(); });
		//리스트중 세대관리 클릭시 세대관리창으로 전환
		adBtnFamily.setOnAction((e)-> { handlerBtnFamilyAction(); });
		//화살표 누르면 초기화면으로 이동
		adBtnExit.setOnAction((e)-> { handlerBtnExitAction(); });
	
}
	
	
	

	//리스트중 관리비 클릭시 관리비 창으로 전환
	private void handlerBtnCostAction() {
			try {
			Stage admincoststage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/admincost.fxml"));
			Parent root = loader.load();
			
			AdminCostController adminCostController=loader.getController();
			adminCostController.admincoststage=admincoststage;
			Scene scene=new Scene(root);
			admincoststage.setScene(scene);
			adminliststage.close();//처음창을 닫는다.
			admincoststage.show();//새로운 창을 띄운다.
				
		} catch (IOException e) { e.printStackTrace();}	
	}

	//리스트중 인사관리 클릭시 직원관리창으로 전환
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
			adminliststage.close();//처음창을 닫는다.
			employeestage.show();//새로운 창을 띄운다.
		} catch (Exception e) { 
			e.printStackTrace();}	
		
		
	}
	//리스트중 세대관리 클릭시 세대관리창으로 전환
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
			adminliststage.close();//처음창을 닫는다.
			adcitistage.show();//새로운 창을 띄운다.
		} catch (Exception e) { 
			e.printStackTrace();}	
		
			
		}
		//화살표 누르면 초기화면으로 이동
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
		alert.setTitle("경고!");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		
		alert.showAndWait();
	}

}
