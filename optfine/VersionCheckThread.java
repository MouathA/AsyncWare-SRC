package optfine;

import java.net.*;
import net.minecraft.client.*;
import java.io.*;

public class VersionCheckThread extends Thread
{
    @Override
    public void run() {
        Config.dbg("Checking for new version");
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("http://optifine.net/version/1.8.8/HD_U.txt").openConnection();
        if (Config.getGameSettings().snooperEnabled) {
            httpURLConnection.setRequestProperty("OF-MC-Version", "1.8.8");
            httpURLConnection.setRequestProperty("OF-MC-Brand", "" + ClientBrandRetriever.getClientModName());
            httpURLConnection.setRequestProperty("OF-Edition", "HD_U");
            httpURLConnection.setRequestProperty("OF-Release", "E1");
            httpURLConnection.setRequestProperty("OF-Java-Version", "" + "\u3fb7\u3fbc\u3fab\u3fbc\u3ff3\u3fab\u3fb8\u3faf\u3fae\u3fb4\u3fb2\u3fb3");
            httpURLConnection.setRequestProperty("OF-CpuCount", "" + Config.getAvailableProcessors());
            httpURLConnection.setRequestProperty("OF-OpenGL-Version", "" + Config.openGlVersion);
            httpURLConnection.setRequestProperty("OF-OpenGL-Vendor", "" + Config.openGlVendor);
        }
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(false);
        httpURLConnection.connect();
        final InputStream inputStream = httpURLConnection.getInputStream();
        final String inputStream2 = Config.readInputStream(inputStream);
        inputStream.close();
        final String[] tokenize = Config.tokenize(inputStream2, "\n\r");
        if (tokenize.length < 1) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return;
        }
        final String newRelease = tokenize[0];
        Config.dbg("Version found: " + newRelease);
        if (Config.compareRelease(newRelease, "E1") <= 0) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return;
        }
        Config.setNewRelease(newRelease);
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }
}
