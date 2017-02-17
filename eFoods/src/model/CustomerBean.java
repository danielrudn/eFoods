package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customer")
public class CustomerBean {
	
	@XmlAttribute(name = "account")
	private String username;
	@XmlElement(name = "name")
	private String fullName;
	
	public CustomerBean() { }
	
	public CustomerBean(String username, String fullName) {
		this.username = username;
		this.fullName = fullName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getFullName() {
		return fullName;
	}

}
