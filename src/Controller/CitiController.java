package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CitiController implements Initializable{
	@FXML private Button elecChart;
	@FXML private Button waterChart;
	@FXML private Button gasChart;
	@FXML private Button heatChart;
	@FXML private Button btnOk;
	@FXML private TextField elecCost;
	@FXML private TextField waterCost;
	@FXML private TextField gasCost;
	@FXML private TextField heatCost;
	@FXML private TextField toCost;
	@FXML private TextField totalCost;
	@FXML private TextField textDong;
	@FXML private TextField textHo;
	@FXML private DatePicker costDay;
	
	
	public Stage citizenstage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

	
}
