package net.minecraft.util;

import java.util.concurrent.*;
import org.apache.logging.log4j.*;

public class Util
{
    public static Object func_181617_a(final FutureTask futureTask, final Logger logger) {
        futureTask.run();
        return futureTask.get();
    }
    
    public static EnumOS getOSType() {
        final String lowerCase = "\u03ba\u03a6\u03fb\u03bb\u03b4\u03b8\u03b0".toLowerCase();
        return lowerCase.contains("win") ? EnumOS.WINDOWS : (lowerCase.contains("mac") ? EnumOS.OSX : (lowerCase.contains("solaris") ? EnumOS.SOLARIS : (lowerCase.contains("sunos") ? EnumOS.SOLARIS : (lowerCase.contains("linux") ? EnumOS.LINUX : (lowerCase.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
    }
    
    public enum EnumOS
    {
        UNKNOWN("UNKNOWN", 4), 
        OSX("OSX", 3), 
        LINUX("LINUX", 0), 
        WINDOWS("WINDOWS", 2);
        
        private static final EnumOS[] $VALUES;
        
        SOLARIS("SOLARIS", 1);
        
        private EnumOS(final String s, final int n) {
        }
        
        static {
            $VALUES = new EnumOS[] { EnumOS.LINUX, EnumOS.SOLARIS, EnumOS.WINDOWS, EnumOS.OSX, EnumOS.UNKNOWN };
        }
    }
}
