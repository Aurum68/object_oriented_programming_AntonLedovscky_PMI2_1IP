package org.practice;

import org.practice.changed.Car;
import org.practice.changed.ExampleListener;
import org.practice.changing.TestListener;
import org.practice.changing.User;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Car mercedes_benz = new Car("Mercedes", "Benz G-class", 2010);
        mercedes_benz.addPropertyChangedListener(new ExampleListener());
        mercedes_benz.setColor("Blue");
        mercedes_benz.setEngine("G 500 (4х4)2 4.0");
        mercedes_benz.setBrakeSystem("Usual");
        mercedes_benz.setWheels("Crossroad");

        User user = new User();
        user.addPropertyChangingListener(new TestListener());
        user.setName("Anton");
        user.setSurname("Ledovscky");
        user.setBirthDate(LocalDate.of(2006, 7, 23));
        user.setEmail("anton.ledovscky@mail.com");
        user.setUsername("Anton");
        user.setPassword("password");
    }
}