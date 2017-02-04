package model;

public class BillDetailsTable {

	private String itemName;
	private int qty;
	private float unitPrice;
	private float totalPrice;
	
	
	public BillDetailsTable(String itemName, int qty, float unitPrice, float totalPrice) {
		super();
		this.itemName = itemName;
		this.qty = qty;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
	}


	public BillDetailsTable() {
		super();
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


	public float getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}


	public float getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}


	@Override
	public String toString() {
		return "BillDetailsTable [itemName=" + itemName + ", qty=" + qty + ", unitPrice=" + unitPrice + ", totalPrice="
				+ totalPrice + "]";
	}
	
	
	
	
	
	
	
}
