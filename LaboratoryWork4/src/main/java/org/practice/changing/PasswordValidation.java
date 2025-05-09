package org.practice.changing;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PasswordValidation implements IPropertyChangingListener{
    private static boolean isValid(String password) {
        if (password.length() < 8) return false;

        String regex = "^[a-zA-Z]+\\S{8,}$";
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        }catch (PatternSyntaxException e){
            throw new PatternSyntaxException("Invalid pattern", regex, -1);
        }

        return pattern.matcher(password).matches();
    }

    @Override
    public <T, V> void onPropertyChanging(T object, String propertyName, V oldValue, V newValue) {
        if (!propertyName.equalsIgnoreCase("password")) return;

        if (!isValid(newValue.toString())) {throw new IllegalArgumentException("Password is invalid");}
        System.out.printf("Changing in %s:%n Property: %s%n Old Value: %s%n New Value: %s%n Validator: %s%n",
                object.getClass().getName(), propertyName, oldValue, newValue, this.getClass().getName());
    }
}
