package net.minecraft.util;

import org.apache.logging.log4j.*;
import java.io.*;

public class LoggingPrintStream extends PrintStream
{
    private final String domain;
    private static final Logger LOGGER;
    
    private void logString(final String s) {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        final StackTraceElement stackTraceElement = stackTrace[Math.min(3, stackTrace.length)];
        LoggingPrintStream.LOGGER.info("[{}]@.({}:{}): {}", new Object[] { this.domain, stackTraceElement.getFileName(), stackTraceElement.getLineNumber(), s });
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    @Override
    public void println(final String s) {
        this.logString(s);
    }
    
    @Override
    public void println(final Object o) {
        this.logString(String.valueOf(o));
    }
    
    public LoggingPrintStream(final String domain, final OutputStream outputStream) {
        super(outputStream);
        this.domain = domain;
    }
}
