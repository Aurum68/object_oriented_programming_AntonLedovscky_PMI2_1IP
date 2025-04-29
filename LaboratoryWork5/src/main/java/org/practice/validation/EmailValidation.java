package org.practice.validation;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EmailValidation {
    public static boolean isEmailValid(String email) {
        String regex = "^\\S+@\\S+\\.\\S+$";
        Pattern p;
        try {
            p = Pattern.compile(regex);
        }catch (PatternSyntaxException e){
            throw new PatternSyntaxException("Invalid regex", regex, 1);
        }
        return p.matcher(email).matches();
    }
}
