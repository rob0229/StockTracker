import java.sql.Time;


public class BuyRandom implements Strategy{
	public String getRecommendation(int stockIndex) {
		
		// Buys if the ticker symbol is 4 characters long
		// Sells if time is between 1:00pm and 3:22pm
		// Holds otherwise
		float percent = ExcelTest.getPercentChange(stockIndex);
		String name = ExcelTest.getSymbol(stockIndex);
		Time time = ExcelTest.getTime(stockIndex);
		
		if(percent > 1){
			return "Buy";
		}
		else if(percent < -1){
			return "Sell";
		}
		else{
			return "Hold";
		}
	}

}
