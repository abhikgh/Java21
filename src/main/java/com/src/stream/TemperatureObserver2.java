package com.src.stream;

import lombok.Getter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@Getter
class TemperatureObservablePCS {

    private int temperature;
    private String weather;

    private PropertyChangeSupport propertyChangeSupport;

    public TemperatureObservablePCS(){
        this.propertyChangeSupport = new PropertyChangeSupport(this); //The bean to be given as the source for any events
    }

    public void setTemperature(int temperature) {
        propertyChangeSupport.firePropertyChange("temperature", this.temperature, temperature);
        this.temperature = temperature;
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }
}

public class TemperatureObserver2 implements PropertyChangeListener{

    private int temperature;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.temperature= (int) evt.getNewValue();
        if(temperature<=15) {
            System.out.println("cold");
        }
        else if(temperature>15 && temperature<30) {
            System.out.println("mild");
        }else {
            System.out.println("cold");
        }
    }

    public static void main(String[] args) {
        TemperatureObservablePCS observable = new TemperatureObservablePCS();
        TemperatureObserver2 observer = new TemperatureObserver2();

        observable.addPropertyChangeListener(observer);
        observable.setTemperature(20);
    }
}
