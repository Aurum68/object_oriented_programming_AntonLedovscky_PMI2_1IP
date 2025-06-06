package org.practice.g2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Class2ForIface2 implements Iface2{

    private final String name;
    private final String surname;

    @Override
    public void method1() {
        System.out.printf("In Class2ForIface2: %d%n", name.length() + surname.length());
    }

    @Override
    public String toString() {
        return "Class2ForIface2{name='%s', surname='%s'}@%h ".formatted(name, surname, hashCode());
    }
}
