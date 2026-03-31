package model;

public class Account {
	private int accNo;
	private String name;
	private String email;
	private String password;
	private double balance;
	
	public Account() {
		super();
	}

	public Account(String name, String email, String password, double balance) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.balance = balance;
	}

	public Account(int accNo, String name, String email, String password, double balance) {
		super();
		this.accNo = accNo;
		this.name = name;
		this.email = email;
		this.password = password;
		this.balance = balance;
	}

	/**
	 * @return the accNo
	 */
	public int getAccNo() {
		return accNo;
	}

	/**
	 * @param accNo the accNo to set
	 */
	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	
}
