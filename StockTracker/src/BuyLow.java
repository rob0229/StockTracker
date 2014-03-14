
public class BuyLow implements Strategy{
	public String getRecommendation(float percent){
		// Buys if stock price is 5% lower than previous close
		// Sells if the change (last � close) is greater than 5%
		// Holds otherwise
		
		if(percent < -5){
			return "Buy";
		}
		else if(percent > 5){
			return "Sell";
		}
		else{
			return "Hold";
		}
		
	}
}
