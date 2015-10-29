DROP TABLE Transaction;
CREATE TABLE Transaction (
	id SERIAL,
	accountNumber VARCHAR(30),
	bookingDate DATE,
	currencyDate DATE,
	verification VARCHAR(100),
	comment VARCHAR(100),
	amount DOUBLE,
	balance DOUBLE
) ENGINE=InnoDB;