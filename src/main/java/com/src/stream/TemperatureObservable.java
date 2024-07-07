package com.src.stream;

import lombok.Getter;

import java.util.Observable;
import java.util.Observer;
/*
Why Observable is deprecated

1. Observable is a class , so class extending Observable can't subclass other class
2. Observable class :: Not Serializable
3. Observable class :: Not thread-safe - methods can be overridden by its subclasses
4. Observable class :: conveys something has changed, but they don't convey which field has changed

	Use :: PropertyChangeSupport, PropertyChangeListener
 */
@Getter
public class TemperatureObservable extends Observable {

	private int temperature;
	private String weather;

	public void setTemperature(int temperature) {
		setChanged();
		notifyObservers();
		this.temperature = temperature;
	}
	
	public static void main(String[] args) {
		TemperatureObservable t0 = new TemperatureObservable();
		t0.addObserver(new TemperatureObserver());
		t0.addObserver(new TemperatureIceCreamObserver());
		t0.setTemperature(12); //cold Ice Cream
	}
}

class TemperatureObserver implements Observer{
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof TemperatureObservable) {
			TemperatureObservable temp = (TemperatureObservable)arg0;
			int temperature = temp.getTemperature();
			if(temperature<=15) {
				System.out.println("cold");
			}
			else if(temperature>15 && temperature<30) {
				System.out.println("mild");
			}else {
				System.out.println("cold");
			}
		}		
	}
}

class TemperatureIceCreamObserver implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof TemperatureObservable) {
			TemperatureObservable temp = (TemperatureObservable)o;
			int temperature = temp.getTemperature();
			if(temperature<=0) {
				System.out.println("Ice Cream");
			}
			else {
				System.out.println("no ice cream");
			}
		}
	}

}