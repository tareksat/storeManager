package model;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.Database;

public class BillDetails {
	
	private int bill_id;
	private int itemId;
	private int qty;
	
	
	public BillDetails(int bill_id, int itemId, int qty) {
		super();
		this.bill_id = bill_id;
		this.itemId = itemId;
		this.qty = qty;
	}


	public BillDetails() {
		super();
	}

	public int getItemId() {
		return itemId;
	}


	public void setItemId(int itemId) {
		this.itemId = itemId;
	}


	
	
	public int getBill_id() {
		return bill_id;
	}


	public void setBill_id(int bill_id) {
		this.bill_id = bill_id;
	}


	public int getQty() {
		return qty;
	}


	public void setQty(int qty) {
		this.qty = qty;
	}


	@Override
	public String toString() {
		return "BillDetails [bill_id=" + bill_id + ", itemId=" + itemId + ", qty=" + qty + "]";
	}


	/*************************************************************************************
	 * 					Find by bill number
	 ********************************************************************************/
	public static ArrayList<BillDetails> findByID(int bill_id){
		
		 String sql = "select * from bill_details where bill_id = ?";
		 ArrayList<BillDetails> bills = new ArrayList<>();
		 try{
		 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
         stmt.setInt(1, bill_id);
         ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
            	int id = rs.getInt("bill_id");
            	int item_id = rs.getInt("item_id");
            	int qty = rs.getInt("qty");
            	bills.add(new BillDetails(id, item_id, qty));
            }
            stmt.close();
            return bills;
		 }catch(Exception e){
			
		 }
		 return null;
	}
	
	
	/*************************************************************************************
	 * 					     Insert new Item 
	 ************************************************************************************/
	public boolean insert(){
		
			String sql = "insert into bill_details values(?,?,?)";
			 try{
				 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
		         stmt.setInt(1, this.getBill_id());
		         stmt.setInt(2, this.getItemId());
		         stmt.setInt(3, this.getQty());
		         
		         stmt.executeUpdate();
		         
		         stmt.close();
		         return true;
			 }catch(Exception e){
				 System.out.println(e.getMessage().toString());
			 	}
			 return false;
		 
	}
	
	/*************************************************************************************
	 * 					     Remove Item 
	 ************************************************************************************/
	public boolean delete(){
			String sql = "delete from bill_details where bill_id = ? and item_id = ?";
			 try{
				 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
		         stmt.setInt(1,  this.getBill_id());
				 stmt.setInt(2, this.getItemId());
		         stmt.executeUpdate();
		         stmt.close();
		         return true;
			 }catch(Exception e){
				 System.out.println(e.getMessage().toString());
			 	}
			 return false;
	}

	
}
