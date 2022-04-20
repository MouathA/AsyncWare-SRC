package viamcp.utils;

import java.util.logging.*;
import java.text.*;

public class JLoggerToLog4j extends Logger
{
    private final org.apache.logging.log4j.Logger base;
    
    @Override
    public void log(final Level level, final String s, final Throwable t) {
        if (level == Level.FINE) {
            this.base.debug(s, t);
        }
        else if (level == Level.WARNING) {
            this.base.warn(s, t);
        }
        else if (level == Level.SEVERE) {
            this.base.error(s, t);
        }
        else if (level == Level.INFO) {
            this.base.info(s, t);
        }
        else {
            this.base.trace(s, t);
        }
    }
    
    @Override
    public void log(final Level level, final String s, final Object o) {
        if (level == Level.FINE) {
            this.base.debug(s, new Object[] { o });
        }
        else if (level == Level.WARNING) {
            this.base.warn(s, new Object[] { o });
        }
        else if (level == Level.SEVERE) {
            this.base.error(s, new Object[] { o });
        }
        else if (level == Level.INFO) {
            this.base.info(s, new Object[] { o });
        }
        else {
            this.base.trace(s, new Object[] { o });
        }
    }
    
    @Override
    public void log(final LogRecord logRecord) {
        this.log(logRecord.getLevel(), logRecord.getMessage());
    }
    
    public JLoggerToLog4j(final org.apache.logging.log4j.Logger base) {
        super("logger", null);
        this.base = base;
    }
    
    @Override
    public void log(final Level level, final String s, final Object[] array) {
        this.log(level, MessageFormat.format(s, array));
    }
    
    @Override
    public void log(final Level level, final String s) {
        if (level == Level.FINE) {
            this.base.debug(s);
        }
        else if (level == Level.WARNING) {
            this.base.warn(s);
        }
        else if (level == Level.SEVERE) {
            this.base.error(s);
        }
        else if (level == Level.INFO) {
            this.base.info(s);
        }
        else {
            this.base.trace(s);
        }
    }
}
