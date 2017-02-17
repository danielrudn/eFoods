package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.CategoryBean;

public class CategoryDAO extends BasicDAO {
	
	public List<CategoryBean> getCategories() throws Exception {
		List<CategoryBean> categories = new ArrayList<CategoryBean>();
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery("select * from roumani.category");
		while(results.next()) {
			categories.add(new CategoryBean(results.getInt(1), results.getString(2), results.getString(3)));
		}
		results.close();
		statement.close();
		connection.close();
		return categories;
	}
	
	public CategoryBean getCategoryById(int categoryId) throws Exception {
		CategoryBean category = null;
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement("select * from roumani.category where id = ?");
		statement.setInt(1, categoryId);
		ResultSet results = statement.executeQuery();
		if(results.next()) {
			category = new CategoryBean(categoryId, results.getString(2), results.getString(3));
		}
		results.close();
		statement.close();
		connection.close();
		return category;
	}
	
}
