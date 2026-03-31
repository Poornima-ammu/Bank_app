package main;
import model.Account;
import service.BankService;
import java.util.Scanner;

public class BankApp {
	public static void main(String []args) {
		Scanner sc=new Scanner(System.in);
		BankService service=new BankService();
		while(true) {
		System.out.println("1.Register");	
		System.out.println("2.Login");	
		System.out.println("3.Deposit");	
		System.out.println("4.Withdraw");
		System.out.println("5.Transfer");
		System.out.println("6.Transaction History");
		System.out.println("7.Check balance");
		System.out.println("8.Exit");
		int choice =sc.nextInt();
		switch(choice) {
		case 1:
			Account ac=new Account();
			System.out.println("Name: ");
			ac.setName(sc.next());
			System.out.println("Email: ");
			ac.setEmail(sc.next());
			
			System.out.println("Password: ");
			ac.setPassword(sc.next());
			
			ac.setBalance(0);
			if(service.register(ac))
				System.out.println("Account Created Sucessfully");
			else
				System.out.println("Error!!");
			break;
		case 2:
			System.out.println("Email: ");
			String email=sc.next();
			System.out.println("Password: ");
			String pass=sc.next();
			
			Account user=service.login(email, pass);
			if(user!=null) {
				System.out.println("Welcome "+ user.getName());
			
			}
			else {
				System.out.println("Invalid Login");
				
			}
			break;
		case 3:
			System.out.println("Enter Account No: ");
			int accNo=sc.nextInt();
			System.out.println("Enter Amount");
			double amt=sc.nextDouble();
			
			if(service.deposit(accNo,amt)) {
				System.out.println("Deposited Sucessfully");
			}
			else {
				System.out.println("Failed");
			}
			break;
		case 4:
			System.out.println("Enter Account No: ");
			int accNow=sc.nextInt();
			System.out.println("Enter Amount: ");
			double amtw=sc.nextDouble();
			if(service.withdraw(accNow,amtw)) {
				System.out.println("Withdraw Sucessful");
			}
			else {
				System.out.println("Failed");
			}
			break;
		case 5:
			System.out.println("From Account :");
			int from=sc.nextInt();
			
			System.out.println("To Account :");
			int to=sc.nextInt();
			
			System.out.println("Amount: ");
			double amount=sc.nextDouble();
			if(service.transfer(from,to,amount))
				System.out.println("Transfer Sucessfully");
			else {
				System.out.println("Transfer failed");
			}
			break;
		case 6:	
			System.out.print("Enter Account NO: ");
			int acNo=sc.nextInt();
			service.getTransactions(acNo);
			break;
		case 7:
			System.out.print("Enter Account No");
			int aNo=sc.nextInt();
			service.checkBalance(aNo);
			break;
		case 8:
			System.out.println("Thank you for using Bank App");
			System.exit(0);		
			break;
		}

		}	
	}

}
