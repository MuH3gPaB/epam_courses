package my.epam.unit07.task02;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

public class LogAppender extends WriterAppender {

    private static LogAppender firstInstance;

    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    public LogAppender() {
        synchronized (LogAppender.class) {
            if (firstInstance == null) {
                firstInstance = this;
            }
            setLayout(new PatternLayout("%m%n"));
            setWriter(new OutputStreamWriter(out));
        }
    }

    public static byte[] getLog() {
        if (firstInstance != null) {
            return firstInstance.out.toByteArray();
        } else {
            return new byte[0];
        }
    }

    public static void clear(){
        if (firstInstance != null) {
            firstInstance.out.reset();
        }
    }
}
