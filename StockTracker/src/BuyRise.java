

public class BuyRise implements Strategy{
	public String getRecommendation(int stockIndex) {
		// Buys if the change (last – close) is greater than 5%
		// Sells if stock price is 5% lower than previous close
		// Holds otherwise
		float percent = ExcelData.getPercentChange(stockIndex);
		
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
