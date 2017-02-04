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
import model.ItemModel;
import model.ItemSearch;

public class ItemSearchController {
	
	@FXML private DatePicker startDatePicker, endDatePicker;
	@FXML private TextField itemNameField;
	@FXML private TableView<ItemSearch> tableView;
	@FXML private TableColumn<ItemSearch, String> itemNamecolumn;
	@FXML private TableColumn<ItemSearch, String> billNumberColumn;
	@FXML private TableColumn<ItemSearch, String> customerNameColumn;
	@FXML private TableColumn<ItemSearch, Integer>  qtyColumn;
	@FXML private TableColumn<ItemSearch, Date> dateColumn;
	
	
	private ArrayList<ItemSearch> list = new ArrayList<>();
	/********************************************************************************************/
	public void setMain(){
		
		startDatePicker.setValue(LocalDate.of(2000, 1, 1));
		endDatePicker.setValue(LocalDate.now());
		
	}
	/********************************************************************************************/
	public void initialize(){
		itemNamecolumn.setCellValueFactory
        (new PropertyValueFactory<ItemSearch, String>("itemName"));
		
		billNumberColumn.setCellValueFactory
		        (new PropertyValueFactory<ItemSearch, String>("billNumber"));
		
		customerNameColumn.setCellValueFactory
		(new PropertyValueFactory<ItemSearch, String>("customerName"));
		
		qtyColumn.setCellValueFactory
		(new PropertyValueFactory<ItemSearch, Integer>("qty"));
		
		dateColumn.setCellValueFactory
		(new PropertyValueFactory<ItemSearch, Date>("date"));
		
	}
	
	
	/********************************************************************************************/
	@FXML
	public void findItem(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/ItemSelectView.fxml"));
			AnchorPane pane = loader.load();
			
			ItemSelectController mainWindowController = loader.getController();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			
			mainWindowController.setMain(stage);
			
			Scene scene = new Scene(pane);
			
			stage.setScene(scene);
			stage.showAndWait();
			ItemModel item = ItemSelectController.selectedItem;
			itemNameField.setText(ItemSelectController.selectedItem.getName());
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
	}
	
	/********************************************************************************************/
	@FXML 
	public void nameSearch(){
		list.clear();
		list = ItemSearch.searchByName(itemNameField.getText());
		ObservableList<ItemSearch> billList = FXCollections.observableArrayList(list);
		tableView.setItems(billList);
	}
	
	/********************************************************************************************/
	@FXML
	public void dateSearch(){
		LocalDate startDate = startDatePicker.getValue().plusMonths(1);
		LocalDate endDate = endDatePicker.getValue().plusMonths(1);
		
		list.clear();
		list = ItemSearch.searchByDate(startDate.toString(), endDate.toString());
		
		ObservableList<ItemSearch> billList = FXCollections.observableArrayList(list);
		
		tableView.setItems(billList);
	}
	
	/********************************************************************************************/
	@FXML 
	public void viewBillDetails(){
		int i = tableView.getSelectionModel().getSelectedIndex();
		BillModel bill = BillModel.findByNumber(list.get(i).getBillNumber());
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
			stage.show();
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
		
	}
	/********************************************************************************************/

}
