public class Main{
	public static void main(String[] args) throws Throwable{

		Account acc = new Account(1);
		System.out.println("Account number: " + acc.getAccountNumber());

		acc.credit(1000);
		System.out.println("Account balance: " + acc.getBalance());

		acc.debit(1100);
	}
}