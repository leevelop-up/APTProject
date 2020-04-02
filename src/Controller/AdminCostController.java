package Controller;



import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import Model.AdminCost;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
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
import javafx.scene.input.MouseEvent;

import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;


import java.io.File;
import java.io.IOException;

public class AdminCostController implements Initializable{
	@FXML public TableView<AdminCost> costTableView;
	@FXML private Button costBtnSearch;
	@FXML private Button costBtnadd;
	@FXML private Button costBtnEdit;
	@FXML private Button costBtnClose;
	@FXML private Button costBtnDel;
	@FXML private Button costBtnChart;
	@FXML private Button inputcost;
	@FXML private TextField costTextDong;
	@FXML private TextField costTextHo;
	@FXML private TextField costTextelec;
	@FXML private TextField costTextWater;
	@FXML private TextField costTextGas;
	@FXML private TextField costTextHeat;
	@FXML private TextField costTextJoint;
	@FXML private TextField costTextTotal;
	@FXML private TextField textSearch;
	@FXML private ComboBox<String> costCmbRecive;
	@FXML private DatePicker costDateRecive;

	public Stage admincoststage;
	public AdminCost selectCost;
	public int selectCostIndex;

	ObservableList<AdminCost> costListData=FXCollections.observableArrayList();
	ObservableList<String> listRecive=FXCollections.observableArrayList();
	
