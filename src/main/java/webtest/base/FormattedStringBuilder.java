package webtest.base;

import static java.lang.String.format;

public class FormattedStringBuilder {
    private StringBuilder content = new StringBuilder();

    /**
     * Add new string to end of original string.<br>
     * String can be plain or formatted.<br>
     * <br>
     * U can use "[%s]" or "[{}]" as well.
     */
    public FormattedStringBuilder add(String format, Object... args) {
        if (format != null) {
            content.append(format(format.replace("{}", "%s"), args));
        }
        return this;
    }

    public FormattedStringBuilder addLn(String format, Object... args) {
        add(format, args);
        content.append(System.lineSeparator());
        return this;
    }

    public FormattedStringBuilder add(Object obj) {
        content.append(obj);
        return this;
    }

    public FormattedStringBuilder addLn(Object obj) {
        add(obj);
        content.append(System.lineSeparator());
        return this;
    }

    public FormattedStringBuilder add(String str) {
        if (str == null) {
            return this;
        }
        content.append(str.replaceAll("%n", System.lineSeparator()));
        return this;
    }

    public FormattedStringBuilder addLn(String str) {
        add(str);
        content.append(System.lineSeparator());
        return this;
    }

    public FormattedStringBuilder add(char c) {
        content.append(c);
        return this;
    }

    public FormattedStringBuilder add(double number) {
        content.append(number);
        return this;
    }

    public FormattedStringBuilder add(float number) {
        content.append(number);
        return this;
    }

    public FormattedStringBuilder add(int number) {
        content.append(number);
        return this;
    }

    public FormattedStringBuilder add(long number) {
        content.append(number);
        return this;
    }

    public FormattedStringBuilder addLine() {
        content.append(System.lineSeparator());
        return this;
    }

    public String toString() {
        return content.toString().trim();
    }

    public static FormattedStringBuilder formatEx(String format, Object... args) {
        return new FormattedStringBuilder().add(format, args);
    }

    public static FormattedStringBuilder formatExLn(String format, Object... args) {
        return new FormattedStringBuilder().addLn(format, args);
    }

}
