package org.practice.changing;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EmailValidation implements IPropertyChangingListener{
    private static boolean isValid(String email) {
        Pattern pattern;
        String regex = "^\\S+@\\S+\\.\\S+$";
        try {
            pattern = Pattern.compile(regex);
        }catch (PatternSyntaxException e){
            throw new PatternSyntaxException("Invalid regex", regex, -1);
        }

        return pattern.matcher(email).matches();
    }

    @Override
    public <T, V> void onPropertyChanging(T object, String propertyName, V oldValue, V newValue) {
        if (!propertyName.equalsIgnoreCase("email")) return;

        if (!isValid(newValue.toString())) {throw new IllegalArgumentException("Email address is invalid");}
        System.out.printf("Changing in %s:%n Property: %s%n Old Value: %s%n New Value: %s%n Validator: %s%n",
                object.getClass().getName(), propertyName, oldValue, newValue, this.getClass().getName());
    }
}
