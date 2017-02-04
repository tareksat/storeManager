package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import java.util.Optional;
import model.CustomerModel;


public class CustomerControlController {

    @FXML private TextField customerNameSearch, customerPhoneSearch;
    @FXML private TextField CustomerName, CustomerPhone, customerEmail;
    @FXML private TextArea customerAddress;
    @FXML private TableView<CustomerModel> tableView;
    @FXML private TableColumn<CustomerModel, String> nameColumn;
    @FXML private TableColumn<CustomerModel, String> phoneColumn;

    private ObservableList<CustomerModel> customerList;
    
    public void setMain(){
       
        loadTableData();
    }

    public void initialize(){

        nameColumn.setCellValueFactory
                (new PropertyValueFactory<CustomerModel, String>("name"));

        phoneColumn.setCellValueFactory
                (new PropertyValueFactory<CustomerModel, String>("phone"));


        // selected row
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obseravble, oldValue, newValue) -> showdetails(newValue));
     

    }

    private void showdetails(Object newValue){
    	if(newValue != null){
    		CustomerModel customer = (CustomerModel) newValue;
    		
    		CustomerName.setText(customer.getName());
    		CustomerPhone.setText(customer.getPhone());
    		customerEmail.setText(customer.getEmail());
    		customerAddress.setText(customer.getAddress());	
    	}
    }

    private void loadTableData(){
  
    	customerList = 
    			FXCollections.observableArrayList(CustomerModel.findAll());
    	
    	tableView.setItems(customerList);
    	
    }
    
    public void addCustomer(){
    	String name    = CustomerName.getText();
    	String phone   = CustomerPhone.getText();
    	String email   = customerEmail.getText();
    	String address = customerAddress.getText();
    	
    	if(name.isEmpty() || phone.isEmpty()){
    		Alert errotAlert = new Alert(AlertType.ERROR);
    		errotAlert.setTitle("error message");
    		errotAlert.setHeaderText("Error!");
    		errotAlert.setContentText("Customer name or phone is empty!");
    		errotAlert.showAndWait();
    		return;
    	}
    	
    	boolean flag = new CustomerModel(name, phone, email, address).insert();
    	if(!flag){
    		Alert errotAlert = new Alert(AlertType.ERROR);
    		errotAlert.setTitle("error message");
    		errotAlert.setHeaderText("Error!");
    		errotAlert.setContentText("Another customer with the same name or phone already exists!");
    		errotAlert.showAndWait();
    	}
    	loadTableData();
    	clearAllValues();
    	
    }
    
    public void deleteCustomer(){
    	int i = tableView.getSelectionModel().getSelectedIndex();
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning");
		alert.setHeaderText("Warning!");
		alert.setContentText("Are you sure you want to delete customer: "+
		customerList.get(i).getName());
		Optional<ButtonType> result =  alert.showAndWait();
		if(result.get() == ButtonType.OK){
			customerList.get(i).delete();
			customerList.clear();
	    	loadTableData();
	    	clearAllValues();
	    	
		}
	
    }
    public void editCustomer(){
    	int i = tableView.getSelectionModel().getSelectedIndex();
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Warning!");
		alert.setContentText("Are you sure you want to update customer: "+
		customerList.get(i).getName());
		Optional<ButtonType> result =  alert.showAndWait();
		if(result.get() == ButtonType.OK){
			String name    = CustomerName.getText();
	    	String phone   = CustomerPhone.getText();
	    	String email   = customerEmail.getText();
	    	String address = customerAddress.getText();
	    	if(name.isEmpty() || phone.isEmpty()){
	    		Alert errotAlert = new Alert(AlertType.ERROR);
	    		errotAlert.setTitle("error message");
	    		errotAlert.setHeaderText("Error!");
	    		errotAlert.setContentText("Customer name or phone is empty!");
	    		errotAlert.showAndWait();
	    		return;
	    	}
	    	boolean flag = new CustomerModel(customerList.get(i).getId(), name,
	    			phone, email, address).update();
	    	if(!flag){
	    		Alert errotAlert = new Alert(AlertType.ERROR);
	    		errotAlert.setTitle("error message");
	    		errotAlert.setHeaderText("Error!");
	    		errotAlert.setContentText("Another customer with the same name or phone already exists!");
	    		errotAlert.showAndWait();
	    	}
	    	loadTableData();
	    	clearAllValues();
	    	
		}
    }
    
    private void clearAllValues(){
    	CustomerName.clear();
		CustomerPhone.clear();
		customerEmail.clear();
		customerAddress.clear();
		customerNameSearch.clear();
		customerPhoneSearch.clear();
    }
    
    public void nameSearch(){
    	String name = customerNameSearch.getText();
    	for(CustomerModel x:customerList){
    		if(name.equals(x.getName())){
    			CustomerName.setText(x.getName());
        		CustomerPhone.setText(x.getPhone());
        		customerEmail.setText(x.getEmail());
        		customerAddress.setText(x.getAddress());	
        		break;
    		}
    	}
    }
    
    public void phoneSearch(){
    	String phone = customerPhoneSearch.getText();
    	for(CustomerModel x:customerList){
    		if(phone.equals(x.getPhone())){
    			CustomerName.setText(x.getName());
        		CustomerPhone.setText(x.getPhone());
        		customerEmail.setText(x.getEmail());
        		customerAddress.setText(x.getAddress());	
        		break;
    		}
    	}
    }


}
