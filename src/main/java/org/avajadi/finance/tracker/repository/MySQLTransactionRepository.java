package org.avajadi.finance.tracker.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.avajadi.finance.tracker.Transaction;
import org.jboss.logging.Logger;


@ApplicationScoped
public class MySQLTransactionRepository implements TransactionRepository {

	private static final String BY_ID_QUERY = "SELECT * FROM Transaction WHERE id=?";
	private static final String ALL_QUERY = "SELECT * FROM Transaction";
	private String databaseURL = "jdbc:mysql://localhost/tracker";
	private String databaseUser = "root";
	private String databasePassword = "semmelkalas";
	private Logger logger = Logger.getLogger(getClass());

	private Connection connection;

	@PostConstruct
	public void init() {
		logger.trace("PostConstruct");
		connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private Connection getConnection() {
		logger.trace("getConnection()");
		boolean needsReconnect;

		try {
			needsReconnect = (connection == null || !connection.isValid(10));
		} catch (SQLException e) {
			System.out.println("JDBC connection needs reconnect (" + e.getMessage() + ")");
			needsReconnect = true;
		}

		if (needsReconnect) {
			System.out.println(String.format("Creating connecting to tracker database: %s, user: %s, pass: %s", databaseURL,
					databaseUser, databasePassword));
			// Open connections
			try {
				connection = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
			} catch (SQLException e) {
				System.out.println(String.format("Connection to STM database failed! %s", e.getMessage()));
				e.printStackTrace();
			}
		}
		return connection;
	}

	/* (non-Javadoc)
	 * @see org.avajadi.finance.tracker.dal.TransactionRepository#create(org.avajadi.finance.tracker.Transaction)
	 */
	@Override
	public Transaction create(Transaction newTransaction) {
		String sql = "INSERT INTO Transaction (accountNumber,comment,verification,amount,balance,bookingDate,currencyDate)"
				+ "VALUES (?, ?, ? ,?, ?, ?, ?)";
		PreparedStatement insertVesselStatement = null;
		try {
			insertVesselStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rsKey;
			insertVesselStatement.setString(1, newTransaction.getAccountNumber());
			insertVesselStatement.setString(2, newTransaction.getComment());
			insertVesselStatement.setString(3, newTransaction.getVerification());
			insertVesselStatement.setDouble(4, newTransaction.getAmount());
			insertVesselStatement.setDouble(5, newTransaction.getBalance());
			insertVesselStatement.setDate(6, Date.valueOf(newTransaction.getBookingDate()));
			insertVesselStatement.setDate(7, Date.valueOf(newTransaction.getCurrencyDate()));

			insertVesselStatement.executeUpdate();

			rsKey = insertVesselStatement.getGeneratedKeys();
			if (rsKey != null && rsKey.next())
				System.out.println( "int returned " + rsKey.getInt(1) );
				return get(rsKey.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (insertVesselStatement != null)
				try {
					insertVesselStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.avajadi.finance.tracker.dal.TransactionRepository#get(int)
	 */
	@Override
	public Transaction get(int id) {
		logger.tracef("get(%d)", id);
		PreparedStatement stmt = null;
		try {
			stmt = getConnection().prepareStatement(BY_ID_QUERY);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.first();
			return buildTransaction(rs);
		} catch (SQLException e) {
			throw new NoSuchTransactionException(id, e);
		}
	}

	@Override
	public List<Transaction> getAll(){
		logger.trace("getAll()");
		List<Transaction> transactions = new ArrayList<>();
		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(ALL_QUERY);
			while( rs.next() ) {
				transactions.add(buildTransaction(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transactions;
	}
	
	private Transaction buildTransaction(ResultSet rs) throws SQLException {
		LocalDate bookingDate = rs.getDate("bookingDate").toLocalDate();
		LocalDate currencyDate = rs.getDate("currencyDate").toLocalDate();
		String verification = rs.getString("verification");
		String comment = rs.getString("comment");
		double amount = rs.getDouble("amount");
		double balance = rs.getDouble("balance");
		String accountNumber = rs.getString("accountNumber");
		int id = rs.getInt("id");

		return new Transaction(id, accountNumber, bookingDate, currencyDate, verification, comment, amount, balance);
	}

}
