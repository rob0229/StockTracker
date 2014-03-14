import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class StrategyButtonListener implements ActionListener, ChangeListener, ItemListener{
	// Strategy pattern
	Strategy strategy;
	
	StrategyButtonListener(){
		setStrategy(new BuyLow());
	}
	
	
	// Object of recommendations
	ArrayList<Object> advice = new ArrayList<Object>();
	

        public void actionPerformed(ActionEvent e) {
            
            if (e.getActionCommand() == "bl") {
               System.out.println("bl");
               setStrategy(new BuyLow());
                
            }else if(e.getActionCommand() == "br") {
            	System.out.println("br");
            	setStrategy(new BuyRise());
            }
            else if(e.getActionCommand() == "brand") {
            	System.out.println("brand");
            }
            else {
            	System.out.println("custom");
            }
            
            
        }
        
    	// Radio buttons to set strategy
    	public void setStrategy(Strategy s){
    		strategy = s;
    	}
    	
    	// Get recommendations
    	public ArrayList<Object> notifyStrategy(ArrayList<Integer> stockIndex){
    		advice.clear();
    		for(int i = 0; i < stockIndex.size(); i++){
    			advice.add(strategy.getRecommendation(stockIndex.get(i)));
    		}
    		
    		return advice;
    	}	
        
        //Required from super class
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			
		}
		//Required from super class
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			
		}
    
	}