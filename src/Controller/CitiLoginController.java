package Controller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.naming.spi.DirStateFactory.Result;

import Model.AdminCost;
import Model.Citizen;
import Model.Employee;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CitiLoginController implements Initializable{

	@FXML private TextField cititextId1;
	@FXML private TextField cititextId2;
	@FXML private PasswordField cititextPassword;
	@FXML private Button citibtnLogin;
	@FXML private Button citibtnClose;
	@FXML private Button citiBtnjoin;
	
	
	ArrayList<AdminCost> dbArrayList=new ArrayList<>();
	ObservableList<AdminCost>t2Listdata=FXCollections.observableArrayList();


	public Stage primaryStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//�α��� ��ưŬ���� ���� ȭ������ �̵�
		citibtnLogin.setOnAction((e)-> { handlerBtnLoginAction(); });
		//�ݱ��ư Ŭ���� ����
		citibtnClose.setOnAction((e)-> { handlerBtnCAction(); });
		//ȸ������â ���
		citiBtnjoin.setOnAction((e)->{handlerBtnjoinAction();});
		
		
		cititextPassword.setOnKeyPressed(event -> { if (event.getCode().equals(KeyCode.ENTER)) { handlerBtnLoginAction(); } });

	}

	
		//�α��� ��ưŬ���� ���� ȭ������ �̵�
		private void handlerBtnLoginAction() {
			 if(cititextId1.getText().equals("1")&&cititextPassword.getText().equals("1")) {
				 
				 cititextId1.clear(); cititextPassword.clear();
		
		try {
			//�α��� ����
			Stage adminliststage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/adlist.fxml"));
			Parent root = loader.load();
			
			AdminListController adminListController=loader.getController();
			adminListController.adminliststage=adminliststage;
			
			Scene scene=new Scene(root);
			adminliststage.setScene(scene);
			primaryStage.close();//ó��â�� �ݴ´�.
			adminliststage.show();//���ο� â�� ����.
			callAlert("������ �α��� ���� : �ݰ����ϴ�. \n ��ſ��Ϸ� �ǽʽÿ�");
			} catch (Exception e) { }
			
			 }else {	
				 

				 String id = cititextId1.getText().trim();
					String id2 = cititextId2.getText().trim();

					String pw = cititextPassword.getText().trim();
					String sql = "select address, address2, password from citizen where address=? and address2=? and password=? "; 
					Parent root=null;
					try {
						Connection con = DBUtility.getConnection();
						PreparedStatement pstmt = con.prepareStatement(sql);
						
						pstmt.setString(1, id);
						pstmt.setString(2, id2);
						pstmt.setString(3, pw);
						
						ResultSet rs = pstmt.executeQuery();
						if (rs.next()) {
							id = rs.getString("address");
							id2= rs.getString("address2");

							Stage citizenstage=new Stage(StageStyle.UTILITY);//���ο� ���������� ����� ����
							citizenstage.initModality(Modality.WINDOW_MODAL);
							citizenstage.initOwner(primaryStage);//������ ������?  ������
							citizenstage.setTitle("������ ");
							  FXMLLoader loader= new FXMLLoader(getClass().getResource("../View/citiho.fxml"));
							  root = loader.load();			
							  
							  
							  TableView<AdminCost> tableviewCost=(TableView)root.lookup("#tableviewCost");
							  Button elecChart=(Button)root.lookup("#elecChart");
							  Button btnOk=(Button)root.lookup("#btnOk");
							  Button waterChart=(Button)root.lookup("#waterChart");
							  Button gasChart=(Button)root.lookup("#gasChart");
							  Button heatChart=(Button)root.lookup("#heatChart");
							  TextField elecCost=(TextField)root.lookup("#elecCost");
							  TextField t2TextPhone=(TextField)root.lookup("#t2TextPhone");
							  TextField waterCost=(TextField)root.lookup("#waterCost");
							  TextField gasCost=(TextField)root.lookup("#gasCost");
							  TextField heatCost=(TextField)root.lookup("#heatCost");
							  TextField toCost=(TextField)root.lookup("#toCost");
							  TextField totalCost=(TextField)root.lookup("#totalCost");
							  TextField textDong=(TextField)root.lookup("#textDong");
							  TextField textHo=(TextField)root.lookup("#textHo");
							  DatePicker costDay=(DatePicker)root.lookup("#costDay");
							
							
							  textDong.setText(id);
							  textDong.setDisable(true);
							  textHo.setText(id2);
							  textHo.setDisable(true);
							  
//							  tableviewCost.setItems(t2Listdata);
//							  dbArrayList=AdminCostDAO.getcostData();
//							  for(AdminCost admincost: dbArrayList) {
//								  t2Listdata.add(admincost);
							//  }
							  
							  
							  
							  
							Scene scene = new Scene(root);
								citizenstage.setScene(scene);
								citizenstage.show();		  
							  
								
//								Stage citizenliststage = new Stage();
//								FXMLLoader loder=new FXMLLoader(getClass().getResource("../View/citiho.fxml"));
//								Parent root = loder.load();
//								CitizenListController citizenListController=loder.getController();
//								citizenListController.citizenliststage = citizenliststage;
//								Scene scene = new Scene(root);
//								citizenliststage.setScene(scene);
//								citizenliststage.show();

							
							
						} else {
							callAlert("�α��� �Ұ�:���̵�� ��й�ȣ Ȯ�� �ٶ��ϴ�.");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					 }
				}
		
		//�ݱ��ư Ŭ���� ����
		private void handlerBtnCAction() {
			Platform.exit();
		}
		
		//ȸ������â ���
		private void handlerBtnjoinAction() {

				try {
					Stage citizensignstage=new Stage();
					FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/signup.fxml"));
					Parent root= loader.load();
					
					SignUpController signUpController=loader.getController();
					signUpController.citizensignstage=citizensignstage;
					
					Scene scene=new Scene(root);
					citizensignstage.setScene(scene);
					citizensignstage.show();//���ο� â�� ����.
					callAlert("ȸ������ â�Դϴ�. :��ĭ���� ��� �ۼ����ּ���");
				} catch (IOException e1) {

					callAlert("ȸ������â ���� : ȭ���� ������ ���� �α����� ���ѵǾ����ϴ�. \n ���˹ٶ��ϴ�.");
					e1.printStackTrace();
				}
				
	
				
				
				
				
		
				
			
	
				
		
		}
			
		



//��Ÿ: �˸�â(�߰��� �� :�� �����ּ��� ����:"�������� : ���� ����� �Է����ּ���"
private void callAlert(String contentText) {
	Alert alert=new Alert(AlertType.INFORMATION);
	alert.setTitle("���!");
	alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
	alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
	
	alert.showAndWait();
}
}