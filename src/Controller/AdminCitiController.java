package Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminCitiController implements Initializable{
	@FXML public TableView<Citizen> citizenTableView;
	@FXML public Button citiBtnEdit;
	@FXML public Button citiBtnClear;
	@FXML public Button citiBtnClose;
	@FXML public Button citiBtnSearch;
	@FXML public TextField citiTextSearch;

	public int selectCitizenindex;
	public Citizen selectCitizen;
	public Stage adcitistage;
	
	ObservableList<Citizen> citiListData=FXCollections.observableArrayList();
	ArrayList<Citizen> dbArrayList;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//���̺� ����
		setCitizenTableView();
		//���� ��ư
		citiBtnEdit.setOnAction((e)-> { handlerBtnEditAction(); });
		//������ư
		citiBtnClear.setOnAction((e)-> { handlerBtnClearAction(); });
	}
	
	



	//���̺� ����
	private void setCitizenTableView() {
		TableColumn tcaddress=citizenTableView.getColumns().get(0);
		tcaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		tcaddress.setStyle("-fx-alignment: CENTER");	
		TableColumn tcaddress2=citizenTableView.getColumns().get(1);
		tcaddress2.setCellValueFactory(new PropertyValueFactory<>("address2"));
		tcaddress2.setStyle("-fx-alignment: CENTER");	
		TableColumn tcpassword=citizenTableView.getColumns().get(2);
		tcpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
		tcpassword.setStyle("-fx-alignment: CENTER");	
		TableColumn tcname=citizenTableView.getColumns().get(3);
		tcname.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcname.setStyle("-fx-alignment: CENTER");	
		TableColumn tcgender=citizenTableView.getColumns().get(4);
		tcgender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		tcgender.setStyle("-fx-alignment: CENTER");	
		TableColumn tcbirth=citizenTableView.getColumns().get(5);
		tcbirth.setCellValueFactory(new PropertyValueFactory<>("birth"));
		tcbirth.setStyle("-fx-alignment: CENTER");	
		TableColumn tcemail=citizenTableView.getColumns().get(6);
		tcemail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tcemail.setStyle("-fx-alignment: CENTER");	
		TableColumn tcphone=citizenTableView.getColumns().get(7);
		tcphone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		tcphone.setStyle("-fx-alignment: CENTER");	
		
		dbArrayList=SignUpDAO.getCitizenDataTotalData();
		for(Citizen citizen:dbArrayList){
			citiListData.add(citizen);
		}		
		
		citizenTableView.setItems(citiListData);
		
		
	}
	//���� ��ư
	private void handlerBtnEditAction() {
			Parent root=null;
			  try {
			Stage czEditStage=new Stage(StageStyle.UTILITY);
			czEditStage.initModality(Modality.WINDOW_MODAL);
			czEditStage.initOwner(adcitistage);
			czEditStage.setTitle("������� ����â");
			  FXMLLoader loader= new FXMLLoader(getClass().getResource("../View/adminciti.fxml"));
		
				root = loader.load();
		
			  
			
			  //employee���� ����
			  Button caBtnAdd=(Button)root.lookup("#caBtnAdd");
			  Button caBtnCancel=(Button)root.lookup("#caBtnCancel");
			  Button caBtnClear=(Button)root.lookup("#caBtnClear");
			  TextField caTextAddr=(TextField)root.lookup("#caTextAddr");
			  TextField caTextAddr1=(TextField)root.lookup("#caTextAddr1");
			  TextField caTextPass=(TextField)root.lookup("#caTextPass");
			  TextField caTextName=(TextField)root.lookup("#caTextName");
			  TextField caTextday=(TextField)root.lookup("#caTextday");
			  ComboBox<String> caTextGender=(ComboBox)root.lookup("#caTextGender");
			  TextField caTextNumber=(TextField)root.lookup("#caTextNumber");
			  TextField caTextEmail=(TextField)root.lookup("#caTextEmail");
			  
			    selectCitizen=citizenTableView.getSelectionModel().getSelectedItem();
		       selectCitizenindex=citizenTableView.getSelectionModel().getSelectedIndex();
		 
		      ObservableList<String>ListGender=FXCollections.observableArrayList();
			
			  //�޺��ڽ�
		      ListGender.addAll("��","��");
			  caTextGender.setItems(ListGender);
	
			 
			  caTextAddr.setText(selectCitizen.getAddress());
			  caTextAddr1.setText(selectCitizen.getAddress2());
			  caTextPass.setText(selectCitizen.getPassword());
			  caTextName.setText(selectCitizen.getName());
			  caTextGender.getSelectionModel().select(selectCitizen.getGender());
			  caTextday.setText(selectCitizen.getBirth());
			  caTextEmail.setText(selectCitizen.getEmail());
			  caTextNumber.setText(selectCitizen.getPhone());
			  
			  caBtnAdd.setOnAction((e)->{
				  String addr=caTextAddr.getText();
				  String addr2=caTextAddr.getText();
				  String pass=caTextPass.getText();
				  String name=caTextName.getText();
				  String Gender=caTextGender.getSelectionModel().getSelectedItem();
				  String day=caTextday.getText();
				  String emil=caTextEmail.getText();
				  String phone=caTextName.getText();
				  
				  Citizen citizen=new Citizen(null,addr, addr2, pass, null, name, Gender, day, emil, phone);
					 int count=SignUpDAO.updateCitizenData(citizen);
					 if(count!=0) {
						 citiListData.remove(selectCitizenindex);
						 citiListData.add(selectCitizenindex, citizen);
						 int arrayIndex=dbArrayList.indexOf(selectCitizen);
							dbArrayList.set(arrayIndex, citizen);

						}else {
							return;
						}
					 czEditStage.close();
			  });
			  //�ʱ�ȭ ��ư
			  caBtnClear.setOnAction((e)->{
				  caTextAddr.clear();
				  caTextPass.clear();
				  caTextName.clear();
				  caTextGender.getSelectionModel().clearSelection();
				  caTextday.clear();
				  caTextEmail.clear();
				  caTextNumber.clear();
				  
					  });
			  
			  
			  
			  //��ҹ�ư Ŭ���� ����â �ݱ�
			  caBtnCancel.setOnMouseClicked((e)->{
				  czEditStage.close();	
				  
				 });
				  
			  Scene scene=new Scene(root);
			  czEditStage.setScene(scene);
			  czEditStage.show();
			  
			  
				} catch (IOException e) {
					callAlert("���â:��ĭ�� ������ ������");
					e.printStackTrace();
				}
			
			
				  
				

			  
			  
	}
	//������ư
	private void handlerBtnClearAction() {
			int count=SignUpDAO.deleteCitizenData(selectCitizen.getAddress());
			if(count!=0) {				
				citiListData.remove(selectCitizenindex);
				dbArrayList.remove(selectCitizen);
				callAlert("�����Ϸ�: " + selectCitizen.getAddress()+ "���� �ڷᰡ �����Ǿ����ϴ�.");
				
			}else {
				return;
			}	
			
		}		
		
	private void callAlert(String contentText) {
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("���!");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		
		alert.showAndWait();
	}
	
	
	
	
}
