package org.practice.validation;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PasswordValidation {
    public static boolean isValid(String password) {
        if (password.length() < 8) return false;

        String regex = "^[a-zA-Z]+\\S{7,}$";
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        }catch (PatternSyntaxException e){
            throw new PatternSyntaxException("Invalid pattern", regex, -1);
        }

        return pattern.matcher(password).matches();
    }
}
