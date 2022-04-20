package net.minecraft.client.resources;

import java.io.*;
import com.mojang.authlib.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import com.mojang.authlib.minecraft.*;
import java.util.*;
import net.minecraft.util.*;
import java.awt.image.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.cache.*;
import java.util.concurrent.*;

public class SkinManager
{
    private final TextureManager textureManager;
    private final MinecraftSessionService sessionService;
    private static final ExecutorService THREAD_POOL;
    private final File skinCacheDir;
    private final LoadingCache skinCacheLoader;
    
    public Map loadSkinFromCache(final GameProfile gameProfile) {
        return (Map)this.skinCacheLoader.getUnchecked((Object)gameProfile);
    }
    
    static MinecraftSessionService access$000(final SkinManager skinManager) {
        return skinManager.sessionService;
    }
    
    public void loadProfileTextures(final GameProfile gameProfile, final SkinAvailableCallback skinAvailableCallback, final boolean b) {
        SkinManager.THREAD_POOL.submit(new Runnable(this, gameProfile, b, skinAvailableCallback) {
            final SkinAvailableCallback val$skinAvailableCallback;
            final boolean val$requireSecure;
            final GameProfile val$profile;
            final SkinManager this$0;
            
            @Override
            public void run() {
                final HashMap hashMap = Maps.newHashMap();
                hashMap.putAll(SkinManager.access$000(this.this$0).getTextures(this.val$profile, this.val$requireSecure));
                if (hashMap.isEmpty() && this.val$profile.getId().equals(Minecraft.getMinecraft().getSession().getProfile().getId())) {
                    this.val$profile.getProperties().clear();
                    this.val$profile.getProperties().putAll((Multimap)Minecraft.getMinecraft().func_181037_M());
                    hashMap.putAll(SkinManager.access$000(this.this$0).getTextures(this.val$profile, false));
                }
                Minecraft.getMinecraft().addScheduledTask(new Runnable(this, hashMap) {
                    final Map val$map;
                    final SkinManager$3 this$1;
                    
                    @Override
                    public void run() {
                        if (this.val$map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                            this.this$1.this$0.loadSkin(this.val$map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, this.this$1.val$skinAvailableCallback);
                        }
                        if (this.val$map.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                            this.this$1.this$0.loadSkin(this.val$map.get(MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, this.this$1.val$skinAvailableCallback);
                        }
                    }
                });
            }
        });
    }
    
    public ResourceLocation loadSkin(final MinecraftProfileTexture minecraftProfileTexture, final MinecraftProfileTexture.Type type) {
        return this.loadSkin(minecraftProfileTexture, type, null);
    }
    
    public ResourceLocation loadSkin(final MinecraftProfileTexture minecraftProfileTexture, final MinecraftProfileTexture.Type type, final SkinAvailableCallback skinAvailableCallback) {
        final ResourceLocation resourceLocation = new ResourceLocation("skins/" + minecraftProfileTexture.getHash());
        if (this.textureManager.getTexture(resourceLocation) != null) {
            if (skinAvailableCallback != null) {
                skinAvailableCallback.skinAvailable(type, resourceLocation, minecraftProfileTexture);
            }
        }
        else {
            this.textureManager.loadTexture(resourceLocation, new ThreadDownloadImageData(new File(new File(this.skinCacheDir, (minecraftProfileTexture.getHash().length() > 2) ? minecraftProfileTexture.getHash().substring(0, 2) : "xx"), minecraftProfileTexture.getHash()), minecraftProfileTexture.getUrl(), DefaultPlayerSkin.getDefaultSkinLegacy(), new IImageBuffer(this, (type == MinecraftProfileTexture.Type.SKIN) ? new ImageBufferDownload() : null, skinAvailableCallback, type, resourceLocation, minecraftProfileTexture) {
                final SkinAvailableCallback val$skinAvailableCallback;
                final SkinManager this$0;
                final MinecraftProfileTexture.Type val$p_152789_2_;
                final IImageBuffer val$iimagebuffer;
                final ResourceLocation val$resourcelocation;
                final MinecraftProfileTexture val$profileTexture;
                
                @Override
                public void skinAvailable() {
                    if (this.val$iimagebuffer != null) {
                        this.val$iimagebuffer.skinAvailable();
                    }
                    if (this.val$skinAvailableCallback != null) {
                        this.val$skinAvailableCallback.skinAvailable(this.val$p_152789_2_, this.val$resourcelocation, this.val$profileTexture);
                    }
                }
                
                @Override
                public BufferedImage parseUserSkin(BufferedImage userSkin) {
                    if (this.val$iimagebuffer != null) {
                        userSkin = this.val$iimagebuffer.parseUserSkin(userSkin);
                    }
                    return userSkin;
                }
            }));
        }
        return resourceLocation;
    }
    
    public SkinManager(final TextureManager textureManager, final File skinCacheDir, final MinecraftSessionService sessionService) {
        this.textureManager = textureManager;
        this.skinCacheDir = skinCacheDir;
        this.sessionService = sessionService;
        this.skinCacheLoader = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build((CacheLoader)new CacheLoader(this) {
            final SkinManager this$0;
            
            public Map load(final GameProfile gameProfile) throws Exception {
                return Minecraft.getMinecraft().getSessionService().getTextures(gameProfile, false);
            }
            
            public Object load(final Object o) throws Exception {
                return this.load((GameProfile)o);
            }
        });
    }
    
    static {
        THREAD_POOL = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    }
    
    public interface SkinAvailableCallback
    {
        void skinAvailable(final MinecraftProfileTexture.Type p0, final ResourceLocation p1, final MinecraftProfileTexture p2);
    }
}
