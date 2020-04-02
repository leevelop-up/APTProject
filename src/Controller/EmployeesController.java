package Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;


public class EmployeesController implements Initializable{
	public Stage employeestage;
	@FXML private TextField empTextSearch;
	@FXML private Button empSearch;
	@FXML private Button empAdd;
	@FXML private Button empEdit;
	@FXML private Button empClose;
	ArrayList<Employee> dbArrayList;
	@FXML private TableView<Employee> t2TableView;

	  ObservableList<Employee>t2Listdata=FXCollections.observableArrayList();
	
	public Employee selectEmployee;
	public int selectEmployeeindex;
	private File imageDir=new File("C:/images");
	private String fileName="";
	private File selectFile=null;
	Image image=null;

	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//���̺�� ����
		setEmployeeTableView();
		//�߰���ư Ŭ���� �˾�
		empAdd.setOnAction((e)-> { handlerBtnAddAction(); });
		
		//ã���ư
		empSearch.setOnAction((e)-> { handlerBtnSearchAction(); });
		//������ư
		empEdit.setOnAction((e)-> { handlerBtnEditAction(); });
		//�ݱ��ư
		empClose.setOnAction((e)-> { handlerBtnCloseAction(); });
	}
	//���̺�� ����
	private void setEmployeeTableView() {
		TableColumn tcnumber=t2TableView.getColumns().get(0);
		tcnumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		tcnumber.setStyle("-fx-alignment: CENTER");		
		TableColumn tcname=t2TableView.getColumns().get(1);
		tcname.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcname.setStyle("-fx-alignment: CENTER");		
		TableColumn tcage=t2TableView.getColumns().get(2);
		tcage.setCellValueFactory(new PropertyValueFactory<>("age"));
		tcage.setStyle("-fx-alignment: CENTER");		
		TableColumn tcdepartment=t2TableView.getColumns().get(3);
		tcdepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
		tcdepartment.setStyle("-fx-alignment: CENTER");		
		TableColumn tcEmploymentday=t2TableView.getColumns().get(4);
		tcEmploymentday.setCellValueFactory(new PropertyValueFactory<>("Employmentday"));
		tcEmploymentday.setStyle("-fx-alignment: CENTER");		
		TableColumn tcposition=t2TableView.getColumns().get(5);
		tcposition.setCellValueFactory(new PropertyValueFactory<>("position"));
		tcposition.setStyle("-fx-alignment: CENTER");		
		TableColumn tcaddress=t2TableView.getColumns().get(6);
		tcaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		tcaddress.setStyle("-fx-alignment: CENTER");		
		TableColumn tcPhone=t2TableView.getColumns().get(7);
		tcPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
		tcPhone.setStyle("-fx-alignment: CENTER");		
		TableColumn tcimage=t2TableView.getColumns().get(8);
		tcimage.setCellValueFactory(new PropertyValueFactory<>("image"));
		tcimage.setStyle("-fx-alignment: CENTER");			

		t2TableView.setItems(t2Listdata);
		dbArrayList=EmployeeDAO.getEmployeeDataTotalData();
		for(Employee employee : dbArrayList) {
			t2Listdata.add(employee);
		}
	}

	//ã���ư
	private void handlerBtnSearchAction() {
		for(Employee employee : dbArrayList) {
		if(empTextSearch.getText().trim().equals(employee.getAddress())) {
			t2TableView.getSelectionModel().select(employee);
			t2TableView.scrollTo(employee);
		}
	}
		
	}
	
	//�߰���ư Ŭ���� �˾�
	private void handlerBtnAddAction() {
		Parent root=null;
		try {
		Stage editStage=new Stage(StageStyle.UTILITY);
		  editStage.initModality(Modality.WINDOW_MODAL);
		  editStage.initOwner(employeestage);
		  editStage.setTitle("������ �߰����ּ���");
		  FXMLLoader loader= new FXMLLoader(getClass().getResource("../View/employee.fxml"));
		  root = loader.load();
		  
		  //employee���� ����
		  ImageView t2ImageView=(ImageView)root.lookup("#t2ImageView");
		  Button t2BtnImage=(Button)root.lookup("#t2BtnImage");
		  Button t2BtnInput=(Button)root.lookup("#t2BtnInput");
		  Button t2BtnCancel=(Button)root.lookup("#t2BtnCancel");
		  Button t2BtnClear=(Button)root.lookup("#t2BtnClear");
		  TextField t2TextPhone=(TextField)root.lookup("#t2TextPhone");
		  TextField t2TextAddress=(TextField)root.lookup("#t2TextAddress");
		  TextField t2TextNumber=(TextField)root.lookup("#t2TextNumber");
		  ComboBox<String> t2CmbPosition=(ComboBox)root.lookup("#t2CmbPosition");
		  TextField t2TextAge=(TextField)root.lookup("#t2TextAge");
		  TextField t2TextName=(TextField)root.lookup("#t2TextName");
		  ComboBox<String> t2CmbDepart=(ComboBox)root.lookup("#t2CmbDepart");
		  DatePicker t2dayEmp=(DatePicker)root.lookup("#t2dayEmp");

			ObservableList<String>t2ListDepart=FXCollections.observableArrayList();
			ObservableList<String>t2ListPosition=FXCollections.observableArrayList();
		 
			//�޺��ڽ�
		  t2ListDepart.addAll("������","������","����","��ȭ��");
		  t2CmbDepart.setItems(t2ListDepart);
		  t2ListPosition.addAll("����","����","����","��ȭ��");
		  t2CmbPosition.setItems(t2ListPosition);
		  

	      selectEmployee=t2TableView.getSelectionModel().getSelectedItem();
	      int index=t2TableView.getSelectionModel().getSelectedIndex();
	  
	
		  //���� ��ư �Է½� �ۼ��� ������ tableview�� �Է�
		  t2BtnInput.setOnAction((e)->{
			 imagesave();
			 String number=t2TextNumber.getText();
			 String name=t2TextName.getText();
			 String age=t2TextAge.getText();
			 String Depart=t2CmbDepart.getSelectionModel().getSelectedItem();
			 String Employmentday=t2dayEmp.getValue().toString();
			 String position=t2CmbPosition.getSelectionModel().getSelectedItem();
			 String address=t2TextAddress.getText();
			 String phone=t2TextPhone.getText();
			 String image=fileName;
			 

			 List<Employee> emList=new ArrayList<>();
			 Employee employee=new Employee(number, name, age, Depart, Employmentday, position, address, phone, fileName);
			 emList.add(employee);
			 ObservableList<Employee> obemList=FXCollections.observableArrayList();
			 for(Employee em : emList) {
				 obemList.add(em);
			 }
			 t2TableView.setItems(obemList);
			 int count=EmployeeDAO.insertEmployeeData(employee);
			 if(count!=0) {
					callAlert("�Է¼��� : ����Ÿ���̽� �Է��� �����Ǿ����ϴ�. ");
					
				}
			 editStage.close();	
		  });
		  //�̹��� ���� ��������
			 t2BtnImage.setOnAction((e1)->{
					FileChooser fileChooser=new FileChooser();
					fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File","*.png","*.jpg","*.gif"));
					selectFile=fileChooser.showOpenDialog(employeestage);
					String localURL="";
					if(selectFile !=null) {
						try {
							localURL=selectFile.toURI().toURL().toString();
						} catch (MalformedURLException e) {  }
					}
		
					t2ImageView.setImage(new Image(localURL,false));
					fileName=selectFile.getName();
			 });
		  //��� ��ư Ŭ���� â �ݱ�
			 t2BtnCancel.setOnAction((e)->{
				 editStage.close();	
			 });
			 
		//�ʱ�ȭ ��ư Ŭ���� �Է����� �ʱ�ȭ
			 t2BtnClear.setOnAction((e)->{
				 t2dayEmp.setValue(null);
				 t2TextNumber.clear();
				 t2TextName.clear();
				 t2TextAge.clear();
				 t2TextAddress.clear();
				 t2TextPhone.clear();
				 t2CmbDepart.getSelectionModel().clearSelection();
				 t2CmbPosition.getSelectionModel().clearSelection();
				 t2ImageView.setImage(new Image(getClass().getResource("../images/�̹���.jpg").toString()));
				 fileName="";		 
			 });

		  Scene scene=new Scene(root);
		  editStage.setScene(scene);
		  editStage.show();
		  
		} catch (IOException e) {       }
		
	}
	
	//������ưŬ����
	private void handlerBtnEditAction() {
			Parent root=null;
			try {
			Stage editStage=new Stage(StageStyle.UTILITY);//���ο� ���������� ����� ����
			  editStage.initModality(Modality.WINDOW_MODAL);
			  editStage.initOwner(employeestage);//������ ������?  ������
			  editStage.setTitle("�������� ����");
			  FXMLLoader loader= new FXMLLoader(getClass().getResource("../View/employee.fxml"));
			  root = loader.load();
			  
			  //employee���� ����
			  ImageView t2ImageView=(ImageView)root.lookup("#t2ImageView");
			  Button t2BtnImage=(Button)root.lookup("#t2BtnImage");
			  Button t2BtnInput=(Button)root.lookup("#t2BtnInput");
			  Button t2BtnCancel=(Button)root.lookup("#t2BtnCancel");
			  Button t2BtnClear=(Button)root.lookup("#t2BtnClear");
			  TextField t2TextPhone=(TextField)root.lookup("#t2TextPhone");
			  TextField t2TextAddress=(TextField)root.lookup("#t2TextAddress");
			  TextField t2TextNumber=(TextField)root.lookup("#t2TextNumber");
			  ComboBox<String> t2CmbPosition=(ComboBox)root.lookup("#t2CmbPosition");
			  TextField t2TextAge=(TextField)root.lookup("#t2TextAge");
			  TextField t2TextName=(TextField)root.lookup("#t2TextName");
			  ComboBox<String> t2CmbDepart=(ComboBox)root.lookup("#t2CmbDepart");
			  DatePicker t2dayEmp=(DatePicker)root.lookup("#t2dayEmp");

			  selectEmployee=t2TableView.getSelectionModel().getSelectedItem();
		      int selectEmployeeindex=t2TableView.getSelectionModel().getSelectedIndex();
		 
		      ObservableList<String>t2ListDepart=FXCollections.observableArrayList();
			ObservableList<String>t2ListPosition=FXCollections.observableArrayList();
			  //�޺��ڽ�
			  t2ListDepart.addAll("������","������","����","��ȭ��");
			  t2CmbDepart.setItems(t2ListDepart);
			  t2ListPosition.addAll("����","����","����","��ȭ��");
			  t2CmbPosition.setItems(t2ListPosition);
			  
			
			 
			  t2CmbPosition.getSelectionModel().select(selectEmployee.getPosition());
			  t2TextAge.setText(selectEmployee.getAge());
			  t2TextName.setText(selectEmployee.getName());
			  t2CmbDepart.getSelectionModel().select(selectEmployee.getDepartment());
			  t2dayEmp.setValue(LocalDate.parse(selectEmployee.getEmploymentday()));
			  t2TextPhone.setText(selectEmployee.getPhone());
			  t2TextAddress.setText(selectEmployee.getAddress());
				 image=new Image("file:///"+imageDir.getAbsolutePath()+"/"+selectEmployee.getImage());		
				t2ImageView.setImage(image);
				 t2TextNumber.setText(selectEmployee.getNumber());
		  
		      

		      
			  //���� ��ư �Է½� �ۼ��� ������ tableview�� �Է�
			  t2BtnInput.setOnAction((e)->{
					if (!(fileName.equals(""))) { //fileName.equals("") :  �̹����� �ȹٲ� ��Ȳ��. ������ �ȵ� ����̴ϱ� // !( fileName.equals("")) : �̹����� �ٲۻ�Ȳ
						imageDelete();
						imagesave();
					}
					String name=t2TextName.getText();
					String age=t2TextAge.getText();
					 String Depart=t2CmbDepart.getSelectionModel().getSelectedItem();
					 String Employmentday=t2dayEmp.getValue().toString();
					 String position=t2CmbPosition.getSelectionModel().getSelectedItem();
					 String address=t2TextAddress.getText();
					 String phone=t2TextPhone.getText();
					 String image=fileName;
					 String number=t2TextNumber.getText();
 
				 Employee employee=new Employee(number, name, age, Depart, Employmentday  , position, address, phone, image);
				 
				 int count=EmployeeDAO.updateEmployeeData(employee);
				 if(count!=0) {
					 t2Listdata.remove(selectEmployeeindex);
					 t2Listdata.add(selectEmployeeindex, employee);
					 int arrayIndex=dbArrayList.indexOf(selectEmployee);
						dbArrayList.set(arrayIndex, employee);
						callAlert("�Է¼��� : ����Ÿ���̽� �Է��� �����Ǿ����ϴ�. ");
						
					}else {
						return;
					}
				  editStage.close();

			  });
			  //�̹��� ���� ��������
				 t2BtnImage.setOnAction((e1)->{
						FileChooser fileChooser=new FileChooser();
						fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File","*.png","*.jpg","*.gif"));
						selectFile=fileChooser.showOpenDialog(employeestage);
						String localURL=null;
						if(selectFile !=null) {
							try {
								localURL=selectFile.toURI().toURL().toString();
							} catch (MalformedURLException e) {  }
						}
			
						t2ImageView.setImage(new Image(localURL,false));
						fileName=selectFile.getName();
				 });
				 
			  //��� ��ư Ŭ���� â �ݱ�
				 t2BtnCancel.setOnMouseClicked((e)->{
					 editStage.close();	
				 });
			//�ʱ�ȭ ��ư Ŭ���� �Է����� �ʱ�ȭ
				 t2BtnClear.setOnAction((e)->{
					 t2dayEmp.setValue(null);
					 t2TextNumber.clear();
					 t2TextName.clear();
					 t2TextAge.clear();
					 t2TextAddress.clear();
					 t2TextPhone.clear();
					 t2CmbDepart.getSelectionModel().clearSelection();
					 t2CmbPosition.getSelectionModel().clearSelection();
					 t2ImageView.setImage(new Image(getClass().getResource("../images/�̹���.jpg").toString()));
					 fileName="";		 
				 });

			  Scene scene=new Scene(root);
			  editStage.setScene(scene);
			  editStage.show();
			
			} catch (IOException e) { callAlert("���â:��ĭ�� ������ ������");      }
			
		}
	
	//�ݱ��ư
	private void handlerBtnCloseAction() {
		
		}
	
	
	//�̹��� ����
	private void imagesave() {
		if(!imageDir.exists()) {
			imageDir.mkdir();
		
		}
		FileInputStream fis=null;
		BufferedInputStream bis=null;
		FileOutputStream fos=null;
		BufferedOutputStream bos=null;

		try {
			fis=new FileInputStream(selectFile);
			bis=new BufferedInputStream(fis);

			fileName="employee"+System.currentTimeMillis()+""+selectFile.getName();
			fos=new FileOutputStream(imageDir.getAbsolutePath()+"\\"+fileName);
			bos=new BufferedOutputStream(fos);
	
			int data=-1;
			while((data=bis.read())!=-1){
			bos.write(data);
			bos.flush();
			
			
		}
	

		}catch (Exception e) {
			callAlert("�̹��� ���� ���� : C/images/�������� ���� ���� �ٶ�.");
		}finally {
			try {
			if(fis !=null) {fis.close();} 
			if(bis !=null) {bis.close();} 
			if(fos !=null) {fos.close();} 
			if(bos !=null) {bos.close();} 
			
			}catch (IOException e){}
		}//end of finall
	
	}
	
	//�̹��� ����
	private void imageDelete() {

		File imageFile = new File(imageDir.getAbsolutePath() + "\\" + selectEmployee.getImage());
		boolean delFlag = false;
		
		if (imageFile.exists() && imageFile.isFile()) {
			delFlag = imageFile.delete();
			if (delFlag == false)
				callAlert("�̹������� ����: �̹��� ���� ���� ���˹ٶ�");
		}
	}

	//�˸�â ����
	private void callAlert(String contentText) {
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("���!");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		
		alert.showAndWait();
	}
}
	

