package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.Database;

public class ItemSearch {
	
	private int customerId;
	private String customerName;
	private int itemId;
	private String itemName;
	private int qty;
	private int billId;
	private String billNumber;
	private String date;
	
	
	public ItemSearch(int customerId, String customerName, int itemId, String itemName, int qty, int billId,
			String billNumber, String date) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.itemId = itemId;
		this.itemName = itemName;
		this.qty = qty;
		this.billId = billId;
		this.billNumber = billNumber;
		this.date = date;
	}


	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public int getItemId() {
		return itemId;
	}


	public void setItemId(int itemId) {
		this.itemId = itemId;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public int getQty() {
		return qty;
	}


	public void setQty(int qty) {
		this.qty = qty;
	}


	public int getBillId() {
		return billId;
	}


	public void setBillId(int billId) {
		this.billId = billId;
	}


	public String getBillNumber() {
		return billNumber;
	}


	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "ItemSearch [customerId=" + customerId + ", customerName=" + customerName + ", itemId=" + itemId
				+ ", itemName=" + itemName + ", qty=" + qty + ", billId=" + billId + ", billNumber=" + billNumber
				+ ", date=" + date + "]";
	}
	
	/*************************************************************************************
	 * 					     Search by date
	 ************************************************************************************/
	public static ArrayList searchByDate(String start, String end){
		
		String sql = "SELECT * from advancedsearch WHERE date between '"+start+"' And '"+end+"'";
		
		ArrayList<ItemSearch> itemList = new ArrayList<>();
		try{
			Statement stmt = (Statement) Database.db.createStatement();
	        
	         ResultSet rs = stmt.executeQuery(sql);
	            while(rs.next()) {
	            	int customerID = rs.getInt("customers_id");
	            	String customerName = rs.getString("name");
	            	int itemID = rs.getInt("item_id");
	            	String itemName = rs.getString("item_name");
	            	int itemQTY = rs.getInt("qty");
	            	int billId = rs.getInt("bill_id");
	            	String billNumber = rs.getString("number");
	            	String date = rs.getString("date");
	            
	            	itemList.add(new ItemSearch(customerID, customerName, itemID, itemName,
	            			itemQTY, billId, billNumber, date));
	            }
	            stmt.close();
	            return itemList;
	         
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
		
		return null;
	}
	
	/*************************************************************************************
	 * 					     Search by name
	 ************************************************************************************/
	public static ArrayList searchByName(String name){
		
		String sql = "SELECT * from advancedsearch WHERE item_name = ?";
		
		ArrayList<ItemSearch> itemList = new ArrayList<>();
		try{
			PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
			stmt.setString(1,  name);
	         ResultSet rs = stmt.executeQuery();
	            while(rs.next()) {
	            	int customerID = rs.getInt("customers_id");
	            	String customerName = rs.getString("name");
	            	int itemID = rs.getInt("item_id");
	            	String itemName = rs.getString("item_name");
	            	int itemQTY = rs.getInt("qty");
	            	int billId = rs.getInt("bill_id");
	            	String billNumber = rs.getString("number");
	            	String date = rs.getString("date");
	            
	            	itemList.add(new ItemSearch(customerID, customerName, itemID, itemName,
	            			itemQTY, billId, billNumber, date));
	            }
	            stmt.close();
	            return itemList;
	         
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
		
		return null;
	}
	

}
