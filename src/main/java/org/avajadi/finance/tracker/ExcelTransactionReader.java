package org.avajadi.finance.tracker;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTransactionReader {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static ExcelTransactionReader reader = null;
	
	private ExcelTransactionReader() {
		super();
	}
	
	public static ExcelTransactionReader getReader() {
		if( reader == null ) {
			reader = new ExcelTransactionReader();
		}
		return reader;
	}
	public List<Transaction> read(String xlsxFilename) throws InvalidFormatException, IOException {
		Workbook wb = new XSSFWorkbook(new FileInputStream(xlsxFilename));
        return read(wb);
	}

	public List<Transaction> read(Workbook wb) {
		List<Transaction> transactions = new ArrayList<>();
		Sheet sheet = wb.getSheetAt(0);
		String accountNumber = sheet.getRow(1).getCell(0).getStringCellValue();
		for (Row row : sheet) {
			if(row.getRowNum() > 4 ) {
				Transaction transaction = readTransaction( accountNumber, row );
				transactions.add(transaction);
			}
		}
		return transactions;
	}

	private Transaction readTransaction(String accountNumber, Row row) {
		LocalDate bookingDate = LocalDate.parse(row.getCell(0).getStringCellValue(), formatter);
		LocalDate currencyDate = LocalDate.parse(row.getCell(1).getStringCellValue(), formatter);
		String verification = row.getCell(2).getStringCellValue();
		String comment = row.getCell(3).getStringCellValue();
		double amount = row.getCell(4).getNumericCellValue();
		double balance = row.getCell(5).getNumericCellValue();

		return new Transaction(accountNumber, bookingDate, currencyDate, verification, comment, amount, balance);
	}

}
