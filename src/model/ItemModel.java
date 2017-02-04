package model;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import common.Database;

public class ItemModel {
	
	private int id;
	private String Name;
	private float price;
	
	
	public ItemModel(int id, String name, float price) {
		super();
		this.id = id;
		Name = name;
		this.price = price;
	}
	
	public ItemModel(String name, float price) {
		super();
		Name = name;
		this.price = price;
	}
	
	public ItemModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "ItemModel [id=" + id + ", Name=" + Name + ", price=" + price + "]";
	}

	/*************************************************************************************
	 * 					Find by id
	 ********************************************************************************/
	public static ItemModel findByID(int id){
		 String sql = "select * from items where id = ?";
		 try{
		 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
         stmt.setInt(1, id);
         ResultSet rs = stmt.executeQuery();
         ItemModel item = new ItemModel();
            while(rs.next()) {
            	item.setId(rs.getInt("id"));
            	item.setName(rs.getString("name"));
            	item.setPrice(rs.getFloat("price"));
            }
            stmt.close();
            return item;
		 }catch(Exception e){
			
		 }
		 return null;
	}
	
	
	/*************************************************************************************
	 * 					      Find by name
	 ********************************************************************************/
	public static ItemModel findByName(String name){
		 String sql = "select * from items where name = ?";
		 try{
		 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
         stmt.setString(1, name);
         ResultSet rs = stmt.executeQuery();
         ItemModel item = new ItemModel();
            while(rs.next()) {
            	item.setId(rs.getInt("id"));
            	item.setName(rs.getString("name"));
            	item.setPrice(rs.getFloat("price"));
            }
            stmt.close();
            return item;
		 }catch(Exception e){
			
		 }
		 return null;
	}
	
	/*************************************************************************************
	 * 					           Find by Part of a name
	 ********************************************************************************/
	public static ArrayList findByNameStart(String name){
		
		 String sql = "select * from items where name LIKE '" +name+"%'";
		 try{
			 Statement stmt = (Statement) Database.db.createStatement();
         
         ResultSet rs = stmt.executeQuery(sql);
         ArrayList<ItemModel> items = new ArrayList<>();
 
            while(rs.next()) {
            	int id = rs.getInt("id");
            	String _name = rs.getString("name");
            	int price = rs.getInt("price");

            	items.add(new ItemModel(id, _name, price));
            }
            stmt.close();
            return items;
		 }catch(Exception e){
			System.out.print(e.getMessage().toString());
		 }
		 return null;
	}
	
	
	/*************************************************************************************
	 * 					           Find by Price range
	 ********************************************************************************/
	public static ArrayList findByPriceRange(int minPrice, int maxPrice){
		
		 String sql = "select * from items where price between ? AND ? ";
		 try{
			 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
         
			 stmt.setInt(1, minPrice);
			 stmt.setInt(2, maxPrice);
	         ResultSet rs = stmt.executeQuery();
	         ArrayList<ItemModel> items = new ArrayList<>();
	 
	            while(rs.next()) {
	            	int id = rs.getInt("id");
	            	String _name = rs.getString("name");
	            	int price = rs.getInt("price");
	
	            	items.add(new ItemModel(id, _name, price));
	            }
	            stmt.close();
	            return items;
		 }catch(Exception e){
			System.out.print(e.getMessage().toString());
		 }
		 return null;
	}
	
	/*************************************************************************************
	 * 					Find all
	 ********************************************************************************/
	public static ArrayList findAll(){
		 String sql = "select * from items";
		 ArrayList<ItemModel> items = new ArrayList<>();
		 try{
		 Statement stmt = (Statement) Database.db.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         
            while(rs.next()) {
            	int _id = rs.getInt("id");
            	String _name = rs.getString("name");
            	float _price = rs.getFloat("price");
            	items.add(new ItemModel(_id, _name, _price));
            }
            stmt.close();
            return items;
		 }catch(Exception e){
			
		 }
		 return items;
	}
	
	/*************************************************************************************
	 * 					     Insert new Item 
	 ************************************************************************************/
	public boolean insert(){
		
		// check if item name already exists
		ItemModel item_test = findByName(this.Name);
		
		if(item_test.getId() != 0){
			return false;  // item already exists
		}
		
		else{  // add the new item to database
			String sql = "insert into items values(null, ?,?)";
			 try{
				 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
		         stmt.setString(1, this.getName());
		         stmt.setFloat(2, this.getPrice());
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
	 * 					     Remove Item 
	 ************************************************************************************/
	public boolean delete(){
		
		// check if item name already exists
		ItemModel item_test = findByName(this.Name);
		
		if(item_test.getId() == 0){
			return false;  // item already exists
		}
		
		else{  // add the new item to database
			String sql = "delete from items where id = ?";
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
		 
	}
	
	/*************************************************************************************
	 * 					     update  Item 
	 ************************************************************************************/
	public boolean update(){
		
		// check if item name already exists
		ItemModel item_test = findByName(this.Name);
		
		if(item_test.getId() != 0 && item_test.getId() != this.id){
			return false;  // item already exists
		}
		
		else{  // add the new item to database
			String sql = "update items set name = ?, price = ? where id = ?";
			 try{
				 PreparedStatement stmt = (PreparedStatement) Database.db.prepareCall(sql);
		         stmt.setString(1, this.getName());
		         stmt.setFloat(2, this.getPrice());
		         stmt.setInt(3, (int) this.getId());
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
