package pothole_solution.core.global.util.formatter;

import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @NotNull
    @Override
    public LocalDate parse(@NotNull String text, @NotNull Locale locale) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @NotNull
    @Override
    public String print(@NotNull LocalDate object, @NotNull Locale locale) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
    }
}
