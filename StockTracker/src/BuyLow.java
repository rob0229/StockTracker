

public class BuyLow implements Strategy{
	public String getRecommendation(int stockIndex){
		// Buys if stock price is 5% lower than previous close
		// Sells if the change (last – close) is greater than 5%
		// Holds otherwise
		float percent = ExcelTest.getPercentChange(stockIndex);
		
		if(percent < -1){
			return "Buy";
		}
		else if(percent > 0){
			return "Sell";
		}
		else{
			return "Hold";
		}
		
	}
}
