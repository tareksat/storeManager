package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.Database;

public class BillModel {
	
	private int id;
	private String number;
	private int customerId;
	private Date date;
	private float totalPrice;
	private float discount;
	
	
	public BillModel(int id, String number, int customerId, Date date, float totalPrice, float discount) {
		super();
		this.id = id;
		this.number = number;
		this.customerId = customerId;
		this.date = date;
		this.totalPrice = totalPrice;
		this.discount = discount;
	}


	public BillModel(String number, int customerId, Date date, float totalPrice, float discount) {
		super();
		this.number = number;
		this.customerId = customerId;
		this.date = date;
		this.totalPrice = totalPrice;
		this.discount = discount;
	}
	
	public BillModel(String number, int customerId, float totalPrice, float discount) {
		super();
		this.number = number;
		this.customerId = customerId;
		this.totalPrice = totalPrice;
		this.discount = discount;
	}


	public BillModel() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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


	@Override
	public String toString() {
		return "BillModel [id=" + id + ", number=" + number + ", customerId=" + customerId + ", date=" + date
				+ ", totalPrice=" + totalPrice + ", discount=" + discount + "]";
	}
	
	/*************************************************************************************
	 * 					Find by id
	 ********************************************************************************/
	public static BillModel findByID(int id){
		 String sql = "select * from bill where id = ?";
		 try{
		 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
         stmt.setInt(1, id);
         ResultSet rs = stmt.executeQuery();
         BillModel bill = new BillModel();
            while(rs.next()) {
            	bill.setId(rs.getInt("id"));
            	bill.setNumber(rs.getString("number"));
            	bill.setCustomerId(rs.getInt("customer_id"));
            	bill.setDate(rs.getDate("date"));
            	bill.setTotalPrice(rs.getFloat("total_price"));
            	bill.setDiscount(rs.getFloat("discount"));
            }
            stmt.close();
            return bill;
		 }catch(Exception e){
			
		 }
		 return null;
	}
	
	
	/*************************************************************************************
	 * 					      Find by number
	 ********************************************************************************/
	public static BillModel findByNumber(String number){
		 String sql = "select * from bill where number = ?";
		 try{
		 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
         stmt.setString(1, number);
         ResultSet rs = stmt.executeQuery();
         BillModel bill = new BillModel();
            while(rs.next()) {
            	bill.setId(rs.getInt("id"));
            	bill.setNumber(rs.getString("number"));
            	bill.setCustomerId(rs.getInt("customer_id"));
            	bill.setDate(rs.getDate("date"));
            	bill.setTotalPrice(rs.getFloat("total_price"));
            	bill.setDiscount(rs.getFloat("discount"));
            }
            stmt.close();
            return bill;
		 }catch(Exception e){
			
		 }
		 return null;
	}
	
	/*************************************************************************************
	 * 					Find all
	 ************************************************************************************/
	public static ArrayList findAll(){
		 String sql = "select * from bill";
		 ArrayList<BillModel> bills = new ArrayList<>();
		 try{
		 Statement stmt = (Statement) Database.db.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         
            while(rs.next()) {
            	int id = rs.getInt("id");
            	String number = rs.getString("number");
            	int customer_id = rs.getInt("customer_id");
            	Date date = rs.getDate("date");
            	float total_price = rs.getFloat("total_price");
            	float discount = rs.getFloat("discount");
            	bills.add(new BillModel(id, number, customer_id, date, total_price, discount));
            }
            stmt.close();
            return bills;
		 }catch(Exception e){
			
		 }
		 return bills;
	}
	
	/*************************************************************************************
	 * 					     Insert new bill 
	 ************************************************************************************/
	public boolean insert(){
		
		// check if item name already exists
		BillModel bill_test = findByNumber(this.number);
		
		if(bill_test.getId() != 0 && bill_test.getId() != this.id){
			return false;  // item already exists
		}
		
		else{  // add the new item to database
			String sql = "insert into bill values(null, ?,?, CURRENT_TIMESTAMP,?,?)";
			 try{
				 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
		       
		         stmt.setString(1,  this.number);
		         stmt.setInt(2,  this.customerId);
		         stmt.setFloat(3,  this.totalPrice);
		         stmt.setFloat(4, this.discount);
		         
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
	 * 					     Remove bill 
	 ************************************************************************************/
	public boolean delete(){
		
		// check if item name already exists
		
			String sql = "delete from bill where id = ?";
			 try{
				 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
		         stmt.setInt(1, this.getId());
		         stmt.executeUpdate();
		         
		         String qry = "delete from bill_details where bill_id=?";
		         stmt = (PreparedStatement) Database.db.prepareCall(qry);
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
	 * 					     update  bill 
	 ************************************************************************************/
	public boolean update(){
		
		// check if item name already exists
		BillModel bill_test = findByNumber(this.number);
		
		if(bill_test.getId() != 0 && bill_test.getId() != this.id){
			return false;  // item already exists
		}
		
		else{  // add the new item to database
			String sql = "update bill set number = ?, customer_id = ?,"
					+ " total_price=?, discount=? where id = ?";
			 try{
				 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
				 
		         stmt.setString(1,  this.number);
		         stmt.setInt(2,  this.customerId);
		         stmt.setFloat(3,  this.totalPrice);
		         stmt.setFloat(4, this.discount);
		         stmt.setInt(5, this.id);
		         
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