	ArrayList<AdminCost> dbArrayList;
	//List<AdminCost> costData = new ArrayList<>();
	
	
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listRecive.addAll("�ϳ�","�̳�");
		costCmbRecive.setItems(listRecive);
		
		
		//���̺�� ����
		setcostTableView();
		//ã�� ���
		costBtnSearch.setOnAction((e)-> { handlerSearchAction(); });
		//���� ���
		costBtnDel.setOnAction((e)-> { handlerDelAction(); });
		//�ѹ� Ŭ���� �ؽ�Ʈ�� ���� ����.
		costTableView.setOnMouseClicked((e)->{handlecostTableviewAction(e);});
		//������ư Ŭ���� ���� �����ȴ�.
		costBtnEdit.setOnAction((e)->{handleEditAction();});
		//+++++++++++���� �ִ°ǵ� �ȵ���.
		inputcost.setOnAction((e)-> { handlerinputcostAction(); });
		//�߰� ��ưŬ���� ȭ����ȯ
		costBtnadd.setOnAction((e)-> { handlercostBtnaddAction(); });
		//�ݱ� ��ưŬ���� ȭ�� ����
		costBtnClose.setOnAction((e)->{handlercostBtnCloseAction();});
		//��Ʈ
		costBtnChart.setOnAction((e)->{handlercostBtnChartAction();});
	}

	//���̺� ����
	private void setcostTableView() {
			TableColumn tcdong=costTableView.getColumns().get(0);
			tcdong.setCellValueFactory(new PropertyValueFactory<>("dong"));
			tcdong.setStyle("-fx-alignment: CENTER");
			
			TableColumn tcho=costTableView.getColumns().get(1);
			tcho.setCellValueFactory(new PropertyValueFactory<>("ho"));
			tcho.setStyle("-fx-alignment: CENTER");
			
			TableColumn tcelectric=costTableView.getColumns().get(2);
			tcelectric.setCellValueFactory(new PropertyValueFactory<>("electric"));
			tcelectric.setStyle("-fx-alignment: CENTER");
			
			TableColumn tcwater=costTableView.getColumns().get(3);
			tcwater.setCellValueFactory(new PropertyValueFactory<>("water"));
			tcwater.setStyle("-fx-alignment: CENTER");
			
			TableColumn tcgas=costTableView.getColumns().get(4);
			tcgas.setCellValueFactory(new PropertyValueFactory<>("gas"));
			tcgas.setStyle("-fx-alignment: CENTER");
			
			TableColumn tcheat=costTableView.getColumns().get(5);
			tcheat.setCellValueFactory(new PropertyValueFactory<>("heat"));
			tcheat.setStyle("-fx-alignment: CENTER");
			
			TableColumn tcjoint=costTableView.getColumns().get(6);
			tcjoint.setCellValueFactory(new PropertyValueFactory<>("joint"));
			tcjoint.setStyle("-fx-alignment: CENTER");
			
			TableColumn tcreceive=costTableView.getColumns().get(7);
			tcreceive.setCellValueFactory(new PropertyValueFactory<>("receive"));
			tcreceive.setStyle("-fx-alignment: CENTER");
			
			TableColumn tctatalCost=costTableView.getColumns().get(8);
			tctatalCost.setCellValueFactory(new PropertyValueFactory<>("tatalCost"));
			tctatalCost.setStyle("-fx-alignment: CENTER");
			
			TableColumn tcreciveDay=costTableView.getColumns().get(9);
			tcreciveDay.setCellValueFactory(new PropertyValueFactory<>("reciveDay"));
			tcreciveDay.setStyle("-fx-alignment: CENTER");
			
			dbArrayList=AdminCostDAO.getAdminCostTotalData();
			for(AdminCost admincost : dbArrayList) {
				costListData.add(admincost);
			}
			costTableView.setItems(costListData);
			
			
		}
	
	//�ѹ� Ŭ���� �ؽ�Ʈ�� ���� ����.
	private void handlecostTableviewAction(MouseEvent e) {
		selectCost=costTableView.getSelectionModel().getSelectedItem();
		selectCostIndex=costTableView.getSelectionModel().getSelectedIndex();
		costTextDong.setDisable(true);
		costTextHo.setDisable(true);
		
		if(e.getClickCount()==1) {
			try {
			
			costTextDong.setText(selectCost.getDong());
			costTextHo.setText(selectCost.getHo());
			costTextelec.setText(selectCost.getElectric());
			costTextGas.setText(selectCost.getGas());
			costTextHeat.setText(selectCost.getHeat());
			costTextJoint.setText(selectCost.getJoint());
			costTextWater.setText(selectCost.getWater());
			costCmbRecive.getSelectionModel().select(selectCost.getReceive());
			costTextTotal.setText(selectCost.getTatalCost());
			costDateRecive.setValue(LocalDate.parse(selectCost.getReciveDay()));
			}catch(Exception e1) {
				callAlert("�����̺� Ŭ�� ����: �����̺��� Ŭ������������");
			}
		}
		
	}
	
	//������ư Ŭ���� ���� �����ȴ�.
	private void handleEditAction() {
		String costtotal="";
		
		 costtotal=String.valueOf(Integer.parseInt(costTextelec.getText())+
					Integer.parseInt(costTextGas.getText())+Integer.parseInt(costTextHeat.getText())+Integer.parseInt(costTextJoint.getText())+
					Integer.parseInt(costTextWater.getText()));
	
		 String info=null;
		AdminCost adminCost=new AdminCost(
				costTextDong.getText(),
				costTextHo.getText(),
				costTextelec.getText(),
				costTextWater.getText(),
				costTextGas.getText(),
				costTextHeat.getText(),
				costTextJoint.getText(), costCmbRecive.getSelectionModel().getSelectedItem(),
				costtotal, costDateRecive.getValue().toString(),info);
		int count=AdminCostDAO.updateAdminCostData(adminCost);
		if(count !=0) {
			costListData.remove(selectCostIndex);
			costListData.add(selectCostIndex, adminCost);
			int arrayin=dbArrayList.indexOf(selectCost);
			dbArrayList.set(arrayin, adminCost);
			
		}
		
		handleBtnClearAction();
		
	}
	//���� ���
	private void handlerDelAction() {
		int count=AdminCostDAO.deleteAdminCostData(selectCost.getDong());
				if(count !=0) {
			costListData.remove(selectCostIndex);
			dbArrayList.remove(selectCost);
				}else {return;}
	
	}

	//ã�� ��� ----------���� �ٸ��˻������� �˸�â �ߵ���!
	private void handlerSearchAction() {
		
		for(AdminCost adminCost : dbArrayList) {
			if(textSearch.getText().trim().equals(adminCost.getDong()+adminCost.getHo())) {
				costTableView.getSelectionModel().select(adminCost);
				costTableView.scrollTo(adminCost);
			}
		}
	
		}
	
	//�ҷ�����
	private void handlerinputcostAction(){ 
//	 FileChooser fileChooser=new FileChooser();
//	      fileChooser.getExtensionFilters().add(new ExtensionFilter("����","*.xlsx"));
//      File file=fileChooser.showOpenDialog(admincoststage);
		   try {
		         Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + 
		               "D:\\java\\cost.xlsx");
		      } catch (IOException e) {
		       
		      }
      }

	//�߰� ��ưŬ���� ȭ����ȯ
	private void handlercostBtnaddAction() {
		
		if(costTextDong!=null&&costTextHo!=null&&costTextelec!=null && costTextWater!=null && costTextGas!=null && costTextHeat!=null && costTextJoint!=null) {
		String total="";
		String info=null;
			total=String.valueOf(Integer.parseInt(costTextelec.getText())+
					Integer.parseInt(costTextWater.getText())+
					Integer.parseInt(costTextGas.getText())+
					Integer.parseInt(costTextHeat.getText())+
					Integer.parseInt(costTextJoint.getText()));

		

		AdminCost admincost=new AdminCost(
				
				costTextDong.getText(),
				costTextHo.getText(),
				costTextelec.getText(),
				costTextWater.getText(),
				costTextGas.getText(),
				costTextHeat.getText(),
				costTextJoint.getText(),
				costCmbRecive.getSelectionModel().getSelectedItem(),
				total,
				costDateRecive.getValue().toString(),info);


		
		costListData.add(admincost);
		int count=AdminCostDAO.insertAdminCostData(admincost);
		if(count!=0) {
			callAlert("�Է¼��� : ����Ÿ���̽� �Է��� �����Ǿ����ϴ�. ");
			handleBtnClearAction();
		}
		}else {callAlert("�Է½��� : ����Ÿ���̽� �Է��� ���еǾ����ϴ�. ");}
		handleBtnClearAction();
	}
	
	//�ݱ� ��ưŬ���� ȭ�� ����
	private void handlercostBtnCloseAction() {
			Platform.exit();
			
		}
	//�ʱ�ȭ ��ưŬ���� ���ʱ�ȭ
	private void handleBtnClearAction() {

		costTextDong.clear();
		costTextHo.clear();
		costTextelec.clear();
		costTextWater.clear();
		costTextGas.clear();
		costTextHeat.clear();
		costTextJoint.clear();
		costTextTotal.clear();
	

	
	}
	
	//��Ʈ
	private void handlercostBtnChartAction() {
		try {
		Stage bcStage = new Stage(StageStyle.UTILITY);
		bcStage.initModality(Modality.WINDOW_MODAL);
		bcStage.initOwner(admincoststage); // ����â�� ������! initOwner () ����â�� mainStage��.
		bcStage.setTitle("BarChart");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/avgchart.fxml"));
		Parent root;

			root = loader.load();
		
		
		//**********ID�� ã�Ƽ� �;��Ѵ� @FXML private Button c1BtnExit**********
		BarChart barChart=(BarChart)root.lookup("#barChart");
		Button charBtnclose = (Button)root.lookup("#charBtnclose");

		//**************�̺�Ʈ ��� �� �ʱ�ȭ, �ڵ鷯����****************************************
		//����Ʈ �ʱ�ȭ�۾�
		//����
		XYChart.Series series101  =new XYChart.Series(); 
		series101.setName("�� ��");
		ObservableList apt101List=FXCollections.observableArrayList();
		for(int i=0;i<costListData.size();i++) {
			apt101List.add(new XYChart.Data<>(costListData.get(i).getDong(), Integer.parseInt(costListData.get(i).getTatalCost())));				
		}
		
		series101.setData(apt101List);
		barChart.getData().add(series101);
		
	

		//********************************************************************************
		charBtnclose.setOnAction(e-> bcStage.close() );
		

		Scene scene = new Scene(root);
		bcStage.setScene(scene);
		bcStage.show();
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}	
	
	
	//��Ÿ �˸�
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
	
}
