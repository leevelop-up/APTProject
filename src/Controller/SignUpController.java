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
		
		setButtonTextFieldInitiate("ó��");
		//���� ��ư Ŭ���� ���� �����Ű�� �α��� ȭ������ ��ȯ
		signBtnSave.setOnAction((e)-> { handlerBtnSaveAction(); });
		
		//�ݱ� ��ư Ŭ���� �α��� ȭ������ ��ȯ
		signBtnClose.setOnAction((e)-> { handlerBtnCloseAction(); });
		
		//���̵� �ߺ�üũ
		idcheck.setOnAction((e)-> { handleridcheckAction(); });
		//�н����� Ȯ��
		pwcheck.setOnAction((e)-> { handlerpwcheckAction(); });
		//�ڵ��� ����
		inputDecimalFormatPhone(sighTextPhone);
		//������� ����
		inputDecimalFormatResidentt(sighTextBirth);
	}

	private void setButtonTextFieldInitiate(String message) {
	
		switch(message) {
		case "����": 
			sighTextPw.setDisable(true);
			sighTextPw2.setDisable(true);

			break;
//		case "Ȯ��": 
//			sighTextAddr.setDisable(true);
//
//			break;
		}
	}

	private void setComboBoxGender() {
		signGenderList.addAll("��","��");
		sighCmbGender.setItems(signGenderList);
		
	}

	//�����ư Ŭ���� ���� ���
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
					callAlert("�Է¼��� : ����Ÿ���̽� �Է��� �����Ǿ����ϴ�. ");
				}
				}
			Scene scene=new Scene(root);
			primaryStage.setScene(scene);
			citizensignstage.close();
			primaryStage.show();
			
	

			}catch (IOException e) {}
				

	}

	//�ݱ� ��ư Ŭ���� �α��� ȭ������ ��ȯ
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
			primaryStage.show();//���ο� â�� ����.
		} catch (IOException e1) {	}
		
	}
	
	//���̵� �ߺ�üũ
	private void handleridcheckAction() {
		   String emailCheck = null;
           dbArrayList = SignUpDAO.getCitizenDataTotalData();//db���� joinmembershiptb2 �� ��� �����͸� �����´�.
           for (Citizen joinmembershipDB : dbArrayList) {
              emailCheck = joinmembershipDB.getEmail();
              if (sighTextAddr.getText().equals(emailCheck)&&sighTextAddr1.getText().equals(emailCheck)) {
           callAlert("���̵� �ߺ�: ���Ұ����� ���̵��Դϴ�. �ٽ� �Է¹ٶ��ϴ�.");
           sighTextAddr.clear();
           sighTextAddr1.clear();
                 return;
              }
           }
           callAlert("���̵� ��밡��: ��밡���� ���̵��Դϴ�.");
          // setButtonTextFieldInitiate("Ȯ��");
		   }

	//�н����� Ȯ��
	private void handlerpwcheckAction() {
		
		if(!(sighTextPw.getText().equals(sighTextPw2.getText()))) {
			callAlert("��й�ȣ�� �������� �ʽ��ϴ�.: ��й�ȣ�� �����ϰ� �־��ּ���");
			sighTextPw.clear(); sighTextPw2.clear();
			return;
		}
		callAlert("��й�ȣ Ȯ�οϷ� : ������ �������ּ���");
		setButtonTextFieldInitiate("����");
		
	}
	

	
	
	//��Ÿ �˸�â
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}

	
	//������� ����
	private void inputDecimalFormatResidentt(TextField textField) {

		DecimalFormat format = new DecimalFormat("##");
		// ���� �Է½� ���� ���� �̺�Ʈ ó��
		textField.setTextFormatter(new TextFormatter<>(event -> {
			// �Է¹��� ������ ������ �̺�Ʈ�� ������.
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			// ������ �м��� ���� ��ġ�� ������.
			ParsePosition parsePosition = new ParsePosition(0);
			// �Է¹��� ����� �м���ġ�� �������������� format ����� ��ġ���� �м���.
			Object object = format.parse(event.getControlNewText(), parsePosition);
			// ���ϰ��� null �̰ų�,�Է��ѱ��̿ͱ����м���ġ���� ���������(�� �м������������� ����)�ų�,�Է��ѱ��̰� 4�̸�(3�ڸ��� �Ѿ����� ����.)
			// �̸� null ������.

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()	|| event.getControlNewText().length() == 9 || event.getControlNewText()=="."
					||event.getControlNewText()=="-") {
				return null;
			} else {
				return event;
			}
		}));
		
	}
	
	//�ڵ��� ����
	private void inputDecimalFormatPhone(TextField textField) {

		DecimalFormat format = new DecimalFormat("##");
		// ���� �Է½� ���� ���� �̺�Ʈ ó��
		textField.setTextFormatter(new TextFormatter<>(event -> {
			// �Է¹��� ������ ������ �̺�Ʈ�� ������.
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			// ������ �м��� ���� ��ġ�� ������.
			ParsePosition parsePosition = new ParsePosition(0);
			// �Է¹��� ����� �м���ġ�� �������������� format ����� ��ġ���� �м���.
			Object object = format.parse(event.getControlNewText(), parsePosition);
			// ���ϰ��� null �̰ų�,�Է��ѱ��̿ͱ����м���ġ���� ���������(�� �м������������� ����)�ų�,�Է��ѱ��̰� 4�̸�(3�ڸ��� �Ѿ����� ����.)
			// �̸� null ������.

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()	|| event.getControlNewText().length() == 12||event.getControlNewText()=="-") {
				return null;
			} else {
				return event;
			}
		}));
		
	}
	
	
	}

