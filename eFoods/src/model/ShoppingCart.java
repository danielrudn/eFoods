package model;

import java.util.Map;

import model.dao.ItemDAO;

public class ShoppingCart {
	
	private ShoppingCartBean cart;
	private ItemDAO itemDAO;
	
	public ShoppingCart(ItemDAO itemDAO, String hst, String shipping, String shippingCutoff) {
		cart = new ShoppingCartBean(Double.parseDouble(hst), Double.parseDouble(shipping), Double.parseDouble(shippingCutoff));
		this.itemDAO = itemDAO;
	}

	public void addItem(String itemNumber) {
		try {
			ItemBean item = itemDAO.getItemByNumber(itemNumber);
			cart.addItem(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateItems(Map<String, String[]> itemNumbers) {
		for(String itemNumber : itemNumbers.keySet()) {
			try {
				int count = Integer.parseInt(itemNumbers.get(itemNumber)[0]);
				if(count >= 0) {
					cart.updateItem(itemNumber, count);					
				}
			} catch(Exception ex) {
				continue;
			}
		}
	}
	
	public ItemDAO getItemDAO() {
		return this.itemDAO;
	}
	
	public ShoppingCartBean getCart() {
		return cart;
	}
	
	public Map<ItemBean, Integer> getItems() {
		return cart.getItems();
	}
	
	public int getItemCount() {
		Map<ItemBean, Integer> items = cart.getItems();
		int count = items.keySet().stream()
				.mapToInt(items::get)
				.sum();
		return count;
	}
	
	public double getSubTotal() {
		return cart.getSubTotal();
	}
	
	public double getTax() {
		return cart.getTax();
	}
	
	public double getShippingCost() {
		return cart.getShippingCost();
	}
	
	public double getTotal() {
		return cart.getTotal();
	}
}
