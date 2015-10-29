package org.avajadi.finance.tracker.repository;

import java.util.List;

import org.avajadi.finance.tracker.Transaction;

public interface TransactionRepository {

	Transaction create(Transaction newTransaction);

	Transaction get(int id);

	List<Transaction> getAll();

	void init();

}