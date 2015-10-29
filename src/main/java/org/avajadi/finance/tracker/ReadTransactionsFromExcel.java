package org.avajadi.finance.tracker;

import java.util.List;

import org.avajadi.finance.tracker.repository.MySQLTransactionRepository;
import org.avajadi.finance.tracker.repository.TransactionRepository;

public class ReadTransactionsFromExcel {
	public static void main(String[] args) throws Exception {
		if( args.length != 1 ) {
			System.err.println("No excel file to read from!");
			System.exit(1);
		}
		TransactionRepository repository = new MySQLTransactionRepository();
		repository.init();
		
		ExcelTransactionReader reader = ExcelTransactionReader.getReader();
		List<Transaction> transactions = reader.read(args[0]);
		for( Transaction transaction : transactions ) {
			Transaction t2 = repository.create(transaction);
			System.out.println(transaction.toString());
			System.out.println(t2.toString());
		}
	}
}
