package com.nquantum.util.auth;

import java.awt.*;
import org.apache.commons.codec.digest.*;
import java.awt.datatransfer.*;
import org.apache.http.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class HWIDUtil
{
    protected static String getHWIDForLinux() {
        final ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "hdparm", "-I", "/dev/sd?", "|", "grep", "'Serial\\ Number'" });
        processBuilder.redirectErrorStream(true);
        final Process start = processBuilder.start();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(start.getInputStream()));
        String string = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            string += line;
        }
        start.waitFor();
        bufferedReader.close();
        if (!string.equals("") && string.contains("Serial Number")) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("RAW:" + string + "\nHashed:" + DigestUtils.sha256Hex(string)), null);
            System.out.println("Your RAW HWID is " + string);
            System.out.println("Your LINUX HWID is " + DigestUtils.sha256Hex(string));
            return DigestUtils.sha256Hex(string);
        }
        return null;
    }
    
    protected static OS getOS() {
        final String s = "\u2aa0\u2abc\u2ae1\u2aa1\u2aae\u2aa2\u2aaa";
        if (s.contains("Windows")) {
            return OS.WINDOWS;
        }
        if (s.contains("Linux")) {
            return OS.LINUX;
        }
        return s.contains("Mac") ? OS.MAC : OS.LINUX;
    }
    
    protected static String executePost(final String s, final ArrayList list) {
        final URLConnection openConnection = new URL(s).openConnection();
        openConnection.setDoOutput(true);
        final OutputStream outputStream = openConnection.getOutputStream();
        String string = "";
        for (final NameValuePair nameValuePair : list) {
            string = string + nameValuePair.getName() + "=" + nameValuePair.getValue() + "&";
        }
        string.substring(0, string.length() - 1);
        final PrintStream printStream = new PrintStream(outputStream);
        printStream.print(string);
        printStream.close();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openConnection.getInputStream()));
        String string2 = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            string2 += line;
        }
        bufferedReader.close();
        return string2;
    }
    
    protected static String getHWIDForWindows() {
        final ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "wmic", "DISKDRIVE", "GET", "SerialNumber" });
        processBuilder.redirectErrorStream(true);
        final Process start = processBuilder.start();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(start.getInputStream()));
        String string = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            string += line;
        }
        start.waitFor();
        bufferedReader.close();
        if (!string.equals("") && string.contains("SerialNumber")) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("RAW:" + string + "\nHashed:" + DigestUtils.sha256Hex(string)), null);
            System.out.println(DigestUtils.sha256Hex(string));
            return DigestUtils.sha256Hex(string);
        }
        return null;
    }
    
    public static String getHashedHWID() {
        final OS os = getOS();
        if (os == OS.WINDOWS) {
            return getHWIDForWindows();
        }
        if (os == OS.LINUX) {
            return getHWIDForLinux();
        }
        if (os == OS.MAC) {
            return getHWIDForOSX();
        }
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("Invaild OS: " + "\u2aa0\u2abc\u2ae1\u2aa1\u2aae\u2aa2\u2aaa"), null);
        return null;
    }
    
    protected static String getHWIDForOSX() {
        final ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "system_profiler", "SPSerialATADataType", "|", "grep", "\"Serial\\ Number\"" });
        processBuilder.redirectErrorStream(true);
        final Process start = processBuilder.start();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(start.getInputStream()));
        String string = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            string += line;
        }
        start.waitFor();
        bufferedReader.close();
        if (!string.equals("") && string.contains("Serial Number")) {
            System.out.println("Your MACOX HWID is " + DigestUtils.sha256Hex(string));
            return DigestUtils.sha256Hex(string);
        }
        return null;
    }
}
