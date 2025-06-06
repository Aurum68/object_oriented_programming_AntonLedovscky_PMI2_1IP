package org.practice.g3;

public class Class2ForIface3 implements Iface3{
    @Override
    public void eq(Object o1, Object o2) {
        System.out.println(o1.hashCode() - o2.hashCode() == 0);
    }

    @Override
    public String toString() {
        return "Class2ForIface3{}@%h ".formatted(hashCode());
    }
}
