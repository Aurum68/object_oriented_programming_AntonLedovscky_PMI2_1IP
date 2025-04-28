package org.practice.changing;

public class TestListener implements IPropertyChangingListener{
    @Override
    public <T, V> void onPropertyChanging(T object, String propertyName, V oldValue, V newValue) {
        System.out.println("In " + object.getClass().getName() +
                " property: " + propertyName + " changed from " + oldValue + " to " + newValue);
    }
}
