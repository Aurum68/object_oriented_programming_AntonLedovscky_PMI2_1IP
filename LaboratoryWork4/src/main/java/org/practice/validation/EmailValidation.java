package org.practice.validation;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EmailValidation{
    public static boolean isValid(String email) {
        Pattern pattern;
        String regex = "^\\S+@\\S+\\.\\S+$";
        try {
            pattern = Pattern.compile(regex);
        }catch (PatternSyntaxException e){
            throw new PatternSyntaxException("Invalid regex", regex, -1);
        }

        return pattern.matcher(email).matches();
    }
}
