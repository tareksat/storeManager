package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.BillDetails;
import model.BillDetailsTable;
import model.BillModel;
import model.CustomerModel;
import model.ItemModel;


public class MainWindowController {
	
	@FXML private TextField discount;
	@FXML private TextField qty;
	@FXML private Label customerName;
	@FXML private Label itemName;
	@FXML private Label totalPrice;
	@FXML private TableView<BillDetailsTable> tableView;
	@FXML private TableColumn<BillDetailsTable, String> itemNameColumn;
	@FXML private TableColumn<BillDetailsTable, Integer> QTYColumn;
	@FXML private TableColumn<BillDetailsTable, Float> unitPriceColumn;
	@FXML private TableColumn<BillDetailsTable, Float> totalPriceColumn;
	
	private ArrayList<BillDetailsTable> list = new ArrayList<BillDetailsTable>();
	private Main main;
	private Stage stage;
									
	private ItemModel item;
	private CustomerModel customer;
	
	private float total = 0;
	
	public void setMain(Main main, Stage stage){
		this.main = main;
		this.stage = stage;
		
	}
	
	
	public void initialize(){
		itemNameColumn.setCellValueFactory
        (new PropertyValueFactory<BillDetailsTable, String>("itemName"));
		
		QTYColumn.setCellValueFactory
		        (new PropertyValueFactory<BillDetailsTable, Integer>("qty"));
		
		unitPriceColumn.setCellValueFactory
		(new PropertyValueFactory<BillDetailsTable, Float>("unitPrice"));
		
		totalPriceColumn.setCellValueFactory
		(new PropertyValueFactory<BillDetailsTable, Float>("totalPrice"));
		
		// selected row
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obseravble, oldValue, newValue) -> showdetails());
		
	}
	
	private void showdetails(){}
	
	private void loadTableData(){
		ObservableList<BillDetailsTable> billList = FXCollections.observableArrayList(list);
		tableView.setItems(billList);
	}
	
	
	@FXML
	public void selectCustomer(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/CustomerSelectView.fxml"));
			AnchorPane pane = loader.load();
			
			CustomerSelectController customerSelectController = loader.getController();
			
			Scene scene = new Scene(pane);
			Stage stage = new Stage();
			customerSelectController.setMain(stage);
			stage.setScene(scene);
			stage.setTitle("Select customer");
			stage.showAndWait();
			customer = CustomerSelectController.selectedCustomer;
			customerName.setText(CustomerSelectController.selectedCustomer.getName());
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
	}
	/**********************************************************************************************/
	@FXML
	public void selectItem(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/ItemSelectView.fxml"));
			AnchorPane pane = loader.load();
			
			ItemSelectController mainWindowController = loader.getController();
			Stage stage = new Stage();
			mainWindowController.setMain(stage);
			
			Scene scene = new Scene(pane);
			
			stage.setScene(scene);
			stage.setTitle("Select item");
			stage.showAndWait();
			this.item = ItemSelectController.selectedItem;
			itemName.setText(ItemSelectController.selectedItem.getName());
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
	}
	/**********************************************************************************************/
	@FXML
	public void insert(){
		if(this.item != null){
			try{
				String name = item.getName();
				int qty = 0;
				try{
					qty = (int) Integer.parseInt(this.qty.getText());
				}
				catch(Exception e){
					Alert errotAlert = new Alert(AlertType.ERROR);
			    		errotAlert.setTitle("error message");
			    		errotAlert.setHeaderText("Error!");
			    		errotAlert.setContentText("Please enter a valid qty!");
			    		errotAlert.showAndWait();
			    		return;
				}
				float unit_price = item.getPrice();
				float total_price = qty * unit_price;
				total += total_price;
				this.totalPrice.setText(total+"");
				list.add(new BillDetailsTable(name, qty, unit_price, total_price));
				
				item = null;
				
				this.itemName.setText("Please select an item...");
				this.qty.setText("1");
				loadTableData();
				
			}catch(Exception e){
				System.out.println(e.getMessage().toString());
			}
			
		}
		else{
			Alert errotAlert = new Alert(AlertType.ERROR);
	    		errotAlert.setTitle("error message");
	    		errotAlert.setHeaderText("Error!");
	    		errotAlert.setContentText("Please select item name first!");
	    		errotAlert.showAndWait();
		}
		
	}
	
	/**********************************************************************************************/
	@FXML
	public void remove(){
		int i = tableView.getSelectionModel().getSelectedIndex();
		total -= list.get(i).getTotalPrice();
		this.totalPrice.setText(total+"");
		list.remove(i);
		
		loadTableData();
	}
	
	/**********************************************************************************************/
	@FXML
	public void createOrder(){
		String order_num = (System.nanoTime()%1000000)+"";
		float discount = 0;

		if(customer == null){
			Alert errotAlert = new Alert(AlertType.ERROR);
	    		errotAlert.setTitle("error message");
	    		errotAlert.setHeaderText("Error!");
	    		errotAlert.setContentText("Please select a customer first!");
	    		errotAlert.showAndWait();
	    		return;
		}
		
		int customerID = customer.getId();
		try{
			discount = Float.parseFloat(this.discount.getText());
		}catch(Exception e){
			Alert errotAlert = new Alert(AlertType.ERROR);
	    		errotAlert.setTitle("error message");
	    		errotAlert.setHeaderText("Error!");
	    		errotAlert.setContentText("Please enter a valid discount value!");
	    		errotAlert.showAndWait();
	    		return;
		}
		
		float total_price = this.total * (1 - (discount / 100));
		BillModel bill =  BillModel.findByNumber(order_num);

		if(bill.getId() != 0){
			Alert errotAlert = new Alert(AlertType.ERROR);
	    		errotAlert.setTitle("error message");
	    		errotAlert.setHeaderText("Error!");
	    		errotAlert.setContentText("Another Order with the same Number already exists!");
	    		errotAlert.showAndWait();
		}
		else{
			Date date = new Date();
			bill = new BillModel(order_num, customerID, date, total_price, discount);
			bill.insert();
			
			for(BillDetailsTable x:list){
				int orderID = BillModel.findByNumber(order_num).getId();
				int itemID = ItemModel.findByName(x.getItemName()).getId();
				BillDetails b = new BillDetails(orderID, itemID, x.getQty());
				
				b.insert();
			}
			bill = BillModel.findByNumber(bill.getNumber());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Info");
			alert.setHeaderText("Order created successfully");
			alert.setContentText("Order Number "+order_num+" had been created and saved to database");
			alert.showAndWait();
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

			this.customerName.setText("Please select customer...");
			this.itemName.setText("Please select an item...");
			this.discount.setText("0");
			this.qty.setText("1");
			this.totalPrice.setText("0.00");
			this.total = 0;
			item = null;
			customer = null;
			list.clear();
			loadTableData();
			
		}
		
	}
	
	/**********************************************************************************************/
	@FXML
	public void cancelOrder(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning message");
		alert.setHeaderText("Warning!");
		alert.setContentText("Are you sure you want to delete this order?");
		Optional<ButtonType> result =  alert.showAndWait();
		if(result.get() == ButtonType.OK){
			this.customerName.setText("");
			this.itemName.setText("");
			this.discount.clear();
			this.qty.clear();
			this.totalPrice.setText("");
			this.total = 0;
			item = null;
			customer = null;
			list.clear();
			loadTableData();
		}
		
	}
	
	/*************************************************************************************************/
	@FXML
	public void viewCusomerControlPanel(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/CustomerControlView.fxml"));
			AnchorPane pane = loader.load();
			
			CustomerControlController controller = loader.getController();
			
			Scene scene = new Scene(pane);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			controller.setMain();
			stage.setScene(scene);
			stage.setTitle("Customer control panel");
			stage.showAndWait();
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
	}
	
	/*************************************************************************************************/
	@FXML
	public void viewItemControlPanel(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/ItemControlView.fxml"));
			AnchorPane pane = loader.load();
			
			ItemControlController controller = loader.getController();
			
			Scene scene = new Scene(pane);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			controller.setMain();
			stage.setScene(scene);
			stage.setTitle("Item control panel");
			stage.showAndWait();
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
	}
	
	/*************************************************************************************************/
	@FXML
	public void viewBillSerachPanel(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/BillSearchView.fxml"));
			AnchorPane pane = loader.load();
			
			BillSearchController controller = loader.getController();
			
			Scene scene = new Scene(pane);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			controller.setMain();
			stage.setScene(scene);
			stage.setTitle("Search");
			stage.showAndWait();
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
	}
	
	/*************************************************************************************************/
	@FXML
	public void viewItemSearchPanel(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/ItemSearchView.fxml"));
			AnchorPane pane = loader.load();
			
			ItemSearchController controller = loader.getController();
			
			Scene scene = new Scene(pane);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			controller.setMain();
			stage.setScene(scene);
			stage.setTitle("Search");
			stage.showAndWait();
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
	}

}
