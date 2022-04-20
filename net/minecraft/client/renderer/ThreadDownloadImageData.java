package net.minecraft.client.renderer;

import java.awt.image.*;
import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import java.net.*;
import net.minecraft.client.*;
import org.apache.commons.io.*;
import javax.imageio.*;
import net.minecraft.client.resources.*;
import java.io.*;

public class ThreadDownloadImageData extends SimpleTexture
{
    private final File cacheFile;
    private boolean textureUploaded;
    private final String imageUrl;
    private static final String __OBFID = "CL_00001049";
    public Boolean imageFound;
    private BufferedImage bufferedImage;
    private Thread imageThread;
    private static final AtomicInteger threadDownloadCounter;
    private final IImageBuffer imageBuffer;
    private static final Logger logger;
    
    @Override
    public int getGlTextureId() {
        this.checkTextureUploaded();
        return super.getGlTextureId();
    }
    
    static {
        logger = LogManager.getLogger();
        threadDownloadCounter = new AtomicInteger(0);
    }
    
    static Logger access$200() {
        return ThreadDownloadImageData.logger;
    }
    
    private void checkTextureUploaded() {
        if (!this.textureUploaded && this.bufferedImage != null) {
            if (this.textureLocation != null) {
                this.deleteGlTexture();
            }
            TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
            this.textureUploaded = true;
        }
    }
    
    public ThreadDownloadImageData(final File cacheFile, final String imageUrl, final ResourceLocation resourceLocation, final IImageBuffer imageBuffer) {
        super(resourceLocation);
        this.imageFound = null;
        this.cacheFile = cacheFile;
        this.imageUrl = imageUrl;
        this.imageBuffer = imageBuffer;
    }
    
    protected void loadTextureFromServer() {
        (this.imageThread = new Thread(this, "Texture Downloader #" + ThreadDownloadImageData.threadDownloadCounter.incrementAndGet()) {
            private static final String __OBFID = "CL_00001050";
            final ThreadDownloadImageData this$0;
            
            @Override
            public void run() {
                ThreadDownloadImageData.access$200().debug("Downloading http texture from {} to {}", new Object[] { ThreadDownloadImageData.access$000(this.this$0), ThreadDownloadImageData.access$100(this.this$0) });
                final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(ThreadDownloadImageData.access$000(this.this$0)).openConnection(Minecraft.getMinecraft().getProxy());
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(false);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() / 100 != 2) {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    this.this$0.imageFound = (ThreadDownloadImageData.access$400(this.this$0) != null);
                    return;
                }
                BufferedImage bufferedImage;
                if (ThreadDownloadImageData.access$100(this.this$0) != null) {
                    FileUtils.copyInputStreamToFile(httpURLConnection.getInputStream(), ThreadDownloadImageData.access$100(this.this$0));
                    bufferedImage = ImageIO.read(ThreadDownloadImageData.access$100(this.this$0));
                }
                else {
                    bufferedImage = TextureUtil.readBufferedImage(httpURLConnection.getInputStream());
                }
                if (ThreadDownloadImageData.access$300(this.this$0) != null) {
                    bufferedImage = ThreadDownloadImageData.access$300(this.this$0).parseUserSkin(bufferedImage);
                }
                this.this$0.setBufferedImage(bufferedImage);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                this.this$0.imageFound = (ThreadDownloadImageData.access$400(this.this$0) != null);
            }
        }).setDaemon(true);
        this.imageThread.start();
    }
    
    public void setBufferedImage(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        if (this.imageBuffer != null) {
            this.imageBuffer.skinAvailable();
        }
        this.imageFound = (this.bufferedImage != null);
    }
    
    static File access$100(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.cacheFile;
    }
    
    static BufferedImage access$400(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.bufferedImage;
    }
    
    static IImageBuffer access$300(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.imageBuffer;
    }
    
    static String access$000(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.imageUrl;
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        if (this.bufferedImage == null && this.textureLocation != null) {
            super.loadTexture(resourceManager);
        }
        if (this.imageThread == null) {
            if (this.cacheFile != null && this.cacheFile.isFile()) {
                ThreadDownloadImageData.logger.debug("Loading http texture from local cache ({})", new Object[] { this.cacheFile });
                this.bufferedImage = ImageIO.read(this.cacheFile);
                if (this.imageBuffer != null) {
                    this.setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
                }
                this.imageFound = (this.bufferedImage != null);
            }
            else {
                this.loadTextureFromServer();
            }
        }
    }
}
