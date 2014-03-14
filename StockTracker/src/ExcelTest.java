import java.sql.*;

import java.io.*;
import java.net.URL;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelTest {
	protected String[] rawdata;
	protected static int totalrows;
	protected static int totalcols;
	protected static String[][] stockList; 
	protected static String[] columnNames = 
		{"Symbol", "Current", "Change", "Pct Change", "Date", 
		"Time", "High", "Low", "Prev. Close", "Volume", "Name", "Expert Advice"};
	
	
	ExcelTest() {
		try {
			// Load the JDBC-ODBC bridge driver
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			// Need to configure ODBC
			Connection con = DriverManager.getConnection("jdbc:odbc:COSC330");
			// Allows you to scroll back
			Statement st = con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = st.executeQuery("Select * from [Sheet1$]");

			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			int numberOfRows = 0;

			// Gets number of Rows
			while (rs.next()) {
				numberOfRows++;
			}

			totalrows = numberOfRows - 1;
			totalcols = numberOfColumns - 1;

			// Go to beginning
			rs.beforeFirst();

			rs.close();
			st.close();
			con.close();
		} catch (Exception ex) {
			System.err.print("Exception: ");
			System.err.println(ex.getMessage());
		}
		
		rawdata = new String[totalrows + 1];
		stockList = new String[totalrows][];
		update();
		sortArray();
	}

	public void sortArray() {
		for (int i = 0; i < totalrows; i++) {
			stockList[i] = rawdata[i].split(",");
		}

		// Printed Array
		for (int i = 0; i < totalrows; i++) {
			for (int j = 0; j < totalcols; j++) {
				stockList[i][j] = (stockList[i][j]).replace("\"", "");
//				System.out.print(stockList[i][j] + " ");
			}
//			System.out.println("");
		}
	}

	public void update() {
//		try {
//			System.out.println("Updating " + totalrows + " stocks");
//			
//			// Read in file
//			FileInputStream file = new FileInputStream(new File("Stocks.xls"));
//
//			// Get the workbook instance for XLS file
//			HSSFWorkbook workbook = new HSSFWorkbook(file);
//
//			// Get first sheet from the workbook
//			HSSFSheet sheet = workbook.getSheetAt(0);
//
//			// Rows start at 0, cells start at 0
//			for (int i = 1; i < totalrows + 1; i++) {
//				HSSFRow row = sheet.getRow(i);
//				HSSFCell cell = row.getCell(1);
//
//				String generate_URL = "http://finance.yahoo.com/d/quotes.txt?s="
//						+ cell.getStringCellValue() + "&f=sl1c1p2d1t1hgpvn";
//				URL yahoo = new URL(generate_URL);
//		        BufferedReader in = new BufferedReader(
//		        new InputStreamReader(yahoo.openStream()));
//		        String inputLine = in.readLine();
//
//				// Put results into first column
//				cell = row.getCell(0);
//				cell.setCellValue(inputLine);
//				// System.out.println(cell.getStringCellValue());
//				System.out.print(i + " ");
//			}
//			System.out.println("");
//			FileOutputStream fileOut = new FileOutputStream("Stocks.xls");
//			workbook.write(fileOut);
//			fileOut.close();
//			file.close();
//
//			System.out.println("Done");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:COSC330");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from [Sheet1$]");

			int index = 0;

			while (rs.next()) {
				String columnValue = rs.getString(1);
				rawdata[index] = columnValue;
				index++;
			}

			rs.close();
			st.close();
			con.close();
		} catch (Exception ex) {
			System.err.print("Exception: ");
			System.err.println(ex.getMessage());
		}

	}
	
	//getter for stock list
	public Object[][] getStockList(){
		Object[][] stockObject = new Object[totalrows][totalcols];
		
		for (int i = 0; i < totalrows; i++) {
			for (int j = 0; j < totalcols; j++) {
				stockObject[i][j] = stockList[i][j];
				System.out.print(stockObject[i][j] + " ");
			}
			System.out.println("");
		}
		return stockObject;
		
	}
	
	//getter for column names
	public String[] getColumnNames(){
		return columnNames;
	}
	
	//getter for percent change
	public static float getPercentChange(int index){
		float change = 0;
		String data = stockList[index][3];
		data = data.replace("%", "");
		
		change = Float.valueOf(data);
		
		return change;
	}
}