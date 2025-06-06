package org.practice.changing;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@FieldNameConstants
public class User implements INotifyDataChanging{

    private final List<IPropertyChangingListener> listeners = new ArrayList<>();

    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private String username;
    private String password;

    public void setName(String name) {
        notifyPropertyChanging(Fields.name, this.name, name);
        this.name = name;
    }

    public void setSurname(String surname) {
        notifyPropertyChanging(Fields.surname, this.surname, surname);
        this.surname = surname;
    }

    public void setBirthDate(LocalDate birthDate) {
        notifyPropertyChanging(Fields.birthDate, this.birthDate, birthDate);
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        notifyPropertyChanging(Fields.email, this.email, email);
        this.email = email;
    }

    public void setUsername(String username) {
        notifyPropertyChanging(Fields.username, this.username, username);
        this.username = username;
    }

    public void setPassword(String password) {
        notifyPropertyChanging(Fields.password, this.password, password);
        this.password = password;
    }

    @Override
    public void addPropertyChangingListener(IPropertyChangingListener listener) {listeners.add(listener);}
    @Override
    public void removePropertyChangingListener(IPropertyChangingListener listener) {listeners.remove(listener);}

    private <T> void notifyPropertyChanging(String propertyName, T oldValue, T newValue) {
        for (IPropertyChangingListener listener : listeners){
            listener.onPropertyChanging(this, propertyName, oldValue, newValue);
            System.out.println();
        }
    }
}
