package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class OrderedItemBean {

	private String number, name;
	private int quantity;
	private double price, extended;
	
	public OrderedItemBean() { }
	
	public OrderedItemBean(ItemBean item, int quantity) {
		this.quantity = quantity;
		this.price = item.getPrice();
		this.name = item.getName();
		this.number = item.getItemNumber();
		this.extended = item.getPrice() * quantity;
	}

	@XmlAttribute
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@XmlElement
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@XmlElement
	public double getExtended() {
		return extended;
	}

	public void setExtended(double extended) {
		this.extended = extended;
	}

}
