package org.practice;

import org.practice.g1.Class1ForIface1;
import org.practice.g1.Class2ForIface1;
import org.practice.g1.Iface1;
import org.practice.g2.Class1ForIface2;
import org.practice.g2.Class2ForIface2;
import org.practice.g2.Iface2;
import org.practice.g3.Class1ForIface3;
import org.practice.g3.Class2ForIface3;
import org.practice.g3.Iface3;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Injector i1 = new Injector();
        Injector i2 = new Injector();

        i1.register(Iface3.class, Class1ForIface3::new);

        Map<String, Object> params = new HashMap<>();
        params.put("arg0", 10);
        params.put("arg1", "Anton");

        i1.register(Iface2.class, Class1ForIface2.class, LifeStyle.SINGLETON, params);
        i1.register(Iface1.class, Class1ForIface1.class, LifeStyle.SCOPE);

        Iface1 o1_1_1 = i1.getInstance(Iface1.class);
        Iface1 o1_2_1 = i1.getInstance(Iface1.class);
        System.out.println(o1_1_1);
        System.out.println(o1_2_1);

        o1_1_1.method();
        o1_2_1.method();

        try (Injector.Scope scope = i1.openScope()) {
            System.out.println("Inside scope");
            Iface1 o1_3_1 = i1.getInstance(Iface1.class);
            Iface1 o1_4_1 = i1.getInstance(Iface1.class);
            System.out.println(o1_3_1);
            System.out.println(o1_4_1);
            o1_3_1.method();
            o1_4_1.method();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Iface2 o2_1_1 = i1.getInstance(Iface2.class);
        Iface2 o2_2_1 = i1.getInstance(Iface2.class);
        System.out.println(o2_1_1);
        System.out.println(o2_2_1);
        o2_1_1.method1();
        o2_2_1.method1();

        Iface3 o3_1_1 = i1.getInstance(Iface3.class);
        Iface3 o3_2_1 = i1.getInstance(Iface3.class);
        System.out.println(o3_1_1);
        System.out.println(o3_2_1);
        o3_1_1.eq(6, 8);
        o3_2_1.eq(o2_1_1, o2_2_1);

        System.out.println();

        params.clear();
        params.put("arg0", generate(random.nextInt(10)));
        params.put("arg1", generate(random.nextInt(10)));

        System.out.println(params);

        i2.register(Iface2.class, Class2ForIface2.class, LifeStyle.SCOPE, params);
        i2.register(Iface3.class, Class2ForIface3.class, LifeStyle.SINGLETON);
        i2.register(Iface1.class, Class2ForIface1.class, LifeStyle.PER_REQUEST);

        Iface1 o1_1_2 = i2.getInstance(Iface1.class);
        Iface1 o1_2_2 = i2.getInstance(Iface1.class);
        System.out.println(o1_1_2);
        System.out.println(o1_2_2);
        o1_1_2.method();
        o1_2_2.method();

        Iface2 o2_1_2 = i2.getInstance(Iface2.class);
        Iface2 o2_2_2 = i2.getInstance(Iface2.class);
        System.out.println(o2_1_2);
        System.out.println(o2_2_2);
        o2_1_2.method1();
        o2_2_2.method1();

        try (Injector.Scope scope = i2.openScope()){
            System.out.println("Inside scope");
            Iface2 o2_3_2 = i2.getInstance(Iface2.class);
            Iface2 o2_4_2 = i2.getInstance(Iface2.class);
            System.out.println(o2_3_2);
            System.out.println(o2_4_2);
            o2_3_2.method1();
            o2_4_2.method1();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Iface3 o3_1_2 = i2.getInstance(Iface3.class);
        Iface3 o3_2_2 = i2.getInstance(Iface3.class);
        System.out.println(o3_1_2);
        System.out.println(o3_2_2);
        params.clear();
    }
}