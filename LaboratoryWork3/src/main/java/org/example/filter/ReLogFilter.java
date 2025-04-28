package org.example.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ReLogFilter implements ILogFilter {

    private final Pattern pattern;

    public ReLogFilter(String pattern) {
        try {
            Pattern.compile(pattern);
        }catch(PatternSyntaxException e) {
            throw new PatternSyntaxException("Invalid pattern", pattern, -1);
        }
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public boolean match(String text) {
        if (text == null) throw new NullPointerException();

        text = text.toLowerCase();
        Matcher matcher = this.pattern.matcher(text);
        return matcher.lookingAt();
    }
}
