package model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
public class PurchaseOrder {

	private int id = 1;
	private Date submitted;
	private CustomerBean customer;
	private OrderedItems items;
	private double total, shipping, HST, grandTotal;
	
	public PurchaseOrder() { }
	
	public PurchaseOrder(int id, CustomerBean customer, ShoppingCartBean cart) {
		submitted = Calendar.getInstance().getTime();
		this.id = id;
		this.customer = customer;
		this.total = cart.getSubTotal();
		this.shipping = cart.getShippingCost();
		this.HST = cart.getTax();
		this.grandTotal = cart.getTotal();
		this.items = new OrderedItems(cart.getItems().keySet().stream()
				.map(item -> new OrderedItemBean(item, cart.getItems().get(item)))
				.collect(Collectors.toList()));
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute
	public Date getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Date submitted)
	{
		this.submitted = submitted;
	}

	public CustomerBean getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerBean customer) {
		this.customer = customer;
	}

	public OrderedItems getItems() {
		return items;
	}

	public void setItems(OrderedItems items) {
		this.items = items;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	public double getHST() {
		return HST;
	}

	public void setHST(double hST) {
		HST = hST;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}
	
	public List<OrderedItemBean> getOrderedItems() {
		return items.getItems();
	}
}

@XmlRootElement(name = "items")
class OrderedItems {

	private List<OrderedItemBean> items;
	
	public OrderedItems() { }
	
	public OrderedItems(List<OrderedItemBean> items) {
		this.items = items;
	}
	
	@XmlElement(name = "item")
	public List<OrderedItemBean> getItems() {
		return items;
	}
	
	public void setItems(List<OrderedItemBean> items) {
		this.items = items;
	}
}
