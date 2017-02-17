package model.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class BasicDAO {

	protected DataSource dataSource;

	public BasicDAO() {
		try {
			this.dataSource = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");	
		} catch(NamingException ex) {
			ex.printStackTrace();
		}
	}
	
}
