import java.util.ArrayList;

public class Tracker extends Subject{
	// Observer Pattern
	protected ArrayList observers;
	protected float last;
	protected float change;
	protected float changepct;
	protected int date;
	protected int time;
	protected float high;
	protected float low;
	protected float close;
	protected int volume;
	protected String name;
	
	
	public Tracker() {
		observers = new ArrayList();
	}
	
	public void registerObserver(Observer obj){
		observers.add(obj);
	}
	
	public void removeObserver(Observer obj){
		int i = observers.indexOf(obj);
		if (i >= 0) {
			observers.remove(i);
		}
	}
	
	public void notifyObservers(){
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer)observers.get(i);
			observer.update();
		}
	}
	
	public void stockPriceChanged(){
		notifyObservers();
	}
	
	// Strategy Pattern	
	public void setStrategy(Strategy strategy){
		this.strategy = strategy;
	}
	
	public void getAdvice(){
		strategy.getRecommendation();
	}
			
}
