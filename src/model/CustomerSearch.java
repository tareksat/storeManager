package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
// View customersSearch

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.Database;

public class CustomerSearch {
	private String customerName;
	private String billNum;
	private Date date;
	private float totalPrice;
	private float discount;
	private int customerId;
	private int billId;
	
	
	public CustomerSearch(String customerName, String billNum, Date date, float totalPrice, float discount,
			int customerId, int billId) {
		super();
		this.customerName = customerName;
		this.billNum = billNum;
		this.date = date;
		this.totalPrice = totalPrice;
		this.discount = discount;
		this.customerId = customerId;
		this.billId = billId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getBillNum() {
		return billNum;
	}


	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public float getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}


	public float getDiscount() {
		return discount;
	}


	public void setDiscount(float discount) {
		this.discount = discount;
	}


	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	public int getBillId() {
		return billId;
	}


	public void setBillId(int billId) {
		this.billId = billId;
	}


	@Override
	public String toString() {
		return "CustomerSearch [customerName=" + customerName + ", billNum=" + billNum + ", date=" + date
				+ ", totalPrice=" + totalPrice + ", discount=" + discount + ", customerId=" + customerId + ", billId="
				+ billId + "]";
	}
	
	
	/*************************************************************************************
	 * 					     Search by date
	 ************************************************************************************/
	public static ArrayList searchByDate(String start, String end){
		String sql = "SELECT * from customersSearch WHERE date between '"+start+"' And '"+end+"'";
		
		ArrayList<CustomerSearch> customerList = new ArrayList<>();
		try{
			Statement stmt = (Statement) Database.db.createStatement();
			 
	    
	         ResultSet rs = stmt.executeQuery(sql);
	            while(rs.next()) {
	            	int customerId = rs.getInt("customer_id");
	            	String customerName = rs.getString("name");
	            	int billId = rs.getInt("bill_id");
	            	String billNumber = rs.getString("number");
	            	Date date = rs.getDate("date");
	            	float totalPrice = rs.getFloat("total_price");
	            	float discount = rs.getFloat("discount");
	            
	            	customerList.add(new CustomerSearch(customerName, billNumber, date, totalPrice,
	            			discount, customerId, billId));
	            }
	            stmt.close();
	            return customerList;
	         
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
		
		return null;
	}
	
	
	/*************************************************************************************
	 * 					    	 Search by total price
	 ************************************************************************************/
	public static ArrayList searchByPrice(float min, float max){
		String sql = "SELECT * from customersSearch WHERE total_price between ? And ?";
		ArrayList<CustomerSearch> customerList = new ArrayList<>();
		try{
			PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
			 
	         stmt.setFloat(1,  min);
	         stmt.setFloat(2,  max);
	         ResultSet rs = stmt.executeQuery();
	            while(rs.next()) {
	            	int customerId = rs.getInt("customer_id");
	            	String customerName = rs.getString("name");
	            	int billId = rs.getInt("bill_id");
	            	String billNumber = rs.getString("number");
	            	Date date = rs.getDate("date");
	            	float totalPrice = rs.getFloat("total_price");
	            	float discount = rs.getFloat("discount");
	            
	            	customerList.add(new CustomerSearch(customerName, billNumber, date, totalPrice,
	            			discount, customerId, billId));
	            }
	            stmt.close();
	            return customerList;
	         
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
		
		return null;
	}
	
	/*************************************************************************************
	 * 					    	 Search by discount
	 ************************************************************************************/
	public static ArrayList searchByDiscount(float min, float max){
		String sql = "SELECT * from customersSearch WHERE discount between ? And ?";
		ArrayList<CustomerSearch> customerList = new ArrayList<>();
		try{
			PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
			 
	         stmt.setFloat(1,  min);
	         stmt.setFloat(2,  max);
	         ResultSet rs = stmt.executeQuery();
	            while(rs.next()) {
	            	int customerId = rs.getInt("customer_id");
	            	String customerName = rs.getString("name");
	            	int billId = rs.getInt("bill_id");
	            	String billNumber = rs.getString("number");
	            	Date date = rs.getDate("date");
	            	float totalPrice = rs.getFloat("total_price");
	            	float discount = rs.getFloat("discount");
	            
	            	customerList.add(new CustomerSearch(customerName, billNumber, date, totalPrice,
	            			discount, customerId, billId));
	            }
	            stmt.close();
	            return customerList;
	         
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
		
		return null;
	}	
	
	/*************************************************************************************
	 * 					     Search by Name
	 ************************************************************************************/
	public static ArrayList searchByName(String name){
		String sql = "SELECT * from customersSearch WHERE name = ?";
		ArrayList<CustomerSearch> customerList = new ArrayList<>();
		try{
			PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
			 
	         stmt.setString(1,  name);
	        
	         ResultSet rs = stmt.executeQuery();
	            while(rs.next()) {
	            	int customerId = rs.getInt("customer_id");
	            	String customerName = rs.getString("name");
	            	int billId = rs.getInt("bill_id");
	            	String billNumber = rs.getString("number");
	            	Date date = rs.getDate("date");
	            	float totalPrice = rs.getFloat("total_price");
	            	float discount = rs.getFloat("discount");
	            
	            	customerList.add(new CustomerSearch(customerName, billNumber, date, totalPrice,
	            			discount, customerId, billId));
	            }
	            stmt.close();
	            return customerList;
	         
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
		
		return null;
	}
	
	/*************************************************************************************
	 * 					     Search by Bill Number
	 ************************************************************************************/
	public static ArrayList searchByBillNumber(String num){
		String sql = "SELECT * from customersSearch WHERE number = ?";
		ArrayList<CustomerSearch> customerList = new ArrayList<>();
		try{
			PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
			 
	         stmt.setString(1,  num);
	        
	         ResultSet rs = stmt.executeQuery();
	            while(rs.next()) {
	            	int customerId = rs.getInt("customer_id");
	            	String customerName = rs.getString("name");
	            	int billId = rs.getInt("bill_id");
	            	String billNumber = rs.getString("number");
	            	Date date = rs.getDate("date");
	            	float totalPrice = rs.getFloat("total_price");
	            	float discount = rs.getFloat("discount");
	            
	            	customerList.add(new CustomerSearch(customerName, billNumber, date, totalPrice,
	            			discount, customerId, billId));
	            }
	            stmt.close();
	            return customerList;
	         
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
		
		return null;
	}

	/*************************************************************************************
	 * 					     Search by name and date
	 ************************************************************************************/
	public static ArrayList searchByNameDate(String name, String start, String end){
		
		String sql = "SELECT * from customersSearch WHERE name = ? AND (date between ? And ?)";
		ArrayList<CustomerSearch> customerList = new ArrayList<>();
		try{
			PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
			stmt.setString(1,  name);
	         stmt.setString(2,  start);
	         stmt.setString(3,  end);
	         ResultSet rs = stmt.executeQuery();
	            while(rs.next()) {
	            	int customerId = rs.getInt("customer_id");
	            	String customerName = rs.getString("name");
	            	int billId = rs.getInt("bill_id");
	            	String billNumber = rs.getString("number");
	            	Date date = rs.getDate("date");
	            	float totalPrice = rs.getFloat("total_price");
	            	float discount = rs.getFloat("discount");
	            
	            	customerList.add(new CustomerSearch(customerName, billNumber, date, totalPrice,
	            			discount, customerId, billId));
	            }
	            stmt.close();
	            return customerList;
	         
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
		
		return null;
	}
	
	
	
}
