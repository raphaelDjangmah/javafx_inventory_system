package application;

import javafx.scene.control.Button;

public class Shop {
	private String name;
	private String date;
	private int quantity,id;
	
	
	public int getId() {
		return id;
	}

	private double buyAt,sellAt;
	private double netBuy,netSell,netAmount;
	private Button btn;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setBuyAt(double buyAt) {
		this.buyAt = buyAt;
	}
	public void setSellAt(double sellAt) {
		this.sellAt = sellAt;
	}
	public void setNetBuy(double netBuy) {
		this.netBuy = netBuy;
	}
	public void setNetSell(double netSell) {
		this.netSell = netSell;
	}
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	public void setBtn(Button btn) {
		this.btn = btn;
	}
	public String getDate() {
		return date;
	}
	public int getQuantity() {
		return quantity;
	}
	public int getid() {
		return id;
	}
	public double getBuyAt() {
		return buyAt;
	}
	public double getSellAt() {
		return sellAt;
	}
	public double getNetBuy() {
		return netBuy;
	}
	public double getNetSell() {
		return netSell;
	}
	public double getNetAmount() {
		return netAmount;
	}
	public Button getBtn() {
		return btn;
	}
	
	public Shop() {}
	
	public Shop(int id,String name, String date, int quantity, double buyAt, double sellAt, double netBuy, double netSell,
			double netAmount) {
		super();
		this.id  = id;
		this.name = name;
		this.date = date;
		this.quantity = quantity;
		this.buyAt = buyAt;
		this.sellAt = sellAt;
		this.netBuy = netBuy;
		this.netSell = netSell;
		this.netAmount = netAmount;
		//this.btn = btn;
	}
	
	
}
