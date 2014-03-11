public abstract class Subject {
	// Methods to register and unregister observers
	public void registerObserver(Observer obj){}
	public void removeObserver(Observer obj){}
	
	// Method to notify observers of change
	public void notifyObservers(){}
	
	
	Strategy strategy;
	
	public Subject() {}
	public void setStrategy(Strategy s){}
	
}
