package org.practice.changing;

public class UsualListener implements IPropertyChangingListener{
    @Override
    public <T, V> void onPropertyChanging(T object, String propertyName, V oldValue, V newValue) {
        System.out.printf("Changing in %s:%n Property: %s%n Old Value: %s%n New Value: %s%n Validator: %s%n",
                object.getClass().getName(), propertyName, oldValue, newValue, this.getClass().getName());
    }
}
