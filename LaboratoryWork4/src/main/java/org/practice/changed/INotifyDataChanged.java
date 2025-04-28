package org.practice.changed;

public interface INotifyDataChanged {
    void addPropertyChangedListener(IPropertyChangedListener listener);
    void removePropertyChangedListener(IPropertyChangedListener listener);
}
