package model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartBean {
	
	private double TAX, SHIPPING, SHIPPING_FREE_CUTOFF;
	
	private Map<ItemBean, Integer> items;

	public ShoppingCartBean(double hst, double shipping, double shippingCutoff) {
		items = new HashMap<ItemBean, Integer>();
		TAX = hst;
		SHIPPING = shipping;
		SHIPPING_FREE_CUTOFF = shippingCutoff;
	}
	
	public Map<ItemBean, Integer> getItems() {
		return items;
	}
	
	public void addItem(ItemBean item) {
		if(!items.containsKey(item)) {
			items.put(item, 1);
		} else {
			items.put(item, items.get(item)+1);
		}
	}
	
	public void updateItem(String itemNumber, int count) {
		items.keySet().stream()
			.filter(item -> item.getItemNumber().equalsIgnoreCase(itemNumber))
			.forEach(item -> {
				if(count == 0) {
					items.remove(item);
				} else {
					items.put(item, count);
				}
			});
	}
	
	public double getSubTotal() {
		return items.keySet().stream()
				.mapToDouble(item -> item.getPrice() * items.get(item))
				.sum();
	}
	
	public double getTax() {
		return getSubTotal() * TAX;
	}
	
	public double getShippingCost() {
		return getSubTotal() < SHIPPING_FREE_CUTOFF ? SHIPPING : 0;
	}
	
	public double getTotal() {
		return getSubTotal() + getTax() + getShippingCost();
	}

}
