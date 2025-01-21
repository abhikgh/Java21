package com.src.stream;

import lombok.Getter;

import java.util.Observable;
import java.util.Observer;

/**
 *  Observer Design Pattern
 *
 *  behavioral design pattern that defines a one-to-many dependency between objects.
 *  When Observable changes state, all its Observers are notified and updated automatically.
 */

/*
Why Observable is deprecated

1. Observable class :: cannot extend , other class is a class , so class extending Observable can't subclass other class
2. conveys something has changed, but they don't convey which field has changed
3. Performance Overhead: Notifying a large number of observers can be time-consuming.
4. Memory Leaks: Observers that are no longer used

	Use :: PropertyChangeSupport, PropertyChangeListener
 */
@Getter
public class TemperatureObservable extends Observable {

	private int temperature;

	//1.setChanged() / notifyObservers()
	public void setTemperature(int temperature) {
		setChanged();
		notifyObservers();
		this.temperature = temperature;
	}
	
	public static void main(String[] args) {
		TemperatureObservable t0 = new TemperatureObservable();
		//add observers to observable
		t0.addObserver(new TemperatureObserver());
		t0.addObserver(new TemperatureIceCreamObserver());
		t0.setTemperature(12); //cold Ice Cream
	}
}

class TemperatureObserver implements Observer{
	@Override
	public void update(Observable observable, Object arg1) {
		if(observable instanceof TemperatureObservable temperatureObservable) {
			int temperature = temperatureObservable.getTemperature();
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
	public void update(Observable observable, Object arg) {
		if(observable instanceof TemperatureObservable temperatureObservable) {
			int temperature = temperatureObservable.getTemperature();
			if(temperature<=0) {
				System.out.println("Ice Cream");
			}
			else {
				System.out.println("no ice cream");
			}
		}
	}

}