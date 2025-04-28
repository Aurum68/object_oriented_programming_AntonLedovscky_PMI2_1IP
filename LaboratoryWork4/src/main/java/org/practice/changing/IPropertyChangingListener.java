package org.practice.changing;

public interface IPropertyChangingListener {
    <T, V> void onPropertyChanging(T object, String propertyName, V oldValue, V newValue);
}
