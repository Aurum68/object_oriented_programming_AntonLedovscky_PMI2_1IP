package org.practice.validation;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UsernameValidation {
    public static boolean isValid(String username) {
        if (username.length() < 3) return false;
        if (username.length() > 20) return false;

        String regex = "^[a-zA-Z0-9_-]{3,20}$";
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        }catch (PatternSyntaxException e){
            throw new PatternSyntaxException("Invalid username", regex, -1);
        }

        return pattern.matcher(username).matches();
    }
}
