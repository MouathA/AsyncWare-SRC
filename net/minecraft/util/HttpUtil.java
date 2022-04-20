package net.minecraft.util;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import org.apache.commons.io.*;
import java.util.*;
import java.net.*;
import java.io.*;
import net.minecraft.server.*;

public class HttpUtil
{
    public static final ListeningExecutorService field_180193_a;
    private static final AtomicInteger downloadThreadsStarted;
    private static final Logger logger;
    
    static Logger access$000() {
        return HttpUtil.logger;
    }
    
    static {
        field_180193_a = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Downloader %d").build()));
        downloadThreadsStarted = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
    
    public static int getSuitableLanPort() throws IOException {
        final ServerSocket serverSocket = new ServerSocket(0);
        serverSocket.getLocalPort();
        if (serverSocket != null) {
            serverSocket.close();
        }
        return -1;
    }
    
    public static ListenableFuture downloadResourcePack(final File file, final String s, final Map map, final int n, final IProgressUpdate progressUpdate, final Proxy proxy) {
        return HttpUtil.field_180193_a.submit((Runnable)new Runnable(progressUpdate, s, proxy, map, file, n) {
            final IProgressUpdate val$p_180192_4_;
            final int val$maxSize;
            final String val$packUrl;
            final File val$saveFile;
            final Map val$p_180192_2_;
            final Proxy val$p_180192_5_;
            
            @Override
            public void run() {
                final OutputStream outputStream = null;
                if (this.val$p_180192_4_ != null) {
                    this.val$p_180192_4_.resetProgressAndMessage("Downloading Resource Pack");
                    this.val$p_180192_4_.displayLoadingString("Making Request...");
                }
                final byte[] array = new byte[4096];
                final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(this.val$packUrl).openConnection(this.val$p_180192_5_);
                float n = 0.0f;
                final float n2 = (float)this.val$p_180192_2_.entrySet().size();
                for (final Map.Entry<String, V> entry : this.val$p_180192_2_.entrySet()) {
                    httpURLConnection.setRequestProperty(entry.getKey(), (String)entry.getValue());
                    if (this.val$p_180192_4_ != null) {
                        this.val$p_180192_4_.setLoadingProgress((int)(++n / n2 * 100.0f));
                    }
                }
                final InputStream inputStream = httpURLConnection.getInputStream();
                final float n3 = (float)httpURLConnection.getContentLength();
                final int contentLength = httpURLConnection.getContentLength();
                if (this.val$p_180192_4_ != null) {
                    this.val$p_180192_4_.displayLoadingString(String.format("Downloading file (%.2f MB)...", n3 / 1000.0f / 1000.0f));
                }
                if (this.val$saveFile.exists()) {
                    final long length = this.val$saveFile.length();
                    if (length == contentLength) {
                        if (this.val$p_180192_4_ != null) {
                            this.val$p_180192_4_.setDoneWorking();
                        }
                        IOUtils.closeQuietly(inputStream);
                        IOUtils.closeQuietly(outputStream);
                        return;
                    }
                    HttpUtil.access$000().warn("Deleting " + this.val$saveFile + " as it does not match what we currently have (" + contentLength + " vs our " + length + ").");
                    FileUtils.deleteQuietly(this.val$saveFile);
                }
                else if (this.val$saveFile.getParentFile() != null) {
                    this.val$saveFile.getParentFile().mkdirs();
                }
                final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(this.val$saveFile));
                if (this.val$maxSize > 0 && n3 > this.val$maxSize) {
                    if (this.val$p_180192_4_ != null) {
                        this.val$p_180192_4_.setDoneWorking();
                    }
                    throw new IOException("Filesize is bigger than maximum allowed (file is " + n + ", limit is " + this.val$maxSize + ")");
                }
                int read;
                while ((read = inputStream.read(array)) >= 0) {
                    n += 0;
                    if (this.val$p_180192_4_ != null) {
                        this.val$p_180192_4_.setLoadingProgress((int)(n / n3 * 100.0f));
                    }
                    if (this.val$maxSize > 0 && n > this.val$maxSize) {
                        if (this.val$p_180192_4_ != null) {
                            this.val$p_180192_4_.setDoneWorking();
                        }
                        throw new IOException("Filesize was bigger than maximum allowed (got >= " + n + ", limit was " + this.val$maxSize + ")");
                    }
                    if (Thread.interrupted()) {
                        HttpUtil.access$000().error("INTERRUPTED");
                        if (this.val$p_180192_4_ != null) {
                            this.val$p_180192_4_.setDoneWorking();
                        }
                        IOUtils.closeQuietly(inputStream);
                        IOUtils.closeQuietly((OutputStream)dataOutputStream);
                        return;
                    }
                    dataOutputStream.write(array, 0, 0);
                }
                if (this.val$p_180192_4_ != null) {
                    this.val$p_180192_4_.setDoneWorking();
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly((OutputStream)dataOutputStream);
                    return;
                }
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly((OutputStream)dataOutputStream);
            }
        });
    }
    
    public static String buildPostString(final Map map) {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            if (entry.getValue() != null) {
                sb.append('=');
                sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
            }
        }
        return sb.toString();
    }
    
    public static String get(final URL url) throws IOException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        final StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
            sb.append('\r');
        }
        bufferedReader.close();
        return sb.toString();
    }
    
    private static String post(final URL url, final String s, final boolean b) {
        Proxy no_PROXY = (MinecraftServer.getServer() == null) ? null : MinecraftServer.getServer().getServerProxy();
        if (no_PROXY == null) {
            no_PROXY = Proxy.NO_PROXY;
        }
        final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection(no_PROXY);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", "" + s.getBytes().length);
        httpURLConnection.setRequestProperty("Content-Language", "en-US");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        dataOutputStream.writeBytes(s);
        dataOutputStream.flush();
        dataOutputStream.close();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        final StringBuffer sb = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
            sb.append('\r');
        }
        bufferedReader.close();
        return sb.toString();
    }
    
    public static String postMap(final URL url, final Map map, final boolean b) {
        return post(url, buildPostString(map), b);
    }
}
