package org.example.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ReLogFilter implements ILogFilter {

    private Pattern pattern;

    public ReLogFilter(String pattern) {
        if (!checkPattern(pattern)){
            throw new PatternSyntaxException("Incorrect pattern", pattern, -1);
        }
        this.pattern = Pattern.compile(pattern);
    }

    private static boolean checkPattern(String pattern) {
        try {
            Pattern.compile(pattern);
        } catch (PatternSyntaxException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean match(String text) {
        if (text == null) throw new NullPointerException();

        text = text.toLowerCase();
        Matcher matcher = this.pattern.matcher(text);
        return matcher.lookingAt();
    }
}
