package org.practice.g1;

import lombok.RequiredArgsConstructor;
import org.practice.g3.Iface3;

@RequiredArgsConstructor
public class Class2ForIface1 implements Iface1{

    private final Iface3 some;

    @Override
    public void method() {
        System.out.printf("Class2ForIface1 has %s%n", some.toString());
    }

    @Override
    public String toString() {
        return String.format("Class2ForIface1{some=%s}@%h ", some.toString(), hashCode());
    }
}
