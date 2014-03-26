

public class BuyLow implements Strategy{
	public String getRecommendation(int stockIndex){
		// Buys if stock price is 1% lower than previous close
		// Sells if the change (last – close) is greater than 1%
		// Holds otherwise
		float percent = ExcelData.getPercentChange(stockIndex);
		
		if(percent < -1){
			return "Buy";
		}
		else if(percent > 1){
			return "Sell";
		}
		else{
			return "Hold";
		}
		
	}
	
	public void update(String message){
		
		
		
	}
}
