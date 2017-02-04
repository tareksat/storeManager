package model;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.text.DefaultEditorKit.CutAction;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.Database;

public class CustomerModel {
	
	private int id;
	private String name;
	private String phone;
	private String email;
	private String address;
	
	
	public CustomerModel(int id, String name, String phone, String email, String address) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.address = address;
	}


	public CustomerModel(String name, String phone, String email, String address) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.address = address;
	}


	public CustomerModel() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "CustomerModel [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", address="
				+ address + "]";
	}
	
	

	/*************************************************************************************
	 * 					Find by id
	 ********************************************************************************/
	public static CustomerModel findByID(int id){
		 String sql = "select * from customers where id = ?";
		 try{
		 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
         stmt.setInt(1, id);
         ResultSet rs = stmt.executeQuery();
         CustomerModel customer = new CustomerModel();
            while(rs.next()) {
            	customer.setId(rs.getInt("id"));
            	customer.setName(rs.getString("name"));
            	customer.setPhone(rs.getString("phone"));
            	customer.setEmail(rs.getString("email"));
            	customer.setAddress(rs.getString("address"));
            }
            stmt.close();
            return customer;
		 }catch(Exception e){
			
		 }
		 return null;
	}
	
	/*************************************************************************************
	 * 					Find by name
	 ********************************************************************************/
	public static CustomerModel findByName(String name){
		 String sql = "select * from customers where name = ?";
		 try{
		 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
         stmt.setString(1, name);
         ResultSet rs = stmt.executeQuery();
         CustomerModel customer = new CustomerModel();
            while(rs.next()) {
            	customer.setId(rs.getInt("id"));
            	customer.setName(rs.getString("name"));
            	customer.setPhone(rs.getString("phone"));
            	customer.setEmail(rs.getString("email"));
            	customer.setAddress(rs.getString("address"));
            }
            stmt.close();
            return customer;
		 }catch(Exception e){
			
		 }
		 return null;
	}
	
	/*************************************************************************************
	 * 					           Find by Part of a name
	 ********************************************************************************/
	public static ArrayList findByNameStart(String name){
		
		 String sql = "select * from customers where name LIKE '" +name+"%'";
		 try{
			 Statement stmt = (Statement) Database.db.createStatement();
         
         ResultSet rs = stmt.executeQuery(sql);
         ArrayList<CustomerModel> customers = new ArrayList<>();
 
            while(rs.next()) {
            	int id = rs.getInt("id");
            	String customerName = rs.getString("name");
            	String phone = rs.getString("phone");
            	String email = rs.getString("email");
            	String address = rs.getString("address");
            	customers.add(new CustomerModel(id, customerName, phone, email, address));
            }
            stmt.close();
            return customers;
		 }catch(Exception e){
			System.out.print(e.getMessage().toString());
		 }
		 return null;
	}
	
	/*************************************************************************************
	 * 					Find by phone
	 ********************************************************************************/
	public static CustomerModel findByPhone(String phone){
		 String sql = "select * from customers where phone = ?";
		 try{
		 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
         stmt.setString(1, phone);
         ResultSet rs = stmt.executeQuery();
         CustomerModel customer = new CustomerModel();
            while(rs.next()) {
            	customer.setId(rs.getInt("id"));
            	customer.setName(rs.getString("name"));
            	customer.setPhone(rs.getString("phone"));
            	customer.setEmail(rs.getString("email"));
            	customer.setAddress(rs.getString("address"));
            }
            stmt.close();
            return customer;
		 }catch(Exception e){
			
		 }
		 return null;
	}
	
	/*************************************************************************************
	 * 					           Find by Part of a phone
	 ********************************************************************************/
	public static ArrayList findByPhoneStart(String phone){
		
		 String sql = "select * from customers where phone LIKE '" +phone+"%'";
		 try{
			 Statement stmt = (Statement) Database.db.createStatement();
         
         ResultSet rs = stmt.executeQuery(sql);
         ArrayList<CustomerModel> customers = new ArrayList<>();
 
            while(rs.next()) {
            	int id = rs.getInt("id");
            	String name = rs.getString("name");
            	String _phone = rs.getString("phone");
            	String email = rs.getString("email");
            	String address = rs.getString("address");
            	customers.add(new CustomerModel(id, name, _phone, email, address));
            }
            stmt.close();
            return customers;
		 }catch(Exception e){
			System.out.print(e.getMessage().toString());
		 }
		 return null;
	}
	/*************************************************************************************
	 * 					Find all
	 ********************************************************************************/
	public static ArrayList findAll(){
		 String sql = "select * from customers";
		 ArrayList<CustomerModel> customers = new ArrayList<>();
		 try{
		 Statement stmt = (Statement) Database.db.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         
            while(rs.next()) {
            	int _id = rs.getInt("id");
            	String _name = rs.getString("name");
            	String _phone = rs.getString("phone");
            	String _email = rs.getString("email");
            	String _address = rs.getString("address");
            	customers.add(new CustomerModel(_id, _name, _phone, _email, _address));
            }
            stmt.close();
            return customers;
		 }catch(Exception e){
			
		 }
		 return customers;
	}
	
	/*************************************************************************************
	 * 					     Insert new customer 
	 ************************************************************************************/
	public boolean insert(){
		
		// check if customer name or phone already exists
		CustomerModel customer_test1 = findByName(this.name);
		CustomerModel customer_test2 = findByPhone(this.phone);
		
		if((customer_test1.getId() != 0) || customer_test2.getId()!=0){
			return false;  // item already exists
		}
		
		else{  // add the new item to database
			String sql = "insert into customers values(null, ?,?,?,?)";
			 try{
			 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
	         stmt.setString(1, this.getName());
	         stmt.setString(2, this.getPhone());
	         stmt.setString(3, this.getEmail());
	         stmt.setString(4, this.getAddress());
	         
	         stmt.executeUpdate();
	         stmt.close();
	            return true;
			 }catch(Exception e){
				 System.out.println(e.getMessage().toString());
			 	}
			 return false;
		}
		 
	}
	
	/*************************************************************************************
	 * 					     Remove Customer 
	 ************************************************************************************/
	public boolean delete(){
		
		
			String sql = "delete from customers where id = ?";
			 try{
			 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
	         stmt.setInt(1, this.getId());
	         stmt.executeUpdate();
	         stmt.close();
	            return true;
			 }catch(Exception e){
				 System.out.println(e.getMessage().toString());
			 	}
			 return false;
		
		 
	}
	
	/*************************************************************************************
	 * 					     update  Customer 
	 ************************************************************************************/
	public boolean update(){
		
		// check if customer name or phone already exists
		CustomerModel customer_test1 = findByName(this.name);
		CustomerModel customer_test2 = findByPhone(this.phone);
		
		if((customer_test1.getId() != 0  && customer_test1.getId() != this.getId()) 
				|| ( customer_test2.getId()!=0) && customer_test2.getId() != this.getId()){
			return false;  // item already exists
		}
		
		else{  // add the new item to database
			String sql = "update customers set name = ?, phone = ?, email=?, address=? where id = ?";
			 try{
			 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
			 
	         stmt.setString(1, this.getName());
	         stmt.setString(2, this.getPhone());
	         stmt.setString(3, this.getEmail());
	         stmt.setString(4, this.getAddress());
	         stmt.setInt(5, this.getId());
	   
	         stmt.executeUpdate();
	         stmt.close();
	            return true;
	            
			 }catch(Exception e){
				 System.out.println(e.getMessage().toString());
			 	}
			 return false;
		}
		 
	}
	
	

}
