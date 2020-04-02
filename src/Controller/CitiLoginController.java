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
		//로그인 버튼클릭시 다음 화면으로 이동
		citibtnLogin.setOnAction((e)-> { handlerBtnLoginAction(); });
		//닫기버튼 클릭시 종료
		citibtnClose.setOnAction((e)-> { handlerBtnCAction(); });
		//회원가입창 띄움
		citiBtnjoin.setOnAction((e)->{handlerBtnjoinAction();});
		
		
		cititextPassword.setOnKeyPressed(event -> { if (event.getCode().equals(KeyCode.ENTER)) { handlerBtnLoginAction(); } });

	}

	
		//로그인 버튼클릭시 다음 화면으로 이동
		private void handlerBtnLoginAction() {
			 if(cititextId1.getText().equals("1")&&cititextPassword.getText().equals("1")) {
				 
				 cititextId1.clear(); cititextPassword.clear();
		
		try {
			//로그인 성공
			Stage adminliststage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/adlist.fxml"));
			Parent root = loader.load();
			
			AdminListController adminListController=loader.getController();
			adminListController.adminliststage=adminliststage;
			
			Scene scene=new Scene(root);
			adminliststage.setScene(scene);
			primaryStage.close();//처음창을 닫는다.
			adminliststage.show();//새로운 창을 띄운다.
			callAlert("관리자 로그인 성공 : 반갑습니다. \n 즐거운하루 되십시오");
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

							Stage citizenstage=new Stage(StageStyle.UTILITY);//새로운 스테이지를 만드는 과정
							citizenstage.initModality(Modality.WINDOW_MODAL);
							citizenstage.initOwner(primaryStage);//주인이 누구야?  맨위에
							citizenstage.setTitle("관리비 ");
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
							callAlert("로그인 불가:아이디와 비밀번호 확인 바랍니다.");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					 }
				}
		
		//닫기버튼 클릭시 종료
		private void handlerBtnCAction() {
			Platform.exit();
		}
		
		//회원가입창 띄움
		private void handlerBtnjoinAction() {

				try {
					Stage citizensignstage=new Stage();
					FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/signup.fxml"));
					Parent root= loader.load();
					
					SignUpController signUpController=loader.getController();
					signUpController.citizensignstage=citizensignstage;
					
					Scene scene=new Scene(root);
					citizensignstage.setScene(scene);
					citizensignstage.show();//새로운 창을 띄운다.
					callAlert("회원가입 창입니다. :빈칸없이 모두 작성해주세요");
				} catch (IOException e1) {

					callAlert("회원가입창 오류 : 화면의 오류로 인해 로그인이 제한되었습니다. \n 점검바랍니다.");
					e1.printStackTrace();
				}
				
	
				
				
				
				
		
				
			
	
				
		
		}
			
		



//기타: 알림창(중간에 꼭 :을 적어주세요 예시:"오류정보 : 값을 제대로 입력해주세요"
private void callAlert(String contentText) {
	Alert alert=new Alert(AlertType.INFORMATION);
	alert.setTitle("경고!");
	alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
	alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
	
	alert.showAndWait();
}
}