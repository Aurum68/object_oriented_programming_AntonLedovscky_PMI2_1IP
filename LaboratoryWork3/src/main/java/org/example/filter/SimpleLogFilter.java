package org.example.filter;

public class SimpleLogFilter implements ILogFilter {

    private final String pattern;

    public SimpleLogFilter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean match(String text) {
        String textLow= text.toLowerCase();
        String pattern = this.pattern.toLowerCase();
        return textLow.contains(pattern);
    }
}
