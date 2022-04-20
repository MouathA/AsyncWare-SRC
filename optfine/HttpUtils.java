package optfine;

import java.net.*;
import net.minecraft.client.*;
import java.util.*;
import java.io.*;

public class HttpUtils
{
    public static final String SERVER_URL = "http://s.optifine.net";
    public static final String POST_URL = "http://optifine.net";
    
    public static String post(final String s, final Map map, final byte[] array) throws IOException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(s).openConnection(Minecraft.getMinecraft().getProxy());
        httpURLConnection.setRequestMethod("POST");
        if (map != null) {
            for (final String next : map.keySet()) {
                httpURLConnection.setRequestProperty(next, "" + map.get(next));
            }
        }
        httpURLConnection.setRequestProperty("Content-Type", "text/plain");
        httpURLConnection.setRequestProperty("Content-Length", "" + array.length);
        httpURLConnection.setRequestProperty("Content-Language", "en-US");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        final OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(array);
        outputStream.flush();
        outputStream.close();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "ASCII"));
        final StringBuffer sb = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
            sb.append('\r');
        }
        bufferedReader.close();
        final String string = sb.toString();
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        return string;
    }
    
    public static byte[] get(final String s) throws IOException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(s).openConnection(Minecraft.getMinecraft().getProxy());
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(false);
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() / 100 != 2) {
            throw new IOException("HTTP response: " + httpURLConnection.getResponseCode());
        }
        final InputStream inputStream = httpURLConnection.getInputStream();
        final byte[] array = new byte[httpURLConnection.getContentLength()];
        while (inputStream.read(array, 0, array.length - 0) >= 0) {
            if (0 >= array.length) {
                final byte[] array2 = array;
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return array2;
            }
        }
        throw new IOException("Input stream closed: " + s);
    }
}
