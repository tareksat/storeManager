package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.BillModel;
import model.CustomerSearch;

public class BillSearchController {
	
	@FXML private DatePicker dateFrom, dateTo;
	@FXML private TextField orderNumberField;
	@FXML private TextField customerNameField;
	@FXML private TextField totalPriceFromField, totalPriceFieldTo;
	@FXML private TextField discountFromField, discountToField;
	
	@FXML private TableView<CustomerSearch> tableView;
	@FXML private TableColumn<CustomerSearch, String> customerNameColumn;
	@FXML private TableColumn<CustomerSearch, String> orderNumberColumn;
	@FXML private TableColumn<CustomerSearch, Date> dateColumn;
	@FXML private TableColumn<CustomerSearch, Float> totalPriceColumn; 
	@FXML private TableColumn<CustomerSearch, Float> discountColumn;
	
	private ArrayList<CustomerSearch> list = new ArrayList<>();
	/********************************************************************************************/
	public void setMain(){
		
		dateFrom.setValue(LocalDate.of(2000, 1, 1));
		dateTo.setValue(LocalDate.now());
		
	}
	/********************************************************************************************/
	public void initialize(){
		customerNameColumn.setCellValueFactory
        (new PropertyValueFactory<CustomerSearch, String>("customerName"));
		
		orderNumberColumn.setCellValueFactory
		        (new PropertyValueFactory<CustomerSearch, String>("billNum"));
		
		dateColumn.setCellValueFactory
		(new PropertyValueFactory<CustomerSearch, Date>("date"));
		
		totalPriceColumn.setCellValueFactory
		(new PropertyValueFactory<CustomerSearch, Float>("totalPrice"));
		
		discountColumn.setCellValueFactory
		(new PropertyValueFactory<CustomerSearch, Float>("discount"));
		
	}
	
	/********************************************************************************************/
	@FXML 
	public void viewBillDetails(){
		int i = tableView.getSelectionModel().getSelectedIndex();
		BillModel bill = BillModel.findByNumber(list.get(i).getBillNum());
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/BilldetailsView.fxml"));
			AnchorPane pane = loader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			
			BillDetailsController controller = loader.getController();
			controller.setMain(bill, stage);
			
			Scene scene = new Scene(pane);
			
			stage.setScene(scene);
			stage.setTitle("Bill details");
			stage.showAndWait();
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
		
		
	}
	
	/********************************************************************************************/
	@FXML
	public void dateSearch(){
				
		LocalDate startDate = dateFrom.getValue().plusMonths(1);
		LocalDate endDate = dateTo.getValue().plusMonths(1);
		
		list.clear();
		list = CustomerSearch.searchByDate(startDate.toString(), endDate.toString());
		for(CustomerSearch x:list){
			System.out.println(x.toString());
		}
		ObservableList<CustomerSearch> billList = FXCollections.observableArrayList(list);
		
		tableView.setItems(billList);
		
	}
	
	/********************************************************************************************/
	@FXML
	public void orderNumber(){

		list.clear();
		list = CustomerSearch.searchByBillNumber(orderNumberField.getText());
		ObservableList<CustomerSearch> billList = FXCollections.observableArrayList(list);
		tableView.setItems(billList);
		
	}
	/********************************************************************************************/
	@FXML
	public void customerNameSearch(){

		list.clear();
		list = CustomerSearch.searchByName(customerNameField.getText());
		ObservableList<CustomerSearch> billList = FXCollections.observableArrayList(list);
		tableView.setItems(billList);
	}
	/********************************************************************************************/
	@FXML
	public void totalPriceSearch(){
		int min = Integer.parseInt(totalPriceFromField.getText());
		int max = Integer.parseInt(totalPriceFieldTo.getText());
		
		list = CustomerSearch.searchByPrice(min, max);
		ObservableList<CustomerSearch> billList = FXCollections.observableArrayList(list);
		tableView.setItems(billList);
		
	}
	/********************************************************************************************/
	@FXML
	public void discountSearch(){
		int min = Integer.parseInt(discountFromField.getText());
		int max = Integer.parseInt(discountToField.getText());
		
		list = CustomerSearch.searchByDiscount(min, max);
		ObservableList<CustomerSearch> billList = FXCollections.observableArrayList(list);
		tableView.setItems(billList);
	}
	/********************************************************************************************/
	@FXML
	public void findCustomer(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/CustomerSelectView.fxml"));
			AnchorPane pane = loader.load();
			
			CustomerSelectController customerSelectController = loader.getController();
			
			Scene scene = new Scene(pane);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			
			customerSelectController.setMain(stage);
			stage.setScene(scene);
			stage.showAndWait();
			customerNameField.setText(CustomerSelectController.selectedCustomer.getName());
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
		
	}
	/********************************************************************************************/
}
