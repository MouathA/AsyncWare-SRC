package net.minecraft.crash;

import com.google.common.collect.*;
import java.text.*;
import java.util.concurrent.*;
import java.lang.management.*;
import java.util.*;
import net.minecraft.world.gen.layer.*;
import optfine.*;
import org.apache.commons.lang3.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import java.io.*;
import org.apache.commons.io.*;

public class CrashReport
{
    private final Throwable cause;
    private boolean reported;
    private File crashReportFile;
    private static final Logger logger;
    private final CrashReportCategory theReportCategory;
    private final List crashReportSections;
    private static final String __OBFID = "CL_00000990";
    private boolean field_85059_f;
    private final String description;
    private StackTraceElement[] stacktrace;
    
    public CrashReport(final String description, final Throwable cause) {
        this.theReportCategory = new CrashReportCategory(this, "System Details");
        this.crashReportSections = Lists.newArrayList();
        this.field_85059_f = true;
        this.stacktrace = new StackTraceElement[0];
        this.reported = false;
        this.description = description;
        this.cause = cause;
        this.populateEnvironment();
    }
    
    private static String getWittyComment() {
        final String[] array = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine." };
        return array[(int)(System.nanoTime() % array.length)];
    }
    
    public CrashReportCategory makeCategory(final String s) {
        return this.makeCategoryDepth(s, 1);
    }
    
    public File getFile() {
        return this.crashReportFile;
    }
    
    public boolean saveToFile(final File crashReportFile) {
        if (this.crashReportFile != null) {
            return false;
        }
        if (crashReportFile.getParentFile() != null) {
            crashReportFile.getParentFile().mkdirs();
        }
        final FileWriter fileWriter = new FileWriter(crashReportFile);
        fileWriter.write(this.getCompleteReport());
        fileWriter.close();
        this.crashReportFile = crashReportFile;
        return true;
    }
    
