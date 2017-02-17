package model;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXB;

public class Order {
	
	private File resources;
	
	public Order(String orderLocation) {
		resources = new File(orderLocation);
		if(!resources.exists()) {
			resources.mkdir();
		}
	}
	
	public String place(CustomerBean customer, ShoppingCart cart) throws Exception {
		if(cart != null && cart.getItemCount() < 1) {
			throw new Exception("Cart is empty!");
		}
		if(customer == null || customer.getFullName() == null || customer.getUsername() == null) {
			throw new Exception("User is not present or invalid!");
		}
		List<File> orders = Arrays.asList(resources.listFiles());
		int lastId = orders.stream()
				.filter(f -> f.getName().matches("^po"  + customer.getUsername() + "_[0-9]*[.]xml$"))
				.map(File::getName)
				.mapToInt(this::getOrderIdFromFileName)
				.sorted()
				.max()
				.orElse(0);
		int newId = lastId + 1;
		String fileName = "po" + customer.getUsername() + "_" + String.format("%02d", newId);
		File orderFile = new File(resources.getAbsolutePath() + "/" + fileName + ".xml");
		PurchaseOrder po = new PurchaseOrder(newId, customer, cart.getCart());
		JAXB.marshal(po, orderFile);
		return fileName;
	}
	
	public PurchaseOrder retrieve(String fileName) throws Exception {
		if(fileName.startsWith("/")) {
			fileName = fileName.substring(1);
		}
		File order = new File(resources.getAbsolutePath() + "/" + fileName + ".xml");
		if(!order.exists()) {
			throw new Exception("The requested order does not exist.");
		}
		return JAXB.unmarshal(order, PurchaseOrder.class);
	}
	
	private int getOrderIdFromFileName(String fileName) {
		try {
			String id = fileName.substring(fileName.indexOf("_")+1, fileName.indexOf(".xml"));
			return Integer.parseInt(id);
		} catch(Exception ex) {
			return 1;
		}
	}

}
