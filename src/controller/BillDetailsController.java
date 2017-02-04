package controller;

import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.BillDetails;
import model.BillDetailsTable;
import model.BillModel;
import model.CustomerModel;
import model.ItemModel;

public class BillDetailsController {
	
	@FXML private Label orderNumberLabel;
	@FXML private Label customerNameLabel;
	@FXML private Label discountLabel;
	@FXML private Label totalPriceLabel;
	@FXML private Label dateLabel;
	
	@FXML private TableView<BillDetailsTable> tableView;
	@FXML private TableColumn<BillDetailsTable, String> itemNameColumn;
	@FXML private TableColumn<BillDetailsTable, Integer> QTYColumn;
	@FXML private TableColumn<BillDetailsTable, Float> unitPriceColumn;
	@FXML private TableColumn<BillDetailsTable, Float> totalPriceColumn;
	
	private BillModel bill;
	private Stage stage;
	
	public void initialize(){
		itemNameColumn.setCellValueFactory
        (new PropertyValueFactory<BillDetailsTable, String>("itemName"));
		
		QTYColumn.setCellValueFactory
		        (new PropertyValueFactory<BillDetailsTable, Integer>("qty"));
		
		unitPriceColumn.setCellValueFactory
		(new PropertyValueFactory<BillDetailsTable, Float>("unitPrice"));
		
		totalPriceColumn.setCellValueFactory
		(new PropertyValueFactory<BillDetailsTable, Float>("totalPrice"));
		
	}
	
	public void setMain(BillModel bill, Stage stage){
		this.stage = stage;
		this.bill = bill;
		
		orderNumberLabel.setText(bill.getNumber());
		customerNameLabel.setText(CustomerModel.findByID(bill.getCustomerId()).getName());
		discountLabel.setText(bill.getDiscount()+"");
		totalPriceLabel.setText(bill.getTotalPrice()+"");
		dateLabel.setText(bill.getDate().toString());
		
		ArrayList<BillDetails> billDetailsList = new ArrayList<>();
		billDetailsList = BillDetails.findByID(bill.getId());
		ArrayList<BillDetailsTable> list = new ArrayList<>();
		
		for(BillDetails x: billDetailsList){
			String itemName = ItemModel.findByID(x.getItemId()).getName();
			int qty = x.getQty();
			float unitPrice = ItemModel.findByID(x.getItemId()).getPrice();
			float totalPrice = qty * unitPrice;
			
			list.add(new BillDetailsTable(itemName, qty, unitPrice, totalPrice));
		}
		
		ObservableList<BillDetailsTable> billList = FXCollections.observableArrayList(list);
		
		tableView.setItems(billList);
		
	}
	
	@FXML
	public void deleteOrder(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning");
		alert.setHeaderText("Warning!");
		alert.setContentText("Are you sure you want to delete this order from database");
		Optional<ButtonType> result =  alert.showAndWait();
		if(result.get() == ButtonType.OK){
			bill.delete();
			stage.close();
		}
		
		
		
	}
	
	

}
