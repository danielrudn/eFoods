package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ItemBean;

public class ItemDAO extends BasicDAO {
	
	public List<ItemBean> getItemsByNameMatching(String name) throws Exception {
		List<ItemBean> items = new ArrayList<ItemBean>();
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement("select * from roumani.item where lower(name) like ?");
		statement.setString(1, "%" + name.toLowerCase() + "%");
		ResultSet results = statement.executeQuery();
		while(results.next()) {
			items.add(new ItemBean(results.getString(1), results.getString(2), results.getString(10), results.getInt(7), results.getDouble(3)));
		}
		results.close();
		statement.close();
		connection.close();
		return items;
	}

	public List<ItemBean> getRandomItems() throws Exception {
		List<ItemBean> items = new ArrayList<ItemBean>();
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery("select * from roumani.item order by random() fetch first 24 rows only");
		while(results.next()) {
			items.add(new ItemBean(results.getString(1), results.getString(2), results.getString(10), results.getInt(7), results.getDouble(3)));
		}
		results.close();
		statement.close();
		connection.close();
		return items;
	}
	
	public ItemBean getItemByNumber(String number) throws Exception {
		ItemBean item = null;
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement("select * from roumani.item where number = ?");
		statement.setString(1, number);
		ResultSet results = statement.executeQuery();
		if(results.next()) {
			item = new ItemBean(number, results.getString(2), results.getString(10), results.getInt(7), results.getDouble(3));
		}
		results.close();
		statement.close();
		connection.close();
		return item;
	}
	
	public List<ItemBean> getItemsInCategory(int categoryId) throws Exception {
		List<ItemBean> items = new ArrayList<ItemBean>();
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement("select * from roumani.item where catid = ?");
		statement.setInt(1, categoryId);
		ResultSet results = statement.executeQuery();
		while(results.next()) {
			items.add(new ItemBean(results.getString(1), results.getString(2), results.getString(10), categoryId, results.getDouble(3)));
		}
		results.close();
		statement.close();
		connection.close();
		return items;
	}
}
