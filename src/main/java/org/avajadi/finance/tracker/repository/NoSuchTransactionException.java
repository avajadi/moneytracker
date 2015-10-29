package org.avajadi.finance.tracker.repository;

import java.sql.SQLException;

public class NoSuchTransactionException extends RuntimeException {
	public NoSuchTransactionException(int id, SQLException e) {
		super("" + id, e);
	}

	private static final long serialVersionUID = -408218112265179610L;

}
