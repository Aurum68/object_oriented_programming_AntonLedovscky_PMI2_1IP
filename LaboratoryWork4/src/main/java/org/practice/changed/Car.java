package org.practice.changed;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@FieldNameConstants
public class Car implements INotifyDataChanged{

    @Getter(AccessLevel.NONE)
    private final List<IPropertyChangedListener> listeners = new ArrayList<>();

    private final String brand;
    private final String model;
    private final int year;

    private String color;
    private String engine;
    private String brakeSystem;
    private String wheels;

    public void setColor(String color) {
        this.color = color;
        notifyPropertyChanged(Car.Fields.color);
    }

    public void setEngine(String engine) {
        this.engine = engine;
        notifyPropertyChanged(Car.Fields.engine);
    }

    public void setBrakeSystem(String brakeSystem) {
        this.brakeSystem = brakeSystem;
        notifyPropertyChanged(Car.Fields.brakeSystem);
    }

    public void setWheels(String wheels) {
        this.wheels = wheels;
        notifyPropertyChanged(Car.Fields.wheels);
    }

    @Override
    public void addPropertyChangedListener(IPropertyChangedListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removePropertyChangedListener(IPropertyChangedListener listener) {
        listeners.remove(listener);
    }

    private void notifyPropertyChanged(String propertyName) {
        for (IPropertyChangedListener listener : listeners){
            listener.onPropertyChanged(this, propertyName);
        }
    }
}