    public String getCompleteReport() {
        if (!this.reported) {
            this.reported = true;
            CrashReporter.onCrashReport(this);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("---- Minecraft Crash Report ----\n");
        sb.append("// ");
        sb.append(getWittyComment());
        sb.append("\n\n");
        sb.append("Time: ");
        sb.append(new SimpleDateFormat().format(new Date()));
        sb.append("\n");
        sb.append("Description: ");
        sb.append(this.description);
        sb.append("\n\n");
        sb.append(this.getCauseStackTraceOrString());
        sb.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
        while (true) {
            sb.append("-");
            int n = 0;
            ++n;
        }
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public CrashReportCategory makeCategoryDepth(final String s, final int n) {
        final CrashReportCategory crashReportCategory = new CrashReportCategory(this, s);
        if (this.field_85059_f) {
            final int prunedStackTrace = crashReportCategory.getPrunedStackTrace(n);
            final StackTraceElement[] stackTrace = this.cause.getStackTrace();
            StackTraceElement stackTraceElement = null;
            StackTraceElement stackTraceElement2 = null;
            final int n2 = stackTrace.length - prunedStackTrace;
            if (n2 < 0) {
                System.out.println("Negative index in crash report handler (" + stackTrace.length + "/" + prunedStackTrace + ")");
            }
            if (stackTrace != null && 0 <= n2 && n2 < stackTrace.length) {
                stackTraceElement = stackTrace[n2];
                if (stackTrace.length + 1 - prunedStackTrace < stackTrace.length) {
                    stackTraceElement2 = stackTrace[stackTrace.length + 1 - prunedStackTrace];
                }
            }
            this.field_85059_f = crashReportCategory.firstTwoElementsOfStackTraceMatch(stackTraceElement, stackTraceElement2);
            if (prunedStackTrace > 0 && !this.crashReportSections.isEmpty()) {
                this.crashReportSections.get(this.crashReportSections.size() - 1).trimStackTraceEntriesFromBottom(prunedStackTrace);
            }
            else if (stackTrace != null && stackTrace.length >= prunedStackTrace && 0 <= n2 && n2 < stackTrace.length) {
                System.arraycopy(stackTrace, 0, this.stacktrace = new StackTraceElement[n2], 0, this.stacktrace.length);
            }
            else {
                this.field_85059_f = false;
            }
        }
        this.crashReportSections.add(crashReportCategory);
        return crashReportCategory;
    }
    
    public CrashReportCategory getCategory() {
        return this.theReportCategory;
    }
    
    private void populateEnvironment() {
        this.theReportCategory.addCrashSectionCallable("Minecraft Version", new Callable(this) {
            private static final String __OBFID = "CL_00001197";
            final CrashReport this$0;
            
            @Override
            public String call() {
                return "1.8.8";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        this.theReportCategory.addCrashSectionCallable("Operating System", new Callable(this) {
            final CrashReport this$0;
            private static final String __OBFID = "CL_00001222";
            
            @Override
            public String call() {
                return "\u43db\u43c7\u439a\u43da\u43d5\u43d9\u43d1" + " (" + "\u43db\u43c7\u439a\u43d5\u43c6\u43d7\u43dc" + ") version " + "\u43db\u43c7\u439a\u43c2\u43d1\u43c6\u43c7\u43dd\u43db\u43da";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        this.theReportCategory.addCrashSectionCallable("CPU", new CrashReport3(this));
        this.theReportCategory.addCrashSectionCallable("Java Version", new Callable(this) {
            final CrashReport this$0;
            private static final String __OBFID = "CL_00001248";
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() {
                return "\ubce3\ubce8\ubcff\ubce8\ubca7\ubcff\ubcec\ubcfb\ubcfa\ubce0\ubce6\ubce7" + ", " + "\ubce3\ubce8\ubcff\ubce8\ubca7\ubcff\ubcec\ubce7\ubced\ubce6\ubcfb";
            }
        });
        this.theReportCategory.addCrashSectionCallable("Java VM Version", new Callable(this) {
            final CrashReport this$0;
            private static final String __OBFID = "CL_00001275";
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() {
                return "\uff54\uff5f\uff48\uff5f\uff10\uff48\uff53\uff10\uff50\uff5f\uff53\uff5b" + " (" + "\uff54\uff5f\uff48\uff5f\uff10\uff48\uff53\uff10\uff57\uff50\uff58\uff51" + "), " + "\uff54\uff5f\uff48\uff5f\uff10\uff48\uff53\uff10\uff48\uff5b\uff50\uff5a\uff51\uff4c";
            }
        });
        this.theReportCategory.addCrashSectionCallable("Memory", new Callable(this) {
            final CrashReport this$0;
            private static final String __OBFID = "CL_00001302";
            
            @Override
            public String call() {
                final Runtime runtime = Runtime.getRuntime();
                final long maxMemory = runtime.maxMemory();
                final long totalMemory = runtime.totalMemory();
                final long freeMemory = runtime.freeMemory();
                return freeMemory + " bytes (" + freeMemory / 1024L / 1024L + " MB) / " + totalMemory + " bytes (" + totalMemory / 1024L / 1024L + " MB) up to " + maxMemory + " bytes (" + maxMemory / 1024L / 1024L + " MB)";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        this.theReportCategory.addCrashSectionCallable("JVM Flags", new Callable(this) {
            final CrashReport this$0;
            private static final String __OBFID = "CL_00001329";
            
            @Override
            public String call() throws Exception {
                final List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
                final StringBuilder sb = new StringBuilder();
                for (final String next : inputArguments) {
                    if (next.startsWith("-X")) {
                        final int n = 0;
                        int n2 = 0;
                        ++n2;
                        if (n > 0) {
                            sb.append(" ");
                        }
                        sb.append((Object)next);
                    }
                }
                return String.format("%d total; %s", 0, sb.toString());
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        this.theReportCategory.addCrashSectionCallable("IntCache", new Callable(this) {
            final CrashReport this$0;
            private static final String __OBFID = "CL_00001355";
            
            @Override
            public Object call() throws Exception {
                return IntCache.getCacheSizes();
            }
        });
        if (Reflector.FMLCommonHandler_enhanceCrashReport.exists()) {
            Reflector.callString(Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]), Reflector.FMLCommonHandler_enhanceCrashReport, this, this.theReportCategory);
        }
    }
    
    public void getSectionsInStringBuilder(final StringBuilder sb) {
        if ((this.stacktrace == null || this.stacktrace.length <= 0) && this.crashReportSections.size() > 0) {
            this.stacktrace = (StackTraceElement[])ArrayUtils.subarray((Object[])this.crashReportSections.get(0).getStackTrace(), 0, 1);
        }
        if (this.stacktrace != null && this.stacktrace.length > 0) {
            sb.append("-- Head --\n");
            sb.append("Stacktrace:\n");
            final StackTraceElement[] stacktrace = this.stacktrace;
            while (0 < stacktrace.length) {
                sb.append("\t").append("at ").append(stacktrace[0].toString());
                sb.append("\n");
                int n = 0;
                ++n;
            }
            sb.append("\n");
        }
        final Iterator<CrashReportCategory> iterator = this.crashReportSections.iterator();
        while (iterator.hasNext()) {
            iterator.next().appendToStringBuilder(sb);
            sb.append("\n\n");
        }
        this.theReportCategory.appendToStringBuilder(sb);
    }
    
    public Throwable getCrashCause() {
        return this.cause;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public static CrashReport makeCrashReport(final Throwable t, final String s) {
        CrashReport crashReport;
        if (t instanceof ReportedException) {
            crashReport = ((ReportedException)t).getCrashReport();
        }
        else {
            crashReport = new CrashReport(s, t);
        }
        return crashReport;
    }
    
    public String getCauseStackTraceOrString() {
        Throwable cause = this.cause;
        if (cause.getMessage() == null) {
            if (cause instanceof NullPointerException) {
                cause = new NullPointerException(this.description);
            }
            else if (cause instanceof StackOverflowError) {
                cause = new StackOverflowError(this.description);
            }
            else if (cause instanceof OutOfMemoryError) {
                cause = new OutOfMemoryError(this.description);
            }
            cause.setStackTrace(this.cause.getStackTrace());
        }
        cause.toString();
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        cause.printStackTrace(printWriter);
        final String string = stringWriter.toString();
        IOUtils.closeQuietly((Writer)stringWriter);
        IOUtils.closeQuietly((Writer)printWriter);
        return string;
    }
}
