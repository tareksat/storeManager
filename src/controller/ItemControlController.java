package controller;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ItemModel;

public class ItemControlController {
	
	@FXML private TextField itemNameSearch;
    @FXML private TextField itemName,  itemPrice;
    @FXML private TableView<ItemModel> tableView;
    @FXML private TableColumn<ItemModel, String> nameColumn;
    @FXML private TableColumn<ItemModel, Float> priceColumn;

    private ObservableList<ItemModel> itemList;
    
    public void setMain(){
    	
    }
    
    public void initialize(){

    	nameColumn.setCellValueFactory
                (new PropertyValueFactory<ItemModel, String>("name"));

    	
    	priceColumn.setCellValueFactory
        (new PropertyValueFactory<ItemModel, Float>("price"));


        // selected row
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obseravble, oldValue, newValue) -> showdetails(newValue));
        	
        loadTableData();

    }

    private void showdetails(Object newValue){
    	if(newValue != null){
    		ItemModel item = (ItemModel) newValue;
    		itemName.setText(item.getName());
    		itemPrice.setText(item.getPrice()+"");
    		
    	}
    }

    private void loadTableData(){
  
    	itemList = 
    			FXCollections.observableArrayList(ItemModel.findAll());
    	
    	tableView.setItems(itemList);
    	
    }
    
    @FXML
    public void addItem(){
    	String name    = itemName.getText();
    	if(name.isEmpty()){
    		Alert errotAlert = new Alert(AlertType.ERROR);
    		errotAlert.setTitle("error message");
    		errotAlert.setHeaderText("Error!");
    		errotAlert.setContentText("Please enter item name!");
    		errotAlert.showAndWait();
    		return;
    	}
    	float price = 0;
    	try{
    		price    = Float.parseFloat(itemPrice.getText());
    	}catch(Exception e){
    		Alert errotAlert = new Alert(AlertType.ERROR);
    		errotAlert.setTitle("error message");
    		errotAlert.setHeaderText("Error!");
    		errotAlert.setContentText("Please enter a valid price value!");
    		errotAlert.showAndWait();
    		return;
    	}
    	
    	boolean flag = new ItemModel(name, price).insert();
    	if(!flag){
    		Alert errotAlert = new Alert(AlertType.ERROR);
    		errotAlert.setTitle("error message");
    		errotAlert.setHeaderText("Error!");
    		errotAlert.setContentText("Another Item with the same name already exists!");
    		errotAlert.showAndWait();
    	}
    	loadTableData();
    	clearAllValues();
    }
    
    @FXML
    public void updateItem(){
    	
    	int i = tableView.getSelectionModel().getSelectedIndex();
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Warning!");
		alert.setContentText("Are you sure you want to update Item: "+
		itemList.get(i).getName());
		Optional<ButtonType> result =  alert.showAndWait();
		String name = this.itemName.getText();
		if(result.get() == ButtonType.OK){
			if(name.isEmpty()){
	    		Alert errotAlert = new Alert(AlertType.ERROR);
	    		errotAlert.setTitle("error message");
	    		errotAlert.setHeaderText("Error!");
	    		errotAlert.setContentText("Please enter item name!");
	    		errotAlert.showAndWait();
	    		return;
	    	}
	    	float price = 0;
	    	try{
	    		price    = Float.parseFloat(itemPrice.getText());
	    	}catch(Exception e){
	    		Alert errotAlert = new Alert(AlertType.ERROR);
	    		errotAlert.setTitle("error message");
	    		errotAlert.setHeaderText("Error!");
	    		errotAlert.setContentText("Please enter a valid price value!");
	    		errotAlert.showAndWait();
	    		return;
	    	}
		    	
		    	boolean flag = new ItemModel(itemList.get(i).getId(), name,
		    			price).update();
		    	if(!flag){
		    		Alert errotAlert = new Alert(AlertType.ERROR);
		    		errotAlert.setTitle("error message");
		    		errotAlert.setHeaderText("Error!");
		    		errotAlert.setContentText("Another item with the same name already exists!");
		    		errotAlert.showAndWait();
		    	}
		    	loadTableData();
		    	clearAllValues();
	    	
		}
    }
    
    @FXML
    public void deleteItem(){
    	
    	int i = tableView.getSelectionModel().getSelectedIndex();
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning");
		alert.setHeaderText("Warning!");
		alert.setContentText("Are you sure you want to delete item: "+
		itemList.get(i).getName());
		Optional<ButtonType> result =  alert.showAndWait();
		if(result.get() == ButtonType.OK){
			itemList.get(i).delete();
			itemList.clear();
	    	loadTableData();
	    	clearAllValues();
	    	
		}
    }
    
    @FXML
    public void searchItem(){
    	String name = itemNameSearch.getText();
    	for(ItemModel x:itemList){
    		if(name.equals(x.getName())){
    			itemName.setText(x.getName());
        		itemPrice.setText(x.getPrice()+"");	
        		break;
    		}
    	}
    }
    
    private void clearAllValues(){
    	itemName.clear();
		itemNameSearch.clear();
		itemPrice.clear();
		
    }
    
    

}
