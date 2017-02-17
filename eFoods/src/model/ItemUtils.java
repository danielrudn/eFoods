package model;

import java.util.List;
import java.util.stream.Collectors;

public class ItemUtils {
	
	private ItemUtils() { }
	
	public static List<ItemBean> applyFilter(List<ItemBean> items, String keywords, String maxPrice, String sort) {
		try {
			double max = Double.parseDouble(maxPrice);
			return items.stream()
				.filter(item -> item.getName().toLowerCase().contains(keywords.toLowerCase()))
				.filter(item -> item.getPrice() <= max)
				.sorted((item1, item2) -> {
					if(sort.equalsIgnoreCase("lowest to highest")) {
						return Double.compare(item1.getPrice(), item2.getPrice());
					} else if(sort.equalsIgnoreCase("highest to lowest")) {
						return Double.compare(item2.getPrice(), item1.getPrice());
					}
					return item1.getName().compareTo(item2.getName());
				}).collect(Collectors.toList());
		} catch(Exception ex) {
			ex.printStackTrace();
			return items;
		}
	}
	
	public static double getMaxPrice(List<ItemBean> items) {
		return items.stream()
				.mapToDouble(item -> item.getPrice())
				.max()
				.orElse(0.00);
	}
	
	public static double getMinPrice(List<ItemBean> items) {
		return items.stream()
				.mapToDouble(item -> item.getPrice())
				.min()
				.orElse(0.00);
	}

}
