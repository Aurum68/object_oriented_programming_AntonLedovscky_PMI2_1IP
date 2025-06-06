package org.practice.g2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Class1ForIface2 implements Iface2{

    private final Integer number;
    private final String name;

    @Override
    public void method1() {
        System.out.printf("In Class1ForIface2: %d%n", number / name.length());
    }

    @Override
    public String toString() {
        return "Class1ForIface2{number=%d, name='%s'}@%h ".formatted(number, name, hashCode());
    }
}
