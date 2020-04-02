package Controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Citizen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SignUpController implements Initializable{
	@FXML private TextField sighTextAddr;
	@FXML private TextField sighTextAddr1;
	@FXML private PasswordField sighTextPw;
	@FXML private PasswordField sighTextPw2;
	@FXML private TextField sighTextName;
	@FXML private ComboBox<String> sighCmbGender;
	@FXML private TextField sighTextBirth;
	@FXML private TextField sighTextEmail;
	@FXML private TextField sighTextPhone;
	@FXML private Button signBtnSave;
	@FXML private Button signBtnClose;
	@FXML private Button pwcheck;
	@FXML private Button idcheck;
	ArrayList<Citizen> dbArrayList=new ArrayList<>();
	
	private boolean editFlag=false;
	public Stage citizensignstage;
	ObservableList<String> signGenderList=FXCollections.observableArrayList();
	ObservableList<Citizen> citizenData=FXCollections.observableArrayList();
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setComboBoxGender();
		
		setButtonTextFieldInitiate("처음");
		//저장 버튼 클릭시 값을 저장시키고 로그인 화면으로 전환
		signBtnSave.setOnAction((e)-> { handlerBtnSaveAction(); });
		
		//닫기 버튼 클릭시 로그인 화면으로 전환
		signBtnClose.setOnAction((e)-> { handlerBtnCloseAction(); });
		
		//아이디 중복체크
		idcheck.setOnAction((e)-> { handleridcheckAction(); });
		//패스워드 확인
		pwcheck.setOnAction((e)-> { handlerpwcheckAction(); });
		//핸드폰 제한
		inputDecimalFormatPhone(sighTextPhone);
		//생년월일 제한
		inputDecimalFormatResidentt(sighTextBirth);
	}

	private void setButtonTextFieldInitiate(String message) {
	
		switch(message) {
		case "저장": 
			sighTextPw.setDisable(true);
			sighTextPw2.setDisable(true);

			break;
//		case "확인": 
//			sighTextAddr.setDisable(true);
//
//			break;
		}
	}

	private void setComboBoxGender() {
		signGenderList.addAll("남","여");
		sighCmbGender.setItems(signGenderList);
		
	}

	//저장버튼 클릭시 저장 기능
	private void handlerBtnSaveAction() {
		
		try {
			Stage primaryStage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/citizenlogin.fxml"));
			Parent root= loader.load();
			
			CitiLoginController CitiLoginController=loader.getController();
			CitiLoginController.primaryStage=primaryStage;
			

			if(sighTextAddr!=null && sighTextAddr1!=null&& sighTextPw!=null && sighTextPw2!=null&& sighTextName!=null && sighCmbGender!=null 
					&& sighTextBirth!=null && sighTextEmail!=null&& sighTextPhone!=null) {
					String dongho=null;
				Citizen citizen=new Citizen(
						dongho,
						sighTextAddr.getText(),
						sighTextAddr1.getText(),
						sighTextPw.getText(),
						sighTextPw2.getText(),
						sighTextName.getText(),
						sighCmbGender.getSelectionModel().getSelectedItem(),
						sighTextBirth.getText(),
						sighTextEmail.getText(),
						sighTextPhone.getText());
				citizenData.add(citizen);	
				int count=SignUpDAO.insertAdminCostData(citizen);
				if(count!=0) {
					callAlert("입력성공 : 데이타베이스 입력이 성공되었습니다. ");
				}
				}
			Scene scene=new Scene(root);
			primaryStage.setScene(scene);
			citizensignstage.close();
			primaryStage.show();
			
	

			}catch (IOException e) {}
				

	}

	//닫기 버튼 클릭시 로그인 화면으로 전환
	private void handlerBtnCloseAction() {
		try {
			Stage primaryStage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/citizenlogin.fxml"));
			Parent root= loader.load();
			
			CitiLoginController CitiLoginController=loader.getController();
			CitiLoginController.primaryStage=primaryStage;
			
			Scene scene=new Scene(root);
			primaryStage.setScene(scene);
			citizensignstage.close();
			primaryStage.show();//새로운 창을 띄운다.
		} catch (IOException e1) {	}
		
	}
	
	//아이디 중복체크
	private void handleridcheckAction() {
		   String emailCheck = null;
           dbArrayList = SignUpDAO.getCitizenDataTotalData();//db에서 joinmembershiptb2 의 모든 데이터를 가져온다.
           for (Citizen joinmembershipDB : dbArrayList) {
              emailCheck = joinmembershipDB.getEmail();
              if (sighTextAddr.getText().equals(emailCheck)&&sighTextAddr1.getText().equals(emailCheck)) {
           callAlert("아이디 중복: 사용불가능한 아이디입니다. 다시 입력바랍니다.");
           sighTextAddr.clear();
           sighTextAddr1.clear();
                 return;
              }
           }
           callAlert("아이디 사용가능: 사용가능한 아이디입니다.");
          // setButtonTextFieldInitiate("확인");
		   }

	//패스워드 확인
	private void handlerpwcheckAction() {
		
		if(!(sighTextPw.getText().equals(sighTextPw2.getText()))) {
			callAlert("비밀번호가 동일하지 않습니다.: 비밀번호를 동일하게 넣어주세요");
			sighTextPw.clear(); sighTextPw2.clear();
			return;
		}
		callAlert("비밀번호 확인완료 : 가입을 진행해주세요");
		setButtonTextFieldInitiate("저장");
		
	}
	

	
	
	//기타 알림창
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}

	
	//생년월일 제한
	private void inputDecimalFormatResidentt(TextField textField) {

		DecimalFormat format = new DecimalFormat("##");
		// 점수 입력시 길이 제한 이벤트 처리
		textField.setTextFormatter(new TextFormatter<>(event -> {
			// 입력받은 내용이 없으면 이벤트를 리턴함.
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			// 구문을 분석할 시작 위치를 지정함.
			ParsePosition parsePosition = new ParsePosition(0);
			// 입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
			Object object = format.parse(event.getControlNewText(), parsePosition);
			// 리턴값이 null 이거나,입력한길이와구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함)거나,입력한길이가 4이면(3자리를 넘었음을 뜻함.)
			// 이면 null 리턴함.

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()	|| event.getControlNewText().length() == 9 || event.getControlNewText()=="."
					||event.getControlNewText()=="-") {
				return null;
			} else {
				return event;
			}
		}));
		
	}
	
	//핸드폰 제한
	private void inputDecimalFormatPhone(TextField textField) {

		DecimalFormat format = new DecimalFormat("##");
		// 점수 입력시 길이 제한 이벤트 처리
		textField.setTextFormatter(new TextFormatter<>(event -> {
			// 입력받은 내용이 없으면 이벤트를 리턴함.
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			// 구문을 분석할 시작 위치를 지정함.
			ParsePosition parsePosition = new ParsePosition(0);
			// 입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
			Object object = format.parse(event.getControlNewText(), parsePosition);
			// 리턴값이 null 이거나,입력한길이와구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함)거나,입력한길이가 4이면(3자리를 넘었음을 뜻함.)
			// 이면 null 리턴함.

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()	|| event.getControlNewText().length() == 12||event.getControlNewText()=="-") {
				return null;
			} else {
				return event;
			}
		}));
		
	}
	
	
	}

