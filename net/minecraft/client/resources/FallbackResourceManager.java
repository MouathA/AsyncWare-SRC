package net.minecraft.client.resources;

import net.minecraft.client.resources.data.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.util.*;
import java.io.*;

public class FallbackResourceManager implements IResourceManager
{
    private static final Logger logger;
    private final IMetadataSerializer frmMetadataSerializer;
    protected final List resourcePacks;
    
    @Override
    public IResource getResource(final ResourceLocation resourceLocation) throws IOException {
        IResourcePack resourcePack = null;
        final ResourceLocation locationMcmeta = getLocationMcmeta(resourceLocation);
        for (int i = this.resourcePacks.size() - 1; i >= 0; --i) {
            final IResourcePack resourcePack2 = this.resourcePacks.get(i);
            if (resourcePack == null && resourcePack2.resourceExists(locationMcmeta)) {
                resourcePack = resourcePack2;
            }
            if (resourcePack2.resourceExists(resourceLocation)) {
                InputStream inputStream = null;
                if (resourcePack != null) {
                    inputStream = this.getInputStream(locationMcmeta, resourcePack);
                }
                return new SimpleResource(resourcePack2.getPackName(), resourceLocation, this.getInputStream(resourceLocation, resourcePack2), inputStream, this.frmMetadataSerializer);
            }
        }
        throw new FileNotFoundException(resourceLocation.toString());
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public FallbackResourceManager(final IMetadataSerializer frmMetadataSerializer) {
        this.resourcePacks = Lists.newArrayList();
        this.frmMetadataSerializer = frmMetadataSerializer;
    }
    
    @Override
    public List getAllResources(final ResourceLocation resourceLocation) throws IOException {
        final ArrayList arrayList = Lists.newArrayList();
        final ResourceLocation locationMcmeta = getLocationMcmeta(resourceLocation);
        for (final IResourcePack resourcePack : this.resourcePacks) {
            if (resourcePack.resourceExists(resourceLocation)) {
                arrayList.add(new SimpleResource(resourcePack.getPackName(), resourceLocation, this.getInputStream(resourceLocation, resourcePack), resourcePack.resourceExists(locationMcmeta) ? this.getInputStream(locationMcmeta, resourcePack) : null, this.frmMetadataSerializer));
            }
        }
        if (arrayList.isEmpty()) {
            throw new FileNotFoundException(resourceLocation.toString());
        }
        return arrayList;
    }
    
    public void addResourcePack(final IResourcePack resourcePack) {
        this.resourcePacks.add(resourcePack);
    }
    
    static ResourceLocation getLocationMcmeta(final ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getResourceDomain(), resourceLocation.getResourcePath() + ".mcmeta");
    }
    
    static Logger access$000() {
        return FallbackResourceManager.logger;
    }
    
    protected InputStream getInputStream(final ResourceLocation resourceLocation, final IResourcePack resourcePack) throws IOException {
        final InputStream inputStream = resourcePack.getInputStream(resourceLocation);
        return FallbackResourceManager.logger.isDebugEnabled() ? new InputStreamLeakedResourceLogger(inputStream, resourceLocation, resourcePack.getPackName()) : inputStream;
    }
    
    @Override
    public Set getResourceDomains() {
        return null;
    }
    
    static class InputStreamLeakedResourceLogger extends InputStream
    {
        private final InputStream field_177330_a;
        private boolean field_177329_c;
        private final String field_177328_b;
        
        @Override
        protected void finalize() throws Throwable {
            if (!this.field_177329_c) {
                FallbackResourceManager.access$000().warn(this.field_177328_b);
            }
            super.finalize();
        }
        
        @Override
        public void close() throws IOException {
            this.field_177330_a.close();
            this.field_177329_c = true;
        }
        
        @Override
        public int read() throws IOException {
            return this.field_177330_a.read();
        }
        
        public InputStreamLeakedResourceLogger(final InputStream field_177330_a, final ResourceLocation resourceLocation, final String s) {
            this.field_177329_c = false;
            this.field_177330_a = field_177330_a;
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new Exception().printStackTrace(new PrintStream(byteArrayOutputStream));
            this.field_177328_b = "Leaked resource: '" + resourceLocation + "' loaded from pack: '" + s + "'\n" + byteArrayOutputStream.toString();
        }
    }
}
