package org.practice.changed;

public interface IPropertyChangedListener {
    <T> void onPropertyChanged(T odject, String propertyName);
}
