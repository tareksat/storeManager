package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CustomerModel;

public class CustomerSelectController {
	
	@FXML TextField name;
	@FXML TextField phone;
	@FXML TableView<CustomerModel> tableView;
	@FXML TableColumn<CustomerModel, String> nameColumn;
	@FXML TableColumn<CustomerModel, String> phoneColumn;
	
	private Stage stage;
	private ObservableList<CustomerModel> customerList;
	public static CustomerModel selectedCustomer;
	
	public void setMain(Stage stage){
		this.stage = stage;
	}
	
	public void initialize(){

        nameColumn.setCellValueFactory
                (new PropertyValueFactory<CustomerModel, String>("name"));

        phoneColumn.setCellValueFactory
                (new PropertyValueFactory<CustomerModel, String>("phone"));
     
    }
	

	@FXML public void nameSearch(){
		String name = this.name.getText();
		customerList = FXCollections.observableArrayList(CustomerModel.findByNameStart(name));
		tableView.setItems(customerList);
		
	}
	
	@FXML public void phoneSearch(){
		String phone = this.phone.getText();
		customerList = FXCollections.observableArrayList(CustomerModel.findByPhoneStart(phone));
		tableView.setItems(customerList);
		}
	
	@FXML public void selectCustomer(){
		int i = tableView.getSelectionModel().getSelectedIndex();
		this.selectedCustomer = customerList.get(i);
		stage.close();
		
	}

}
