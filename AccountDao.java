package dao;
import java.sql.Connection;
import Password.PasswordUtil;
import util.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Account;

public class AccountDao {
	
	public boolean createAccount(Account acc) {
		try {
			Connection con=DBConnection.getConnection();
			String sql="INSERT INTO ACCOUNTS(NAME,EMAIL,PASSWORD,BALANCE) VALUES(?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, acc.getName());
			ps.setString(2,acc.getEmail());
			//ps.setString(3, acc.getPassword());
			String hashedPassword=PasswordUtil.hashPassword(acc.getPassword());
			ps.setString(3, hashedPassword);
			ps.setDouble(4,acc.getBalance());
			 return ps.executeUpdate()>0;
			 	
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
			
		}
	}
		public Account login(String email,String password) {
			try {
				Connection con=DBConnection.getConnection();
				String sql="Select * from accounts Where email=? and password=?";
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setString(1,email);
				//ps.setString(2, password);
				String hashedPassword=PasswordUtil.hashPassword(password);
				ps.setString(2, hashedPassword);
				
				ResultSet rs=ps.executeQuery();
				if(rs.next()) {
					Account acc=new Account();
					acc.setAccNo(rs.getInt("acc_no"));
					acc.setName(rs.getString("name"));
					acc.setBalance(rs.getDouble("balance"));
					return acc;
				}
			
			}
			catch(Exception e) {
				e.printStackTrace();
				
			}
			return null;
		
	}
		public boolean deposit (int accNo,double amount) {
			try {
				Connection con=DBConnection.getConnection();
				String sql="Update accounts set balance=balance+? where acc_no=?";
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setDouble(1, amount);
				ps.setInt(2, accNo);
				
				 if( ps.executeUpdate()>0) {
					 saveTransaction(accNo,"Deposit",amount);
					 return true;
				 }
			}
			catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			return false;
		}
		public boolean withdraw(int accNo,double amount) {
			try {
				Connection con=DBConnection.getConnection();
				/*String checksql="Select balance from accounts where acc_No=?";
				PreparedStatement ps1=con.prepareStatement(checksql);
				ps1.setInt(1, accNo);*/
				double balance=getBalance(accNo);
				
				if(balance==-1) {
					System.out.println("Account not found");
					return false;
				}
				if(balance<amount) {
					System.out.println("Insufficent Balance");
					return false;
				}
				
				
				/*ResultSet rs=ps1.executeQuery();
				if(rs.next()) {
					double currentbalance=rs.getDouble("balance");
					if(currentbalance>=amount) {*/
						String updatesql="update accounts set balance=balance- ? where acc_no=?";
						PreparedStatement ps2=con.prepareStatement(updatesql);
						ps2.setDouble(1,amount);
						ps2.setInt(2, accNo);
						if(ps2.executeUpdate()>0) {
							saveTransaction(accNo,"Withdraw",amount);
							return true;
						}
		//	}}	
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		public boolean transfer(int fromAcc,int toAcc,double amount) {
			Connection con=null;
			try {
				con=DBConnection.getConnection();
				con.setAutoCommit(false);
				// check sender
				//String checksql="Select balance from accounts where acc_no =?";
				//PreparedStatement ps1=con.prepareStatement(checksql);
				//ps1.setInt(1, fromAcc);
				
				//ResultSet rs=ps1.executeQuery();
				//if(rs.next()) {
					//double balance =rs.getDouble("balance");
				double balance=getBalance(fromAcc);
				if(balance==-1)
				{
						System.out.println("Sender not found");
						con.rollback();
						return false;
				}
						
						if(balance<amount) {
						System.out.println("Insufficent Balance");
						con.rollback();;
						return false;
						
					}
			//}
					
				//check recirver
				String checkRec="Select acc_no from accounts where acc_no=?";
				PreparedStatement pscheck=con.prepareStatement(checkRec);
				pscheck.setInt(1, toAcc);
				ResultSet rs2=pscheck.executeQuery();
				if(!rs2.next()) {
					System.out.println("Receiver not found");
					con.rollback();
					return false;
				}
				// add to receiver
				String deductsql="Update accounts set balance=balance - ? where acc_no=?";
				PreparedStatement ps2=con.prepareStatement(deductsql);
				ps2.setDouble(1,amount);
				ps2.setInt(2, fromAcc);
				int rows1=ps2.executeUpdate();
				
				//add to receiver
				String addsql="Update accounts set balance=balance + ? where acc_no=?";
				PreparedStatement ps3=con.prepareStatement(addsql);
				ps3.setDouble(1,amount);
				ps3.setInt(2, toAcc);
				 int rows2=ps3.executeUpdate();
				 if(rows1>0 && rows2>0) {
					 con.commit();
					 saveTransaction(fromAcc,"Transfer_sent",amount);
					 saveTransaction(toAcc,"Transfer_received",amount);
					 return true;
				 }
				 else {
					 	con.rollback();
					 return false;
				 }
			}
			catch(Exception e) {
				try {
					con.rollback();
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				e.printStackTrace();
				return false;
			}
				finally {
					try {if(con!=null)
						con.setAutoCommit(true);
						
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}			
			
		}
		public void saveTransaction(int accNo,String type,double amount) {
			try{
				Connection con=DBConnection.getConnection();
			
			String sql="Insert into transactions(acc_no,type,amount) values(?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, accNo);
			ps.setString(2, type);
			ps.setDouble(3, amount);
			
			ps.executeUpdate();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
public void getTransactions(int accNo) {
	try {
		Connection con=DBConnection.getConnection();
		String sql="Select * from transactions where acc_no=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setInt(1, accNo);
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString("type")+ "|"+ rs.getDouble("amount")+ "|"+rs.getTimestamp("date"));
		}
		
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}
		public double getBalance(int accNo) {
			try {
				Connection con=DBConnection.getConnection();
				String sql="Select balance from accounts where acc_no=?";
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setInt(1, accNo);
				
				ResultSet rs=ps.executeQuery();
				if(rs.next()) {
					return rs.getDouble("balance");
				}
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return -1;
		}
		
	
}
