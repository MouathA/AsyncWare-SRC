package optfine;

import net.minecraft.crash.*;
import java.util.*;
import net.minecraft.client.settings.*;

public class CrashReporter
{
    private static String makeSummary(final CrashReport crashReport) {
        final Throwable crashCause = crashReport.getCrashCause();
        if (crashCause == null) {
            return "Unknown";
        }
        final StackTraceElement[] stackTrace = crashCause.getStackTrace();
        String trim = "unknown";
        if (stackTrace.length > 0) {
            trim = stackTrace[0].toString().trim();
        }
        return crashCause.getClass().getName() + ": " + crashCause.getMessage() + " (" + crashReport.getDescription() + ") [" + trim + "]";
    }
    
    private static String makeReport(final CrashReport crashReport) {
        final StringBuffer sb = new StringBuffer();
        sb.append("OptiFineVersion: " + Config.getVersion() + "\n");
        sb.append("Summary: " + makeSummary(crashReport) + "\n");
        sb.append("\n");
        sb.append(crashReport.getCompleteReport());
        sb.append("\n");
        sb.append("OpenGlVersion: " + Config.openGlVersion + "\n");
        sb.append("OpenGlRenderer: " + Config.openGlRenderer + "\n");
        sb.append("OpenGlVendor: " + Config.openGlVendor + "\n");
        sb.append("CpuCount: " + Config.getAvailableProcessors() + "\n");
        return sb.toString();
    }
    
    public static void onCrashReport(final CrashReport crashReport) {
        final GameSettings gameSettings = Config.getGameSettings();
        if (gameSettings == null) {
            return;
        }
        if (!gameSettings.snooperEnabled) {
            return;
        }
        final String s = "http://optifine.net/crashReport";
        final byte[] bytes = makeReport(crashReport).getBytes("ASCII");
        final IFileUploadListener fileUploadListener = new IFileUploadListener() {
            @Override
            public void fileUploadFinished(final String s, final byte[] array, final Throwable t) {
            }
        };
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("OF-Version", Config.getVersion());
        hashMap.put("OF-Summary", makeSummary(crashReport));
        final FileUploadThread fileUploadThread = new FileUploadThread(s, hashMap, bytes, fileUploadListener);
        fileUploadThread.setPriority(10);
        fileUploadThread.start();
        Thread.sleep(1000L);
    }
}
