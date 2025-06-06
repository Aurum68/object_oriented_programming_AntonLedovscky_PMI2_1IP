package org.practice;

import lombok.Getter;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class Injector {

    @Getter
    private static class Registration{
        private Class<?> implementation;
        private final LifeStyle lifeStyle;
        private Map<String, Object> parameters;
        private Supplier<?> factory;

        Registration(Class<?> implementation, LifeStyle lifeStyle, Map<String, Object> parameters){
            this.implementation = implementation;
            this.lifeStyle = lifeStyle;
            this.parameters = parameters == null ? new HashMap<>() : parameters;
        }

        Registration(Supplier<?> factory){
            this.factory = factory;
            this.lifeStyle = LifeStyle.PER_REQUEST;
        }
    }

    public static class Scope implements AutoCloseable{

        private final ThreadLocal<Map<Class<?>, Object>> cache;

        Scope(ThreadLocal<Map<Class<?>, Object>> cache){
            this.cache = cache;
        }

        @Override
        public void close() throws Exception {
            cache.remove();
        }
    }

    private final Map<Class<?>, Registration> registrations = new HashMap<>();

    private final Map<Class<?>, Object> singletonsCache = new ConcurrentHashMap<>();

    private final ThreadLocal<Map<Class<?>, Object>> scopeCache = ThreadLocal.withInitial(HashMap::new);

    public <T> void register(Class<T> interfaceType, Class<? extends T> implementation, LifeStyle lifeStyle){
        registrations.put(interfaceType, new Registration(implementation, lifeStyle, null));
    }

    public <T> void register(Class<T> interfaceType, Class<? extends T> implementation, LifeStyle lifeStyle, Map<String, Object> parameters){
        registrations.put(interfaceType, new Registration(implementation, lifeStyle, parameters));
    }

    public <T> void register(Class<T> interfaceType, Supplier<? extends T> factory){
        registrations.put(interfaceType, new Registration(factory));
    }


    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> interfaceType){
        Registration registration = registrations.get(interfaceType);
        if(registration == null){throw new IllegalArgumentException("No such interface type: " + interfaceType);}

        switch (registration.getLifeStyle()){
            case SINGLETON -> {
                return (T) singletonsCache.computeIfAbsent(interfaceType, k -> createInstance(registration));
            }
            case SCOPE -> {
                Map<Class<?>, Object> cache = scopeCache.get();
                return (T) cache.computeIfAbsent(interfaceType, k -> createInstance(registration));
            }
            case PER_REQUEST -> {return (T) createInstance(registration);}
            default -> throw new IllegalArgumentException("Unsupported lifeStyle: " + registration.getLifeStyle());
        }
    }

    private Object createInstance(Registration registration){
        if (registration.getFactory() != null){
            return registration.getFactory().get();
        }

        try {
            return Arrays.stream(registration.getImplementation().getConstructors())
                    .max(Comparator.comparingInt(Constructor::getParameterCount))
                    .map(constructor -> {
                        try {
                            Class<?>[] parameterTypes = constructor.getParameterTypes();
                            Object[] args = new Object[parameterTypes.length];
                            for (int i = 0; i < parameterTypes.length; i++) {
                                Object param;
                                String paramName = constructor.getParameters()[i].getName();

                                if (registration.getParameters() != null && registration.getParameters().containsKey(paramName)){
                                    param = registration.getParameters().get(paramName);
                                }else if (registrations.containsKey(parameterTypes[i])){
                                    param = getInstance(parameterTypes[i]);
                                }else throw new RuntimeException("Unresolved dependency for: " + parameterTypes[i]);

                                args[i] = param;
                            }
                            return constructor.newInstance(args);
                        }catch (Exception e){
                            throw new RuntimeException(e);
                        }
                    })
                    .orElseThrow(() -> new RuntimeException("No constructor found"));
        }catch (Exception e){
            throw new RuntimeException("Failed to create instance: " + e.getMessage(), e);
        }
    }

    public Scope openScope(){
        scopeCache.set(new HashMap<>());
        return new Scope(scopeCache);
    }
}
