package org.practice.g1;

import lombok.RequiredArgsConstructor;
import org.practice.g2.Iface2;

@RequiredArgsConstructor
public class Class1ForIface1 implements Iface1{

    private final Iface2 some;

    @Override
    public void method() {
        System.out.printf("Class1ForIface1 has %s%n", some);
    }

    @Override
    public String toString() {
        return String.format("Class1ForIface1{some=%s}@%h ", some, hashCode());
    }
}
