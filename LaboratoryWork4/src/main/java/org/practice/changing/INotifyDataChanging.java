package org.practice.changing;

public interface INotifyDataChanging {
    void addPropertyChangingListener(IPropertyChangingListener listener);
    void removePropertyChangingListener(IPropertyChangingListener listener);
}
