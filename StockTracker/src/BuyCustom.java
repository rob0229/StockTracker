
public class BuyCustom implements Strategy {
	public String getRecommendation(int stockIndex) {
		// Buys if the volume traded is greater than 2 million
		// Sells if the volume traded is less than 2 million but greater than 1 million
		// Holds otherwise
		float volumeTraded = ExcelData.getVolume(stockIndex);
		
		if(volumeTraded > 2000000){
			return "Buy";
		}
		else if(volumeTraded < 2000000 && volumeTraded > 1000000) {
			
			return "Sell";
			
		}
		else{
			return "Hold";
		}
	}

}
