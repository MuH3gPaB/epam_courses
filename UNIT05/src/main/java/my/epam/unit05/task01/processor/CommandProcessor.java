package my.epam.unit05.task01.processor;

import java.io.OutputStream;
import java.util.Objects;

public abstract class CommandProcessor {

    private final OutputStream out;

    public CommandProcessor(OutputStream out) {
        Objects.requireNonNull(out);
        this.out = out;
    }

    public abstract void proceed(String command);
}
