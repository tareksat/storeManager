package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ItemModel;

public class ItemSelectController {
	
	@FXML TextField name;
	@FXML TableView<ItemModel> tableView;
	@FXML TableColumn<ItemModel, String> nameColumn;
	@FXML TableColumn<ItemModel, Float> priceColumn;
	
	private Stage stage;
	private ObservableList<ItemModel> itemList;
	
	public static ItemModel selectedItem;
	
	public void setMain(Stage stage){
		this.stage = stage;
	}
	
	public void initialize(){

        nameColumn.setCellValueFactory
                (new PropertyValueFactory<ItemModel, String>("name"));

        priceColumn.setCellValueFactory
        (new PropertyValueFactory<ItemModel, Float>("price"));
     
    }
	

	@FXML public void nameSearch(){
		String name = this.name.getText();
		itemList = FXCollections.observableArrayList(ItemModel.findByNameStart(name));
		tableView.setItems(itemList);
		
	}
	
	
	
	@FXML public void selectItem(){
		int i = tableView.getSelectionModel().getSelectedIndex();
		ItemSelectController.selectedItem = itemList.get(i);
		stage.close();
		 
		
	}


}
