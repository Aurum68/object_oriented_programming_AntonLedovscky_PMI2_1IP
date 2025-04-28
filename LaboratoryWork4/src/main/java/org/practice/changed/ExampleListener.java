package org.practice.changed;

public class ExampleListener implements IPropertyChangedListener{
    @Override
    public <T> void onPropertyChanged(T odject, String propertyName) {
        System.out.println("In " + odject.getClass().getName() + " property: " + propertyName + " changed");
    }
}
