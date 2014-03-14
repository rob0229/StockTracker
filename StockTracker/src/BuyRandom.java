import java.sql.Time;


public class BuyRandom implements Strategy{
	public String getRecommendation(int stockIndex) {
		String symbol = ExcelTest.getSymbol(stockIndex);
		String time = ExcelTest.getTime(stockIndex);
		
		String subtime1;
		String subtime2;
		int hour, minute;
		String AMPM;
		
		// Split up time
		subtime2 = time.substring(time.lastIndexOf(":") + 1);
		subtime1 = time.replace(subtime2, "");
		subtime1 = subtime1.replace(":", "");
		hour = Integer.valueOf(subtime1);
		minute = Integer.valueOf(subtime2.substring(0, 2));
		AMPM = subtime2.substring(2);

		
		// Buys if the ticker symbol is 4 characters long
		// Sells if time is between 1:00pm and 3:22pm
		// Holds otherwise
		if(symbol.length() == 4){
			return "Buy";
		}
		else if((hour == 1 && minute >= 0 && AMPM.equals("pm")) || 
				(hour == 3 && minute <= 22 && AMPM.equals("pm")) ||
				(hour == 2 && AMPM.equals("pm"))){
			return "Sell";
		}
		else{
			return "Hold";
		}
	}
}
