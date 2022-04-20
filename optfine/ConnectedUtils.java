package optfine;

import java.io.*;
import net.minecraft.client.resources.*;
import java.util.zip.*;
import java.util.*;
import net.minecraft.util.*;

public class ConnectedUtils
{
    private static String[] collectFilesFolder(final File file, final String s, final String s2, final String s3) {
        final ArrayList<String> list = new ArrayList<String>();
        final String s4 = "assets/minecraft/";
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return new String[0];
        }
        while (0 < listFiles.length) {
            final File file2 = listFiles[0];
            if (file2.isFile()) {
                final String string = s + file2.getName();
                if (string.startsWith(s4)) {
                    final String substring = string.substring(s4.length());
                    if (substring.startsWith(s2) && substring.endsWith(s3)) {
                        list.add(substring);
                    }
                }
            }
            else if (file2.isDirectory()) {
                final String[] collectFilesFolder = collectFilesFolder(file2, s + file2.getName() + "/", s2, s3);
                while (0 < collectFilesFolder.length) {
                    list.add(collectFilesFolder[0]);
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static String[] collectFiles(final IResourcePack resourcePack, final String s, final String s2, final String[] array) {
        if (resourcePack instanceof DefaultResourcePack) {
            return collectFilesFixed(resourcePack, array);
        }
        if (!(resourcePack instanceof AbstractResourcePack)) {
            return new String[0];
        }
        final File resourcePackFile = ResourceUtils.getResourcePackFile((AbstractResourcePack)resourcePack);
        return (resourcePackFile == null) ? new String[0] : (resourcePackFile.isDirectory() ? collectFilesFolder(resourcePackFile, "", s, s2) : (resourcePackFile.isFile() ? collectFilesZIP(resourcePackFile, s, s2) : new String[0]));
    }
    
    private static String[] collectFilesZIP(final File file, final String s, final String s2) {
        final ArrayList<String> list = new ArrayList<String>();
        final String s3 = "assets/minecraft/";
        final ZipFile zipFile = new ZipFile(file);
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            final String name = ((ZipEntry)entries.nextElement()).getName();
            if (name.startsWith(s3)) {
                final String substring = name.substring(s3.length());
                if (!substring.startsWith(s) || !substring.endsWith(s2)) {
                    continue;
                }
                list.add(substring);
            }
        }
        zipFile.close();
        return list.toArray(new String[list.size()]);
    }
    
    public static int getAverage(final int[] array) {
        if (array.length <= 0) {
            return 0;
        }
        while (0 < array.length) {
            final int n = 0 + array[0];
            int n2 = 0;
            ++n2;
        }
        int n2 = 0 / array.length;
        return 0;
    }
    
    private static String[] collectFilesFixed(final IResourcePack resourcePack, final String[] array) {
        if (array == null) {
            return new String[0];
        }
        final ArrayList<String> list = new ArrayList<String>();
        while (0 < array.length) {
            final String s = array[0];
            if (resourcePack.resourceExists(new ResourceLocation(s))) {
                list.add(s);
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new String[list.size()]);
    }
}
