package org.practice.changing;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UsernameValidation implements IPropertyChangingListener{
    private static boolean isValid(String username) {
        if (username.length() < 3) return false;
        if (username.length() > 20) return false;

        String regex = "^[a-zA-Z]{3,20}[0-9]+$";
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        }catch (PatternSyntaxException e){
            throw new PatternSyntaxException("Invalid username", regex, -1);
        }

        return pattern.matcher(username).matches();
    }

    @Override
    public <T, V> void onPropertyChanging(T object, String propertyName, V oldValue, V newValue) {
        if (!propertyName.equalsIgnoreCase("username")) return;

        if (!isValid(newValue.toString())) {throw new IllegalArgumentException("Username is invalid");}
        System.out.printf("Changing in %s:%n Property: %s%n Old Value: %s%n New Value: %s%n Validator: %s%n",
                object.getClass().getName(), propertyName, oldValue, newValue, this.getClass().getName());
    }
}
