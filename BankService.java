package service;
import dao.AccountDao;
import model.Account;
public class BankService {
	AccountDao dao=new AccountDao();
	public boolean register (Account acc) {
		return dao.createAccount(acc);
	}
	public Account login(String email,String password) {
		return dao.login(email,password);
	}
	public boolean deposit(int accNo,double amount) {
		return dao.deposit(accNo,amount);
	}
	public boolean withdraw(int accNo,double amount) {
		return dao.withdraw(accNo, amount);
	}
	public boolean transfer(int fromAcc,int toAcc,double amount) {
	return dao.transfer(fromAcc,toAcc,amount);
	}
	public void getTransactions(int accNo) {
		dao.getTransactions(accNo);
		
	}
	public void checkBalance(int accNo) {
		AccountDao dao=new AccountDao();
		double balance=dao.getBalance(accNo);
		if(balance==-1) {
			System.out.println("Account not found");
		}
		else {
			System.out.println("Your Balance: "+balance);
		}
	}
}
	

