import java.sql.*;

public class ExcelTest {
	protected String [] rawdata;
	protected int totalrows;
	protected int totalcols;
	protected String [][] stocklist;
	
	ExcelTest(){
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager
					.getConnection("jdbc:odbc:COSC330");
			// Allows you to scroll back
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			
			ResultSet rs = st.executeQuery("Select * from [Sheet1$]");

			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			int numberOfRows = 0;
			
			// Gets number of Rows
			while (rs.next()){
				  numberOfRows++;
			}
			
			totalrows = numberOfRows - 1;
			totalcols = numberOfColumns - 1;
			
			
			// Tracker Delete later
//			System.out.println("No of rows " + numberOfRows);
//			System.out.println("No of cols " + numberOfColumns);
			
			
			
			rawdata = new String[numberOfRows];
			int index = 0;

			// Gets first row
			rs.first();
			rawdata[index] = rs.getString(1);
			index++;
			
			while(rs.next()){
				String columnValue = rs.getString(1);
//				System.out.print(columnValue);
//				System.out.println("");
				
				rawdata[index] = columnValue;
				index++;
			}
			
			System.out.println("");
			
//			for(int i = 0; i < numberOfRows - 1; i++){
//				System.out.print(rawdata[i]);
//				System.out.println("");
//			}
			
			rs.close();
			st.close();
			con.close();
		} catch (Exception ex) {
			System.err.print("Exception: ");
			System.err.println(ex.getMessage());
		}
	}
	
	public static void main(String[] args) {
		ExcelTest run = new ExcelTest();
		run.sortArray();
		
	}
	
	public void sortArray()
	{
		stocklist = new String[totalrows][];
		
		for(int i = 0; i < totalrows; i++){
			stocklist[i] = rawdata[i].split(",");
		}		
		
		// Printed Array
		for(int i = 0; i < totalrows; i++){
			for(int j = 0; j < totalcols; j++){
				stocklist[i][j] = stocklist[i][j].replace("\"","");
				System.out.print(stocklist[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	
}